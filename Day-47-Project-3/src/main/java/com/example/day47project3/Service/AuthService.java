package com.example.day47project3.Service;

import com.example.day47project3.DTO.MyUserDTO_out;
import com.example.day47project3.DTO.MyUserDTO_out;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List <MyUserDTO_out> getAll (){
        return converToDTO(authRepository.findAll());
    }

    public List<MyUserDTO_out> converToDTO (List<MyUser> users){
        List<MyUserDTO_out> dtos = new ArrayList<>();
        for (MyUser user : users) {
            dtos.add(new MyUserDTO_out(user.getUsername(), user.getName(),user.getEmail(),user.getRole()));
        }
        return dtos;
    }



}
