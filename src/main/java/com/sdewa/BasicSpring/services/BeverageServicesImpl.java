package com.sdewa.BasicSpring.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.models.BeverageEntity;
import com.sdewa.BasicSpring.repositories.BeverageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeverageServicesImpl implements BeverageServices {

    private final BeverageRepository beverageRepository;

    @Override
    public Optional<BeverageEntity> getBeverageById(Long id) {
        return beverageRepository.findById(id);
    }

    @Override
    public Optional<BeverageEntity> deleteBeverage(Long id) {
        return beverageRepository.findById(id).map(existing -> {
            beverageRepository.delete(existing);
            return existing;
        });
    }

    @Override
    public Optional<BeverageEntity> createBeverage(BeverageCreateRequest beverage) {
        BeverageEntity newBeverage = BeverageEntity.builder()
                .name(beverage.getName())
                .description(beverage.getDescription())
                .number(beverage.getNumber())
                .build();
        return Optional.of(beverageRepository.save(newBeverage));
    }

    @Override
    public Optional<BeverageEntity> updateBeverage(Long id, BeverageCreateRequest beverage) {
        return beverageRepository.findById(id).map(existing -> {
            existing.setName(beverage.getName());
            existing.setDescription(beverage.getDescription());
            existing.setNumber(beverage.getNumber());
            return beverageRepository.save(existing);
        });
    }

    @Override
    public List<BeverageEntity> getBeverages() {
        return beverageRepository.findAll();
    }

}
