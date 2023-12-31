package com.Geekster.Blogging_Platform_Backend_Api.Controller;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.*;
import com.Geekster.Blogging_Platform_Backend_Api.Service.AuthenticationService;
import com.Geekster.Blogging_Platform_Backend_Api.Service.BloggerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class BloggerController {

  @Autowired
    private BloggerService bloggerService;
  @Autowired
  private AuthenticationService authenticationService;
    @PostMapping("SignUp")
    public SignUpOutput userSignUp(@RequestBody @Valid Blogger blogger)
    {
        return bloggerService.bloggerSignUp(blogger);
    }

    @PostMapping("SignIn")
    public String userLogIn(@RequestBody @Valid SignInInput signInInput)
    {
        return bloggerService.bloggerLogIn(signInInput);
    }

  @PostMapping("blog")
  public String createInstaPost(@RequestBody @Valid BlogInput blog, @RequestParam String email, @RequestParam String token)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.createBlogPost(blog,email);
    }
    else {
      return "Not an Authenticated user activity!!!";
    }
  }
  @PostMapping("blogs")
  public String createInstaPost(@RequestBody @Valid List<BlogInput> blogs, @RequestParam String email, @RequestParam String token) {
    if (authenticationService.authenticate(email, token)) {
      return bloggerService.createBlogPosts(blogs, email);
    } else {
      return "Not an Authenticated user activity!!!";
    }
  }


    @PostMapping("follow")
  public String followBlogger(@RequestParam String email, @RequestParam String token,@RequestParam String followBlogger)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.followBlogger(followBlogger,email);
    }
    else {
      return "Not an Authenticated user activity!!!";
    }
  }
  @PostMapping("comment/Blog")
  public String blogComment(@RequestBody CommentInput commentInput,@RequestParam String email, @RequestParam String token )
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.blogComment(commentInput,email);
    }
    else
    {
      return "Not an Authenticated user activity!!!";
    }
  }
  @GetMapping("bloggers")
  public List<BloggerInfo> getAllBloggers(@RequestParam String Email, @RequestParam String Token,
                                          @RequestParam(value= "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
                                          @RequestParam(value= "pageSize",defaultValue = "5",required = false)Integer pageSize
                                          ){
     if(authenticationService.authenticate(Email,Token)) {
        return bloggerService.getAllBloggers(Email,pageNumber,pageSize);
    }
   throw new IllegalStateException("Not a valid user");
  }
  @GetMapping("blogger/bloggerHandle")
  public BloggerInfo getBlogger(@RequestParam String email, @RequestParam String token,@RequestParam String bloggerHandle)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.getBlogger(bloggerHandle);
    }
    throw new IllegalStateException("Not a valid user");
  }
  @GetMapping("blogs")
  public List<BlogOutput> getAllBlogs(
          @RequestParam(value= "pageNumber",defaultValue = "1",required = false)Integer PageNumber,
          @RequestParam(value= "pageSize",defaultValue = "5",required = false)Integer PageSize
          ) {return bloggerService.getAllBlogs(PageNumber,PageSize);}
  @GetMapping("/MyProfile")
  public BloggerInfo getMyProfile(@RequestParam String email, @RequestParam String token)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.getMyProfile(email);
    }
    throw new IllegalStateException("Not a valid user");
  }



  // get all my blogs

  @GetMapping("myBlogs")
  public List<BlogOutput> getAllMyBlog(@RequestParam String email,@RequestParam String token)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.getAllMyBlog(email);
    }
    throw new IllegalStateException("Not a valid user");
  }



  //get all comments in a from single post = (pagination if u can)
  @GetMapping("blog/{blogId}/comments")
  public List<CommentOutput> getAllCommentsFromBlog(@RequestParam String email,@RequestParam String token,
                                                    @PathVariable Integer blogId,
                                                    @RequestParam(value= "pageNumber",defaultValue = "1",required = false)
                                                      Integer pageNumber,
                                                    @RequestParam(value= "pageSize",defaultValue = "5",required = false)
                                                      Integer PageSize){
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.getAllCommentsFromBlog(blogId,pageNumber,PageSize);
    }
    throw new IllegalStateException("Not a valid user");
  }




  //Update A Blog Only the blog owner can do it
  @PutMapping("/blog/{blogId}")
  public String updateBlogWithBlogId(@RequestParam String email,@RequestParam String token,
                                     @PathVariable Integer blogId,@RequestParam String blogUpdateContent)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.updateBlogWithBlogId(blogId,email,blogUpdateContent);
    }
    else
      return "Not a Valid user";
  }


  //Delete Session signOutUser
  @DeleteMapping("blogger/signOut")
  public String sigOutBlogger(String email, String token)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.sigOutBlogger(email);
    }
    else {
      return "Sign out not allowed for non authenticated Blogger.";
    }
  }



  //Delete a Comment the BlogOwner and commentOwner both
    @DeleteMapping("comment")
    public String removeBlogComment(@RequestParam Integer commentId, @RequestParam String email, @RequestParam String token)
    {
      if(authenticationService.authenticate(email,token)) {
        return bloggerService.removeBlogComment(commentId,email);
      }
      else {
        return "Not an Authenticated user activity!!!";
      }
    }


    //Delete a Blog only a user can delete it

  @DeleteMapping("Blog/{blogId}")
  public String removeBlog( @RequestParam String email, @RequestParam String token,@PathVariable Integer blogId)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.removeBlog(blogId,email);
    }
    else {
      return "Not an Authenticated user activity!!!";
    }
  }


}


