package com.example.managerFlow.user.repository;


import com.example.managerFlow.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // 데이터 접근계층 인식
public interface UserRepository extends JpaRepository<User, Long> {
    //email로 회원정보 조회
    Optional<User> findByEmail(String email);
    Optional<User> findByUserNameAndEmail(String email, String userName);

}
