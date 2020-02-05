package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.FaqSubCategoryDto;
import com.seeka.app.exception.ValidationException;

public interface IFaqSubCategoryService {

	void addFaqSubCategory(FaqSubCategoryDto faqSubCategoryDto) throws ValidationException;

	void updateFaqSubCategory(FaqSubCategoryDto faqSubCategoryDto, String faqSubCategoryId) throws ValidationException;

	void deleteFaqSubCategory(String faqSubCategoryId) throws ValidationException;

	List<FaqSubCategoryDto> getFaqSubCategoryList(Integer startIndex, Integer pageSize, String faqCategoryId);

	int getFaqSubCategoryCount(String faqCategoryId);

	FaqSubCategoryDto getFaqSubCategoryDetail(String faqSubCategoryId) throws ValidationException;

}
