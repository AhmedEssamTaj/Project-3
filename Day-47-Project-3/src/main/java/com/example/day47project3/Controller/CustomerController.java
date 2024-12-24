package com.example.day47project3.Controller;

import com.example.day47project3.Api.ApiResponse;
import com.example.day47project3.DTO.CustomerDTO_in;
import com.example.day47project3.DTO.EmployeeDTO_in;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity registerCustomer (@RequestBody @Valid CustomerDTO_in customerDTOIn){
        customerService.customerRegister(customerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("customer registered successfully"));
    }
    @PutMapping("/update")
    public ResponseEntity updateCustomer (@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid CustomerDTO_in customerDTOIn){
        customerService.updateCustomer(myUser.getId(),customerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("customer updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer (@AuthenticationPrincipal MyUser myUser){
        customerService.deleteCustomer(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("customer deleted successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllCustomer (@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(200).body(customerService.getAllCustomer(myUser.getId()));
    }
}
