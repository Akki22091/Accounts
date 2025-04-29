package com.npst.accounts.controller;


import com.npst.accounts.dao.UserDto;
import com.npst.accounts.entity.AppUser;
import com.npst.accounts.mapper.UserMapper;
import com.npst.accounts.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private IUserService customUserDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public void registerUser(@RequestBody UserDto userDto) {
        System.out.println(userDto.getPassword());

        System.out.println(userDto.getUsername());

        AppUser user = new AppUser();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto udt = UserMapper.mapTOUserDto(user, userDto);
        customUserDetailService.registerUser(udt);
    }

}
