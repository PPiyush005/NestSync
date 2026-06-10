package com.SocietyManagementSystem.SocietyManagementSystem_Backend.security;

import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.User;
import com.SocietyManagementSystem.SocietyManagementSystem_Backend.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    private final UserRepository userRepository;

    public AuthHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get currently logged-in User entity
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged in user not found"));
    }

    // Get current user's blockId (for SECRETARY)
    public Long getCurrentUserBlockId() {
        Long blockId = getCurrentUser().getBlockId();
        if (blockId == null)
            throw new RuntimeException("No block assigned to current user");
        return blockId;
    }

    // Get current user's buildingId (for SECURITY_GUARD)
    public Long getCurrentUserBuildingId() {
        Long buildingId = getCurrentUser().getBuildingId();
        if (buildingId == null)
            throw new RuntimeException("No building assigned to current user");
        return buildingId;
    }

    // Get current user's flatId (for RESIDENT)
    public Long getCurrentUserFlatId() {
        Long flatId = getCurrentUser().getFlatId();
        if (flatId == null)
            throw new RuntimeException("No flat assigned to current user");
        return flatId;
    }

    // Get current user's role
    public String getCurrentUserRole() {
        return getCurrentUser().getRole().name();
    }
}