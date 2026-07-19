package com.sdewa.BasicSpring.services;

import java.util.Optional;
import java.util.List;

import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.models.AccountCreateRequest;

public interface AccountServices {

    public Optional<AccountsEntity> createAccount(AccountCreateRequest account);
    
    public Optional<AccountsEntity> getAccountById(Long id);
    
    public List<AccountsEntity> getAccounts();

    public Optional<AccountsEntity> updateAccount(Long id, AccountCreateRequest account);

    public Optional<AccountsEntity> deleteAccount(Long id);
    
}
