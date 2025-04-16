package io.spring.oauth2.jwt.permission;

import io.spring.oauth2.jwt.common.constant.Constant;
import io.spring.oauth2.jwt.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = Constant.Table.PERMISSIONS)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;
}