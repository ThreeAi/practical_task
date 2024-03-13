package com.practical.task.services;

import com.practical.task.models.enums.RoleType;
import com.practical.task.models.Role;
import com.practical.task.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public void createRole(RoleType role) {
        if (roleRepository.findByName(role) == null)
            roleRepository.save(new Role(role));
    }
}
