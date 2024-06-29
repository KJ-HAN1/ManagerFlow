package com.example.managerFlow.user.service;

import java.util.Optional;

public class LoginService {

    public UserDTO login(UserDTO userDTO){
            //userEmail 조회과정
            //findByUserEmail로 repository내 userDTO값 조회
        Optional<UserEntity> byUserEmail = userRepository.findByUserEmail(userDTO.getUserEmail());
        if(byUserEmail.isPresent()){
            //userEmail true
            UserEntity userEntity = byUserEmail.get();

            //password 일치확인 과정
            if(userEntity.getUserPassword().equals(UserDTO.getUserPassword())){
                //password true
                //entity to dto & return
                UserDTO dto = userDTO.toUserDTO(userEntity);
                return dto;
            }else {
                //password false
                //null return
                return null;
            }
        }else {
            //userEmail false
            return null;
    }
}
