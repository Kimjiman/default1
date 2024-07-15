package com.example.default1.utils;

import com.example.default1.base.model.Response;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Map;

@Slf4j
public class CommonUtils {
    private static final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    private static final MediaType jsonMimeType = MediaType.APPLICATION_JSON;

    public static <T> void responseSuccess(T obj, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.success(obj), jsonMimeType, new ServletServerHttpResponse(response));
    }

    public static void responseFail(Integer status, String message, HttpServletResponse response) throws IOException {
        jsonConverter.write(Response.fail(status, message), jsonMimeType, new ServletServerHttpResponse(response));
    }

    /**
     * jwtToken을 decode해서 Map으로 변환한다.
     *
     * @param token
     * @return
     */
    public static Map<String, Object> decodeJwtToken(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        return gson.fromJson(new String(decoder.decode(chunks[1])), type);
    }
}
