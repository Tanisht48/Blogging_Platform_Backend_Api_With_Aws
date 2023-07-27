package com.Geekster.Blogging_Platform_Backend_Api.Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BloggerInfo {

    private String bloggerHandle;
    private String bloggerBio;
    private Integer followerCount;
    private Integer followingCount;
    private Integer blogCount;

}
