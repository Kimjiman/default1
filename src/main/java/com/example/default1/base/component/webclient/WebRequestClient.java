package com.example.default1.base.component.webclient;

import com.example.default1.base.model.Response;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface WebRequestClient {

    <T> Mono<Response<T>> get(String path, Map<String, String> queryParams);

    <T> Mono<Response<T>> get(String path, Map<String, String> queryParams, Map<String, String> headers);

    <T> Mono<Response<T>> post(String path, Object body);

    <T> Mono<Response<T>> post(String path, Object body, Map<String, String> headers);

    <T> Mono<Response<T>> put(String path, Object body);

    <T> Mono<Response<T>> put(String path, Object body, Map<String, String> headers);

    <T> Mono<Response<T>> delete(String path, Map<String, String> queryParams);

    <T> Mono<Response<T>> delete(String path, Map<String, String> queryParams, Map<String, String> headers);
}
