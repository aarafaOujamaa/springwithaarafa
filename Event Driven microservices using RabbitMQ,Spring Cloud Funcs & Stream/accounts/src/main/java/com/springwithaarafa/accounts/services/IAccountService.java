package com.springwithaarafa.accounts.services;

import com.springwithaarafa.accounts.dto.CustomerDto;
import org.springframework.stereotype.Component;


public interface IAccountService {

    /**
     * Creates a new account and binds it to the customer specified in the request.
     * @param customerDto The customer to which the account will be bound.
     */
    void createAccount(final CustomerDto customerDto);

    /**
     * Retrieves the account details for the customer with the given mobile number.
     * @param mobile The mobile number of the customer to retrieve the account for.
     * @return The account details associated with the given customer.
     */
    CustomerDto fetchAccountDetails(final String mobile);

    /**
     *
     * @param customerDto - CustomerDto containing the details to be updated
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Deletes the account details for the customer with the given mobile number.
     * @param mobileNumber The mobile number of the customer to delete the account for.
     * @return boolean indicating if the deletion of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);

    /**
     *
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    boolean updateCommunicationStatus(Long accountNumber);


}
