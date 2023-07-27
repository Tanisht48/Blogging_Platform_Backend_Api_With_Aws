package com.Geekster.Blogging_Platform_Backend_Api.Model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogOutput {
    private Integer blogId;
    private String bloggerHandle;
    private String blogContentType;
    private String blogContent;
    private String blogType;
}
