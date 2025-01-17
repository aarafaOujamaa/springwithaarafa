package com.springwithaarafa.accounts.services.Impl;

import com.springwithaarafa.accounts.exceptions.CustomerAlreadyExistsException;
import com.springwithaarafa.accounts.exceptions.ResourceNotFoundException;
import com.springwithaarafa.accounts.Mappers.AccountsMapper;
import com.springwithaarafa.accounts.Mappers.CustomerMapper;
import com.springwithaarafa.accounts.constants.AccountsConstants;
import com.springwithaarafa.accounts.dto.AccountsDto;
import com.springwithaarafa.accounts.dto.CustomerDto;
import com.springwithaarafa.accounts.entites.Accounts;
import com.springwithaarafa.accounts.entites.Customer;
import com.springwithaarafa.accounts.repository.AccountsRepository;
import com.springwithaarafa.accounts.repository.CustomerRepository;
import com.springwithaarafa.accounts.services.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Aarafa Oujamaa
 */

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(final CustomerDto customerDto) {
        final String mobileNumber = customerDto.getMobileNumber();
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(mobileNumber);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists registered with given mobileNumber" +
                    mobileNumber);
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccountDetails(final String mobile) {
        Customer customer= customerRepository.findByMobileNumber(mobile).orElseThrow(() ->
                new ResourceNotFoundException("Customer", "mobileNumber", mobile)
        );

        Optional<Accounts> accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        if(!accounts.isPresent()) {
            throw  new ResourceNotFoundException("Account", "customer", customer.getCustomerId().toString());
        }

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts.get(), new AccountsDto()));
        return customerDto;
    }


    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(Objects.nonNull(accountsDto)){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }


    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


    private Accounts createNewAccount(final Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }
}
