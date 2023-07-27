package com.Geekster.Blogging_Platform_Backend_Api.Repository;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Comment;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.CommentOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepo extends JpaRepository<Comment,Integer> {

    List<Comment> findByBlogPost(Blog blog);

    Page<Comment> findByBlogPostBlogId(Integer blogId, Pageable p);
}
