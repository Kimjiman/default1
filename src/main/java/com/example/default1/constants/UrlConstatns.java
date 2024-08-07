package com.example.default1.constants;

/**
 * packageName    : com.example.default1.constants
 * fileName       : UrlConstatns
 * author         : KIM JIMAN
 * date           : 24. 7. 15. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 24. 7. 15.     KIM JIMAN      First Commit
 */
public class UrlConstatns {
    public static String[] RESOURCE_URLS = {
            "/static/**"
            , "/swagger-ui/**"
            , "/swagger-ui.html"
            , "/swagger-resources/**"
            , "/v3/api-docs/**"
            , "/api-docs/**"
    };

    public static String[] ALLOWED_URLS = {
            "/auth/login"
            , "/auth/token"
    };

    public static String[] NOT_ALLOWED_URLS = {

    };
}
