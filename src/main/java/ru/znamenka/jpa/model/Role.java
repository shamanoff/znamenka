package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "JF_roles")
public class Role implements GrantedAuthority, BaseModel<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id", nullable = false, unique = true)
    @Getter @Setter
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    @Getter @Setter
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
