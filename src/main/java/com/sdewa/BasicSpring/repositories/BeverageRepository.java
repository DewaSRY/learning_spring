package com.sdewa.BasicSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdewa.BasicSpring.models.BeverageEntity;


@Repository
public interface BeverageRepository extends JpaRepository<BeverageEntity, Long> {
    
}
