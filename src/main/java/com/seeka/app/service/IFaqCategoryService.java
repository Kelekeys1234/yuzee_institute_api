package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.FaqCategory;
import com.seeka.app.exception.ValidationException;

public interface IFaqCategoryService {

	void addFaqCategory(FaqCategory faqCategory) throws ValidationException;

	void updateFaqCategory(FaqCategory faqCategory, BigInteger faqCategoryId) throws ValidationException;

	void deleteFaqCategory(BigInteger faqCategoryId) throws ValidationException;

	List<FaqCategory> getFaqCategoryList(Integer startIndex, Integer pageSize);

	int getFaqCategoryCount();

	FaqCategory getFaqCategoryDetail(BigInteger faqCategoryId);

}
