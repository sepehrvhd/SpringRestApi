package com.example.project.models;

import com.example.project.Enums.militaryservicestatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.expression.spel.ast.StringLiteral;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "OrginalStudents")
public class OrginalStudents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String firstname;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String lastname;
    private LocalDate dateofbirth;
    private String studentnumber;
    @Enumerated(EnumType.STRING)
    private militaryservicestatus militaryservicestatus;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String province;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String city;
    private String Email;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String address;


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDate dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber;
    }

    public com.example.project.Enums.militaryservicestatus getMilitaryservicestatus() {
        return militaryservicestatus;
    }

    public void setMilitaryservicestatus(com.example.project.Enums.militaryservicestatus militaryservicestatus) {
        this.militaryservicestatus = militaryservicestatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void updateFields(OrginalStudents updatedOrginalStudents) {

        if (updatedOrginalStudents.getFirstname() != null) {
            this.setFirstname(updatedOrginalStudents.getFirstname());
        }

        if (updatedOrginalStudents.getLastname() != null) {
            this.setLastname(updatedOrginalStudents.getLastname());
        }

        if (updatedOrginalStudents.getDateofbirth() != null) {
            this.setDateofbirth(updatedOrginalStudents.getDateofbirth());
        }

        if (updatedOrginalStudents.getStudentnumber() != null) {
            this.setStudentnumber(updatedOrginalStudents.getStudentnumber());
        }

        if (updatedOrginalStudents.getMilitaryservicestatus() != null) {
            this.setMilitaryservicestatus(updatedOrginalStudents.getMilitaryservicestatus());
        }
        if (updatedOrginalStudents.getProvince() != null) {
            this.setProvince(updatedOrginalStudents.getProvince());
        }
        if (updatedOrginalStudents.getCity() != null) {
            this.setCity(updatedOrginalStudents.getCity());
        }
        if (updatedOrginalStudents.getEmail() != null) {
            this.setEmail(updatedOrginalStudents.getEmail());
        }
        if (updatedOrginalStudents.getAddress() != null) {
            this.setAddress(updatedOrginalStudents.getAddress());
        }


    }






}
