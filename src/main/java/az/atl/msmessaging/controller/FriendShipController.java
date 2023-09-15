package az.atl.msmessaging.controller;

import az.atl.msmessaging.dto.request.AcceptFriendShipRequest;
import az.atl.msmessaging.dto.request.FriendShipRequest;
import az.atl.msmessaging.dto.response.AcceptFriendShipResponse;
import az.atl.msmessaging.dto.response.FriendListResponse;
import az.atl.msmessaging.dto.response.FriendShipResponse;
import az.atl.msmessaging.service.impl.FriendShipServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendship")
public class FriendShipController{

    private final FriendShipServiceImpl service;

    public FriendShipController(FriendShipServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<FriendShipResponse> sendFriendShip(
            @RequestBody FriendShipRequest request
    ){
        return ResponseEntity.ok(service.sendFriendshipRequest(request));
    }

    @PostMapping("/accept")
    public ResponseEntity<FriendShipResponse> acceptFriendship(
            @RequestBody AcceptFriendShipRequest request
    ){
        return ResponseEntity.ok(service.acceptFriendshipRequest(request));
    }

    @DeleteMapping("/reject/{username}")
    public ResponseEntity<FriendShipResponse> rejectFriendRequest(
            @PathVariable(name = "username") String username
    ){
        return ResponseEntity.ok(service.rejectFriendshipRequest(username));
    }

    @GetMapping("/friendList")
    public ResponseEntity<List<FriendListResponse>> friendList(){
        return ResponseEntity.ok(service.listFriends());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<AcceptFriendShipResponse>> getPendingUsers(){
        return ResponseEntity.ok(service.friendshipRequests());
    }

    @DeleteMapping("/block/{username}")
    public ResponseEntity<FriendShipResponse> blockUser(
            @PathVariable(name = "username")String username
    ){
        return ResponseEntity.ok(service.blockUser(username));
    }

    @PostMapping("/unblock/{username}")
    public ResponseEntity<FriendShipResponse> unblockUser(
            @PathVariable(name = "username") String username
    ){
        return ResponseEntity.ok(service.unblockUser(username));
    }

}
