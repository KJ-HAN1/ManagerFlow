package com.example.managerFlow.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //빌더 패턴사용
@Entity(name = "USER") // 엔티티임을 명시

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드를 초기화하는 생성자 추가

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
