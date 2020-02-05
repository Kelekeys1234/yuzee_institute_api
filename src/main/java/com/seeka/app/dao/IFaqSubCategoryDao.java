package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.FaqSubCategory;

public interface IFaqSubCategoryDao {

	FaqSubCategory getFaqSubCategoryDetail(String faqSubCategoryId);

	void saveFaqSubCategory(FaqSubCategory faqSubCategory);

	void updateFaqSubCategory(FaqSubCategory faqSubCategory);

	List<FaqSubCategory> getFaqSubCategoryList(Integer startIndex, Integer pageSize, String faqCategoryId);

	int getFaqSubCategoryCount(String faqCategoryId);

	FaqSubCategory getFaqSubCategoryBasedOnName(String name, String faqCategoryId, String faqSubCategoryId);
}
