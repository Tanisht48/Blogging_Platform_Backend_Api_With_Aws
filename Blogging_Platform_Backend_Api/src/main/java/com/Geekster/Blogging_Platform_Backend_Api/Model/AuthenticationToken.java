package com.Geekster.Blogging_Platform_Backend_Api.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String tokenValue;
    private LocalDateTime tokenCreationDateTime;

    //mapping
    @OneToOne
    @JoinColumn(name = "fk_user_Id")
    Blogger blogger;


    //create a parameterized constructor which takes user as an argument
    public AuthenticationToken(Blogger blogger)
    {
        this.blogger = blogger;
        this.tokenValue = UUID.randomUUID().toString();
        this.tokenCreationDateTime = LocalDateTime.now();
    }
}
