package com.practical.task.services;

import com.practical.task.models.Role;
import com.practical.task.models.User;
import com.practical.task.models.enums.RoleType;
import com.practical.task.repositories.RoleRepository;
import com.practical.task.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toSet())
        );
    }

    public void createNewUser(User user, List<RoleType> roles) {
        ArrayList<Role> userRoles = new ArrayList<>();
        roles.forEach(r -> userRoles.add(new Role(r)));
        user.setRoles(userRoles);
        userRepository.save(user);
    }
}
