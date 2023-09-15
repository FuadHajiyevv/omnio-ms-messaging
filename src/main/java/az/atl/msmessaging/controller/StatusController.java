package az.atl.msmessaging.controller;

import az.atl.msmessaging.dto.request.SwitchStatusRequest;
import az.atl.msmessaging.dto.response.SwitchResponse;
import az.atl.msmessaging.service.impl.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final AuthServiceImpl service;

    public StatusController(AuthServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/switch")
    ResponseEntity<SwitchResponse> switchStatus(
            @RequestBody SwitchStatusRequest request
    ){
        return ResponseEntity.ok(service.switchStatus(request));
    }

}
