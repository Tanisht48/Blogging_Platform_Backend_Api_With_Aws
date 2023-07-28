package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.BlogInput;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.BlogOutput;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.IBlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Blog getBlog(Integer blogPostId) {
        return blogRepo.findById(blogPostId).orElse(null);
    }

    public List<BlogOutput> getAllBlogs(Integer pageNumber,Integer pageSize) {
      ;
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Blog> pageBlogs = blogRepo.findAll(p);//Page
        List<Blog> blogs = pageBlogs.getContent();//List

        List<BlogOutput> blogOutputList = new ArrayList<>();
        for(Blog blog: blogs)
        {
            Integer blogId = blog.getBlogId();
            String bloggerHandle = blog.getBlogOwner().getBloggerHandle();
            String blogContentType =blog.getBlogContentType().toString();
            String blogType = blog.getBlogType().toString();
            String blogContent = blog.getBlogContent();
            blogOutputList.add(new BlogOutput(blogId,bloggerHandle,blogContentType,blogType,blogContent));
        }

        return blogOutputList;
    }

    public List<BlogOutput> getAllBloggersBlog(List<Integer> followingIds) {
        List<Blog> blogs =  blogRepo.findAllById(followingIds);

        List<BlogOutput> blogOutputList = new ArrayList<>();
        for(Blog blog: blogs)
        {
            Integer blogId = blog.getBlogId();
            String bloggerHandle = blog.getBlogOwner().getBloggerHandle();
            String blogContentType =blog.getBlogContentType().toString();
            String blogType = blog.getBlogType().toString();
            String blogContent = blog.getBlogContent();
            blogOutputList.add(new BlogOutput(blogId,bloggerHandle,blogContentType,blogType,blogContent));
        }

        return blogOutputList;
    }

    public List<BlogOutput> getAllMyBlogs(Blogger blogger) {
        List<Blog> blogs =  blogRepo.findByBlogOwner(blogger);
        List<BlogOutput> blogOutputList = new ArrayList<>();
        for(Blog blog : blogs)
        {
            Integer blogId = blog.getBlogId();
            String bloggerHandle = blog.getBlogOwner().getBloggerHandle();
            String blogContentType =blog.getBlogContentType().toString();
            String blogType = blog.getBlogType().toString();
            String blogContent = blog.getBlogContent();
            blogOutputList.add(new BlogOutput(blogId,bloggerHandle,blogContentType,blogType,blogContent));
        }

        return  blogOutputList;
    }

    public boolean isBlogOwner(Blog blog, Blogger blogger) {
        return blog.getBlogOwner().equals(blogger);
    }

    public String updateBlogContent(Blog blog, String blogUpdateContent) {
        blog.setBlogContent(blogUpdateContent);
        blogRepo.save(blog);

        return  "Blog Edited!!!";
    }

    public String removeBlog(Blog blog) {
        blogRepo.delete(blog);

        return "Blog removed";
    }

    public String createBlogPosts(List<BlogInput> blogInputs, Blogger blogOwner) {

        List<Blog>blogs = new ArrayList<>();
        for(BlogInput b : blogInputs) {
            Blog blog = new Blog(b.getBlogType(), b.getBlogContentType(), b.getBlogContent(), blogOwner);
            blog.setBlogCreatedTimeStamp(LocalDateTime.now());
            blogs.add(blog);
        }

        blogRepo.saveAll(blogs);

        return "Blogs Created !!!";
    }
}
