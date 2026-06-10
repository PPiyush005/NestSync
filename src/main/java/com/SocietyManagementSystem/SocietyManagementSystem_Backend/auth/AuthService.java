package com.SocietyManagementSystem.SocietyManagementSystem_Backend.auth;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.security.JwtUtil;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.Role;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.User;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // Register
    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username already exists: " + request.getUsername());

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + request.getRole()
                    + ". Valid values: ADMIN, SECRETARY, RESIDENT, SECURITY_GUARD");
        }

        // Validate required fields per role
        if (role == Role.SECRETARY && request.getBlockId() == null)
            throw new RuntimeException("blockId is required for SECRETARY");
        if (role == Role.SECURITY_GUARD && request.getBuildingId() == null)
            throw new RuntimeException("buildingId is required for SECURITY_GUARD");
        if (role == Role.RESIDENT && request.getFlatId() == null)
            throw new RuntimeException("flatId is required for RESIDENT");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setBlockId(request.getBlockId());
        user.setBuildingId(request.getBuildingId());
        user.setFlatId(request.getFlatId());

        userRepository.save(user);
        return "User registered successfully: " + request.getUsername();
    }

    // Login
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    // Get current user info
    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}