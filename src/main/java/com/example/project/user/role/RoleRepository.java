
package com.example.project.user.role;
import com.example.project.models.OrginalRoles;
import com.example.project.models.OrginalUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<OrginalRoles,String> {

    Optional<OrginalRoles> findByRoleId(String RoleId);
}