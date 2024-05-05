package com.example.ServiceBookingSystem.Repository;

import com.example.ServiceBookingSystem.Models.Security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepos extends JpaRepository<Role,Integer> {

    Role findByName(String name);
}
