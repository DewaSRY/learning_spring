package com.sdewa.BasicSpring.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sdewa.BasicSpring.TestcontainersConfiguration;
import com.sdewa.BasicSpring.repositories.BeverageRepository;
import com.sdewa.BasicSpring.models.BeverageCreateRequest;
// import com.sdewa.BasicSpring.models.BeverageEntity;

// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class BeverageServicesTestIt {

    @Autowired
    private BeverageServices beverageServices;

    @Autowired
    private BeverageRepository beverageRepository;
    

    @Test
    void contextLoads() {
        assertThat(beverageRepository.count()).isNotZero();
    }


    @Test
    public void testCrudBeverage() {
        //created
        var requestCreated =  BeverageCreateRequest.builder()
            .name("Beverage 1")
            .description("this is beverage 1")
            .build();
    
        var createdBeverageOption = beverageServices.createBeverage(requestCreated);
        assertThat(createdBeverageOption.get()).isNotNull();
        var createdBeverage = createdBeverageOption.get();
        assertThat(createdBeverage.getId()).isNotNull();

        // get the beverage by id
        var fetchedBeverageOption = beverageServices.getBeverageById(createdBeverage.getId());
        assertThat(fetchedBeverageOption.get()).isNotNull();
        var fetchedBeverage = fetchedBeverageOption.get();
        assertThat(fetchedBeverage.getId()).isEqualTo(createdBeverage.getId());

        // Update the beverage
        var requestUpdated =  BeverageCreateRequest.builder()
            .name("Beverage 1 Updated")
            .description("this is beverage 1 updated")
            .build();
        var updatedBeverageOption = beverageServices.updateBeverage(createdBeverage.getId(), requestUpdated);
        assertThat(updatedBeverageOption.get()).isNotNull();
        var updatedBeverage = updatedBeverageOption.get();
        assertThat(updatedBeverage.getName()).isEqualTo(requestUpdated.getName());  
        assertThat(updatedBeverage.getDescription()).isEqualTo(requestUpdated.getDescription());


        // Delete the beverage
        beverageServices.deleteBeverage(createdBeverage.getId());
        var deletedBeverageOption = beverageServices.getBeverageById(createdBeverage.getId());
        assertThat(deletedBeverageOption.isEmpty()).isTrue();   
    }
}
