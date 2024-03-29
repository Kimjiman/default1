package com.example.default1.module.user.model;

import com.example.default1.utils.Pager;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends Pager {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDateTime createTime;
    private Long createId;
}
