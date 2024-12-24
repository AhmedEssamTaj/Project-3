package com.example.day47project3.Service;

import com.example.day47project3.Api.ApiException;
import com.example.day47project3.DTO.EmployeeDTO_in;
import com.example.day47project3.DTO.EmployeeDTO_out;
import com.example.day47project3.DTO.MyUserDTO_out;
import com.example.day47project3.Model.Employee;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Repository.AuthRepository;
import com.example.day47project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public void employeeRegister (EmployeeDTO_in employeeDTO_in){
        MyUser myUser = new MyUser();
        myUser.setUsername(employeeDTO_in.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO_in.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setEmail(employeeDTO_in.getEmail());
        myUser.setName(employeeDTO_in.getName());
        myUser.setRole("EMPLOYEE");
        authRepository.save(myUser);
        // ==================================
        Employee employee = new Employee();
        employee.setMyUser(myUser);
        employee.setPosition(employeeDTO_in.getPosition());
        employee.setSalary(employeeDTO_in.getSalary());
        employeeRepository.save(employee);
    }

    public void updateEmployee (Integer employeeId,EmployeeDTO_in employeeDTO_in){
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        MyUser myUser = authRepository.findMyUserById(employeeId);
        if (employee == null || myUser == null) {
            throw new ApiException("employee was not found");
        }
        myUser.setEmail(employeeDTO_in.getEmail());
        myUser.setName(employeeDTO_in.getName());
        myUser.setUsername(employeeDTO_in.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO_in.getPassword());
        myUser.setPassword(hashPassword);
        authRepository.save(myUser);
        employee.setPosition(employeeDTO_in.getPosition());
        employee.setSalary(employeeDTO_in.getSalary());
        employeeRepository.save(employee);
    }

    public void deleteEmployee (Integer employeeId){
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        MyUser myUser = authRepository.findMyUserById(employeeId);
        if (myUser == null) {
            throw new ApiException("employee was not found");
        }
        employee.setMyUser(null);
        myUser.setEmployee(null);
        employeeRepository.delete(employee);
        authRepository.delete(myUser);
    }

    public List<EmployeeDTO_out> getAllEmployees(Integer id){
        MyUser myUser = authRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("user was not found");
        }
        return convertToDTO (employeeRepository.findAll());
    }
    public List<EmployeeDTO_out> convertToDTO(List<Employee> employeeList){
        List<EmployeeDTO_out> employeeDTO_out = new ArrayList<>();
        for (Employee employee : employeeList) {
            MyUser myUser = authRepository.findMyUserById(employee.getMyUser().getId());
            employeeDTO_out.add(new EmployeeDTO_out(new MyUserDTO_out(myUser.getUsername(),
                    myUser.getName(),myUser.getEmail(),myUser.getRole()) , employee.getPosition(), employee.getSalary()));
        }
        return employeeDTO_out;
    }
}
