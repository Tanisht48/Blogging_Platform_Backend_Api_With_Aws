package com.Geekster.Blogging_Platform_Backend_Api.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followId;


    @ManyToOne
    @JoinColumn(name = "fk_actual_user")
    Blogger currentBlogger;

    @ManyToOne
    @JoinColumn(name = "fk_follower_of_actual_user")
    Blogger currentBloggerFollower;

        public Follow(Blogger currentBlogger,Blogger currentBloggerFollower)
        {
            this.currentBlogger = currentBlogger;
            this.currentBloggerFollower = currentBloggerFollower;
        }

}
