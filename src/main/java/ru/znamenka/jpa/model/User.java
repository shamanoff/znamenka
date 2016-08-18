package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity(name = "JF_users")
@NamedEntityGraph(name = "user.graph", attributeNodes = @NamedAttributeNode("authorities"))
public class User implements UserDetails {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    @Getter @Setter
    private String username;

    @Column(name = "password", nullable = false)
    @Getter @Setter
    private String password;

    @Column(name = "trainer_id")
    @Getter @Setter
    private Long trainerId;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "JF_user_roles",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    @Getter @Setter
    private List<Role> authorities;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
