package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.EnrollmentChat;

public interface IEnrollmentChatDao {

	void save(EnrollmentChat enrollmentChat);

	void update(EnrollmentChat enrollmentChat);

	List<EnrollmentChat> getEnrollmentChatList(Integer startIndex, Integer pageSize);

	EnrollmentChat getEnrollmentChat(BigInteger enrollmentChatId);

	EnrollmentChat getEnrollmentChatBasedOnEnrollmentId(BigInteger enrollmentId);

}
