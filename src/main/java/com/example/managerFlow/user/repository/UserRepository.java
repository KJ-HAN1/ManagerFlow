package com.example.managerFlow.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //email로 회원정보 조회
    Optional<UserEntity> findByUserEmail(String userEmail);

}
