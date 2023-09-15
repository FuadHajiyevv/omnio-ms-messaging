package az.atl.msmessaging.controller;

import az.atl.msmessaging.dto.request.MessageRequest;
import az.atl.msmessaging.dto.response.ChatListResponse;
import az.atl.msmessaging.dto.response.DeliverResponse;
import az.atl.msmessaging.dto.response.MessageResponse;
import az.atl.msmessaging.service.impl.MessageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/messaging")
public class MessageController {

    private final MessageServiceImpl service;

    public MessageController(MessageServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<DeliverResponse> sendMessage(
            @RequestBody MessageRequest message
            ) {
        return ResponseEntity.ok(service.sendMessage(message));
    }

    @GetMapping("/getMessages/{username}")
    public ResponseEntity<List<MessageResponse>> getMessages(
            @PathVariable(name = "username")String username
    ){
        return ResponseEntity.ok(service.getMessages(username));
    }

    @GetMapping("/getChats")
    public ResponseEntity<List<ChatListResponse>> getChats(){
        return ResponseEntity.ok(service.getChatList());
    }
}
