package com.Geekster.Blogging_Platform_Backend_Api.Repository;

import com.Geekster.Blogging_Platform_Backend_Api.Model.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByTokenValue(String authTokenValue);
}
