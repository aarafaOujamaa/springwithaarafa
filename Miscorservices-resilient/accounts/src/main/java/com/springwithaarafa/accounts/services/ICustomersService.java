package com.springwithaarafa.accounts.services;

import com.springwithaarafa.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {


    /**
     * @param correlationId - Input correlationId
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String correlationId, String mobileNumber);
}