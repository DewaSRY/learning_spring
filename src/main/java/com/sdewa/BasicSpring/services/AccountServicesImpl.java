package com.sdewa.BasicSpring.services;

import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

import com.sdewa.BasicSpring.models.AccountCreateRequest;
import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServicesImpl implements AccountServices {

    private final AccountRepository accountRepository;

    @Override
    public Optional<AccountsEntity> createAccount(AccountCreateRequest account) {
        AccountsEntity newAccount = AccountsEntity.builder()
                .name(account.getName())
                .number(account.getNumber())
                .build();
        return Optional.of(accountRepository.save(newAccount));
    }

    @Override
    public Optional<AccountsEntity> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<AccountsEntity> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<AccountsEntity> updateAccount(Long id, AccountCreateRequest account) {
        return accountRepository.findById(id).map(existing -> {
            existing.setName(account.getName());
            existing.setNumber(account.getNumber());
            return accountRepository.save(existing);
        });
    }

    @Override
    public Optional<AccountsEntity> deleteAccount(Long id) {
        return accountRepository.findById(id).map(existing -> {
            accountRepository.delete(existing);
            return existing;
        });
    }

}
