package com.npst.accounts.controller;


import com.npst.accounts.constants.ApplicationConstants;
import com.npst.accounts.dao.CustomerDto;
import com.npst.accounts.dao.ResponseDto;
import com.npst.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Slf4j
public class AccountsController {

    @Autowired
    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        log.info("Received request to create account for mobile number: {}", customerDto.getMobileNumber());
        iAccountsService.createAccounts(customerDto);
        log.info("Account created successfully for mobile number: {}", customerDto.getMobileNumber());
        return ResponseEntity.ok(new ResponseDto("success", "Account created successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber) {
        log.info("Fetching account details for mobile number: {}", mobileNumber);
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        log.info("Fetched account details for mobile number: {}", mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        log.info("Received request to update account for mobile number: {}", customerDto.getMobileNumber());
        boolean isUpdated = iAccountsService.updateCustomerWithAccount(customerDto);
        if (isUpdated) {
            log.info("Account updated successfully for mobile number: {}", customerDto.getMobileNumber());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_200,
                            ApplicationConstants.MESSAGE_200
                    )
            );
        } else {
            log.error("Failed to update account for mobile number: {}", customerDto.getMobileNumber());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_500,
                            ApplicationConstants.MESSAGE_500
                    )
            );
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetail(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber) {
        log.info("Received request to delete account for mobile number: {}", mobileNumber);
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            log.info("Account deleted successfully for mobile number: {}", mobileNumber);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_200,
                            ApplicationConstants.MESSAGE_200
                    )
            );
        } else {
            log.error("Failed to delete account for mobile number: {}", mobileNumber);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDto(
                            ApplicationConstants.STATUS_500,
                            ApplicationConstants.MESSAGE_500
                    )
            );
        }
    }
}
