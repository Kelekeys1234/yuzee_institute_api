package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Chat;
import com.seeka.app.bean.ChatConversation;
import com.seeka.app.constant.ChatInitiator;
import com.seeka.app.constant.NotificationType;
import com.seeka.app.dao.IChatConversationDao;
import com.seeka.app.dao.IChatDao;
import com.seeka.app.dto.ChatConversationDto;
import com.seeka.app.dto.ChatRequestDto;
import com.seeka.app.dto.ChatResposneDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.ChatType;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ChatService implements IChatService {

	@Autowired
	private IChatDao iChatDao;

	@Autowired
	private IChatConversationDao iChatConversationDao;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private IUsersService iUsersService;

	@Autowired
	private IMediaService iMediaService;

	@Override
	public ChatConversation startChat(final ChatRequestDto chatRequestDto) throws ValidationException {

		ChatConversation chatConversation;
		Chat existingChat = null;
		/**
		 * One Entity can have only one chat
		 */
		if (!chatRequestDto.getEntityType().equals(ChatType.USER.name())) {
			if (chatRequestDto.getEntityId() == null) {
				throw new ValidationException("Entity id is required for entityType : " + chatRequestDto.getEntityType());
			}
			existingChat = iChatDao.getChatBasedOnEntityIdAndEntityType(chatRequestDto.getEntityId(), chatRequestDto.getEntityType());
		} else {
			if (chatRequestDto.getInitiateFromId() == null || chatRequestDto.getInitiateToId() == null) {
				throw new ValidationException("InitiatefromId and InitiateToId is required for entityType : " + chatRequestDto.getEntityType());
			}
			List<ChatConversation> chatConversations = iChatConversationDao.getChatListBasedOnEntityType(chatRequestDto.getEntityType(),
					chatRequestDto.getInitiateFromId(), chatRequestDto.getInitiateToId(), null, null);
			if (!chatConversations.isEmpty()) {
				existingChat = iChatDao.getChat(chatConversations.get(0).getChat().getId());
			}

		}
		if (existingChat != null) {
			chatConversation = addChatConversation(chatRequestDto, existingChat);
		} else {
			Chat chat = new Chat();
			chat.setCreatedOn(new Date());
			chat.setUserId(chatRequestDto.getUserId());
			chat.setEntityId(chatRequestDto.getEntityId());
			chat.setEntityType(chatRequestDto.getEntityType());
			iChatDao.save(chat);
			chatRequestDto.setId(chat.getId());

			/**
			 * Add chat conversation
			 */
			chatConversation = addChatConversation(chatRequestDto, chat);
		}

		if (chatRequestDto.getInitiateFrom().equals(ChatInitiator.SEEKA.name())) {
			sentPushNotificationForChatConversation(chatRequestDto.getMessage(), chatRequestDto.getUserId());
		}
		return chatConversation;
	}

	private ChatConversation addChatConversation(final ChatRequestDto chatRequestDto, final Chat chat) throws ValidationException {
		ChatConversation chatConversation = new ChatConversation();
		chatConversation.setCreatedOn(new Date());
		chatConversation.setChat(chat);
		chatConversation.setInitiateFrom(chatRequestDto.getInitiateFrom());
		chatConversation.setInitiateFromId(chatRequestDto.getInitiateFromId());
		chatConversation.setInitiateTo(chatRequestDto.getInitiateTo());
		chatConversation.setInitiateToId(chatRequestDto.getInitiateToId());
		chatConversation.setMessage(chatRequestDto.getMessage());
		chatConversation.setStatus("SENT");
		iChatConversationDao.save(chatConversation);
		return chatConversation;
	}

	private void sentPushNotificationForChatConversation(final String message, final String userId) throws ValidationException {
		iUsersService.sendPushNotification(userId, message, NotificationType.CHAT_CONVERSATION.name());
	}

	@Override
	public void changeChatAssignee(final String chatId, final String assigneeId) throws ValidationException {
		Chat chat = iChatDao.getChat(chatId);
		if (chat == null) {
			throw new ValidationException("chat not found for id " + chatId);
		}
		chat.setAssigneeId(assigneeId);
		iChatDao.update(chat);
	}

	@Override
	public ChatResposneDto getChatListBasedOnEntityIdAndEntityType(final String entityId, final String entityType, final Integer startIndex,
			final Integer pageSize) throws ValidationException {
		Chat chat = iChatDao.getChatBasedOnEntityIdAndEntityType(entityId, entityType);
		if (chat == null) {
			ChatResposneDto chatResposneDto = new ChatResposneDto();
			chatResposneDto.setEntityId(entityId);
			chatResposneDto.setEntityType(entityType);
			return chatResposneDto;
		}
		ChatResposneDto chatResposneDto = new ChatResposneDto();
		BeanUtils.copyProperties(chat, chatResposneDto);
		if (chat.getUserId() != null) {
			UserDto userDto = iUsersService.getUserById(chat.getUserId());
			chatResposneDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		}

		List<ChatConversationDto> chatConversationDtos = new ArrayList<>();
		List<ChatConversation> conversationList = iChatConversationDao.getChatConversationBasedOnChatId(chat.getId(), startIndex, pageSize);
		for (ChatConversation chatConversation : conversationList) {
			ChatConversationDto chatConversationDto = new ChatConversationDto();
			BeanUtils.copyProperties(chatConversation, chatConversationDto);
			chatConversationDto.setChatId(chatConversation.getChat().getId());
			if (chatConversation.getInitiateFromId() != null) {
				UserDto userDto = iUsersService.getUserById(chatConversation.getInitiateFromId());
				chatConversationDto.setInitiateFromName(userDto.getFirstName() + " " + userDto.getLastName());
			}
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(chatConversationDto.getId(), ImageCategory.CHAT_CONVERSATION.name(), null,
					"en");
			if (storageDTOList != null && !storageDTOList.isEmpty()) {
				chatConversationDto.setStorageDto(storageDTOList.get(0));
			}

			chatConversationDtos.add(chatConversationDto);
		}
		chatResposneDto.setChatConversationDtos(chatConversationDtos);
		return chatResposneDto;
	}

	@Override
	public void readChatConversation(final String chatConversationId) throws ValidationException {
		ChatConversation chatConversation = iChatConversationDao.getChatConversation(chatConversationId);
		if (chatConversation == null) {
			throw new ValidationException("Chat conversation not found for id" + chatConversationId);
		}
		chatConversation.setStatus("READ");
		iChatConversationDao.update(chatConversation);
	}

	@Override
	public void addChatImage(final MultipartFile file, final ChatConversation chatConversation) {
		String imageName = iMediaService.uploadImage(file, chatConversation.getId(), ImageCategory.CHAT_CONVERSATION.name(), null);
		System.out.println("Chat conversation image upload for conversation id: " + chatConversation.getId() + " and image name : " + imageName);
	}

	@Override
	public Integer getChatConversationCountBasedOnChatId(final String chatId) {
		return iChatConversationDao.getChatConversationCountBasedOnChatId(chatId);
	}

	@Override
	public ChatResposneDto getChatListBasedOnEntityType(final String entityType, final String userFromId, final String initiateToId,
			final Integer startIndex, final Integer pageSize) throws ValidationException {
		ChatResposneDto chatResposneDto = new ChatResposneDto();
		List<ChatConversationDto> chatConversationDtos = new ArrayList<>();
		List<ChatConversation> conversationList = iChatConversationDao.getChatListBasedOnEntityType(entityType, userFromId, initiateToId, startIndex, pageSize);
		for (ChatConversation chatConversation : conversationList) {
			ChatConversationDto chatConversationDto = new ChatConversationDto();
			BeanUtils.copyProperties(chatConversation, chatConversationDto);
			chatConversationDto.setChatId(chatConversation.getChat().getId());
			if (chatConversation.getInitiateFromId() != null) {
				UserDto userDto = iUsersService.getUserById(chatConversation.getInitiateFromId());
				chatConversationDto.setInitiateFromName(userDto.getFirstName() + " " + userDto.getLastName());
			}
			if (chatConversation.getInitiateToId() != null) {
				UserDto userDto = iUsersService.getUserById(chatConversation.getInitiateToId());
				chatConversationDto.setInitiateToName(userDto.getFirstName() + " " + userDto.getLastName());
			}
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(chatConversationDto.getId(), ImageCategory.CHAT_CONVERSATION.name(), null,
					"en");
			if (storageDTOList != null && !storageDTOList.isEmpty()) {
				chatConversationDto.setStorageDto(storageDTOList.get(0));
			}

			chatConversationDtos.add(chatConversationDto);
		}
		chatResposneDto.setChatConversationDtos(chatConversationDtos);
		return chatResposneDto;
	}

	@Override
	public Integer getChatConversationCountBasedOnEntityType(final String entityType, final String initiateFromId, final String initiateToId) {
		return iChatConversationDao.getChatConversationCountBasedOnEntityType(entityType, initiateFromId, initiateToId);
	}

}
