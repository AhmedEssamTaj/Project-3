package com.example.day47project3.Api;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }
}
