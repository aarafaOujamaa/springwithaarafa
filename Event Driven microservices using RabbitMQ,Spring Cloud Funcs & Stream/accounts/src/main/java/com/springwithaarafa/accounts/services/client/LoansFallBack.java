package com.springwithaarafa.accounts.services.client;

import com.springwithaarafa.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallBack implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
