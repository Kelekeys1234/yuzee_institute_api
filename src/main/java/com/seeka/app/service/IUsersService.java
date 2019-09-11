package com.seeka.app.service;

import java.math.BigInteger;

import com.seeka.app.dto.UserDto;

public interface IUsersService {

	UserDto getUserById(BigInteger userId);

}
