package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "roles", schema = "common")
@Getter @Setter
public class Role implements GrantedAuthority, BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @Override
    public String getAuthority() {
        return getRoleName();
    }

    @Override
    public String toString() {
        return getRoleName();
    }
}
