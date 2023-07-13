package com.example.default1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableAspectJAutoProxy
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
