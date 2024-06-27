package com.example.managerFlow.user.service;

import com.example.managerFlow.user.domain.User;
import com.example.managerFlow.user.dto.UserJoinDto;
import com.example.managerFlow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserJoinDto userJoinDto) {


        String encodedPassword = passwordEncoder.encode(userJoinDto.getPassword());

        User user = User.builder().
                userName(userJoinDto.getUserName()).
                email(userJoinDto.getEmail()).
                storeName(userJoinDto.getStoreName()).
                password(encodedPassword).
                build();

        userRepository.save(user);

    }
}
