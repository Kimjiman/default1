package com.example.basicarch.config;

import com.example.basicarch.base.component.ShellExecute;
import com.example.basicarch.base.component.ShellResult;
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

    private record Service(String name, String host, int port) {
    }

    @Bean
    static BeanFactoryPostProcessor dockerComposeStarter() {
        return factory -> {
            String postgresHost = factory.resolveEmbeddedValue("${postgres.host:localhost}");
            int postgresPort = Integer.parseInt(factory.resolveEmbeddedValue("${postgres.port:5432}"));

            String redisHost = factory.resolveEmbeddedValue("${redis.host:localhost}");
            int redisPort = Integer.parseInt(factory.resolveEmbeddedValue("${redis.port:6379}"));

            String prometheusHost = factory.resolveEmbeddedValue("${prometheus.host:localhost}");
            int prometheusPort = Integer.parseInt(factory.resolveEmbeddedValue("${prometheus.port:9090}"));

            String grafanaHost = factory.resolveEmbeddedValue("${grafana.host:localhost}");
            int grafanaPort = Integer.parseInt(factory.resolveEmbeddedValue("${grafana.port:3000}"));

            log.info("[Docker] local 프로필 — docker-compose up -d 실행");
            ShellResult result = new ShellExecute().execute(List.of("docker-compose", "up", "-d"), 300);
            if (!result.isSuccess()) {
                log.warn("[Docker] docker-compose 실행 실패: {}", result.getStderr());
            }

            List<Service> services = List.of(
                    new Service("PostgreSQL", postgresHost, postgresPort),
                    new Service("Redis", redisHost, redisPort),
                    new Service("Prometheus", prometheusHost, prometheusPort),
                    new Service("Grafana", grafanaHost, grafanaPort)
            );

            for (Service service : services) {
                waitForPort(service, 30_000);
            }
        };
    }

    private static void waitForPort(Service service, long timeoutMs) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < deadline) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(service.host(), service.port()), 500);
                log.info("[Docker] {} 준비 완료 ({}:{})", service.name(), service.host(), service.port());
                return;
            } catch (IOException ignored) {
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        log.warn("[Docker] {} 준비 대기 타임아웃 ({}ms)", service.name(), timeoutMs);
    }
}
