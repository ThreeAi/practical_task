package com.practical.task.configurations;

import com.practical.task.models.Role;
import com.practical.task.models.User;
import com.practical.task.models.enums.RoleType;
import com.practical.task.repositories.RoleRepository;
import com.practical.task.repositories.UserRepository;
import com.practical.task.services.RoleService;
import com.practical.task.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    @Value("${admin.username}")
    private String username;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUserName("admin").isPresent()) {
            return;
        }
        roleService.createRole(RoleType.ROLE_ADMIN);
        roleService.createRole(RoleType.ROLE_ALBUMS);
        roleService.createRole(RoleType.ROLE_POSTS);
        roleService.createRole(RoleType.ROLE_USERS);
        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userService.createNewUser(user);
    }
}
