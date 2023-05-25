package com.example.default1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class Default1Application extends SpringBootServletInitializer {
    private static final Class<?> app = Default1Application.class;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        SpringApplication springApplication = new SpringApplication(app);
        springApplication.addListeners(new ApplicationPidFileWriter("app.pid"));
        springApplication.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(app);
    }

}
