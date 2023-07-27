package com.Geekster.Blogging_Platform_Backend_Api.Repository;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBloggerRepo extends JpaRepository<Blogger,Integer> {
    Blogger findFirstByBloggerEmail(String newEmail);

    Blogger findFirstByBloggerHandle(String followingHandle);


}
