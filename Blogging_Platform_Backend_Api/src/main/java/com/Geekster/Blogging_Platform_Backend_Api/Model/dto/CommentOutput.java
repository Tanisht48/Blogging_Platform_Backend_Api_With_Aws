package com.Geekster.Blogging_Platform_Backend_Api.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentOutput {
    private String commenter;
    private String commentBody;


}
