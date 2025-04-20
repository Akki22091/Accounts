package com.npst.accounts.dao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name Cannot be null or Empty")
    @Size(min = 5, max = 30, message = "The Length of Customer Name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email Cannot be null or Empty")
    @Email(message = "Email address should be valid value")
    private String email;

    @NotEmpty(message = "Mobile number Cannot be null or Empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;

}
