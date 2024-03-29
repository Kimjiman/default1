package com.example.default1.config.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginUser {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDateTime createTime;
    private Long createId;
    private List<String> roleList;
}
