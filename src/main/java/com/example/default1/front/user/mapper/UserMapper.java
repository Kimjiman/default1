package com.example.default1.front.user.mapper;

import com.example.default1.front.user.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findById(Long id);
    void insert(User user);
    void update(User user);
    void deleteById(Long id);
}
