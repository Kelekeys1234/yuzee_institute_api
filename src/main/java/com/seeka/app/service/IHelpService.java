package com.seeka.app.service;

import java.math.BigInteger;
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

public interface IHelpService {

	Map<String, Object> save(@Valid HelpDto helpDto, BigInteger userId);

	Map<String, Object> get(BigInteger id);

	Map<String, Object> update(HelpDto helpDto, BigInteger id, BigInteger userId);

	Map<String, Object> getAll(Integer pageNumber, Integer pageSize);

	Map<String, Object> save(@Valid HelpCategoryDto helpCategoryDto);

	Map<String, Object> save(@Valid HelpSubCategoryDto helpSubCategoryDto);

	Map<String, Object> getCategory(BigInteger id);

	Map<String, Object> getSubCategory(BigInteger id);

	List<HelpSubCategoryDto> getSubCategoryByCategory(BigInteger id);

	Map<String, Object> getHelpByCategory(BigInteger id);

	Map<String, Object> getSubCategoryCount();

	Map<String, Object> saveAnswer(@Valid HelpAnswerDto helpAnswerDto, MultipartFile file);

	Map<String, Object> getAnswerByHelpId(BigInteger userId);

	List<HelpCategoryDto> getCategory(Integer startIndex, Integer pageSize);

	Map<String, Object> delete(@Valid BigInteger id);

	Map<String, Object> updateStatus(BigInteger id, BigInteger userId, String status);

	Map<String, Object> filter(String status, String mostRecent, BigInteger categoryId);

	List<SeekaHelp> getUserHelpList(BigInteger userId, int startIndex, Integer pageSize);

	int getUserHelpCount(BigInteger userId);

	void setIsFavouriteFlag(BigInteger id, boolean isFavourite) throws NotFoundException;

	int getCategoryCount();

	int getSubCategoryCount(BigInteger categoryId);

}
