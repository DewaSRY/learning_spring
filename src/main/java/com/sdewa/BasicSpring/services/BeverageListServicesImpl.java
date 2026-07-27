package com.sdewa.BasicSpring.services;


import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sdewa.BasicSpring.models.PaginationDataResponse;
import com.sdewa.BasicSpring.models.BeverageEntity;
import com.sdewa.BasicSpring.models.BeverageQuery;
import com.sdewa.BasicSpring.repositories.BeverageRepository;
@Service
@RequiredArgsConstructor
public class BeverageListServicesImpl implements BeverageListServices {

    private final BeverageRepository beverageRepository;

    @Override
    public PaginationDataResponse<BeverageEntity> getBeverages(BeverageQuery query) {
            Sort sort = query.getSortOrder().equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(query.getSortBy()).ascending()
                : Sort.by(query.getSortBy()).descending();

        Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), sort);

        Page<BeverageEntity> page = beverageRepository.findAll(BeverageRepository.queryBeverage(query), pageable);
        return PaginationDataResponse.<BeverageEntity>builder()
                .data(page.getContent())
                .meta(com.sdewa.BasicSpring.models.MetaDataResponse.builder()
                        .page(page.getNumber())
                        .size(page.getSize())
                        .totalAmount(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .last(page.isLast())
                        .build())
                .build();
    }
}
