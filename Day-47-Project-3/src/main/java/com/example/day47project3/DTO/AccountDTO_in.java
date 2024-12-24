package com.example.day47project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDTO_in {

    @NotEmpty(message = "account number cannot be empty")
    @Pattern(regexp = "^52\\d{2}-\\d{4}-\\d{4}-\\d{4}$", message = "account must start with 52 and follow 52XX-XXXX-XXXX-XXXX")
    private String accountNumber;

    @NotNull(message = "balance cannot be null")
    @Positive(message = "balance must be positive")
    private Double balance;


}
