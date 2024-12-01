package luanjesus.tech.anafitservice.application.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import luanjesus.tech.anafitservice.application.service.AuthenticationService;
import luanjesus.tech.anafitservice.domain.user.User;
import luanjesus.tech.anafitservice.infrastructure.service.JwtService;
import luanjesus.tech.anafitservice.presentation.dto.LoginResponseDto;
import luanjesus.tech.anafitservice.presentation.dto.LoginUserDto;
import luanjesus.tech.anafitservice.presentation.dto.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.register(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse = LoginResponseDto.builder()
                .fullName(authenticatedUser.getFullName())
                .email(authenticatedUser.getEmail())
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }
}
