package com.example.default1.base.code;

import com.example.default1.base.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeGroup extends BaseModel {
    private Long id;
    private String codeGroup;
    private String name;
    private List<Code> codeList;
}
