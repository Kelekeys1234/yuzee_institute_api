package com.seeka.app.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.SeekaHelp;
import com.seeka.app.dto.HelpAnswerDto;
import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;

public interface IHelpService {

	Map<String, Object> save(@Valid HelpDto helpDto, String userId);

	Map<String, Object> get(String id);

	Map<String, Object> update(HelpDto helpDto, String id, String userId);

	Map<String, Object> getAll(Integer pageNumber, Integer pageSize);

	Map<String, Object> save(@Valid HelpCategoryDto helpCategoryDto);

	Map<String, Object> save(@Valid HelpSubCategoryDto helpSubCategoryDto);

	Map<String, Object> getCategory(String id);

	Map<String, Object> getSubCategory(String id);

	List<HelpSubCategoryDto> getSubCategoryByCategory(String id, Integer startIndex, Integer pageSize);

	Map<String, Object> getHelpByCategory(String id);

	Map<String, Object> getSubCategoryCount();

	Map<String, Object> saveAnswer(@Valid HelpAnswerDto helpAnswerDto, MultipartFile file);

	Map<String, Object> getAnswerByHelpId(String userId);

	List<HelpCategoryDto> getCategory(Integer startIndex, Integer pageSize);

	Map<String, Object> delete(@Valid String id);

	Map<String, Object> updateStatus(String id, String userId, String status);

	Map<String, Object> filter(String status, String mostRecent, String categoryId);

	List<SeekaHelp> getUserHelpList(String userId, int startIndex, Integer pageSize, Boolean isArchive);

	int getUserHelpCount(String userId, Boolean isArchive);

	void setIsFavouriteFlag(String id, boolean isFavourite) throws NotFoundException;

	int getCategoryCount();

	int getSubCategoryCount(String categoryId);

	void archiveHelpSupport(String entityId, boolean isArchive);

	List<String> getRelatedSearchQuestions(String searchString) throws ValidationException;

}
