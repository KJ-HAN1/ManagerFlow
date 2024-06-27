package com.example.managerFlow.user.repository;

import com.example.managerFlow.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 데이터 접근계층 인식
public interface UserRepository extends JpaRepository<User, Long> {
}
