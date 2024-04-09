
package com.example.project.models;

import com.example.project.Enums.IsEbabled;
import com.example.project.Enums.roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.switchuser.SwitchUserGrantedAuthority;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrginalUsers implements UserDetails {


    @Id
    private String userId;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String username;
    private String Password;
    private String EMAIL;

    @Enumerated(EnumType.STRING)
    private IsEbabled isEbabled;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "User_Role",
            joinColumns = @JoinColumn(name = "User_Id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "Role_Id", referencedColumnName = "roleId")
    )
    private Set<OrginalRoles> roles = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (roles != null) {
            // Assuming userRoles is the set of OrginalRoles associated with the user
            for (OrginalRoles role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleId()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.Password;
    }

    @Override
    public String getUsername() {
        return getUserId();
    }

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

    public Collection<String> getAuthorityNames() {
        Set<String> authorityNames = new HashSet<>();

        // Ensure that roles is not null before iterating
        if (roles != null) {
            for (OrginalRoles role : roles) {
                authorityNames.add(role.getRoleId());
            }
        }

        return authorityNames;
    }

}
