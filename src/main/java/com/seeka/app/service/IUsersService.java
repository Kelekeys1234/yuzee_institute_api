package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.dto.UserAchivements;
import com.seeka.app.dto.UserDto;
import com.seeka.app.exception.ValidationException;

public interface IUsersService {

	UserDto getUserById(BigInteger userId) throws ValidationException;

	void sendPushNotification(BigInteger userId, String message, String notificationType) throws ValidationException;

	List<UserAchivements> getUserAchivementsByUserId(BigInteger userId) throws ValidationException;

}
