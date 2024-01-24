package com.example.project;

import com.example.project.repositories.orginalStudentsRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

	private orginalStudentsRepo orginalStudentsRepo;
	public ProjectApplication(orginalStudentsRepo orginalStudentsRepo){
		this.orginalStudentsRepo=orginalStudentsRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
