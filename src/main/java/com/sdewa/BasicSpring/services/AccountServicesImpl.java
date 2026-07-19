package com.sdewa.BasicSpring.services;

import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.models.AccountCreateRequest;
import java.util.Optional;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;




@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountServicesImpl implements AccountServices {

    private List<AccountsEntity> accounts; 

    public AccountServicesImpl() {
        this.accounts = new ArrayList<>();
    }

    @Override
    public Optional<AccountsEntity> createAccount(AccountCreateRequest account) {
        AccountsEntity newAccount =  AccountsEntity.builder()
                .name(account.getName())
                .number(account.getNumber())
                .id((long) (accounts.size() + 1))
                .createdAt(LocalDateTime.now())
                .build();

        accounts.add(newAccount);
        return Optional.of(newAccount);
    }

    @Override
    public Optional<AccountsEntity> getAccountById(Long id) {
        return accounts.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public List<AccountsEntity> getAccounts() {
        return accounts;
    }

    @Override
    public Optional<AccountsEntity> updateAccount(Long id, AccountCreateRequest account) {
        AccountsEntity existingAccount = getAccountById(id).orElse(null);
        if (existingAccount != null) {
            existingAccount.setName(account.getName());
            existingAccount.setNumber(account.getNumber());
            
            return Optional.of(existingAccount);
        }
        return Optional.empty();
    }

    @Override
    public Optional<AccountsEntity> deleteAccount(Long id) {
        AccountsEntity existingAccount = getAccountById(id).orElse(null);
        if (existingAccount != null) {
            accounts.remove(existingAccount);
            return Optional.of(existingAccount);
        }
        return Optional.empty();
    }
    
}
