package com.sdewa.BasicSpring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdewa.BasicSpring.models.BeverageEntity;
import com.sdewa.BasicSpring.exception.CommonContentNotFound;
import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.services.BeverageServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/beverages")
@AllArgsConstructor
public class BeverageController {

    private final BeverageServices beverageServices;

    @GetMapping
    public ResponseEntity<List<BeverageEntity>> getBeverages() {
        List<BeverageEntity> beverages = beverageServices.getBeverages();
        return ResponseEntity.ok(beverages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeverageEntity> getBeverageById(@PathVariable Long id) {
        Optional<BeverageEntity> beverage = beverageServices.getBeverageById(id);
        return beverage.map(ResponseEntity::ok)
                .orElseThrow(() -> new CommonContentNotFound("Beverage not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<BeverageEntity> createBeverage(
            @Valid @RequestBody BeverageCreateRequest beverageCreateRequest) {

        return beverageServices.createBeverage(beverageCreateRequest)
                .map(beverage -> ResponseEntity.status(HttpStatus.CREATED).body(beverage))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeverageEntity> updateBeverage(
                    @Valid @PathVariable Long id,
            @RequestBody BeverageCreateRequest beverageCreateRequest) {
        Optional<BeverageEntity> updatedBeverage = beverageServices.updateBeverage(id, beverageCreateRequest);
        return updatedBeverage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeverage(@PathVariable Long id) {
        Optional<BeverageEntity> deletedBeverage = beverageServices.deleteBeverage(id);
        if (deletedBeverage.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
