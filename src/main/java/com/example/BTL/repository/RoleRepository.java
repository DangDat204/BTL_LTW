package com.example.BTL.repository;

import com.example.BTL.entity.Role;
import com.example.BTL.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleEnum roleName);
}
