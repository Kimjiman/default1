package com.example.default1.common.code.model;

import com.example.default1.utils.Pager;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeGroup {
    private Long id;
    private String codeGroup;
    private String name;
    private LocalDateTime createTime;
    private Long createId;
    private LocalDateTime updateTime;
    private Long updateId;
    private List<Code> codeList;
}
