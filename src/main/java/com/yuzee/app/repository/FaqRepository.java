package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Faq;
import com.yuzee.app.constant.FaqEntityType;
import com.yuzee.common.lib.dto.CountDto;

@Repository
public interface FaqRepository extends MongoRepository<Faq, String> {

	@Query("SELECT faq from Faq faq where :entityId = faq.entityId"
			+ " and (:faqCategoryId is null or :faqCategoryId = '' or :faqCategoryId = faq.faqSubCategory.faqCategory.id)"
			+ " and (:faqSubCategoryId is null or :faqSubCategoryId = '' or :faqSubCategoryId = faq.faqSubCategory.id)"
			+ " and (:searchKeyword is null or :searchKeyword = '' or (faq.title LIKE %:searchKeyword% or faq.description LIKE %:searchKeyword% "
			+ " or faq.description LIKE %:searchKeyword% or faq.faqSubCategory.name LIKE %:searchKeyword% "
			+ " or faq.faqSubCategory.faqCategory.name LIKE %:searchKeyword%))")
	Page<Faq> getFaqList(String entityId, String faqCategoryId, String faqSubCategoryId, String searchKeyword,
			Pageable pageable);
	
	@Query("SELECT new com.yuzee.common.lib.dto.CountDto(f.entityId, count(f)) from Faq f WHERE f.entityType = :entityType AND f.entityId IN :entityIds group by f.entityId")
	List<CountDto> countByEntityTypeAndEntityIdIn(FaqEntityType entityType, List<String> entityIds);
}
