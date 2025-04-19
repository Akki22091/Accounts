package com.npst.accounts.service.impl;

import com.npst.accounts.constants.ApplicationConstants;
import com.npst.accounts.dao.CustomerDto;
import com.npst.accounts.entity.Accounts;
import com.npst.accounts.entity.Customer;
import com.npst.accounts.exception.CustomerAlreadyExists;
import com.npst.accounts.mapper.CustomerMapper;
import com.npst.accounts.repository.AccountsRepository;
import com.npst.accounts.repository.CustomerRepository;
import com.npst.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccounts(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalMobileNO = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalMobileNO.isPresent()) {
            throw new CustomerAlreadyExists("Customer already exists with given mobile no " + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(ApplicationConstants.SAVINGS);
        newAccount.setBranchAddress(ApplicationConstants.ADDRESS);
        return newAccount;
    }

}
