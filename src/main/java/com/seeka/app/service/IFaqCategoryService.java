package com.seeka.app.service;

import java.util.List;

import com.seeka.app.bean.FaqCategory;
import com.seeka.app.exception.ValidationException;

public interface IFaqCategoryService {

	void addFaqCategory(FaqCategory faqCategory) throws ValidationException;

	void updateFaqCategory(FaqCategory faqCategory, String faqCategoryId) throws ValidationException;

	void deleteFaqCategory(String faqCategoryId) throws ValidationException;

	List<FaqCategory> getFaqCategoryList(Integer startIndex, Integer pageSize);

	int getFaqCategoryCount();

	FaqCategory getFaqCategoryDetail(String faqCategoryId);

}
