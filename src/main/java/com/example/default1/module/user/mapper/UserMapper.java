package com.example.default1.module.user.mapper;

import com.example.default1.base.mapper.BaseMapper;
import com.example.default1.module.user.dto.UserDTO;
import com.example.default1.module.user.model.User;
import com.example.default1.module.user.model.UserSearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDTO, UserSearchParam, Long> {
    UserDTO findByLoginId(String loginId);
}
