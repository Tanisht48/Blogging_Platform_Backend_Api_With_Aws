package com.Geekster.Blogging_Platform_Backend_Api.Model;


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
@Table(name = "PostComment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(nullable = false)
    private String commentBody;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hide this in json but not in database table column
    private LocalDateTime commentCreationTimeStamp;


    @ManyToOne
    @JoinColumn(name = "fk_comment_post_id")
    private Blog blogPost;

    @ManyToOne
    @JoinColumn(name = "fk_commenter_id")
    private Blogger commenter;
}
