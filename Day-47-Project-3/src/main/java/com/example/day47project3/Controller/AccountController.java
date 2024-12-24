package com.example.day47project3.Controller;

import com.example.day47project3.Api.ApiResponse;
import com.example.day47project3.DTO.AccountDTO_in;
import com.example.day47project3.Model.Account;
import com.example.day47project3.Model.MyUser;
import com.example.day47project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity createAccount(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid AccountDTO_in accountDTO_in) {
        accountService.createAccount(myUser.getId(), accountDTO_in);
        return ResponseEntity.status(200).body(new ApiResponse("account created successfully"));
    }

    @PutMapping("/activate/{accountId}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId) {
        accountService.activateAccount(myUser.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account activated successfully"));
    }

    @GetMapping("/get-account/{accountId}")
    public ResponseEntity getAccountDetails(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccount(myUser.getId(), accountId));
    }

    @GetMapping("/get-my")
    public ResponseEntity getAllUserAccounts(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(accountService.getAccounts(myUser.getId()));
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId) {
        accountService.deleteAccount(myUser.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account deleted successfully"));
    }

    @PutMapping("/deposit/account-{accountId}/amount-{amount}")
    public ResponseEntity depositMoney(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.deposit(myUser.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("amount deposited successfully"));
    }

    @PutMapping("/withdraw/account-{accountId}/amount-{amount}")
    public ResponseEntity withdrawMoney (@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId, @PathVariable Double amount){
        accountService.withdraw(myUser.getId(), accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("amount withdrawn successfully"));
    }

    @PutMapping("/transfer/from-{sourceAccountId}/to-{destinationAccountId}/amount-{amount}")
    public ResponseEntity transfer(@AuthenticationPrincipal MyUser myUser,@PathVariable Integer sourceAccountId
            ,@PathVariable Integer destinationAccountId,@PathVariable Double amount) {
        accountService.transfer(myUser.getId(), sourceAccountId, destinationAccountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("transfer successfully"));
    }

    @PutMapping("/ban/{accountId}")
    public ResponseEntity block (@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId) {
        accountService.banAccount(myUser.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("account blocked successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity getAll (@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(accountService.getAllAccounts(myUser.getId()));
    }
}
