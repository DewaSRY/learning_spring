package com.sdewa.BasicSpring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sdewa.BasicSpring.models.PaginationDataResponse;
import com.sdewa.BasicSpring.models.MetaDataResponse;
import com.sdewa.BasicSpring.models.AccountsEntity;
import com.sdewa.BasicSpring.models.AccountQuery;
import com.sdewa.BasicSpring.repositories.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountListServicesImpl implements AccountListServices {

    private final AccountRepository accountRepository;
    
    @Override
    public PaginationDataResponse<AccountsEntity> getAccounts(AccountQuery query) {
        Sort sort = query.getSortOrder().equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(query.getSortBy()).ascending()
                : Sort.by(query.getSortBy()).descending();

        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);
        Page<AccountsEntity> page = accountRepository.findAll(AccountRepository.queryAccount(query), pageable);

        return PaginationDataResponse.<AccountsEntity>builder()
                .data(page.getContent())
                .meta(MetaDataResponse.builder()
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalAmount(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .last(page.isLast())
                        .build())
                .build();
    }
    
}
