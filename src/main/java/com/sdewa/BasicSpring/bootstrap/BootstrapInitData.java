package com.sdewa.BasicSpring.bootstrap;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.sdewa.BasicSpring.services.BeverageServices;
import com.sdewa.BasicSpring.models.AccountCreateRequest;
import com.sdewa.BasicSpring.models.BeverageCreateRequest;
import com.sdewa.BasicSpring.services.AccountServices;

@Component
@RequiredArgsConstructor
public class BootstrapInitData implements CommandLineRunner {

    private final BeverageServices bootstrapService;
    private final AccountServices accountService;

    @Override
    public void run(String... args) throws Exception {

        dummyAccounts(10);
        dummyBeverages(10);
    }

    private void dummyBeverages(Integer numberOfBeverages) {
        for (int i = 0; i < numberOfBeverages; i++) {
            BeverageCreateRequest beverageCreateRequest = BeverageCreateRequest.builder()
                    .name("Beverage " + (i + 1))
                    .description("Description for Beverage " + (i + 1))
                    .build();
            bootstrapService.createBeverage(beverageCreateRequest);
        }
    }

    private void dummyAccounts(Integer numberOfAccounts) {
        List<String> firstNames = List.of("John", "Jane", "Alice", "Bob", "Charlie");
        List<String> lastNames = List.of("Doe", "Smith", "Johnson", "Brown", "Davis");

        for (int i = 0; i < numberOfAccounts; i++) {
            String firstName = firstNames.get((int) (Math.random() * firstNames.size()));
            String lastName = lastNames.get((int) (Math.random() * lastNames.size()));
            String accountNumber = generateRandomNumberFromDate(generateRandomDateTime());

            AccountCreateRequest accountCreateRequest = AccountCreateRequest.builder()
                    .name(firstName + " " + lastName)
                    .number(accountNumber)
                    .build();
            accountService.createAccount(accountCreateRequest);
        }

    }

    private LocalDateTime generateRandomDateTime() {
        int year = (int) (Math.random() * 5) + 2020; // Random year between 2020 and 2024
        int month = (int) (Math.random() * 12) + 1; // Random month between 1 and 12
        int day = (int) (Math.random() * 28) + 1; // Random day between 1 and 28 to avoid invalid dates
        int hour = (int) (Math.random() * 24); // Random hour between 0 and 23
        int minute = (int) (Math.random() * 60); // Random minute between 0 and 59
        int second = (int) (Math.random() * 60); // Random second between 0 and 59

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    private String generateRandomNumberFromDate(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        return String.format("%04d%02d%02d%02d%02d%02d", year, month, day, hour, minute, second);
    }

}
