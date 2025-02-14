package com.example.day47project3.Service;


import com.example.day47project3.Api.ApiException;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = authRepository.findMyUserByUsername(username);
        if (myUser == null) {
            throw new ApiException("username or password is incorrect");
        }
        return myUser;
    }
}
