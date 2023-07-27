package com.Geekster.Blogging_Platform_Backend_Api.Repository;

import com.Geekster.Blogging_Platform_Backend_Api.Model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFollowRepo extends JpaRepository<Follow,Integer> {
}
