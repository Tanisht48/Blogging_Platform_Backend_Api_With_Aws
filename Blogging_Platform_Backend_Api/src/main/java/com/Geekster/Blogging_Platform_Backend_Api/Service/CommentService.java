package com.Geekster.Blogging_Platform_Backend_Api.Service;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blog;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Comment;
import com.Geekster.Blogging_Platform_Backend_Api.Model.dto.CommentOutput;
import com.Geekster.Blogging_Platform_Backend_Api.Repository.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    private ICommentRepo commentRepo;
    public String blogComment(String commentBody, Blog blog, Blogger blogger) {
        Comment comment = new Comment(commentBody,blog,blogger);

       commentRepo.save(comment);


       return "Commented on "+blog.getBlogOwner().getBloggerHandle()+"'s Blog";
    }

    public List<CommentOutput> getAllComments(Blog blog,Integer pageNumber,Integer pageSize ) {
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Comment> pagedComments =   commentRepo.findByBlogPostBlogId(blog.getBlogId(),p);
        List<Comment>comments = pagedComments.getContent();
        List<CommentOutput> commentOutputList = new ArrayList<>();
        for(Comment comment:comments)
        {
            String commentOwner = comment.getCommenter().getBloggerHandle();
            String commentBody = comment.getCommentBody();
            commentOutputList.add(new CommentOutput(commentOwner,commentBody));
        }

        return  commentOutputList;
    }

    public Comment findComment(Integer commentId) {
        return  commentRepo.findById(commentId).orElse(null);
    }

    public void removeComment(Comment comment) {
        commentRepo.delete(comment);
    }
}
