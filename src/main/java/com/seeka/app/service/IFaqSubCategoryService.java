package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.dto.FaqSubCategoryDto;
import com.seeka.app.exception.ValidationException;

public interface IFaqSubCategoryService {

	void addFaqSubCategory(FaqSubCategoryDto faqSubCategoryDto) throws ValidationException;

	void updateFaqSubCategory(FaqSubCategoryDto faqSubCategoryDto, BigInteger faqSubCategoryId) throws ValidationException;

	void deleteFaqSubCategory(BigInteger faqSubCategoryId) throws ValidationException;

	List<FaqSubCategoryDto> getFaqSubCategoryList(Integer startIndex, Integer pageSize);

	int getFaqSubCategoryCount();

	FaqSubCategoryDto getFaqSubCategoryDetail(BigInteger faqSubCategoryId) throws ValidationException;

}
