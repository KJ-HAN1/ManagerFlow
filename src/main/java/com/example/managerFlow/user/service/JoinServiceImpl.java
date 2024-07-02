package com.example.managerFlow.user.service;

import com.example.managerFlow.user.domain.User;
import com.example.managerFlow.user.dto.UserDto;
import com.example.managerFlow.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinServiceImpl implements JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public JoinServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserDto userDto) {


        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = User.builder().
                userName(userDto.getUserName()).
                email(userDto.getEmail()).
                storeName(userDto.getStoreName()).
                password(encodedPassword).
                build();

        userRepository.save(user);

    }
}
