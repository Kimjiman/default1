package com.example.default1.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Response<T> {
    @Builder.Default
    private int status = 0;
    private String message;
    private T response;

    public static <T> Response<T> fail(int status, String message) {
        return Response.<T>builder()
                .status(status)
                .message(message)
                .build();
    }

    public static <T> Response<T> success(T response) {
        return Response.<T>builder()
                .status(0)
                .response(response)
                .build();
    }
}
