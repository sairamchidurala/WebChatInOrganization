package com.example.sms.service;

import org.springframework.stereotype.Service;

import com.example.sms.model.Message;
import com.example.sms.repository.MessageRepository;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public Message createMessage(Message message) {
        message.setCreatedOn(new Date());
        message.setUpdatedOn(new Date());
        return messageRepository.save(message);
    }

    public List<Message> getAllMessageByUser(String fromUser, String toUser) {
        int n1 = Integer.parseInt(fromUser);
        int n2 = Integer.parseInt(toUser);

        if (n1 > n2) {
            int temp = n1;
            n1 = n2;
            n2 = temp;
        }

        return messageRepository.findByUsers(n1 + ":" + n2);
    }

}
