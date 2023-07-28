package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.*;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.*;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.IBloggerRepo;
import com.Geekster.Blogging_Platform_Backend_Api.Service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BloggerService {

    @Autowired
    private IBloggerRepo bloggerRepo;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private BlogService blogService;

    @Autowired
    private FollowService followService;

    @Autowired
    private CommentService commentService;
    public SignUpOutput bloggerSignUp(Blogger blogger) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = blogger.getBloggerEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this blogger email already exists ??


        Blogger existingBlogger = bloggerRepo.findFirstByBloggerEmail(newEmail);

        if(existingBlogger != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password


        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(blogger.getBloggerPassword());

            //saveAppointment the blogger with the new encrypted password

            blogger.setBloggerPassword(encryptedPassword);
            blogger.setBloggerAccountCreationDateTime(LocalDateTime.now());
            bloggerRepo.save(blogger);

            return new SignUpOutput(signUpStatus, "blogger registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String bloggerLogIn(SignInInput signInInput) {

        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this blogger email already exists ??
        Blogger existingBlogger = bloggerRepo.findFirstByBloggerEmail(signInEmail);

        if(existingBlogger == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingBlogger.getBloggerPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and blogger id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingBlogger);
                authenticationService.saveAuthToken(authToken);


                return "Session Created with the Token "+authToken.getTokenValue();
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String createBlogPost(BlogInput blogInput, String email) {
        Blogger blogOwner = bloggerRepo.findFirstByBloggerEmail(email);
        blogOwner.setBlogCount(blogOwner.getBlogCount()==null?1:blogOwner.getBlogCount()+1);
        return blogService.createBlogPost(blogInput,blogOwner);

    }

    public String followBlogger(String followingHandle, String followerEmail) {
        Blogger followTargetBlogger = bloggerRepo.findFirstByBloggerHandle(followingHandle);

        Blogger follower = bloggerRepo.findFirstByBloggerEmail(followerEmail);

        if(followTargetBlogger!=null)
        {
            if(followService.isFollowAllowed(followTargetBlogger,follower))
            {
                followService.startFollowing(followTargetBlogger,follower);
                Integer newFollowingCount = (follower.getFollowingCount()==null)?1:follower.getFollowingCount()+1;
                follower.setFollowingCount(newFollowingCount);
                Integer newFollowerCount = (followTargetBlogger.getFollowerCount()==null)?1:followTargetBlogger.getFollowerCount()+1;
                followTargetBlogger.setFollowerCount(newFollowerCount);
                bloggerRepo.save(follower);
                bloggerRepo.save(followTargetBlogger);
                return follower.getBloggerHandle()  + " is now following " + followTargetBlogger.getBloggerHandle();
            }
            else {
                return follower.getBloggerHandle()  + " already follows " + followTargetBlogger.getBloggerHandle();
            }
        }
        else {
            return "User to be followed is Invalid!!!";
        }


    }

    public String blogComment(CommentInput commentInput, String followerEmail) {
        Blog blog = blogService.getBlog(commentInput.getBlogPostId());

        Blogger blogger = bloggerRepo.findFirstByBloggerEmail(followerEmail);

        if(blog!=null)
        {
            return commentService.blogComment(commentInput.getCommentBody(),blog,blogger);
        }
        else
        {
            return"Not A Valid Post";
        }
    }

    public List<BloggerInfo> getAllBloggers(String email,Integer pageNumber,Integer pageSize) {
        Blogger me = bloggerRepo.findFirstByBloggerEmail(email);
         Pageable p = PageRequest.of(pageNumber,pageSize);
         Page<Blogger>bloggerPage = bloggerRepo.findByBloggerIdNot(me.getBloggerId(),p);
        List<Blogger> bloggerList = bloggerPage.getContent();

        List<BloggerInfo> bloggerInfoList = new ArrayList<>();

        for(Blogger blogger : bloggerList)
        {
            BloggerInfo bloggerInfo = new BloggerInfo(blogger.getBloggerHandle(),blogger.getBloggerBio(),
                    blogger.getFollowerCount(),blogger.getFollowingCount(),blogger.getBlogCount());
            bloggerInfoList.add(bloggerInfo);
        }

        return bloggerInfoList;
    }

    public BloggerInfo getBlogger(String bloggerHandle) {
        Blogger blogger =  bloggerRepo.findFirstByBloggerHandle(bloggerHandle);
        if(blogger!=null)
        {

            return new BloggerInfo(blogger.getBloggerHandle(),blogger.getBloggerBio(),
                    blogger.getFollowerCount(),blogger.getFollowingCount(),blogger.getBlogCount());
        }

        throw new IllegalStateException("Blogger does not Exist");
    }

    public List<BlogOutput> getAllBlogs(Integer pageNumber,Integer pageSize) {
        return  blogService.getAllBlogs(pageNumber,pageSize);
    }

    public List<BlogOutput> getAllBloggersBlog(String followerEmail,Integer pageNumber,Integer pageSize) {
        Blogger blogger = bloggerRepo.findFirstByBloggerEmail(followerEmail);
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Follow> pageFollow = followService.getAllBloggersFollowings(blogger,p);
        List<Follow> following = pageFollow.getContent();
        List<Integer>followingIds = new ArrayList<>();
        for(Follow follow : following)
        {
            followingIds.add(follow.getCurrentBlogger().getBloggerId());
        }
        return blogService.getAllBloggersBlog(followingIds);


    }

    public List<BlogOutput> getAllMyBlog(String followerEmail) {
        Blogger blogger = bloggerRepo.findFirstByBloggerEmail(followerEmail);
        return blogService.getAllMyBlogs(blogger);

    }

    public List<CommentOutput> getAllCommentsFromBlog(Integer blogId,Integer pageNumber,Integer pageSize) {
        Blog blog = blogService.getBlog(blogId);
        if(blog==null)
        {
            throw new IllegalStateException("Blog Does Not Exist");
        }
        return commentService.getAllComments(blog,pageNumber,pageSize);

    }

    public String updateBlogWithBlogId(Integer blogId,String followerEmail,String blogUpdateContent) {
        Blog blog = blogService.getBlog(blogId);
        if(blog ==null)
        {
            return "Blog Does not Exist";
        }
        Blogger blogger = bloggerRepo.findFirstByBloggerEmail(followerEmail);

        boolean isBlogOwner = blogService.isBlogOwner(blog,blogger);

        if(isBlogOwner)
        {
            return blogService.updateBlogContent(blog,blogUpdateContent);
        }

        return "Not The Blog Owner!!!";

    }

    public String sigOutBlogger(String email) {
        Blogger blogger= bloggerRepo.findFirstByBloggerEmail(email);
        AuthenticationToken token = authenticationService.findFirstByBlogger(blogger);
        authenticationService.removeToken(token);
        return "Blogger Signed out successfully";
    }
    boolean authorizeCommentRemover(String email,Comment comment)
    {
        String  commentOwnerEmail = comment.getCommenter().getBloggerEmail();
        String  BlogOwnerEmail  = comment.getBlogPost().getBlogOwner().getBloggerEmail();

        return  BlogOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }
    public String removeBlogComment(Integer commentId, String email) {
        Comment comment  = commentService.findComment(commentId);
        if(comment!=null)
        {
            if(authorizeCommentRemover(email,comment))
            {
                commentService.removeComment(comment);
                return "comment deleted successfully";
            }
            else
            {
                return "Unauthorized delete detected...Not allowed!!!!";
            }

        }
        else
        {
            return "Invalid Comment";
        }
    }

    public String removeBlog(Integer blogId, String email) {
       Blog blog =  blogService.getBlog(blogId);
       if(blog != null) {
           String bloggerEmail = blog.getBlogOwner().getBloggerEmail();
           if (bloggerEmail.equals(email)) {
               return blogService.removeBlog(blog);
           }
           else
           {
               return "Unauthorized Delete";
           }
       }
       else
           return "Blog Does Not Exist";
    }

    public String createBlogPosts(List<BlogInput> blogs, String email) {
        Blogger blogOwner = bloggerRepo.findFirstByBloggerEmail(email);

        blogOwner.setBlogCount(blogOwner.getBlogCount()==null?blogs.size():blogOwner.getBlogCount()+blogs.size());
        return blogService.createBlogPosts(blogs,blogOwner);
    }


    public BloggerInfo getMyProfile(String email) {
        Blogger blogger = bloggerRepo.findFirstByBloggerEmail(email);
        return new BloggerInfo(blogger.getBloggerHandle(),blogger.getBloggerBio(),
                blogger.getFollowerCount(),blogger.getFollowingCount(),blogger.getBlogCount());
    }
}
