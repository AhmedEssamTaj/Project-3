package com.example.day47project3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDTO_out {

    private String customerName;
    private String accountNumber;
    private Double balance;

}
