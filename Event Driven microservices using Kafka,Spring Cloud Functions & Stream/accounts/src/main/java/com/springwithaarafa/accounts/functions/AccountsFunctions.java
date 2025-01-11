package com.springwithaarafa.accounts.functions;


import com.springwithaarafa.accounts.services.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunctions {


    private static final Logger log = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    // NO Need to make any injection of IAccountService here , its already injected automaticaly
    public Consumer<Long> updateCommunication(IAccountService accountsService) {
        return accountNumber -> {
            log.info("Updating Communication status for the account number : " + accountNumber.toString());
            accountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
