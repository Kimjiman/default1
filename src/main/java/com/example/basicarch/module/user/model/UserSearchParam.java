package com.example.basicarch.module.user.model;

import com.example.basicarch.base.model.BaseSearchParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * packageName    : com.example.basicarch.module.user.dto
 * fileName       : UserSearchParam
 * author         : KIM JIMAN
 * date           : 25. 8. 11. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 11.     KIM JIMAN      First Commit
 */
@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchParam extends BaseSearchParam<Long> {
    private String loginId;
    private String name;
}
