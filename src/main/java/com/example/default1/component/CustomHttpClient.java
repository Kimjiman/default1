package com.example.default1.component;

import com.example.default1.base.model.Response;
import com.example.default1.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomHttpClient {
    private CloseableHttpClient HTTP_CLIENT;
    private final Map<String, Object> HEADERS = new HashMap<>();

    @PostConstruct
    public void init() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(30000)
                .setSocketTimeout(60000)
                .build();

        HTTP_CLIENT = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build();

        HEADERS.put("Content-Type", "application/json");
        HEADERS.put("Accept", "*/*");
    }

    public Response<?> get(String url, Map<String, String> queryParams) throws IOException, URISyntaxException {
        return get(url, queryParams, HEADERS);
    }

    public Response<?> get(String url, Map<String, String> queryParams, Map<String, Object> headers) throws IOException, URISyntaxException {
        URI uri = buildUri(url, queryParams);
        HttpGet http = new HttpGet(uri);
        return executeRequest(http, headers);
    }

    public Response<?> post(String url, Map<String, Object> bodyParams) throws IOException {
        return post(url, bodyParams, HEADERS);
    }

    public Response<?> post(String url, Map<String, Object> bodyParams, Map<String, Object> headers) throws IOException {
        HttpPost http = new HttpPost(buildUri(url));
        setBody(http, bodyParams);
        return executeRequest(http, headers);
    }

    public Response<?> post(String url, String jsonStr) throws IOException {
        return post(url, jsonStr, HEADERS);
    }

    public Response<?> post(String url, String jsonStr, Map<String, Object> headers) throws IOException {
        HttpPost http = new HttpPost(buildUri(url));
        setJsonBody(http, jsonStr);
        return executeRequest(http, headers);
    }

    public Response<?> put(String url, Map<String, Object> bodyParams) throws IOException {
        return put(url, bodyParams, HEADERS);
    }

    public Response<?> put(String url, Map<String, Object> bodyParams, Map<String, Object> headers) throws IOException {
        HttpPut http = new HttpPut(buildUri(url));
        setBody(http, bodyParams);
        return executeRequest(http, headers);
    }

    public Response<?> put(String url, String jsonStr) throws IOException {
        return put(url, jsonStr, HEADERS);
    }

    public Response<?> put(String url, String jsonStr, Map<String, Object> headers) throws IOException {
        HttpPut http = new HttpPut(buildUri(url));
        setJsonBody(http, jsonStr);
        return executeRequest(http, headers);
    }

    public Response<?> delete(String url, Map<String, String> queryParams) throws IOException, URISyntaxException {
        return delete(url, queryParams, HEADERS);
    }

    public Response<?> delete(String url, Map<String, String> queryParams, Map<String, Object> headers) throws IOException, URISyntaxException {
        URI uri = buildUri(url, queryParams);
        HttpDelete http = new HttpDelete(uri);
        return executeRequest(http, headers);
    }

    private String buildUri(String uri) {
        return uri;
    }

    private URI buildUri(String uri, Map<String, String> queryParams) throws URISyntaxException {
        uri = this.buildUri(uri);
        URIBuilder builder = new URIBuilder(uri);
        if (queryParams != null) queryParams.forEach((key, value) -> builder.addParameter(key, value));
        return builder.build();
    }

    private void setBody(HttpEntityEnclosingRequestBase http, Map<String, Object> bodyParams) {
        if (bodyParams == null) return;
        String jsonStr = JsonUtils.toJson(bodyParams);
        StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        http.setEntity(entity);
    }

    private void setJsonBody(HttpEntityEnclosingRequestBase http, String jsonStr) {
        if (jsonStr == null) return;
        StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
        entity.setContentType("application/json");
        http.setEntity(entity);
    }

    private Response<?> executeRequest(HttpRequestBase http, Map<String, Object> headers) throws IOException {
        if(headers != null) headers.forEach((key, value) -> http.setHeader(key, value.toString()));

        try (CloseableHttpResponse response = HTTP_CLIENT.execute(http)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String resultMsg = EntityUtils.toString(response.getEntity());

            if(statusCode != 200) {
                return Response.fail(statusCode, resultMsg);
            } else {
                return Response.success(resultMsg);
            }
        }
    }
}
