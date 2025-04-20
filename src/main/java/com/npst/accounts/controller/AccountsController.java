package com.npst.accounts.controller;


import com.npst.accounts.dao.CustomerDto;
import com.npst.accounts.dao.ResponseDto;
import com.npst.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        iAccountsService.createAccounts(customerDto);
        return ResponseEntity.ok(new ResponseDto("success", "Account created successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<ResponseDto> fetchAccount(@RequestParam String mobileNumber) {

        return null;


    }


}
