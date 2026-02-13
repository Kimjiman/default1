package com.example.default1.base.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ShellExecute {

    private static final long DEFAULT_TIMEOUT_SECONDS = 60;

    public ShellResult execute(List<String> command) {
        return execute(command, DEFAULT_TIMEOUT_SECONDS);
    }

    public ShellResult execute(List<String> command, long timeoutSeconds) {
        log.info("Shell execute: {}", command);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(false);

            Process process = processBuilder.start();

            String stdout = readStream(process, true);
            String stderr = readStream(process, false);

            boolean completed = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!completed) {
                process.destroyForcibly();
                log.error("Shell timeout after {}s: {}", timeoutSeconds, command);
                return ShellResult.builder()
                        .exitCode(-1)
                        .stdout(stdout)
                        .stderr("Process timed out after " + timeoutSeconds + " seconds")
                        .build();
            }

            int exitCode = process.exitValue();

            if (exitCode != 0) {
                log.error("Shell failed (exit={}): {} | stderr: {}", exitCode, command, stderr);
            } else {
                log.info("Shell success: {}", command);
            }

            return ShellResult.builder()
                    .exitCode(exitCode)
                    .stdout(stdout)
                    .stderr(stderr)
                    .build();

        } catch (IOException e) {
            log.error("Shell IO error: {}", command, e);
            return ShellResult.builder()
                    .exitCode(-1)
                    .stdout("")
                    .stderr(e.getMessage())
                    .build();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Shell interrupted: {}", command, e);
            return ShellResult.builder()
                    .exitCode(-1)
                    .stdout("")
                    .stderr(e.getMessage())
                    .build();
        }
    }

    private String readStream(Process process, boolean isStdout) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        isStdout ? process.getInputStream() : process.getErrorStream(),
                        StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
