package com.example.day47project3.Service;

import com.example.day47project3.Api.ApiException;
import com.example.day47project3.DTO.AccountDTO_in;
import com.example.day47project3.DTO.AccountDTO_out;
import com.example.day47project3.Model.Account;
import com.example.day47project3.Model.Customer;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Repository.AccountRepository;
import com.example.day47project3.Repository.AuthRepository;
import com.example.day47project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public void createAccount(Integer customerId, AccountDTO_in accountDTOIn) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account account = new Account();
        account.setAccountNumber(accountDTOIn.getAccountNumber());
        account.setBalance(accountDTOIn.getBalance());
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public void activateAccount(Integer id, Integer accountId) {
//        Customer customer = customerRepository.findCustomerById(customerId);
        MyUser myUser = authRepository.findMyUserById(id);
        if (myUser == null) {
            throw new ApiException("user not found");
        }
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }
        if (account.getIsActive()){
            throw new ApiException("account is already active");
        }
            account.setIsActive(true);
            accountRepository.save(account);


    }

    public AccountDTO_out getAccount(Integer customerId, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }
        if(customer == null){
            throw new ApiException("customer not found");
        }
        if (!account.getCustomer().equals(customer)) {
            throw new ApiException("wrong account, try again");
        }
        AccountDTO_out accountDTOOut = new AccountDTO_out(customer.getMyUser().getName()
                ,account.getAccountNumber()
                ,account.getBalance());
        return accountDTOOut;
    }

    public List<AccountDTO_out> getAccounts(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        return convertToDTO(accountRepository.findAccountByCustomer(customer));
    }

    public List<AccountDTO_out> convertToDTO(List<Account> accounts) {
        List<AccountDTO_out> accountDTOOuts = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOOuts.add(new AccountDTO_out(account.getCustomer().getMyUser().getName(), account.getAccountNumber(), account.getBalance()));
        }
        return accountDTOOuts;
    }

    public void deleteAccount(Integer customerId, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }
        if (!account.getCustomer().equals(customer)) {
            throw new ApiException("wrong account, try again");
        }
        account.setCustomer(null);
        accountRepository.delete(account);
    }

    public void deposit (Integer customerId, Integer accountId, Double amount) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }
        if (!account.getCustomer().equals(customer)) {
            throw new ApiException("wrong account, try again");
        }
        if (!account.getIsActive()){
            throw new ApiException("account is not active");
        }
        if (amount <= 0){
            throw new ApiException("amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw (Integer customerId, Integer accountId, Double amount) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("account not found");
        }
        if (!account.getCustomer().equals(customer)) {
            throw new ApiException("wrong account, try again");
        }
        if (!account.getIsActive()){
            throw new ApiException("account is not active");
        }
        if (amount <= 0){
            throw new ApiException("amount must be greater than 0");
        }
        if (amount > account.getBalance()){
            throw new ApiException("amount exceeds balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transfer (Integer customerId, Integer sourceAccountId, Integer targetAccountId ,Double amount) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        Account sourceAccount = accountRepository.findAccountById(sourceAccountId);
        Account targetAccount = accountRepository.findAccountById(targetAccountId);
        if (sourceAccount == null) {
            throw new ApiException("sourceAccount not found");
        }
        if (targetAccount == null) {
            throw new ApiException("targetAccount not found");
        }
        if (!sourceAccount.getCustomer().equals(customer)) {
            throw new ApiException("wrong account, try again");
        }

        if(amount > sourceAccount.getBalance()){
            throw new ApiException("amount exceeds balance");
        }
        if (!sourceAccount.getIsActive()){
            throw new ApiException("your account is not active");
        }
        if (!targetAccount.getIsActive()){
            throw new ApiException("target account is not active");
        }
        if (amount <= 0){
            throw new ApiException("amount must be greater than 0");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
    }

    public void banAccount (Integer id ,Integer accountId) {
      MyUser myUser = authRepository.findMyUserById(id);
      if (myUser == null) {
          throw new ApiException("user not found");
      }
      Account account = accountRepository.findAccountById(accountId);
      if (account == null) {
          throw new ApiException("account not found");
      }
        if (!account.getIsActive()){
            throw new ApiException("account is already not active");
        }
        account.setIsActive(false);
        accountRepository.save(account);
    }

    public List<AccountDTO_out> getAllAccounts (Integer Id) {
        MyUser myUser = authRepository.findMyUserById(Id);
        if (myUser == null) {
            throw new ApiException("user not found");
        }
        return convertToDTO(accountRepository.findAll());
    }
}
