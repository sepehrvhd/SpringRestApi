package com.example.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PropertyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long NpgId;

    private String npgGroup;

    private String npgName;

    private String npgValue;

    private String npgNode;



}
