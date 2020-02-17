package com.seeka.app.controller.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.ChatConversation;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.ChatRequestDto;
import com.seeka.app.dto.ChatResposneDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IChatService;
import com.seeka.app.util.PaginationUtil;

@RestController("chatControllerV1")
@RequestMapping("/api/v1/chat")
public class ChatController {

	@Autowired
	private IChatService iChatService;

	@PostMapping
	public ResponseEntity<?> addChat(@RequestParam(name = "file", required = false) final MultipartFile file,
			@RequestHeader(name = "userId") final String userId, @ModelAttribute @Valid final ChatRequestDto chatRequestDto,
			final BindingResult bindingResult) throws ValidationException {
		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}
		if (file == null && chatRequestDto.getMessage() == null) {
			throw new ValidationException("Either attachment or message is required");
		}
		chatRequestDto.setUserId(userId);
		chatRequestDto.setInitiateFromId(userId);
		ChatConversation chatConversation = iChatService.startChat(chatRequestDto);
		if (file != null) {
			iChatService.addChatImage(file, chatConversation);
		}
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Created Chat successfully").create();
	}

	@PutMapping("{chatId}/assignee/{assigneeId}")
	public ResponseEntity<?> changeChatAssignee(@PathVariable final String chatId, @PathVariable final String assigneeId) throws ValidationException {
		iChatService.changeChatAssignee(chatId, assigneeId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Update chat assignee successfully").create();
	}

	@PutMapping("/{chatConversationId}")
	public ResponseEntity<?> readChatConversation(@PathVariable final String chatConversationId) throws ValidationException {
		iChatService.readChatConversation(chatConversationId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Updated Chat conversation successfully").create();
	}

	@GetMapping("/entityType/{entityType}/entityId/{entityId}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getChatListBasedOnEntityTypeAndEntityId(@PathVariable final String entityType, @PathVariable final String entityId,
			@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		ChatResposneDto chatResposneDto = iChatService.getChatListBasedOnEntityIdAndEntityType(entityId, entityType, startIndex, pageSize);
		Integer totalCount = iChatService.getChatConversationCountBasedOnChatId(chatResposneDto.getId());
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Chat list successfully");
		responseMap.put("data", chatResposneDto);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

	@GetMapping("/entityType/{entityType}/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<?> getChatListBasedOnEntityType(@PathVariable final String entityType, @PathVariable final Integer pageNumber,
			@PathVariable final Integer pageSize, @RequestParam final String initiateFromId, @RequestParam final String initiateToId)
			throws ValidationException {
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		ChatResposneDto chatResposneDto = iChatService.getChatListBasedOnEntityType(entityType, initiateFromId, initiateToId, startIndex, pageSize);
		Integer totalCount = iChatService.getChatConversationCountBasedOnEntityType(entityType, initiateFromId, initiateToId);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		Map<String, Object> responseMap = new HashMap<>(10);
		responseMap.put("status", HttpStatus.OK);
		responseMap.put("message", "Get Chat list successfully");
		responseMap.put("data", chatResposneDto);
		responseMap.put("totalCount", totalCount);
		responseMap.put("pageNumber", paginationUtilDto.getPageNumber());
		responseMap.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		responseMap.put("hasNextPage", paginationUtilDto.isHasNextPage());
		responseMap.put("totalPages", paginationUtilDto.getTotalPages());
		return new ResponseEntity<>(responseMap, HttpStatus.OK);
	}

}
