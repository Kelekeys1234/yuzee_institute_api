package com.seeka.app.service;

import java.math.BigInteger;

import com.seeka.app.dto.UserDeviceInfoDto;
import com.seeka.app.dto.UserDto;

public interface IUsersService {

	UserDto getUserById(BigInteger userId);

	UserDeviceInfoDto getUserDeviceById(BigInteger userId);

	void sendPushNotification(UserDeviceInfoDto userDeviceInfoDto, String message);

	void sendPushNotification(BigInteger userId, String message);

}
