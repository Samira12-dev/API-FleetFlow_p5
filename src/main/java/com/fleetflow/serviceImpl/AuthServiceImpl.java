package com.fleetflow.serviceImpl;

import com.fleetflow.dto.LoginRequestDto;
import com.fleetflow.dto.RegisterRequestDto;
import com.fleetflow.entity.Chauffeur;
import com.fleetflow.entity.Role;
import com.fleetflow.entity.User;
import com.fleetflow.repository.ChauffeurRepository;
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
    private final ChauffeurRepository chauffeurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepo userRepository,
                           ChauffeurRepository chauffeurRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.chauffeurRepository = chauffeurRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Map<String, Object> register(RegisterRequestDto request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Role role = request.getRole() != null ? request.getRole() : Role.MANAGER;

        User savedUser;

        if (role == Role.CHAUFFEUR) {

            Chauffeur chauffeur = new Chauffeur();
            chauffeur.setUsername(request.getUsername());
            chauffeur.setEmail(request.getEmail());
            chauffeur.setPassword(passwordEncoder.encode(request.getPassword()));
            chauffeur.setRole(Role.CHAUFFEUR);
            chauffeur.setTelephone("");
            chauffeur.setPermisType("");
            chauffeur.setDisponible(false);

            savedUser = chauffeurRepository.save(chauffeur);

        } else {

            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(role);

            savedUser = userRepository.save(user);
        }

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

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

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