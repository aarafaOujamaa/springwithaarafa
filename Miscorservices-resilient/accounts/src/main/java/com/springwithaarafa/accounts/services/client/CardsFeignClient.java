package com.springwithaarafa.accounts.services.client;


import com.springwithaarafa.accounts.dto.CardsDto;
import com.springwithaarafa.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="cards", fallback=CardsFallBack.class )// same value must be registred in eureka server
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("ms-correlation-id") String correlationId,
                                                     @RequestParam String mobileNumber);
}
