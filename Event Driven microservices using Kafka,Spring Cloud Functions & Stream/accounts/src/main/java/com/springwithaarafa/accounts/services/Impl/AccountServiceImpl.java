package com.springwithaarafa.accounts.services.Impl;

import com.springwithaarafa.accounts.dto.AccountsMsgDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
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

    private static final Logger log =  LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

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
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        // send message via destination binding with name "sendCommunication-out-0" that acts like output channel
       /*
       ***Microservice as a producer (output):**
        If the microservice sends messages:
        - The messages go through an **Output Destination Binding**.-> "sendCommunication-out-0"
        - These messages are sent to an **Exchange**, which then routes them to the corresponding queues.
       ***Microservice as a consumer (input):**
        If the microservice consumes messages:
        - An **Input Destination Binding** connects a queue to the microservice.
        - The messages from this queue are delivered to the microservice for processing.*/
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
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

    /**
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber !=null ){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return  isUpdated;
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
