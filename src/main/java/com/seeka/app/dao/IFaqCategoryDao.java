package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.FaqCategory;

public interface IFaqCategoryDao {

	FaqCategory getFaqCategoryDetail(BigInteger faqCategoryId);

	void saveFaqCategory(FaqCategory faqCategory);

	void updateFaqCategory(FaqCategory faqCategory);

	List<FaqCategory> getFaqCategoryList(Integer startIndex, Integer pageSize);

	int getFaqCategoryCount();

	FaqCategory getFaqCategoryBasedOnName(String name, BigInteger faqCategoryId);
}
