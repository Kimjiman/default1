package com.example.default1.common.code.model;

import com.example.default1.utils.Pager;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeGroup extends Pager {
    private Long id;
    private String groupCode;
    private String name;
    private LocalDateTime createTime;
    private Long createId;
    private LocalDateTime updateTime;
    private Long updateId;
}
