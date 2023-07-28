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


    public List<Follow> getAllBloggersFollowing(Blogger blogger) {
        return followRepo.findByCurrentBloggerFollower(blogger);
    }

    public Page<Follow> getAllBloggersFollowings(Blogger blogger, Pageable p) {
        return followRepo.findByCurrentBloggerFollower(blogger,p);
    }

    public boolean isFollowAllowed(Blogger followTargetBlogger, Blogger follower) {
        List<Follow> followList =  followRepo.findByCurrentBloggerAndCurrentBloggerFollower(followTargetBlogger,follower);

        return followList!=null && followList.isEmpty() && !followTargetBlogger.equals(follower);
    }

    public void startFollowing(Blogger followTargetBlogger, Blogger follower) {
        Follow follow  = new Follow(followTargetBlogger,follower);
        followRepo.save(follow);
    }
}
