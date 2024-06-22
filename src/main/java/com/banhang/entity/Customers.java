package com.banhang.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullname;

    private String phone;

    private String email;

    private String postcode;

    private String city;

    private String district;

    private String ward;

    private String address;

    private String note;
}
