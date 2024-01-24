package com.example.project.user;

import com.example.project.models.OrginalUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<OrginalUsers,Long> {


    Optional<OrginalUsers> findByuserid(String username);
}
