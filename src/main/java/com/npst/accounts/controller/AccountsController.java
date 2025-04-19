package com.npst.accounts.controller;


import com.npst.accounts.dao.CustomerDto;
import com.npst.accounts.dao.ResponseDto;
import com.npst.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
