package com.example.shopwatchbackend.repositories;

import com.example.shopwatchbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    boolean existsByRoleName(String roleName);
}
