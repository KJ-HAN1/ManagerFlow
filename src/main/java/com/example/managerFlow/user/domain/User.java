package com.example.managerFlow.user.domain;

import jakarta.persistence.*;
import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "USER")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;


    @Column(name = "USER_NAME",
            length = 30,
            nullable = false)
    private String userName;

    @Column(name = "EMAIL",
            length = 50,
            unique = true,
            nullable = false)
    private String email;

    @Column(name = "STORE_NAME",
            length = 30,
            nullable = false)
    private String storeName;

    @Column(name = "PASSWORD",
            nullable = false)
    private String password;


}
