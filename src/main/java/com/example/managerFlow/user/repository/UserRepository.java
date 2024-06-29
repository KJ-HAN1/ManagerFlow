package com.example.managerFlow.user.repository;

<<<<<<< HEAD

import com.example.managerFlow.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 데이터 접근계층 인식
public interface UserRepository extends JpaRepository<User, Long> {
    //email로 회원정보 조회
    Optional<UserEntity> findByUserEmail(String userEmail);
>>>>>>> 4a70e299ff0217de91d0cefd57bf6a0913e7bc44
}
