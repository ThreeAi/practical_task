package com.practical.task.models;

import com.practical.task.models.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType name;

    public Role(RoleType role) {
        this.name = role;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }
}
