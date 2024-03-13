package com.practical.task.repositories;

import com.practical.task.models.Role;
import com.practical.task.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType role);
}
