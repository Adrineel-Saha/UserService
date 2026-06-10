package com.cognizant.userservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="User_Id")
    private Long id;

    @Column(name="User_Name")
    private String userName;

    @Column(name="Email")
    private String email;

}
