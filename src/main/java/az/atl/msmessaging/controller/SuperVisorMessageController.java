package az.atl.msmessaging.controller;

import az.atl.msmessaging.dto.response.ActivityReportResponse;
import az.atl.msmessaging.dto.response.ChatListResponse;
import az.atl.msmessaging.dto.response.FriendListResponse;
import az.atl.msmessaging.dto.response.MessageResponse;
import az.atl.msmessaging.service.impl.SuperVisorMessageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supervisor/message")
public class SuperVisorMessageController {

    private final SuperVisorMessageServiceImpl visorMessageService;

    public SuperVisorMessageController(SuperVisorMessageServiceImpl visorMessageService) {
        this.visorMessageService = visorMessageService;
    }

    @GetMapping("/getMessages/{u1}/{u2}")
    public ResponseEntity<List<MessageResponse>> getMessagesByUsername(
            @PathVariable(name = "u1") String u1,
            @PathVariable(name = "u2") String u2
    ) {
        return ResponseEntity.ok(visorMessageService.getMessagesByUsername(u1, u2));
    }

    @GetMapping("/getChats/{username}")
    public ResponseEntity<List<ChatListResponse>> getChatsOfUserByUsername(
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(visorMessageService.getChatsOfUserByUsername(username));
    }

    @GetMapping("/getFriends/{username}")
    public ResponseEntity<List<FriendListResponse>> getFriendsOfUser(
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(visorMessageService.getFriendsOfUser(username));
    }

    @GetMapping("/getActivityReport/{username}")
    public ResponseEntity<ActivityReportResponse> getActivityReport(
            @PathVariable(name = "username") String username
    ){
        return ResponseEntity.ok(visorMessageService.getActivityReport(username));
    }
}
