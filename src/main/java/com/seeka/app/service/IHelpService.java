package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.dto.HelpAnswerDto;
import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;

public interface IHelpService {

	public Map<String, Object> save(@Valid HelpDto helpDto, BigInteger userId);

	public Map<String, Object> get(BigInteger id);

	public Map<String, Object> update(HelpDto helpDto, BigInteger id, BigInteger userId);

	public Map<String, Object> getAll(Integer pageNumber, Integer pageSize);

	public Map<String, Object> save(@Valid HelpCategoryDto helpCategoryDto);

	public Map<String, Object> save(@Valid HelpSubCategoryDto helpSubCategoryDto);

	public Map<String, Object> getCategory(BigInteger id);

	public Map<String, Object> getSubCategory(BigInteger id);

	public Map<String, Object> getSubCategoryByCategory(BigInteger id);

	public Map<String, Object> getHelpByCategory(BigInteger id);

	public Map<String, Object> getSubCategoryCount();

	public Map<String, Object> saveAnswer(@Valid HelpAnswerDto helpAnswerDto);
	
	public Map<String, Object> getAnswerByHelpId(BigInteger userId);

    public Map<String, Object> getCategory();

    public Map<String, Object> delete(@Valid BigInteger id);

    public Map<String, Object>  updateStatus(BigInteger id, BigInteger userId, String status);

    public Map<String, Object> filter(String status, String mostRecent, BigInteger categoryId);

}
