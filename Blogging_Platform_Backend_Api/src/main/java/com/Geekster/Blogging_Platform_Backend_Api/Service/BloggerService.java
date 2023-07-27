package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.AuthenticationToken;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.BlogInput;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.SignInInput;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.SignUpOutput;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.IBloggerRepo;
import com.Geekster.Blogging_Platform_Backend_Api.Service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        blogOwner.setBlogCount(blogOwner.getBlogCount()+1);



        return blogService.createBlogPost(blogInput,blogOwner);

    }

    public String followBlogger(Integer followId, String followerEmail) {
            Blogger followingBlogger = bloggerRepo.findById(followId).orElse(null);
            Blogger followerBlogger = bloggerRepo.findFirstByBloggerEmail(followerEmail);
            if(followingBlogger!=null)
            {
                followingBlogger.setFollowerCount(followerBlogger.getFollowerCount()+1);
                followerBlogger.setFollowingCount(followingBlogger.getFollowingCount()+1);
                bloggerRepo.save(followerBlogger);
                bloggerRepo.save(followingBlogger);
                return followService.followBlogger(followerBlogger,followingBlogger);
            }
            else
            {
                return "Blogger you are trying to follow does not Exist";
            }


    }
}
