package com.banhang.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fullname", nullable = false, length = 255)
    private String fullname;

    @Column(name = "day_of_birth", length = 255)
    private String dayOfBirth;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "phone", length = 255)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "role")
    private String role;
}
