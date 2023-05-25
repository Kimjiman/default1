package com.example.default1.utils;

import org.springframework.stereotype.Component;
import java.io.InputStreamReader;
@Component
public class ShellExecute {
    private final Runtime runtime = Runtime.getRuntime();
    private final String[] exec = new String[3];

    public ShellExecute() {
        this.exec[0] = "/bin/bash";
        this.exec[1] = "-c";
    }

    public int shellCmd(String command) throws Exception {
        this.exec[2] = command;
        Process process = runtime.exec(command);
        InputStreamReader in = new InputStreamReader(process.getInputStream(), "MS949");
        int c = 0;
        while ((c = in.read()) != -1) {
            System.out.println((char) c);
        }
        in.close();
        return 0;
    }
}

