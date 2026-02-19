package com.example.default1.config;

import com.example.default1.base.component.ShellExecute;
import com.example.default1.base.component.ShellResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * local 프로필 시 docker-compose 자동 실행.
 * BeanFactoryPostProcessor로 DataSource 생성 전에 컨테이너를 기동한다.
 */
@Slf4j
@Profile("local")
@Configuration
public class LocalDockerConfig {

    @Bean
    static BeanFactoryPostProcessor dockerComposeStarter() {
        return factory -> {
            log.info("[Docker] local 프로필 — docker-compose up -d 실행");
            ShellResult result = new ShellExecute().execute(List.of("docker-compose", "up", "-d"), 30);
            if (result.isSuccess()) {
                log.info("[Docker] 컨테이너 시작 완료");
            } else {
                log.warn("[Docker] 실행 실패 (이미 실행 중이거나 Docker 미설치): {}", result.getStderr());
            }
        };
    }
}
