package com.example.day47project3.Controller;

import com.example.day47project3.Api.ApiResponse;
import com.example.day47project3.DTO.EmployeeDTO_in;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity registerEmployee (@RequestBody @Valid EmployeeDTO_in employeeDTO_in){
        employeeService.employeeRegister(employeeDTO_in);
        return ResponseEntity.status(200).body(new ApiResponse("employee registered successfully"));
    }
    @PutMapping("/update")
    public ResponseEntity updateEmployee (@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid EmployeeDTO_in employeeDTO_in){
        employeeService.updateEmployee(myUser.getId(),employeeDTO_in);
        return ResponseEntity.status(200).body(new ApiResponse("employee updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteEmployee (@AuthenticationPrincipal MyUser myUser){
        employeeService.deleteEmployee(myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("employee deleted successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllEmployee (@AuthenticationPrincipal MyUser myUser){
       return ResponseEntity.status(200).body( employeeService.getAllEmployees(myUser.getId()));
    }
}
