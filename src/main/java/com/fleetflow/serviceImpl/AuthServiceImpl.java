package com.fleetflow.serviceImpl;

import com.fleetflow.dto.LoginRequestDto;
import com.fleetflow.dto.RegisterRequestDto;
import com.fleetflow.entity.Role;
import com.fleetflow.entity.User;
import com.fleetflow.repository.UserRepo;
import com.fleetflow.security.JwtService;
import com.fleetflow.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepo userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Map<String, Object> register(RegisterRequestDto request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.MANAGER);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                savedUser.getEmail(),
                savedUser.getRole().name()
        );

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);
        response.put("type", "Bearer");
        response.put("email", savedUser.getEmail());
        response.put("username", savedUser.getUsername());
        response.put("role", savedUser.getRole());

        return response;
    }
    @Override
    public Map<String, Object> login(LoginRequestDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);
        response.put("type", "Bearer");
        response.put("email", user.getEmail());
        response.put("username", user.getUsername());
        response.put("role", user.getRole());

        return response;
    }
}
