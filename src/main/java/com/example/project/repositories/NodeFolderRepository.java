package com.example.project.repositories;

import com.example.project.models.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFolderRepository extends JpaRepository<Folder,String> {

}
