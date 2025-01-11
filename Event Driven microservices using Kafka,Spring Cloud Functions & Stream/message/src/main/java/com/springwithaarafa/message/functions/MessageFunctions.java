package com.springwithaarafa.message.functions;

import com.springwithaarafa.message.dto.AccountsMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {
    private static Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    // using spring cloud function, all my methods are exposed like as REST APIs automatically
    // this one features is provided by spring cloud function -> http://localhost:8080/email via Postman
    @Bean
    // write function implementation here, lambda method
    public Function<AccountsMsgDto, AccountsMsgDto> email() {
        return accountsMsgDto -> {
            // u can add implementation here for sending email
            log.info("send email with details to end user: {}", accountsMsgDto);
            return accountsMsgDto;
        };
    }

    // http://localhost:8080/sms via Postman
    @Bean
    public Function<AccountsMsgDto, Long> sms() {
        return accountsMsgDto -> {
            // u can add implementation here for sending email
            log.info("send sms with details to end user: {}", accountsMsgDto);
            return accountsMsgDto.accountNumber();
        };
    }
}
