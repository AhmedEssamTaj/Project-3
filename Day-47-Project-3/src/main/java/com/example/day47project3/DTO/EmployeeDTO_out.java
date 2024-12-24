package com.example.day47project3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO_out {

    private MyUserDTO_out myUserDTO;
    private String position;
    private Double salary;

}
