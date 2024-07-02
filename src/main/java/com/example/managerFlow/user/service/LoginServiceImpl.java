package com.example.managerFlow.user.service;


import com.example.managerFlow.user.domain.User;
import com.example.managerFlow.user.dto.UserDto;
import com.example.managerFlow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private final PasswordEncoder passwordEncoder;
    private  UserRepository userRepository; // UserRepository 의존성 주입

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto login(UserDto userDto) {
        //userEmail 조회과정
        //findByEmail로 repository내 userDTO값 조회
        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
        if (byEmail.isPresent()) {
            //userEmail true
            User user = byEmail.get();

            //password 일치확인 과정
            if (passwordEncoder.matches(userDto.getPassword(),user.getPassword())) {
                //password true
                //entity to dto & return
                return UserDto.toUserDto(user);
            } else {
                //password false
                //null return
                return null;
            }
        } else {
            //userEmail false
            return null;
        }
    }
}
