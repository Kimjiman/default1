package com.example.default1.base.code;

import com.example.default1.base.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeGroup extends BaseModel {
    private Long id;
    private String codeGroup;
    private String name;
    private List<Code> codeList;
}
