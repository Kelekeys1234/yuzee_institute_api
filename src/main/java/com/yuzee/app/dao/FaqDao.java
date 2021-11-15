package com.yuzee.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.Faq;
import com.yuzee.app.constant.FaqEntityType;
import com.yuzee.common.lib.dto.CountDto;
import com.yuzee.common.lib.exception.ValidationException;

public interface FaqDao {

	void saveOrUpdate(Faq faq) throws ValidationException;

	Optional<Faq> getById(String id);

	Page<Faq> getFaqList(String entityId, String faqCategoryId, String faqSubCategoryId, String searchKeyword,
			Pageable pageable);

	void deleteFaqById(String faqId);

	List<CountDto> countByEntityTypeAndEntityIdIn(FaqEntityType entityType, List<String> entityIds);
}