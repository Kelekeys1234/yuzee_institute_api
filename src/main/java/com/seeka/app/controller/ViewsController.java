package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.UserViewData;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.UserViewDataRequestDto;
import com.seeka.app.dto.ViewEntityDto;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IViewService;

@RestController
@RequestMapping("/view")
public class ViewsController {

	@Autowired
	private IViewService iViewService;

	@PostMapping
	public ResponseEntity<?> createUserViewData(@RequestHeader final BigInteger userId, @RequestBody final UserViewDataRequestDto userViewDataRequestDto)
			throws ValidationException {
		userViewDataRequestDto.setUserId(userId);
		iViewService.createUserViewData(userViewDataRequestDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Added to user view data.").create();
	}

	@GetMapping
	public ResponseEntity<?> getUserViewData(@RequestParam(name = "userId") final BigInteger userId, @RequestParam final String entityType,
			@RequestParam(name = "isUnique", required = false) final boolean isUnique) throws ValidationException {
		List<UserViewData> userViewDatas = iViewService.getUserViewData(userId, entityType, isUnique);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Get user view data.").setData(userViewDatas).create();
	}

	@GetMapping(value = "/user")
	public ResponseEntity<?> getUserViewDataCountBasedOnUserId(@RequestHeader final BigInteger userId, @RequestParam final BigInteger entityId,
			@RequestParam final String entityType) throws ValidationException {
		int count = iViewService.getUserViewDataCountBasedOnUserId(userId, entityId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Get user view count.").setData(count).create();
	}

	@GetMapping(value = "/entity")
	public ResponseEntity<?> getUserViewDataCountBasedOnEntityId(@RequestParam final BigInteger entityId, @RequestParam final String entityType)
			throws ValidationException {
		int count = iViewService.getUserViewDataCountBasedOnEntityId(entityId, entityType);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Get user view count based on entity.").setData(count).create();
	}

	@GetMapping(value = "/visit")
	public ResponseEntity<?> userVisited(@RequestHeader final BigInteger userId, @RequestParam final BigInteger entityId, @RequestParam final String entityType)
			throws ValidationException {
		int count = iViewService.getUserViewDataCountBasedOnUserId(userId, entityId, entityType);
		boolean flag = count > 0;
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(" visited entity.").setData(flag).create();
	}

	@PostMapping(value = "/visit/entity")
	public ResponseEntity<?> userVisitedBasedonEntityId(@RequestHeader final BigInteger userId, @RequestBody final ViewEntityDto viewEntityDto)
			throws ValidationException {
		List<BigInteger> viewedEntityIds = iViewService.getUserViewDataBasedOnEntityIdList(userId, viewEntityDto.getEntityType(), viewEntityDto.getEntityIds());
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage(" visited entity.").setData(viewedEntityIds).create();
	}

}
