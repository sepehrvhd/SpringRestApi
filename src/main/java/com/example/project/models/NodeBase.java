package com.example.project.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "NodeBase")
public class NodeBase {
    @Id
    private String nbsUuid;

    private String nbsAuthor;
    private LocalDateTime nbsCreated;
    private String nbsName;
    private String nbsPath;

    @Column(columnDefinition = "varchar(1)")
    private String flag;

    @OneToMany(mappedBy = "NodeBase", cascade = CascadeType.ALL)
    private List<PropertyGroup> propertyGroups;


}
