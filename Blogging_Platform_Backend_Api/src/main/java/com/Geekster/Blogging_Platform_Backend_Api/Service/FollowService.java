package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Follow;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private IFollowRepo followRepo;
    public String followBlogger(Blogger followerBlogger, Blogger followingBlogger) {
        Follow follow  = new Follow(followingBlogger,followerBlogger);
       return "You Have Started following "+followingBlogger.getBloggerHandle() ;
    }
}
