package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.EnrollmentChatConversation;

public interface IEnrollmentChatConversationDao {

	void save(EnrollmentChatConversation enrollmentChatConversation);

	void update(EnrollmentChatConversation enrollmentChatConversation);

	EnrollmentChatConversation getEnrollmentChatConversation(BigInteger enrollmentChatConversationId);

	List<EnrollmentChatConversation> getEnrollmentChatConversationBasedOnEnrollmentChatId(BigInteger enrollmentChatId);

}
