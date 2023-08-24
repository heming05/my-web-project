package com.example.springboot_devtools_datasourceregister;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class MyHandler extends TextWebSocketHandler {
    private static ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String taskName = message.getPayload();
        sessions.put(taskName, session);
        System.out.println("Added new session for taskName: " + taskName); // 添加这一行
    }


    public synchronized static void sendLog(String taskName, String log) throws Exception {
        WebSocketSession session = sessions.get(taskName);
        if (session != null && session.isOpen()) {
            System.out.println("Sending log for taskName: " + taskName); // 添加这一行
            session.sendMessage(new TextMessage(log));
        } else {
            System.out.println("No open session for taskName: " + taskName); // 添加这一行
        }
    }

}
