package az.atl.msmessaging.controller;

import az.atl.msmessaging.dto.response.DeleteResponse;
import az.atl.msmessaging.service.impl.SuperVisorProfileServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supervisor/profile")
public class SuperVisorProfileController {

    private final SuperVisorProfileServiceImpl service;

    public SuperVisorProfileController(SuperVisorProfileServiceImpl service) {
        this.service = service;
    }

    @DeleteMapping("{username}")
    public ResponseEntity<DeleteResponse> deleteUser(
            @PathVariable(name = "username")String username
    ){
        return ResponseEntity.ok(service.deleteByUsername(username));
    }
}
