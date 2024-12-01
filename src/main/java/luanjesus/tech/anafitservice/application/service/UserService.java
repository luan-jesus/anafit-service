package luanjesus.tech.anafitservice.application.service;

import luanjesus.tech.anafitservice.domain.user.User;
import luanjesus.tech.anafitservice.infrastructure.hibernate.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}