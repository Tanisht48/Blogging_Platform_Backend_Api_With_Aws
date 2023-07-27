package com.Geekster.Blogging_Platform_Backend_Api.Model.dto;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Enums.BlogContentType;
import com.Geekster.Blogging_Platform_Backend_Api.Model.Enums.BlogType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogInput {
    private BlogContentType blogContentType;
    private String blogContent;
    private BlogType blogType;
}
