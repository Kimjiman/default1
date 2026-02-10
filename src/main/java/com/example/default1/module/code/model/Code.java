package com.example.default1.module.code.model;

import com.example.default1.base.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Code extends BaseModel<Long> {
    private Long codeGroupId;
    private String codeGroup;
    private String code;
    private String codeGroupName;
    private String name;
    private Integer order;
    private String info;
}
