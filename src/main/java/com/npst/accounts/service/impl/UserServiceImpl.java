package com.npst.accounts.service.impl;

import com.npst.accounts.dao.UserDto;
import com.npst.accounts.entity.AppUser;
import com.npst.accounts.mapper.UserMapper;
import com.npst.accounts.repository.UserRepository;
import com.npst.accounts.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(UserDto userDto) {
        AppUser appUser = UserMapper.mapToUser(userDto, new AppUser());
        userRepository.save(appUser);
    }
}
