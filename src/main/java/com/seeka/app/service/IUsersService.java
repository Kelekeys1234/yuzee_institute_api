package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.UserAchivements;
import com.seeka.app.dto.UserDto;
import com.seeka.app.exception.ValidationException;

public interface IUsersService {

	UserDto getUserById(String userId) throws ValidationException;

	void sendPushNotification(String userId, String message, String notificationType) throws ValidationException;

	List<UserAchivements> getUserAchivementsByUserId(String userId) throws ValidationException;

}
