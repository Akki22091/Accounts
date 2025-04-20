package com.npst.accounts.service.impl;

import com.npst.accounts.constants.ApplicationConstants;
import com.npst.accounts.dao.AccountsDto;
import com.npst.accounts.dao.CustomerDto;
import com.npst.accounts.entity.Accounts;
import com.npst.accounts.entity.Customer;
import com.npst.accounts.exception.CustomerAlreadyExists;
import com.npst.accounts.exception.ResourceNotFoundException;
import com.npst.accounts.mapper.AccountsMapper;
import com.npst.accounts.mapper.CustomerMapper;
import com.npst.accounts.repository.AccountsRepository;
import com.npst.accounts.repository.CustomerRepository;
import com.npst.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateCustomerWithAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "Account-Number", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccount(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);
            Integer customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "Customer-Id", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("customer", "Mobile Number", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
