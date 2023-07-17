package com.example.default1.utils;

import com.example.default1.base.model.Response;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommonUtil {
    private static final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    private static final MediaType jsonMimeType = MediaType.APPLICATION_JSON;


    public static void responseSuccess(String message, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.success(message), jsonMimeType, new ServletServerHttpResponse(response));
    }


    public static void responseFail(Integer status, String message, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.fail(status, message), jsonMimeType, new ServletServerHttpResponse(response));
    }
}
