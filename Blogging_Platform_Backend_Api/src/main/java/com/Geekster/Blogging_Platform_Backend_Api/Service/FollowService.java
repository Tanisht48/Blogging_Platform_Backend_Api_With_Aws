package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Follow;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.IFollowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    @Autowired
    private IFollowRepo followRepo;
    public String followBlogger(Blogger followerBlogger, Blogger followingBlogger) {
        Follow follow  = new Follow(followingBlogger,followerBlogger);
        followRepo.save(follow);
       return "You Have Started following "+followingBlogger.getBloggerHandle() ;
    }

    public List<Follow> getAllBloggersFollowing(Blogger blogger) {
        return followRepo.findByCurrentBloggerFollower(blogger);
    }

    public Page<Follow> getAllBloggersFollowings(Integer bloggerId, Pageable p) {
        return followRepo.findByCurrentBloggerFollowerBloggerId(bloggerId,p);
    }
}
