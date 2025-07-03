//package com.example.default1.utils;
//
//import com.google.common.collect.ImmutableMap;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.hc.core5.net.URIBuilder;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class HttpClientUtils {
//    private CloseableHttpClient HTTP_CLIENT;
//    private final Gson GSON = new Gson();
//    private final Map<String, Object> HEADERS = new HashMap<>();
//
//    @PostConstruct
//    public void init() {
//        RequestConfig config = RequestConfig.custom()
//                .setConnectTimeout(30000)
//                .setSocketTimeout(60000)
//                .build();
//
//        HTTP_CLIENT = HttpClients.custom()
//                .setDefaultRequestConfig(config)
//                .build();
//
//        HEADERS.put("Content-Type", "application/json");
//        HEADERS.put("Accept", "*/*");
//        HEADERS.put("X-Requested-With", "XMLHttpRequest");
//    }
//
//    /**
//     * @Method Name : get
//     * @작성일 : 2025.05.13
//     * @작성자 : jiman
//     * @Method 설명 : HttpMethod Get을 요청한다.
//     * @param url url
//     * @param queryParams 쿼리스트링(map)
//     * @return
//     * @throws
//     */
//    public Map<String, Object> get(String url, Map<String, String> queryParams) throws IOException, URISyntaxException {
//        return get(url, queryParams, HEADERS);
//    }
//
//    public Map<String, Object> get(String url, Map<String, String> queryParams, Map<String, Object> headers) throws IOException, URISyntaxException {
//        URI uri = buildUri(url, queryParams);
//        HttpGet http = new HttpGet(uri);
//        return executeRequest(http, headers);
//    }
//
//    /**
//     * @Method Name : post
//     * @작성일 : 2025.05.13
//     * @작성자 : jiman
//     * @Method 설명 : HttpMethod Post을 요청한다.
//     * @param url url
//     * @param bodyParams Body에 담기는 데이터
//     * @return
//     * @throws
//     */
//    public Map<String, Object> post(String url, Map<String, Object> bodyParams) throws IOException {
//        return post(url, bodyParams, HEADERS);
//    }
//
//    public Map<String, Object> post(String url, Map<String, Object> bodyParams, Map<String, Object> headers) throws IOException {
//        HttpPost http = new HttpPost(buildUri(url));
//        setBody(http, bodyParams);
//        return executeRequest(http, headers);
//    }
//
//    public Map<String, Object> post(String url, String jsonStr) throws IOException {
//        return post(url, jsonStr, HEADERS);
//    }
//
//    public Map<String, Object> post(String url, String jsonStr, Map<String, Object> headers) throws IOException {
//        HttpPost http = new HttpPost(buildUri(url));
//        setJsonBody(http, jsonStr);
//        return executeRequest(http, headers);
//    }
//
//    /**
//     * @Method Name : put
//     * @작성일 : 2025.05.13
//     * @작성자 : jiman
//     * @Method 설명 : HttpMethod Put을 요청한다.
//     * @param url url
//     * @param bodyParams Body에 담기는 데이터
//     * @return
//     * @throws
//     */
//    public Map<String, Object> put(String url, Map<String, Object> bodyParams) throws IOException {
//        return put(url, bodyParams, HEADERS);
//    }
//
//    public Map<String, Object> put(String url, Map<String, Object> bodyParams, Map<String, Object> headers) throws IOException {
//        HttpPut http = new HttpPut(buildUri(url));
//        setBody(http, bodyParams);
//        return executeRequest(http, headers);
//    }
//
//    public Map<String, Object> put(String url, String jsonStr) throws IOException {
//        return put(url, jsonStr, HEADERS);
//    }
//
//    public Map<String, Object> put(String url, String jsonStr, Map<String, Object> headers) throws IOException {
//        HttpPut http = new HttpPut(buildUri(url));
//        setJsonBody(http, jsonStr);
//        return executeRequest(http, headers);
//    }
//
//    /**
//     * @Method Name : delete
//     * @작성일 : 2025.05.13
//     * @작성자 : jiman
//     * @Method 설명 : HttpMethod Delete를 요청한다.
//     * @param url url
//     * @param queryParams 쿼리스트링(map)
//     * @return
//     * @throws
//     */
//    public Map<String, Object> delete(String url, Map<String, String> queryParams) throws IOException, URISyntaxException {
//        return delete(url, queryParams, HEADERS);
//    }
//
//    public Map<String, Object> delete(String url, Map<String, String> queryParams, Map<String, Object> headers) throws IOException, URISyntaxException {
//        URI uri = buildUri(url, queryParams);
//        HttpDelete http = new HttpDelete(uri);
//        return executeRequest(http, headers);
//    }
//
//    private String buildUri(String url) {
//        return url;
//    }
//
//    private URI buildUri(String url, Map<String, String> queryParams) throws URISyntaxException {
//        URIBuilder builder = new URIBuilder(buildUri(url));
//        if (queryParams != null) queryParams.forEach((key, value) -> builder.addParameter(key, value));
//        return builder.build();
//    }
//
//    private void setBody(HttpEntityEnclosingRequestBase http, Map<String, Object> bodyParams) {
//        if (bodyParams == null) return;
//        String jsonStr = GSON.toJson(bodyParams);
//        StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
//        entity.setContentType("application/json");
//        http.setEntity(entity);
//    }
//
//    private void setJsonBody(HttpEntityEnclosingRequestBase http, String jsonStr) {
//        if (jsonStr == null) return;
//        StringEntity entity = new StringEntity(jsonStr, StandardCharsets.UTF_8);
//        entity.setContentType("application/json");
//        http.setEntity(entity);
//    }
//
//    private Map<String, Object> executeRequest(HttpRequestBase http, Map<String, Object> headers) throws IOException {
//        if(headers != null) headers.forEach((key, value) -> http.setHeader(key, value.toString()));
//
//        try (CloseableHttpResponse response = HTTP_CLIENT.execute(http)) {
//            int statusCode = response.getStatusLine().getStatusCode();
//            String resultMsg = EntityUtils.toString(response.getEntity());
//
//            return ImmutableMap.<String, Object>builder()
//                    .put("statusCode", statusCode)
//                    .put("resultMsg", resultMsg)
//                    .build();
//    }
//}
