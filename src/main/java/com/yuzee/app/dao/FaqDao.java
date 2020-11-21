package com.yuzee.app.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Faq;
import com.yuzee.app.exception.ValidationException;

public interface FaqDao {

	void saveOrUpdate(Faq faq) throws ValidationException;

	Optional<Faq> getById(String id);

	Page<Faq> getFaqList(String entityId, String faqCategoryId, String faqSubCategoryId, String searchKeyword,
			Pageable pageable);

	void deleteFaqById(String faqId);
}