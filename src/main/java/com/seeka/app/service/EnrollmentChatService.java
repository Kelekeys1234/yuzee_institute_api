package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentChat;
import com.seeka.app.bean.EnrollmentChatConversation;
import com.seeka.app.constant.EnrollmentInitiator;
import com.seeka.app.dao.IEnrollmentChatConversationDao;
import com.seeka.app.dao.IEnrollmentChatDao;
import com.seeka.app.dao.IEnrollmentDao;
import com.seeka.app.dto.EnrollmentChatConversationDto;
import com.seeka.app.dto.EnrollmentChatRequestDto;
import com.seeka.app.dto.EnrollmentChatResposneDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class EnrollmentChatService implements IEnrollmentChatService {

	@Autowired
	private IEnrollmentChatDao iEnrollmentChatDao;

	@Autowired
	private IEnrollmentDao iEnrollmentDao;

	@Autowired
	private IEnrollmentChatConversationDao iEnrollmentChatConversationDao;

	@Autowired
	private IStorageService iStorageService;

	@Autowired
	private IUsersService iUsersService;

	@Autowired
	private IMediaService iMediaService;

	@Override
	public EnrollmentChatConversation startEnrollmentChat(final EnrollmentChatRequestDto enrollmentChatRequestDto) throws ValidationException {

		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentChatRequestDto.getEnrollmentId());
		if (enrollment == null) {
			throw new ValidationException("Enrollment not found for id " + enrollmentChatRequestDto.getEnrollmentId());
		}
		EnrollmentChatConversation enrollmentChatConversation;
		/**
		 * One Enrollment can have only one chat
		 */
		EnrollmentChat existingEnrollmentChat = iEnrollmentChatDao.getEnrollmentChatBasedOnEnrollmentId(enrollmentChatRequestDto.getEnrollmentId());
		if (existingEnrollmentChat != null) {
			enrollmentChatConversation = addEnrollmentChatConversation(enrollmentChatRequestDto, existingEnrollmentChat);
		} else {
			EnrollmentChat enrollmentChat = new EnrollmentChat();
			enrollmentChat.setCreatedOn(new Date());
			enrollmentChat.setUserId(enrollmentChatRequestDto.getUserId());
			enrollmentChat.setEnrollment(enrollment);
			iEnrollmentChatDao.save(enrollmentChat);
			enrollmentChatRequestDto.setId(enrollmentChat.getId());

			/**
			 * Add Enrollment chat conversation
			 */
			enrollmentChatConversation = new EnrollmentChatConversation();
			enrollmentChatConversation.setCreatedOn(new Date());
			enrollmentChatConversation.setEnrollmentChat(enrollmentChat);
			enrollmentChatConversation.setInitiateFrom(enrollmentChatRequestDto.getInitiateFrom());
			enrollmentChatConversation.setInitiateFromId(enrollmentChatRequestDto.getInitiateFromId());
			enrollmentChatConversation.setMessage(enrollmentChatRequestDto.getMessage());
			enrollmentChatConversation.setStatus("SENT");
			iEnrollmentChatConversationDao.save(enrollmentChatConversation);
		}

		if (enrollmentChatRequestDto.getInitiateFrom().equals(EnrollmentInitiator.SEEKA.name())) {
			sentPushNotificationForEnrollmentConversation(enrollmentChatRequestDto.getMessage(), enrollmentChatRequestDto.getUserId());
		}
		return enrollmentChatConversation;
	}

	private EnrollmentChatConversation addEnrollmentChatConversation(EnrollmentChatRequestDto enrollmentChatRequestDto, EnrollmentChat existingEnrollmentChat)
			throws ValidationException {
		EnrollmentChatConversation enrollmentChatConversation = new EnrollmentChatConversation();
		enrollmentChatConversation.setCreatedOn(new Date());
		enrollmentChatConversation.setEnrollmentChat(existingEnrollmentChat);
		enrollmentChatConversation.setInitiateFrom(enrollmentChatRequestDto.getInitiateFrom());
		enrollmentChatConversation.setInitiateFromId(enrollmentChatRequestDto.getInitiateFromId());
		enrollmentChatConversation.setMessage(enrollmentChatRequestDto.getMessage());
		enrollmentChatConversation.setStatus("SENT");
		iEnrollmentChatConversationDao.save(enrollmentChatConversation);
		return enrollmentChatConversation;
	}

	private void sentPushNotificationForEnrollmentConversation(final String message, final BigInteger userId) throws ValidationException {
		iUsersService.sendPushNotification(userId, message);
	}

	@Override
	public void changeEnrollmentChatAssignee(final BigInteger enrollmentChatId, final BigInteger assigneeId) throws ValidationException {
		EnrollmentChat enrollmentChat = iEnrollmentChatDao.getEnrollmentChat(enrollmentChatId);
		if (enrollmentChat == null) {
			throw new ValidationException("Enrollment chat not found for id " + enrollmentChatId);
		}
		enrollmentChat.setAssigneeId(assigneeId);
		iEnrollmentChatDao.update(enrollmentChat);
	}

	@Override
	public EnrollmentChatResposneDto getEnrollmentChatListBasedOnEnrollment(final BigInteger enrollmentId) throws ValidationException {
		EnrollmentChat enrollmentChat = iEnrollmentChatDao.getEnrollmentChatBasedOnEnrollmentId(enrollmentId);
		if (enrollmentChat == null) {
			EnrollmentChatResposneDto enrollmentChatResposneDto = new EnrollmentChatResposneDto();
			enrollmentChatResposneDto.setEnrollmentId(enrollmentId);
			return enrollmentChatResposneDto;
		}
		EnrollmentChatResposneDto enrollmentChatResposneDto = new EnrollmentChatResposneDto();
		BeanUtils.copyProperties(enrollmentChat, enrollmentChatResposneDto);
		enrollmentChatResposneDto.setEnrollmentId(enrollmentChat.getEnrollment().getId());
		if (enrollmentChat.getUserId() != null) {
			UserDto userDto = iUsersService.getUserById(enrollmentChat.getUserId());
			enrollmentChatResposneDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		}

		List<EnrollmentChatConversationDto> chatConversationDtos = new ArrayList<>();
		List<EnrollmentChatConversation> conversationList = iEnrollmentChatConversationDao
				.getEnrollmentChatConversationBasedOnEnrollmentChatId(enrollmentChat.getId());
		for (EnrollmentChatConversation enrollmentChatConversation : conversationList) {
			EnrollmentChatConversationDto enrollmentChatConversationDto = new EnrollmentChatConversationDto();
			BeanUtils.copyProperties(enrollmentChatConversation, enrollmentChatConversationDto);
			enrollmentChatConversationDto.setEnrollmentChatId(enrollmentChatConversation.getEnrollmentChat().getId());
			if (enrollmentChatConversation.getInitiateFromId() != null) {
				UserDto userDto = iUsersService.getUserById(enrollmentChatConversation.getInitiateFromId());
				enrollmentChatConversationDto.setInitiateFromName(userDto.getFirstName() + " " + userDto.getLastName());
			}
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(enrollmentChatConversationDto.getId(),
					ImageCategory.ENROLLMENT_CHAT_CONVERSATION.name(), null, "en");
			if (storageDTOList != null && !storageDTOList.isEmpty()) {
				enrollmentChatConversationDto.setStorageDto(storageDTOList.get(0));
			}

			chatConversationDtos.add(enrollmentChatConversationDto);
		}
		enrollmentChatResposneDto.setChatConversationDtos(chatConversationDtos);
		return enrollmentChatResposneDto;
	}

	@Override
	public List<EnrollmentChatResposneDto> getEnrollmentChatList(final Integer startIndex, final Integer pageSize) {
		List<EnrollmentChat> enrollmentChats = iEnrollmentChatDao.getEnrollmentChatList(startIndex, pageSize);
		List<EnrollmentChatResposneDto> enrollmentChatDtos = new ArrayList<>();
		for (EnrollmentChat enrollmentChat : enrollmentChats) {
			EnrollmentChatResposneDto enrollmentChatDto = new EnrollmentChatResposneDto();
			BeanUtils.copyProperties(enrollmentChat, enrollmentChatDto);
			enrollmentChatDto.setEnrollmentId(enrollmentChat.getEnrollment().getId());
			enrollmentChatDtos.add(enrollmentChatDto);
		}
		return enrollmentChatDtos;
	}

	@Override
	public void readEnrollmentChatConversation(final BigInteger enrollmentChatConversationId) throws ValidationException {
		EnrollmentChatConversation enrollmentChatConversation = iEnrollmentChatConversationDao.getEnrollmentChatConversation(enrollmentChatConversationId);
		if (enrollmentChatConversation == null) {
			throw new ValidationException("Enrollment chat conversation not found for id" + enrollmentChatConversationId);
		}
		enrollmentChatConversation.setStatus("READ");
		iEnrollmentChatConversationDao.update(enrollmentChatConversation);
	}

	@Override
	public void addEnrollmentChatImage(MultipartFile file, EnrollmentChatConversation enrollmentChatConversation) {
		String imageName = iMediaService.uploadImage(file, enrollmentChatConversation.getId(), ImageCategory.ENROLLMENT_CHAT_CONVERSATION.name(), null);
		System.out.println(
				"Enrollment chat conversation image upload for conversation id: " + enrollmentChatConversation.getId() + " and image name : " + imageName);
	}

}
