package com.example.project.models;

import com.example.project.Enums.roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrginalUser_roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "ID")
    private OrginalStudents user;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private roles role;



















    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public OrginalStudents getUser() {
        return user;
    }

    public void setUser(OrginalStudents user) {
        this.user = user;
    }

    public roles getRole() {
        return role;
    }

    public void setRole(roles role) {
        this.role = role;
    }
}
