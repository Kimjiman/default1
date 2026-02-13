package com.example.default1.base.component.webclient;

import com.example.default1.base.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Slf4j
public class DefaultWebRequestClient implements WebRequestClient {

    private final WebClient webClient;
    private final String domainName;

    public DefaultWebRequestClient(WebClient webClient, String domainName) {
        this.webClient = webClient;
        this.domainName = domainName;
    }

    @Override
    public <T> Mono<Response<T>> get(String path, Map<String, String> queryParams) {
        return get(path, queryParams, Collections.emptyMap());
    }

    @Override
    public <T> Mono<Response<T>> get(String path, Map<String, String> queryParams, Map<String, String> headers) {
        return executeRequest(HttpMethod.GET, path, queryParams, null, headers);
    }

    @Override
    public <T> Mono<Response<T>> post(String path, Object body) {
        return post(path, body, Collections.emptyMap());
    }

    @Override
    public <T> Mono<Response<T>> post(String path, Object body, Map<String, String> headers) {
        return executeRequest(HttpMethod.POST, path, null, body, headers);
    }

    @Override
    public <T> Mono<Response<T>> put(String path, Object body) {
        return put(path, body, Collections.emptyMap());
    }

    @Override
    public <T> Mono<Response<T>> put(String path, Object body, Map<String, String> headers) {
        return executeRequest(HttpMethod.PUT, path, null, body, headers);
    }

    @Override
    public <T> Mono<Response<T>> delete(String path, Map<String, String> queryParams) {
        return delete(path, queryParams, Collections.emptyMap());
    }

    @Override
    public <T> Mono<Response<T>> delete(String path, Map<String, String> queryParams, Map<String, String> headers) {
        return executeRequest(HttpMethod.DELETE, path, queryParams, null, headers);
    }

    @SuppressWarnings("unchecked")
    private <T> Mono<Response<T>> executeRequest(HttpMethod method, String path,
                                                  Map<String, String> queryParams,
                                                  Object body,
                                                  Map<String, String> headers) {
        log.info("[{}] {} {}", domainName, method, path);

        WebClient.RequestBodySpec requestSpec = webClient.method(method)
                .uri(uriBuilder -> {
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriBuilder.build().toString() + path);
                    if (queryParams != null) {
                        queryParams.forEach(builder::queryParam);
                    }
                    return builder.build().toUri();
                })
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::set);
                    }
                });

        WebClient.ResponseSpec responseSpec;
        if (body != null && (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH)) {
            responseSpec = requestSpec.bodyValue(body).retrieve();
        } else {
            responseSpec = requestSpec.retrieve();
        }

        return responseSpec
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("[{}] {} {} - status: {}, error: {}",
                                            domainName, method, path, clientResponse.statusCode().value(), errorBody);
                                    return Mono.error(new WebClientRequestException(
                                            clientResponse.statusCode().value(), errorBody));
                                })
                )
                .bodyToMono(String.class)
                .map(responseBody -> (Response<T>) Response.success(responseBody))
                .onErrorResume(WebClientRequestException.class, e ->
                        Mono.just(Response.fail(e.getStatusCode(), e.getMessage()))
                )
                .onErrorResume(Exception.class, e -> {
                    log.error("[{}] {} {} - unexpected error: {}", domainName, method, path, e.getMessage(), e);
                    return Mono.just(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
                });
    }
}
