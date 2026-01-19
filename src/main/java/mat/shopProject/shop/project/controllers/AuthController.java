package mat.shopProject.shop.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mat.shopProject.shop.project.dto.CreateUserRequest;
import mat.shopProject.shop.project.dto.LoginRequest;
import mat.shopProject.shop.project.dto.LoginResponse;
import mat.shopProject.shop.project.dto.UserResponseDto;
import mat.shopProject.shop.project.securityConfig.JWTService;
import mat.shopProject.shop.project.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authManager;

    private final AuthService service;

    private final JWTService jwtService;

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody CreateUserRequest user){
        return ResponseEntity.ok(service.register(user));
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
