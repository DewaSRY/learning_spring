package com.sdewa.BasicSpring.services;

import com.sdewa.BasicSpring.models.PaginationDataResponse;
import com.sdewa.BasicSpring.models.BeverageEntity;
import com.sdewa.BasicSpring.models.BeverageQuery;


public interface BeverageListServices {
    PaginationDataResponse<BeverageEntity> getBeverages(BeverageQuery query);
}
