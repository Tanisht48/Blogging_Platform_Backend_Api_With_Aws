package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.BlogInput;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.IBlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class BlogService {
    @Autowired
    private IBlogRepo blogRepo;
    public String createBlogPost(BlogInput blogInput , Blogger blogger) {
       Blog blog = new Blog(blogInput.getBlogType(),blogInput.getBlogContentType(),blogInput.getBlogContent(),blogger);
        blog.setBlogCreatedTimeStamp(LocalDateTime.now());
        blogRepo.save(blog);
      return "Blog Created !!!";
    }
}
