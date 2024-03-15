package com.example.sms.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.sms.model.Message;
import com.example.sms.service.MessageService;

@Controller
@RestController
@CrossOrigin
@RequestMapping("/api/messages")
public class MessageController {

	private final MessageService messageService;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/send")
	public ResponseEntity sendMessage(@RequestBody Message message) {
		int n1 = Integer.parseInt(message.getFromUser());
		int n2 = Integer.parseInt(message.getToUser());

		if (n1 > n2) {
			int temp = n1;
			n1 = n2;
			n2 = temp;
		}

		String fromUser = "" + n1;
		String toUser = "" + n2;

		message.setUsers(fromUser + ":" + toUser);
		String userMsg = message.getMessage();
		System.out.println(String.format("users: %s, userMsg: %s", message.getUsers(), userMsg));
		messageService.createMessage(message);

		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/get")
	public ResponseEntity<List<Message>> getchatHistory(@RequestBody Message message) {
		List<Message> messages = messageService.getAllMessageByUser(message.getFromUser(), message.getToUser());

		if (messages.isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
		return ResponseEntity.ok(messages);
	}
}
