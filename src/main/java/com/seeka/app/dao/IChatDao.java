package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Chat;

public interface IChatDao {

	void save(Chat chat);

	void update(Chat chat);

	List<Chat> getChatList(Integer startIndex, Integer pageSize);

	Chat getChat(String chatId);

	Chat getChatBasedOnEntityId(BigInteger entityId);

	Chat getChatBasedOnEntityIdAndEntityType(BigInteger entityId, String entityType);

}
