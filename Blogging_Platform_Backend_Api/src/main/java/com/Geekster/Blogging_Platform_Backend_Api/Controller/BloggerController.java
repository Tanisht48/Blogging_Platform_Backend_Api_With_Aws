package com.Geekster.Blogging_Platform_Backend_Api.Controller;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.BlogInput;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.SignInInput;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.SignUpOutput;
import com.Geekster.Blogging_Platform_Backend_Api.Service.AuthenticationService;
import com.Geekster.Blogging_Platform_Backend_Api.Service.BloggerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
  public String createInstaPost(@RequestBody BlogInput blog, @RequestParam String email, @RequestParam String token)
  {
    if(authenticationService.authenticate(email,token)) {
      return bloggerService.createBlogPost(blog,email);
    }
    else {
      return "Not an Authenticated user activity!!!";
    }
  }

  @PostMapping("follow")
  public String followBlogger(@RequestParam Integer followId, @RequestParam String followerEmail, @RequestParam String followerToken)
  {
    if(authenticationService.authenticate(followerEmail,followerToken)) {
      return bloggerService.followBlogger(followId,followerEmail);
    }
    else {
      return "Not an Authenticated user activity!!!";
    }
  }

}
