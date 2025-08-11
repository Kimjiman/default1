package com.example.default1.module.user.mapper;

import com.example.default1.module.user.model.User;
import com.example.default1.module.user.model.UserSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    Long countAllBy(UserSearchParam param);
    List<User> findAllBy(UserSearchParam param);
    User findById(Long id);
    void create(User user);
    void update(User user);
    int removeById(Long id);
}
