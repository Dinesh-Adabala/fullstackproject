package com.neoteric.fullstackdemo_31._8._4.controller;

import com.neoteric.fullstackdemo_31._8._4.exception.AccountCreationFailedException;
import com.neoteric.fullstackdemo_31._8._4.model.Account;
import com.neoteric.fullstackdemo_31._8._4.service.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;




@RestController
@CrossOrigin("*")
public class AccountController {
    @PostMapping(value = "/api/createAccount/jdbc",
            consumes = "application/json",
            produces = "application/json")
    public Account getAccountNumber(@RequestBody Account account) throws Exception {
        AccountService accountService = new AccountService();
      String accountNumber =  accountService.createAccount(account);
      account.setAccountnumber(accountNumber);
       return account;
    }


    @PostMapping(value = "/api/createAccount/hibernet",
            consumes = "application/json",
            produces = "application/json")
    public Account getAccountNumberUsingHibernet(@RequestBody Account account) throws Exception {
        AccountService accountService = new AccountService();
        String accountNumber =  accountService.createAccountUsingHibernet(account);
        account.setAccountnumber(accountNumber);
        return account;
    }


    @PostMapping(value = "/api/createAccount",
            consumes = "application/json",
            produces = "application/json")
    public Account getAccountNumberUsingHibernetFromUI(@RequestBody Account account) throws Exception {
        AccountService accountService = new AccountService();
        String accountNumber =  accountService.oneToManyUsingHibernateFromUI(account);
        account.setAccountnumber(accountNumber);
        return account;
    }


    @GetMapping(value = "/api/sreachAccount",
            consumes = "application/json",
            produces = "application/json")
    public Account getAccountByAccountNumber(@RequestHeader("accountnumber") String accountNumber )  {

      AccountService accountService = new AccountService();
      return accountService.getAccount(accountNumber);
    }

}
