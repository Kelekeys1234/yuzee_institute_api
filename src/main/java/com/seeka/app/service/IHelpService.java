package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.dto.HelpCategoryDto;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.dto.HelpSubCategoryDto;

public interface IHelpService {

	public Map<String, Object> save(@Valid HelpDto helpDto);

	public Map<String, Object> get(BigInteger id);

	public Map<String, Object> update(HelpDto helpDto, BigInteger id);

	public Map<String, Object> getAll(Integer pageNumber, Integer pageSize);

	public Map<String, Object> save(@Valid HelpCategoryDto helpCategoryDto);

	public Map<String, Object> save(@Valid HelpSubCategoryDto helpSubCategoryDto);

	public Map<String, Object> getCategory(BigInteger id);

	public Map<String, Object> getSubCategory(BigInteger id);

	public Map<String, Object> getSubCategoryByCategory(BigInteger id);

	public Map<String, Object> getHelpByCategory(BigInteger id);

	public Map<String, Object> getSubCategoryCount();

}
