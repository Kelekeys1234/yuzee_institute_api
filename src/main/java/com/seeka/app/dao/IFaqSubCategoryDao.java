package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.FaqSubCategory;

public interface IFaqSubCategoryDao {

	FaqSubCategory getFaqSubCategoryDetail(BigInteger faqSubCategoryId);

	void saveFaqSubCategory(FaqSubCategory faqSubCategory);

	void updateFaqSubCategory(FaqSubCategory faqSubCategory);

	List<FaqSubCategory> getFaqSubCategoryList(Integer startIndex, Integer pageSize);

	int getFaqSubCategoryCount();

	FaqSubCategory getFaqSubCategoryBasedOnName(String name, BigInteger faqCategoryId, BigInteger faqSubCategoryId);
}
