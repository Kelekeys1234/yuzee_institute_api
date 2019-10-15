package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.ChatConversation;

public interface IChatConversationDao {

	void save(ChatConversation chatConversation);

	void update(ChatConversation chatConversation);

	ChatConversation getChatConversation(BigInteger chatConversationId);

	List<ChatConversation> getChatConversationBasedOnChatId(BigInteger chatId, Integer startIndex, Integer pageSize);

	Integer getChatConversationCountBasedOnChatId(BigInteger chatId);

	List<ChatConversation> getChatListBasedOnEntityType(String entityType, BigInteger initiateFromId, BigInteger initiateToId, Integer startIndex,
			Integer pageSize);

	Integer getChatConversationCountBasedOnEntityType(String entityType, BigInteger initiateFromId, BigInteger initiateToId);

}
