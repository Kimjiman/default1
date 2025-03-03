package com.example.default1.component;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.default1.component
 * fileName       : SocketComponent
 * author         : KIM JIMAN
 * date           : 25. 3. 3. 월요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 3. 3.     KIM JIMAN      First Commit
 */
@Component
public class SocketComponent {
    public Map<String, Object> socketConnector(Map<String, Object> param, String targetUrl, int targetPort, int timeout) {
        try (Socket socket = new Socket();
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())){

            SocketAddress address = new InetSocketAddress(targetUrl, targetPort);
            socket.connect(address, timeout);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new HashMap<>();
    }
}
