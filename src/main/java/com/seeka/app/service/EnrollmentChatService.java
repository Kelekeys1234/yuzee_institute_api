package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentChat;
import com.seeka.app.bean.EnrollmentChatConversation;
import com.seeka.app.bean.EnrollmentChatMedia;
import com.seeka.app.constant.EnrollmentInitiator;
import com.seeka.app.dao.IEnrollmentChatConversationDao;
import com.seeka.app.dao.IEnrollmentChatDao;
import com.seeka.app.dao.IEnrollmentChatMediaDao;
import com.seeka.app.dao.IEnrollmentDao;
import com.seeka.app.dto.EnrollmentChatConversationDto;
import com.seeka.app.dto.EnrollmentChatRequestDto;
import com.seeka.app.dto.EnrollmentChatResposneDto;
import com.seeka.app.dto.ImageResponseDto;
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
	private IMediaService iMediaService;

	@Autowired
	private IEnrollmentChatMediaDao iEnrollmentChatImagesDao;

	@Value("${s3.url}")
	private String s3URL;

	@Autowired
	private IUsersService iUsersService;

	@Override
	public EnrollmentChatConversation startEnrollmentChat(final EnrollmentChatRequestDto enrollmentChatRequestDto) throws ValidationException {

		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentChatRequestDto.getEnrollmentId());
		if (enrollment == null) {
			throw new ValidationException("Enrollment not found for id " + enrollmentChatRequestDto.getEnrollmentId());
		}
		/**
		 * One Enrollment can have only one chat
		 */
		EnrollmentChat existingEnrollmentChat = iEnrollmentChatDao.getEnrollmentChatBasedOnEnrollmentId(enrollmentChatRequestDto.getEnrollmentId());
		if (existingEnrollmentChat != null) {
			throw new ValidationException("Enrollment chat exists for enrollment id " + enrollmentChatRequestDto.getEnrollmentId());
		}
		EnrollmentChat enrollmentChat = new EnrollmentChat();
		enrollmentChat.setCreatedOn(new Date());
		enrollmentChat.setUserId(enrollmentChatRequestDto.getUserId());
		enrollmentChat.setEnrollment(enrollment);
		iEnrollmentChatDao.save(enrollmentChat);
		enrollmentChatRequestDto.setId(enrollmentChat.getId());

		/**
		 * Add Enrollment chat conversation
		 */
		EnrollmentChatConversation enrollmentChatConversation = new EnrollmentChatConversation();
		enrollmentChatConversation.setCreatedOn(new Date());
		enrollmentChatConversation.setEnrollmentChat(enrollmentChat);
		enrollmentChatConversation.setInitiateFrom(enrollmentChatRequestDto.getInitiateFrom());
		enrollmentChatConversation.setInitiateFromId(enrollmentChatRequestDto.getInitiateFromId());
		enrollmentChatConversation.setMessage(enrollmentChatRequestDto.getMessage());
		enrollmentChatConversation.setStatus("SENT");
		iEnrollmentChatConversationDao.save(enrollmentChatConversation);
		if (enrollmentChatRequestDto.getInitiateFrom().equals(EnrollmentInitiator.SEEKA.name())) {
			sentPushNotificationForEnrollmentConversation(enrollmentChatRequestDto.getMessage(), enrollmentChatRequestDto.getUserId());
		}
		return enrollmentChatConversation;
	}

	@Override
	public EnrollmentChatConversation addEnrollmentChatConversation(final EnrollmentChatConversationDto enrollmentChatConversationDto)
			throws ValidationException {
		EnrollmentChat enrollmentChat = iEnrollmentChatDao.getEnrollmentChat(enrollmentChatConversationDto.getEnrollmentChatId());
		if (enrollmentChat == null) {
			throw new ValidationException("Enrollment chat not found for id " + enrollmentChatConversationDto.getEnrollmentChatId());
		}
		EnrollmentChatConversation enrollmentChatConversation = new EnrollmentChatConversation();
		enrollmentChatConversation.setCreatedOn(new Date());
		enrollmentChatConversation.setEnrollmentChat(enrollmentChat);
		enrollmentChatConversation.setInitiateFrom(enrollmentChatConversationDto.getInitiateFrom());
		enrollmentChatConversation.setInitiateFromId(enrollmentChatConversationDto.getInitiateFromId());
		enrollmentChatConversation.setMessage(enrollmentChatConversationDto.getMessage());
		enrollmentChatConversation.setStatus("SENT");
		iEnrollmentChatConversationDao.save(enrollmentChatConversation);
		if (enrollmentChatConversationDto.getInitiateFrom().equals(EnrollmentInitiator.SEEKA.name())) {
			sentPushNotificationForEnrollmentConversation(enrollmentChatConversationDto.getMessage(), enrollmentChat.getUserId());
		}
		return enrollmentChatConversation;
	}

	private void sentPushNotificationForEnrollmentConversation(final String message, final BigInteger userId) {
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
	public void addEnrollmentChatImage(final MultipartFile file, final EnrollmentChatConversation enrollmentChatConversation) {
		String imageName = iMediaService.uploadImage(file, enrollmentChatConversation.getId(), ImageCategory.ENROLLMENT.name(), null);
		EnrollmentChatMedia enrollmentChatImages = new EnrollmentChatMedia();
		enrollmentChatImages.setEnrollmentChatConversation(enrollmentChatConversation);
		enrollmentChatImages.setImageName(imageName);
		iEnrollmentChatImagesDao.save(enrollmentChatImages);
	}

	@Override
	public EnrollmentChatResposneDto getEnrollmentChatListBasedOnEnrollment(final BigInteger enrollmentId) throws ValidationException {
		EnrollmentChat enrollmentChat = iEnrollmentChatDao.getEnrollmentChatBasedOnEnrollmentId(enrollmentId);
		if (enrollmentChat == null) {
			throw new ValidationException("Enrollment chat not found for enrollment Id: " + enrollmentId);
		}
		EnrollmentChatResposneDto enrollmentChatResposneDto = new EnrollmentChatResposneDto();
		BeanUtils.copyProperties(enrollmentChat, enrollmentChatResposneDto);
		enrollmentChatResposneDto.setEnrollmentId(enrollmentChat.getEnrollment().getId());
		List<EnrollmentChatConversationDto> chatConversationDtos = new ArrayList<>();
		List<EnrollmentChatConversation> conversationList = iEnrollmentChatConversationDao
				.getEnrollmentChatConversationBasedOnEnrollmentChatId(enrollmentChat.getId());
		for (EnrollmentChatConversation enrollmentChatConversation : conversationList) {
			EnrollmentChatConversationDto enrollmentChatConversationDto = new EnrollmentChatConversationDto();
			BeanUtils.copyProperties(enrollmentChatConversation, enrollmentChatConversationDto);
			enrollmentChatConversationDto.setEnrollmentChatId(enrollmentChatConversation.getEnrollmentChat().getId());

			EnrollmentChatMedia enrollmentChatMedia = iEnrollmentChatImagesDao.getEnrollmentImage(enrollmentChatConversationDto.getId());
			ImageResponseDto imageResponseDto = new ImageResponseDto();
			imageResponseDto.setCategory(ImageCategory.ENROLLMENT.name());
			imageResponseDto.setCategoryId(enrollmentChatConversationDto.getId());
			imageResponseDto.setId(enrollmentChatMedia.getId());
			imageResponseDto.setImageName(enrollmentChatMedia.getImageName());
			imageResponseDto.setBaseUrl(s3URL);
			enrollmentChatConversationDto.setImageResponseDto(imageResponseDto);
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

}
