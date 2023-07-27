package com.Geekster.Blogging_Platform_Backend_Api.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Blogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bloggerId;
    @NotEmpty
    private String bloggerName;
    @NotEmpty
    private String bloggerHandle;
    private String bloggerBio;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @Column(unique = true)
    private String bloggerEmail;
    @NotBlank
    private String bloggerPassword;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime bloggerAccountCreationDateTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer followerCount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer followingCount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer blogCount;








}
