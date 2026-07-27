package com.sdewa.BasicSpring.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.sdewa.BasicSpring.TestcontainersConfiguration;
import com.sdewa.BasicSpring.models.AccountCreateRequest;

// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class AccountServicesTestIt {

    @Autowired
    private AccountServices accountServices;


    @Test
    public void testCRUDAccount() {
        //created
        var requestCreated =  AccountCreateRequest.builder()
        .number("request-number")
        .name("account name")
        .build();

        var createdAccountOption = accountServices.createAccount(requestCreated);
        assertThat(createdAccountOption.get()).isNotNull();
        var createdAccount = createdAccountOption.get();
        assertThat(createdAccount.getId()).isNotNull();

        // get the account by id
        var fetchedAccountOption = accountServices.getAccountById(createdAccount.getId());
        assertThat(fetchedAccountOption.get()).isNotNull();
        var fetchedAccount = fetchedAccountOption.get();
        assertThat(fetchedAccount.getId()).isEqualTo(createdAccount.getId());

        // Update the account
        var requestUpdated =  AccountCreateRequest.builder()
        .number("request-number-updated")
        .name("account name updated")
        .build();
        
        var updatedAccountOption = accountServices.updateAccount(createdAccount.getId(), requestUpdated);
        assertThat(updatedAccountOption.get()).isNotNull();
        var updatedAccount = updatedAccountOption.get();
        assertThat(updatedAccount.getId()).isEqualTo(createdAccount.getId());

        // Delete the account
        accountServices.deleteAccount(createdAccount.getId());
        var deletedAccountOption = accountServices.getAccountById(createdAccount.getId());  
        assertThat(deletedAccountOption.isEmpty()).isTrue();
    }
    
}
