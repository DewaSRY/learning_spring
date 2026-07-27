package com.sdewa.BasicSpring.bootstrap;

// import java.time.LocalDateTime;
import java.util.List;
import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.sdewa.BasicSpring.services.BeverageServices;
// import com.sdewa.BasicSpring.models.AccountCreateRequest;
import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.models.BeverageEntity;
// import com.sdewa.BasicSpring.services.AccountServices;

@Component
@RequiredArgsConstructor
public class BootstrapInitData implements CommandLineRunner {

    private final BeverageServices bootstrapService;
    // private final AccountServices accountService;

    private final BeverageNameListService beverageNameListService;

    @Override
    public void run(String... args) throws Exception {

        List<BeverageEntity> beverages = bootstrapService.getBeverages();

        if (beverages.isEmpty()) {
            dummyBeveragesFromCSV("src/main/resources/csv/beverage_name_list.csv");

        }
    }

    private void dummyBeveragesFromCSV(String csvFilePath) {
        List<BeverageNameListRecord> beverageRecords = beverageNameListService
                .getBeverageNameList(new File(csvFilePath));
        if (beverageRecords != null) {
            for (BeverageNameListRecord record : beverageRecords) {
                BeverageCreateRequest beverageCreateRequest = BeverageCreateRequest.builder()
                        .name(record.getBeverageName())
                        .description("Category: " + record.getCategory() + ", Type: " + record.getType()
                                + ", Main Ingredient: " + record.getMainIngredient())
                        .build();
                bootstrapService.createBeverage(beverageCreateRequest);
            }
        } else {
            System.out.println("No beverage records found in the CSV file.");
        }
    }


}
