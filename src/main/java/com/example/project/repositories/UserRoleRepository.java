package com.example.project.repositories;

import com.example.project.models.OrginalUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<OrginalUsers, String> {


}