package com.sdewa.BasicSpring.services;

import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.models.BeverageEntity;

import java.util.List;
import java.util.Optional;

public interface BeverageServices {

    Optional<BeverageEntity> createBeverage(BeverageCreateRequest beverage);

    Optional<BeverageEntity> getBeverageById(Long id);

    Optional<BeverageEntity> updateBeverage(Long id, BeverageCreateRequest beverage);

    List<BeverageEntity> getBeverages();

    Optional<BeverageEntity> deleteBeverage(Long id);

}
