package com.ex.mapper;

import com.ex.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper  {
    List<User> queryAll();
}
