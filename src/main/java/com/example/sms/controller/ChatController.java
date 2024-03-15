package com.example.sms.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.sms.model.ChatMessage;
import com.example.sms.model.Message;
import com.example.sms.service.MessageService;

@Controller
@CrossOrigin
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/chat.register")
    public void register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("Message From: " + chatMessage.getSender());
        System.out.println("Message To: " + chatMessage.getRecipient());
        System.out.println("Message Content: " + chatMessage.getContent());

        Message message = new Message();

        int n1 = Integer.parseInt(chatMessage.getSender());
        int n2 = Integer.parseInt(chatMessage.getRecipient());

        if (n1 > n2) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        String fromUser = "" + n1;
        String toUser = "" + n2;

        message.setUsers(fromUser + ":" + toUser);

        message.setFromUser(fromUser);
        message.setToUser(toUser);
        message.setCreatedOn(new Date());
        message.setMessage(chatMessage.getContent());

        messageService.createMessage(message);

        messagingTemplate.convertAndSendToUser(chatMessage.getRecipient(), "/queue/messages", chatMessage);
    }
}