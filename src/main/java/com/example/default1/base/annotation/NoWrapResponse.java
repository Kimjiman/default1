package com.example.default1.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName    : com.example.default1.annotation
 * fileName       : NoWrapResponse
 * author         : KIM JIMAN
 * date           : 25. 7. 18. 금요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 7. 18.     KIM JIMAN      First Commit
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD,
        ElementType.TYPE
})
public @interface NoWrapResponse {

}
