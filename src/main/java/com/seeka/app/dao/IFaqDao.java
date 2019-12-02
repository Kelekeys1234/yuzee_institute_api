package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.bean.Faq;

public interface IFaqDao {

	void save(Faq faq);

	void update(Faq existingFaq);

	Faq getFaqDetail(BigInteger faqId);

	List<Faq> getFaqList(Integer startIndex, Integer pageSize, BigInteger faqCategoryId, BigInteger faqSubCategoryId, String sortByField, String sortByType,
			String searchKeyword);

	int getFaqCount(BigInteger faqCategoryId, BigInteger faqSubCategoryId, String searchKeyword);

}
