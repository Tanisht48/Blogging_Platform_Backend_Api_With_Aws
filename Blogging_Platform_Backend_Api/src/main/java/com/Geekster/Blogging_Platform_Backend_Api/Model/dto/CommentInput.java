package com.Geekster.Blogging_Platform_Backend_Api.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentInput {

    private String commentBody;

    private Integer blogPostId;


}
