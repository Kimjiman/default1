package com.example.basicarch.module.user.repository;

import com.example.basicarch.module.user.entity.User;
import com.example.basicarch.module.user.model.UserSearchParam;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllBy(UserSearchParam param);
}
