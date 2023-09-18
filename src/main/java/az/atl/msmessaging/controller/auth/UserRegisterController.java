package az.atl.msmessaging.controller.auth;

import az.atl.msmessaging.dto.request.UserAuthRequest;
import az.atl.msmessaging.dto.response.AuthResponse;
import az.atl.msmessaging.service.impl.AuthServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserRegisterController {

    private final AuthServiceImpl authService;

    public UserRegisterController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/save")
    public ResponseEntity<AuthResponse> saveUser(
            @RequestBody UserAuthRequest request
    ) {
        return ResponseEntity.ok(authService.saveNewRegisterUser(request));
    }


}
