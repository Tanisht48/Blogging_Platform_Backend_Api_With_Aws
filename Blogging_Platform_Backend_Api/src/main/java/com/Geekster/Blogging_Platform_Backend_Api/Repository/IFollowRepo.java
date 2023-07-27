package com.Geekster.Blogging_Platform_Backend_Api.Repository;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFollowRepo extends JpaRepository<Follow,Integer> {
    List<Follow> findByCurrentBloggerFollower(Blogger blogger);

    Page<Follow> findByCurrentBloggerFollowerBloggerId(Integer bloggerId, Pageable p);
}
