package com.example.day47project3.Repository;

import com.example.day47project3.Model.Account;
import com.example.day47project3.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountById(Integer id);

    List<Account> findAccountByCustomer(Customer customer);


}
