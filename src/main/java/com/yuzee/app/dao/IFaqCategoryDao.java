package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.FaqCategory;

public interface IFaqCategoryDao {

	FaqCategory getFaqCategoryDetail(String faqCategoryId);

	void saveFaqCategory(FaqCategory faqCategory);

	void updateFaqCategory(FaqCategory faqCategory);

	List<FaqCategory> getFaqCategoryList(Integer startIndex, Integer pageSize);

	int getFaqCategoryCount();

	FaqCategory getFaqCategoryBasedOnName(String name, String faqCategoryId);
}
