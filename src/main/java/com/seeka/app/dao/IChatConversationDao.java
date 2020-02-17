package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.ChatConversation;

public interface IChatConversationDao {

	void save(ChatConversation chatConversation);

	void update(ChatConversation chatConversation);

	ChatConversation getChatConversation(String chatConversationId);

	List<ChatConversation> getChatConversationBasedOnChatId(String chatId, Integer startIndex, Integer pageSize);

	Integer getChatConversationCountBasedOnChatId(String chatId);

	List<ChatConversation> getChatListBasedOnEntityType(String entityType, String initiateFromId, String initiateToId, Integer startIndex,
			Integer pageSize);

	Integer getChatConversationCountBasedOnEntityType(String entityType, String initiateFromId, String initiateToId);

}
