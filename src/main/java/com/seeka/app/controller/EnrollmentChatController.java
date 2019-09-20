package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.EnrollmentChatConversation;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EnrollmentChatConversationDto;
import com.seeka.app.dto.EnrollmentChatRequestDto;
import com.seeka.app.dto.EnrollmentChatResposneDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IEnrollmentChatService;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/enrollment/chat")
public class EnrollmentChatController {

	@Autowired
	private IEnrollmentChatService iEnrollmentChatService;

	@PostMapping
	public ResponseEntity<?> startEnrollmentChat(@RequestParam(name = "file", required = false) final MultipartFile file,
			@ModelAttribute final EnrollmentChatRequestDto enrollmentChatRequestDto) throws ValidationException {
		EnrollmentChatConversation enrollmentChatConversation = iEnrollmentChatService.startEnrollmentChat(enrollmentChatRequestDto);
		if (file != null) {
			iEnrollmentChatService.addEnrollmentChatImage(file, enrollmentChatConversation);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created enrollment Chat successfully").create();
	}

	@PostMapping("/conversation")
	public ResponseEntity<?> addEnrollmentChatConversation(@RequestParam(name = "file", required = false) final MultipartFile file,
			@ModelAttribute final EnrollmentChatConversationDto enrollmentChatConversationDto) throws ValidationException {
		EnrollmentChatConversation enrollmentChatConversation = iEnrollmentChatService.addEnrollmentChatConversation(enrollmentChatConversationDto);
		if (file != null) {
			iEnrollmentChatService.addEnrollmentChatImage(file, enrollmentChatConversation);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Add enrollment chat conversation successfully").create();
	}

	@PutMapping("{enrollmentChatId}/assignee/{assigneeId}")
	public ResponseEntity<?> changeEnrollmentChatAssignee(@PathVariable final BigInteger enrollmentChatId, @PathVariable final BigInteger assigneeId)
			throws ValidationException {
		iEnrollmentChatService.changeEnrollmentChatAssignee(enrollmentChatId, assigneeId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Update enrollment chat assignee successfully").create();
	}

	@PutMapping("/{enrollmentChatConversationId}")
	public ResponseEntity<?> readEnrollmentChatConversation(@PathVariable final BigInteger enrollmentChatConversationId) throws ValidationException {
		iEnrollmentChatService.readEnrollmentChatConversation(enrollmentChatConversationId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated enrollment Chat conversation successfully").create();
	}

	@GetMapping("/{enrollmentId}")
	public ResponseEntity<?> getEnrollmentChatListBasedOnEnrollment(@PathVariable final BigInteger enrollmentId) throws ValidationException {
		EnrollmentChatResposneDto resultEnrollmentChatDtos = iEnrollmentChatService.getEnrollmentChatListBasedOnEnrollment(enrollmentId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(resultEnrollmentChatDtos)
				.setMessage("Get enrollment Chat list successfully").create();
	}

	@GetMapping("/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getEnrollmentChatList(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws ValidationException {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<EnrollmentChatResposneDto> resultEnrollmentChatDtos = iEnrollmentChatService.getEnrollmentChatList(startIndex, pageSize);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(resultEnrollmentChatDtos)
				.setMessage("Get enrollment Chat list successfully").create();
	}

}
