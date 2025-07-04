package com.example.default1.base.code;

import com.example.default1.base.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code extends BaseModel {
    private Long id;
    private Long codeGroupId;
    private String codeGroup;
    private String code;
    private String codeGroupName;
    private String name;
    private Integer order;
    private String info;
    private LocalDateTime createTime;
    private Long createId;
    private LocalDateTime updateTime;
    private Long updateId;
}
