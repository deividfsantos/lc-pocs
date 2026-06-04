package com.dsantos.handler;

import com.dsantos.storage.PadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PadWebSocketHandler extends TextWebSocketHandler {

    private final PadRepository padRepository;
    private final Map<String, List<WebSocketSession>> padSessions = new ConcurrentHashMap<>();

    @Autowired
    public PadWebSocketHandler(PadRepository padRepository) {
        this.padRepository = padRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String padId = extractPadId(session);
        padSessions.computeIfAbsent(padId, k -> new CopyOnWriteArrayList<>()).add(session);
        String content = padRepository.getContent(padId);
        session.sendMessage(new TextMessage(content));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String padId = extractPadId(session);
        String content = message.getPayload();
        padRepository.saveContent(padId, content);
        broadcast(padId, session, content);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String padId = extractPadId(session);
        List<WebSocketSession> sessions = padSessions.get(padId);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                padSessions.remove(padId);
            }
        }
    }

    private void broadcast(String padId, WebSocketSession sender, String content) throws IOException {
        List<WebSocketSession> sessions = padSessions.getOrDefault(padId, List.of());
        for (WebSocketSession s : sessions) {
            if (s.isOpen() && !s.getId().equals(sender.getId())) {
                s.sendMessage(new TextMessage(content));
            }
        }
    }

    private String extractPadId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
