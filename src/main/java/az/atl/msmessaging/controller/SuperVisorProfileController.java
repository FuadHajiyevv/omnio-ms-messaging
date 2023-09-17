package az.atl.msmessaging.controller;

import az.atl.msmessaging.dto.response.DeleteResponse;
import az.atl.msmessaging.dto.response.UpdateResponse;
import az.atl.msmessaging.service.impl.AgentProfileServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent/profile")
public class SuperVisorProfileController {

    private final AgentProfileServiceImpl agentProfileService;

    public SuperVisorProfileController(AgentProfileServiceImpl agentProfileService) {
        this.agentProfileService = agentProfileService;
    }

    @PutMapping("/updateUsername/{username}")
    public ResponseEntity<UpdateResponse> updateUsername(
            @PathVariable(name = "username") String username
    ){
        return ResponseEntity.ok(agentProfileService.updateUsername(username));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteResponse> deleteUser(){
        return ResponseEntity.ok(agentProfileService.deleteUser());
    }
}
