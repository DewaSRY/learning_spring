package com.sdewa.BasicSpring.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDateTime;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.models.BeverageEntity;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BeverageServicesImpl implements BeverageServices {

    private List<BeverageEntity> beverages;

    public BeverageServicesImpl() {
        // Initialize the beverages list
        this.beverages = new ArrayList<>();
    }

    @Override
    public Optional<BeverageEntity> getBeverageById(Long id) {


        return beverages.stream().filter(b -> b.getId().equals(id)).findFirst();
    }


    @Override
    public Optional<BeverageEntity> deleteBeverage(Long id) {
        Optional<BeverageEntity> beverage = getBeverageById(id);
        beverage.ifPresent(b -> beverages.remove(b));
        return beverage;
    }

    @Override
    public Optional<BeverageEntity> createBeverage(BeverageCreateRequest beverage) {
        BeverageEntity newBeverage = BeverageEntity.builder()
                .name(beverage.getName())
                .description(beverage.getDescription())
                .number(beverage.getNumber())
                .id((long) (beverages.size() + 1))
                .createdAt(LocalDateTime.now()) 
                .build();

        beverages.add(newBeverage);
        return Optional.of(newBeverage);
    }

    @Override
    public Optional<BeverageEntity> updateBeverage(Long id, BeverageCreateRequest beverage) {
        Optional<BeverageEntity> existingBeverageOpt = getBeverageById(id);
        if (existingBeverageOpt.isPresent()) {
            BeverageEntity existingBeverage = existingBeverageOpt.get();
            existingBeverage.setName(beverage.getName());
            existingBeverage.setDescription(beverage.getDescription());
            existingBeverage.setNumber(beverage.getNumber());
            return Optional.of(existingBeverage);
        }
        return Optional.empty();
    }

    @Override
    public List<BeverageEntity> getBeverages() {
        return beverages;
    }

}
