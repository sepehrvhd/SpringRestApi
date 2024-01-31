package com.example.project.services;

import com.example.project.repositories.UserRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    private EntityManager entityManager;

    public boolean hasAdminRole(String userId) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM user_role WHERE user_id = :userId AND role_id = 'ROLE_ADMIN'");
        query.setParameter("userId", userId);
        Integer count = (Integer) query.getSingleResult();
        return count != null && count > 0;

    }
}