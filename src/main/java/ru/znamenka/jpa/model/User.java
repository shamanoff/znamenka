package ru.znamenka.jpa.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity(name = "JF_users")
@NamedEntityGraph(name = "user.graph",
        attributeNodes = {
                @NamedAttributeNode("authorities"),
                @NamedAttributeNode("trainer")
        })
public class User implements UserDetails {

    private static final long serialVersionUID = -8376436720803866112L;

    @Id
    @Column(name = "username", nullable = false, unique = true)
    @Getter
    @Setter
    private String username;

    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    private String password;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "trainer_id")
    @Getter
    @Setter
    private Trainer trainer;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "JF_user_roles",
            joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    @Getter
    @Setter
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
