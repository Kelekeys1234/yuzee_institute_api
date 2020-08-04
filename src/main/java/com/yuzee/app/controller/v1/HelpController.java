package com.yuzee.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuzee.app.bean.Help;
import com.yuzee.app.dto.HelpAnswerDto;
import com.yuzee.app.dto.HelpCategoryDto;
import com.yuzee.app.dto.HelpDto;
import com.yuzee.app.dto.HelpSubCategoryDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.endpoint.HelpInterface;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.HelpProcessor;
import com.yuzee.app.util.PaginationUtil;

@RestController("helpControllerV1")
public class HelpController implements HelpInterface {

	@Autowired
	private HelpProcessor helpProcessor;

	@Override
	public ResponseEntity<?> getAll(final Integer pageNumber, final Integer pageSize) throws Exception {
		PaginationResponseDto paginationResponseDto = helpProcessor.getAll(pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Help Fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> save(final HelpDto helpDto, final String userId) throws Exception {
		helpProcessor.saveHelp(helpDto, userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Help Saved Successfully").create();
	}

	@Override
	public ResponseEntity<?> get(final String id) throws Exception {
		HelpDto helpDto = helpProcessor.getYuzeeHelp(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(helpDto)
				.setMessage("Help Fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> getHelpByCategoryId(final String id) throws Exception {
		List<HelpDto> helpDtos = helpProcessor.getHelpByCategory(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(helpDtos)
				.setMessage("Help Category Fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> update(final String id, final HelpDto helpDto, @RequestHeader final String userId) {
		helpProcessor.updateHelp(helpDto, id, userId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Help Updated Successfully").create();
	}

	@Override
	public ResponseEntity<?> saveCategory(final HelpCategoryDto categoryDto) throws Exception {
		helpProcessor.saveHelpCategory(categoryDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Help Category Saved Successfully").create();
	}
	
	@Override
	public ResponseEntity<?> getCatgeory(final String id) throws Exception {
		HelpCategoryDto helpCategoryDto = helpProcessor.getCategory(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(helpCategoryDto)
				.setMessage("Help Category Fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> saveSubCategory(final HelpSubCategoryDto subCategoryDto) throws Exception {
		helpProcessor.saveHelpSubCategory(subCategoryDto);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Help SubCategory Saved Successfully").create();
	}

	@Override
	public ResponseEntity<?> getSubCatgeory(final String id) throws Exception {
		HelpSubCategoryDto helpSubCategoryDto = helpProcessor.getSubCategory(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(helpSubCategoryDto)
				.setMessage("Help SubCategory fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> getSubCategoryByCategory(final String categoryId, final Integer pageNumber,
			final Integer pageSize) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<HelpSubCategoryDto> subCategoryDtos = helpProcessor.getSubCategoryByCategory(categoryId, startIndex, pageSize);
		int totalCount = helpProcessor.getSubCategoryCount(categoryId);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setResponse(subCategoryDtos);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Help SubCategory fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> getSubCategoryCount() throws Exception {
		List<HelpSubCategoryDto> helpSubCategories = helpProcessor.getSubCategoryCount();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(helpSubCategories)
				.setMessage("Help SubCategory Count fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> saveAnswer(final MultipartFile file, final HelpAnswerDto helpAnswerDto) throws Exception {
		helpProcessor.saveAnswer(helpAnswerDto, file);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Help Answer saved Successfully").create();
	}

	@Override
	public ResponseEntity<?> getAnswerByHelpId(final String helpId) throws Exception {
		List<HelpAnswerDto> helpAnswerDtos = helpProcessor.getAnswerByHelpId(helpId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(helpAnswerDtos)
				.setMessage("Help Answer fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> getCategory(final Integer pageNumber, final Integer pageSize) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<HelpCategoryDto> helpCategoryDtos = helpProcessor.getCategory(startIndex, pageSize);
		int totalCount = helpProcessor.getCategoryCount();
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setResponse(helpCategoryDtos);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("Help Category fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> delete(final String id) throws Exception {
		helpProcessor.delete(id);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Help deleted Successfully").create();
	}

	@Override
	public ResponseEntity<?> updateStatus(final String id, final String userId,
			final String status, final String assignedUserId) throws Exception {
		helpProcessor.updateStatus(id, assignedUserId, status);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK)
				.setMessage("Help status updated Successfully").create();
	}

	@Override
	public ResponseEntity<?> filter(final String status, final String mostRecent,
			final String categoryId) throws Exception {
		List<HelpDto> helpDtos = helpProcessor.filter(status, mostRecent, categoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(helpDtos)
				.setMessage("Help fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> getUserHelpList(final String userId, final Integer pageNumber,
			final Integer pageSize, final boolean isArchive) throws Exception {
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<HelpDto> helps = helpProcessor.getUserHelpList(userId, startIndex, pageSize, isArchive);
		int totalCount = helpProcessor.getUserHelpCount(userId, isArchive);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		paginationResponseDto.setTotalCount(totalCount);
		paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		paginationResponseDto.setResponse(helps);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(paginationResponseDto)
				.setMessage("User Help list fetched Successfully").create();
	}

	@Override
	public ResponseEntity<?> setIsFavourite(final String userId, final String id, final boolean isFavourite) throws NotFoundException {
		helpProcessor.setIsFavouriteFlag(id, isFavourite);
		return new GenericResponseHandlers.Builder().setMessage("Updated Successfuly").setStatus(HttpStatus.OK).create();
	}

	@Override
	public ResponseEntity<?> getOptionOnUserSearch(final String userId, final String searchString) throws ValidationException {
		List<String> questionList = helpProcessor.getRelatedSearchQuestions(searchString);
		return new GenericResponseHandlers.Builder().setMessage("Related Questions Displayed Successfully").setStatus(HttpStatus.OK).setData(questionList)
				.create();
	}
}