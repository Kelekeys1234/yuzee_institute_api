package com.seeka.app.dao;

import java.math.BigInteger;

import com.seeka.app.bean.EnrollmentChatMedia;

public interface IEnrollmentChatMediaDao {

	void save(EnrollmentChatMedia enrollmentChatImages);

	EnrollmentChatMedia getEnrollmentImage(BigInteger EnrollmentChatConversationId);

}
