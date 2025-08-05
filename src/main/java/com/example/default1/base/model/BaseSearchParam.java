package com.example.default1.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * packageName    : com.example.default1.base.model
 * fileName       : SearchParam
 * author         : KIM JIMAN
 * date           : 25. 8. 5. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 5.     KIM JIMAN      First Commit
 */
@Setter
@Getter
@ToString
public abstract class BaseSearchParam {
    private Long id;
    private String createTimeStart;
    private String createTimeLast;
    private String updateTimeStart;
    private String updateTimeLast;
}


