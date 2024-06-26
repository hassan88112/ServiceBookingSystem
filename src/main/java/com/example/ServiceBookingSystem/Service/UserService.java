package com.example.ServiceBookingSystem.Service;

import com.example.ServiceBookingSystem.Models.Security.UserRole;
import com.example.ServiceBookingSystem.Models.User;


import java.util.Set;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    void save (User user);

    User createUser(User user, Set<UserRole> userRoles);
}
