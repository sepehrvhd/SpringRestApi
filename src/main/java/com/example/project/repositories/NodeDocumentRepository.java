package com.example.project.repositories;

import com.example.project.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeDocumentRepository extends JpaRepository<Document,String> {
}
