package com.example.default1.base.component;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ShellExecute {
    private final Runtime runtime = Runtime.getRuntime();
    private final String[] exec = new String[3];

    public ShellExecute() {
        this.exec[0] = "/bin/bash";
        this.exec[1] = "-c";
    }

    public int shellCmd(String command) {
        this.exec[2] = command;
        Process process;
        try {
            process = runtime.exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStreamReader in = new InputStreamReader(process.getInputStream(), "MS949")) {
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }
}

