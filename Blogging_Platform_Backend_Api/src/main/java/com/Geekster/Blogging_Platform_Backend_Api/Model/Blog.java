package com.Geekster.Blogging_Platform_Backend_Api.Model;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Enums.BlogContentType;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Enums.BlogType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogId;
    @Enumerated(EnumType.STRING)
    private BlogType blogType;
    @Enumerated(EnumType.STRING)
    private BlogContentType blogContentType;
    private String blogContent;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hide this in json but not in database table column
    private LocalDateTime blogCreatedTimeStamp;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "fk_blog_user_id")
    private Blogger blogOwner;

    public Blog( BlogType blogType, BlogContentType blogContentType,String blogContent,Blogger blogOwner)
    {
        this.blogContentType = blogContentType;
        this.blogContent =blogContent;
        this.blogType = blogType;
        this.blogOwner = blogOwner;
    }
}
