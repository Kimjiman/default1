package com.example.default1.module.user.repository;

import com.example.default1.module.user.entity.User;
import com.example.default1.module.user.model.UserSearchParam;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findAllBy(UserSearchParam param);
}
