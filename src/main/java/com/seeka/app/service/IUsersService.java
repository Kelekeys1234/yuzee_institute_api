package com.seeka.app.service;

import java.math.BigInteger;

import com.seeka.app.dto.UserDto;
import com.seeka.app.exception.ValidationException;

public interface IUsersService {

	UserDto getUserById(BigInteger userId);

	void sendPushNotification(BigInteger userId, String message) throws ValidationException;

}
