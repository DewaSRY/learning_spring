package com.sdewa.BasicSpring.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sdewa.BasicSpring.exception.CommonContentNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import lombok.AllArgsConstructor;

import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.models.AccountCreateRequest;
import com.sdewa.BasicSpring.services.AccountServices;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountServices accountServices;

    @GetMapping
    public ResponseEntity<List<AccountsEntity>> getAccounts() {
        return ResponseEntity.ok(accountServices.getAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountsEntity> getAccountById(@PathVariable Long id) {
        return accountServices.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CommonContentNotFound("Account not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<AccountsEntity> createAccount(@RequestBody AccountCreateRequest account) {
        return accountServices.createAccount(account)
                .map(createdAccount -> ResponseEntity.status(HttpStatus.CREATED).body(createdAccount))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountsEntity> updateAccount(@PathVariable Long id,
            @RequestBody AccountCreateRequest account) {
        return accountServices.updateAccount(id, account)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountServices.deleteAccount(id)
                .map(deletedAccount -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());

        return ResponseEntity.noContent().build();
    }
}
