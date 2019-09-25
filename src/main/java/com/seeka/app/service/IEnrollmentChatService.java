package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.EnrollmentChatConversation;
import com.seeka.app.dto.EnrollmentChatConversationDto;
import com.seeka.app.dto.EnrollmentChatRequestDto;
import com.seeka.app.dto.EnrollmentChatResposneDto;
import com.seeka.app.exception.ValidationException;

public interface IEnrollmentChatService {

	EnrollmentChatConversation startEnrollmentChat(EnrollmentChatRequestDto enrollmentChatRequestDto) throws ValidationException;

	EnrollmentChatConversation addEnrollmentChatConversation(EnrollmentChatConversationDto enrollmentChatConversationDto) throws ValidationException;

	void addEnrollmentChatImage(MultipartFile file, EnrollmentChatConversation enrollmentChatConversation);

	void changeEnrollmentChatAssignee(BigInteger enrollmentChatId, BigInteger assigneeId) throws ValidationException;

	EnrollmentChatResposneDto getEnrollmentChatListBasedOnEnrollment(BigInteger enrollmentId) throws ValidationException;

	List<EnrollmentChatResposneDto> getEnrollmentChatList(Integer startIndex, Integer pageSize);

	void readEnrollmentChatConversation(BigInteger enrollmentChatConversationId) throws ValidationException;

}
