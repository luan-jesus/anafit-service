package luanjesus.tech.anafitservice.application.service;

import lombok.AllArgsConstructor;
import luanjesus.tech.anafitservice.application.service.exception.DuplicatedUserException;
import luanjesus.tech.anafitservice.domain.user.User;
import luanjesus.tech.anafitservice.infrastructure.hibernate.repository.UserRepository;
import luanjesus.tech.anafitservice.presentation.dto.LoginUserDto;
import luanjesus.tech.anafitservice.presentation.dto.RegisterUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public User register(RegisterUserDto input) {
        if (userService.findByEmail(input.getEmail()).isPresent()) {
            throw new DuplicatedUserException();
        }

        User user = User.builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();


        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
