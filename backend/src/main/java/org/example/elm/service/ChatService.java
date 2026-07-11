package org.example.elm.service;

import org.example.elm.entity.ChatRequest;
import org.example.elm.entity.ChatResponse;

public interface ChatService {
    ChatResponse chat(ChatRequest request);
}