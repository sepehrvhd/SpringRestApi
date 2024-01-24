package com.example.project.repositories;

import com.example.project.models.OrginalStudents;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface orginalStudentsRepo extends CrudRepository<OrginalStudents,Long> {
}
