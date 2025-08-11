package com.example.default1.module.code.dto;

import com.example.default1.base.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * packageName    : com.example.default1.module.code.dto
 * fileName       : CodeDTO
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeDTO extends BaseDTO {
    private Long codeGroupId;
    private String codeGroup;
    private String code;
    private String codeGroupName;
    private String name;
    private Integer order;
    private String info;
}
