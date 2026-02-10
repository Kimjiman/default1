package com.example.default1.module.user.model;

import com.example.default1.base.model.BaseSearchParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * packageName    : com.example.default1.module.user.dto
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
