package com.npst.accounts.controller;


import com.npst.accounts.constants.ApplicationConstants;
import com.npst.accounts.dao.CustomerDto;
import com.npst.accounts.dao.ResponseDto;
import com.npst.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccounts(customerDto);
        return ResponseEntity.ok(new ResponseDto("success", "Account created successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                    @RequestParam String mobileNumber) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateCustomerWithAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_200,
                            ApplicationConstants.MESSAGE_200
                    )
            );
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_500,
                            ApplicationConstants.MESSAGE_500
                    )
            );
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetail(@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                           @RequestParam String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_200,
                            ApplicationConstants.MESSAGE_200
                    )
            );
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_500,
                            ApplicationConstants.MESSAGE_500
                    )
            );
        }
    }
}
