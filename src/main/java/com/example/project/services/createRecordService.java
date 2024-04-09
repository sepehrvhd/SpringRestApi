package com.example.project.services;

import com.example.project.models.Folder;
import com.example.project.models.NodeBase;
import com.example.project.models.PropertyGroup;
import com.example.project.repositories.nodeBaseRepository;
import com.example.project.repositories.nodePropertyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class createRecordService {

    private final nodePropertyRepository propertyRepository;

    private final nodeBaseRepository baseRepository;

    private final EntityManager entityManager;

    public NodeBase loadFolderByUuid(String uuid) {
        try {
            return entityManager.find(NodeBase.class, uuid);
        } catch (EntityNotFoundException ex) {
            return null;
        }
    }





}
