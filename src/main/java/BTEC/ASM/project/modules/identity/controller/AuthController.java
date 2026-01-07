package BTEC.ASM.project.modules.identity.controller;

import BTEC.ASM.project.modules.identity.dto.request.LoginRequest;
import BTEC.ASM.project.modules.identity.dto.response.LoginResponse;
import BTEC.ASM.project.modules.identity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
