package com.example.default1.component;

import com.example.default1.base.model.Response;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CustomWebClient {
    private WebClient webClient;

    @PostConstruct
    public void init() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(10, TimeUnit.SECONDS)));

        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Accept", "*/*")
                .build();
    }

    public <T> Mono<Response<T>> get(String url, Map<String, String> queryParams) {
        return get(url, queryParams, Collections.emptyMap());
    }

    public <T> Mono<Response<T>> get(String url, Map<String, String> queryParams, Map<String, String> headers) {
        return this.webClient.get()
                .uri(uriBuilder -> {
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
                    if (queryParams != null) {
                        queryParams.forEach(builder::queryParam);
                    }
                    return builder.build().toUri();
                })
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
                )
                .bodyToMono(String.class)
                .map(responseBody -> Response.success((T) responseBody))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()))
                );
    }

    public <T> Mono<Response<T>> post(String url, Map<String, Object> bodyParams) {
        return post(url, bodyParams, Collections.emptyMap());
    }

    public <T> Mono<Response<T>> post(String url, Map<String, Object> bodyParams, Map<String, String> headers) {
        return this.webClient.post()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .bodyValue(bodyParams)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
                )
                .bodyToMono(String.class)
                .map(responseBody -> Response.success((T) responseBody))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()))
                );
    }

    public <T> Mono<Response<T>> put(String url, Map<String, Object> bodyParams) {
        return put(url, bodyParams, Collections.emptyMap());
    }

    public <T> Mono<Response<T>> put(String url, Map<String, Object> bodyParams, Map<String, String> headers) {
        return this.webClient.put()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .bodyValue(bodyParams)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
                )
                .bodyToMono(String.class)
                .map(responseBody -> Response.success((T) responseBody))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()))
                );
    }

    public <T> Mono<Response<T>> delete(String url, Map<String, String> queryParams) {
        return delete(url, queryParams, Collections.emptyMap());
    }

    public <T> Mono<Response<T>> delete(String url, Map<String, String> queryParams, Map<String, String> headers) {
        return this.webClient.delete()
                .uri(uriBuilder -> {
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
                    if (queryParams != null) {
                        queryParams.forEach(builder::queryParam);
                    }
                    return builder.build().toUri();
                })
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(errorBody)))
                )
                .bodyToMono(String.class)
                .map(responseBody -> Response.success((T) responseBody))
                .onErrorResume(RuntimeException.class, e ->
                        Mono.just(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()))
                );
    }
}