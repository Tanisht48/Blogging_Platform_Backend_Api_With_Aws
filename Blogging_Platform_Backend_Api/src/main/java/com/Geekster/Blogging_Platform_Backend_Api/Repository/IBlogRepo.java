package com.Geekster.Blogging_Platform_Backend_Api.Repository;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlogRepo extends JpaRepository<Blog,Integer> {
    List<Blog> findByBlogOwner(Blogger blogger);
}
