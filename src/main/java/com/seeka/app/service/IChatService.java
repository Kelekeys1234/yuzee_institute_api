package com.seeka.app.service;

import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.ChatConversation;
import com.seeka.app.dto.ChatRequestDto;
import com.seeka.app.dto.ChatResposneDto;
import com.seeka.app.exception.ValidationException;

public interface IChatService {

	ChatConversation startChat(ChatRequestDto enrollmentChatRequestDto) throws ValidationException;

	void addChatImage(MultipartFile file, ChatConversation chatConversation);

	void changeChatAssignee(BigInteger enrollmentChatId, BigInteger assigneeId) throws ValidationException;

	ChatResposneDto getChatListBasedOnEntityIdAndEntityType(BigInteger entityId, String entityType, Integer startIndex, Integer pageSize)
			throws ValidationException;

	void readChatConversation(BigInteger enrollmentChatConversationId) throws ValidationException;

	Integer getChatConversationCountBasedOnChatId(BigInteger chatId);

	ChatResposneDto getChatListBasedOnEntityType(String entityType, BigInteger initiateFromId, BigInteger initiateToId, Integer startIndex, Integer pageSize)
			throws ValidationException;

	Integer getChatConversationCountBasedOnEntityType(String entityType, BigInteger initiateFromId, BigInteger initiateToId);

}
