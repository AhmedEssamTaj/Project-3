package com.example.day47project3.Service;

import com.example.day47project3.Api.ApiException;
import com.example.day47project3.DTO.*;
import com.example.day47project3.Model.Account;
import com.example.day47project3.Model.Customer;
import com.example.day47project3.Model.Employee;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Repository.AccountRepository;
import com.example.day47project3.Repository.AuthRepository;
import com.example.day47project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final AccountRepository accountRepository;

    public void customerRegister (CustomerDTO_in customerDTOIn){
        MyUser myUser = new MyUser();
        myUser.setUsername(customerDTOIn.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        myUser.setEmail(customerDTOIn.getEmail());
        myUser.setName(customerDTOIn.getName());
        myUser.setRole("CUSTOMER");
        authRepository.save(myUser);
        // ==================================
        Customer customer = new Customer();
        customer.setMyUser(myUser);
        customer.setPhoneNumber(customerDTOIn.getPhoneNumber());
        customerRepository.save(customer);
    }

    public void updateCustomer (Integer customerId,CustomerDTO_in customerDTOIn){
        Customer customer = customerRepository.findCustomerById(customerId);
        MyUser myUser = authRepository.findMyUserById(customerId);
        if (customer == null || myUser == null) {
            throw new ApiException("customer was not found");
        }
        myUser.setEmail(customerDTOIn.getEmail());
        myUser.setName(customerDTOIn.getName());
        myUser.setUsername(customerDTOIn.getUsername());
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTOIn.getPassword());
        myUser.setPassword(hashPassword);
        authRepository.save(myUser);
        customer.setPhoneNumber(customerDTOIn.getPhoneNumber());
        customerRepository.save(customer);
    }

    public void deleteCustomer (Integer customerId){
        Customer customer = customerRepository.findCustomerById(customerId);
        MyUser myUser = authRepository.findMyUserById(customerId);
        if (myUser == null) {
            throw new ApiException("employee was not found");
        }
        customer.setMyUser(null);
        myUser.setCustomer(null);
        customerRepository.delete(customer);
        authRepository.delete(myUser);

    }

    public List<CustomerDTO_out> getAllCustomer(Integer id){
        MyUser myUser = authRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("user was not found");
        }
        List<Customer> customers = customerRepository.findAll();
        return convertToDTO(customers);
    }

    public List<CustomerDTO_out> convertToDTO(List<Customer> customerList){
        List<CustomerDTO_out> customerDTOList = new ArrayList<>();

        for (Customer customer : customerList) {
            MyUser user = authRepository.findMyUserById(customer.getMyUser().getId());
            customerDTOList.add(new CustomerDTO_out(new MyUserDTO_out(user.getUsername(),user.getName(),user.getEmail(),user.getRole())
                    ,customer.getPhoneNumber(), convertAccountDTO(accountRepository.findAccountByCustomer(customer))));
        }
        return customerDTOList;
    }
    public List<AccountDTO_out> convertAccountDTO(List<Account> accounts){
        List<AccountDTO_out> accountDTOOuts = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOOuts.add(new AccountDTO_out(account.getCustomer().getMyUser().getName(),account.getAccountNumber(),account.getBalance()));
        }
        return accountDTOOuts;
    }
}
