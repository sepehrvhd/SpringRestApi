
package com.example.project.models;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "npgNode",referencedColumnName = "nbsUuid")
    private NodeBase NodeBase;



}
