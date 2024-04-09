package com.example.project.repositories;

import com.example.project.models.NodeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface nodeBaseRepository extends JpaRepository<NodeBase, String> {

     NodeBase findByNbsUuid(String nbsUuid);



}
