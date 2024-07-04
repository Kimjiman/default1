package com.example.default1.module.user.model;

import com.example.default1.utils.Pager;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends Pager {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private LocalDateTime createTime;
    private Long createId;
}
