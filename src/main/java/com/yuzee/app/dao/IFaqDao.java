package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Faq;

public interface IFaqDao {

	void save(Faq faq);

	void update(Faq existingFaq);

	Faq getFaqDetail(String faqId);

	List<Faq> getFaqList(Integer startIndex, Integer pageSize, String faqCategoryId, String faqSubCategoryId, String sortByField, String sortByType,
			String searchKeyword);

	int getFaqCount(String faqCategoryId, String faqSubCategoryId, String searchKeyword);
	
	List<Faq> getFaqBasedOnEntityId (String entityId) ;

}
