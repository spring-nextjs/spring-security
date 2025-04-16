package io.spring.oauth2.jwt.token;

import io.spring.oauth2.jwt.common.constant.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = Constant.Table.TOKENS)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID jwtId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private boolean valid;

    @Column(nullable = false)
    private TokenType type;
}