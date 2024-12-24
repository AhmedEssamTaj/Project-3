package com.example.day47project3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CustomerDTO_out {
    private  MyUserDTO_out myUserDTO;
    private String phoneNumber;
   private List<AccountDTO_out> accountDTOOuts;
}
