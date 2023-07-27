package com.Geekster.Blogging_Platform_Backend_Api.Repository;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlogRepo extends JpaRepository<Blog,Integer> {
}
