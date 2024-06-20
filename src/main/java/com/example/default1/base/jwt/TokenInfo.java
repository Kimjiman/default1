package com.example.default1.base.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
