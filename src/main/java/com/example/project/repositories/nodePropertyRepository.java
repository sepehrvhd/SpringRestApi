package com.example.project.repositories;

import com.example.project.models.PropertyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nodePropertyRepository extends JpaRepository<PropertyGroup,Long> {



}
