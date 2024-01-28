
package com.example.project.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class OrginalRoles {
    @Id
    private String roleId;

    @ManyToMany
    @JoinTable(name = "User_Role",
    joinColumns = @JoinColumn(name = "Role_Id"),
            inverseJoinColumns = @JoinColumn(name = "User_Id")
    )
    private Set<OrginalUsers> users=new HashSet<>();


}