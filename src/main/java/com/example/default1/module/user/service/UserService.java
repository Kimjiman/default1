package com.example.default1.module.user.service;

import com.example.default1.module.user.mapper.UserMapper;
import com.example.default1.module.user.model.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public User selectUser(Long id) {
        return userMapper.findById(id);
    }

    public void createUser(User user) {
        userMapper.insert(user);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

}
