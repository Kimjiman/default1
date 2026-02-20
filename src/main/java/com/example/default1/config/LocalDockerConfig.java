package com.example.default1.config;

import com.example.default1.base.component.ShellExecute;
import com.example.default1.base.component.ShellResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

@Slf4j
@Profile("local")
@Configuration
public class LocalDockerConfig {

    @Bean
    static BeanFactoryPostProcessor dockerComposeStarter() {
        return factory -> {
            String host = factory.resolveEmbeddedValue("${redis.host:localhost}");
            int port = Integer.parseInt(factory.resolveEmbeddedValue("${redis.port:6379}"));

            if (isRedisStartable(host, port)) {
                log.info("[Docker] Redis 이미 실행 중 — docker-compose 스킵");
                return;
            }
            log.info("[Docker] local 프로필 — docker-compose up -d 실행");
            ShellResult result = new ShellExecute().execute(List.of("docker-compose", "up", "-d"), 30);
            if (!result.isSuccess()) {
                log.warn("[Docker] docker-compose 실행 실패: {}", result.getStderr());
            }
            log.info("[Docker] Redis 준비 대기 중...");
            waitForRedis(host, port, 30_000);
        };
    }

    /**
     * Redis 실행 유무 판단
     * @param host
     * @param port
     * @return
     */
    private static boolean isRedisStartable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 500);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Redis가 시작되는 시간을 기다린다.
     * @param host
     * @param port
     * @param timeoutMs
     */
    private static void waitForRedis(String host, int port, long timeoutMs) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), 500);
                log.info("[Docker] Redis 준비 완료 ({}:{})", host, port);
                return;
            } catch (IOException ignored) {
                //log.info("[Docker] Redis Connect 재시도");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        log.warn("[Docker] Redis 준비 대기 타임아웃 ({}ms). 그냥 진행.", timeoutMs);
    }
}
