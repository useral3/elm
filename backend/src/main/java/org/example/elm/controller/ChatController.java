package org.example.elm.controller;

import org.example.elm.entity.ChatRequest;
import org.example.elm.entity.ChatResponse;
import org.example.elm.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ChatResponse sendMessage(@RequestBody ChatRequest request) {
        System.out.println("收到消息请求: " + request.getMessage());
        return chatService.chat(request);
    }
    }