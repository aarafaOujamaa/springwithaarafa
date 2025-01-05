package com.springwithaarafa.accounts.services.Impl;

import com.springwithaarafa.accounts.Mappers.AccountsMapper;
import com.springwithaarafa.accounts.Mappers.CustomerMapper;
import com.springwithaarafa.accounts.dto.AccountsDto;
import com.springwithaarafa.accounts.dto.CardsDto;
import com.springwithaarafa.accounts.dto.CustomerDetailsDto;
import com.springwithaarafa.accounts.dto.LoansDto;
import com.springwithaarafa.accounts.entites.Accounts;
import com.springwithaarafa.accounts.entites.Customer;
import com.springwithaarafa.accounts.exceptions.ResourceNotFoundException;
import com.springwithaarafa.accounts.repository.AccountsRepository;
import com.springwithaarafa.accounts.repository.CustomerRepository;
import com.springwithaarafa.accounts.services.ICustomersService;
import com.springwithaarafa.accounts.services.client.CardsFeignClient;
import com.springwithaarafa.accounts.services.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String correlationId, String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if(null != loansDtoResponseEntity.getBody())
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

       ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
       if(null != cardsDtoResponseEntity.getBody())
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
