package com.example.day47project3.Controller;

import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/get-all")
    public ResponseEntity getAllUsers (@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(authService.getAll());
    }

}
