package com.yuzee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, String> {

	@Query("SELECT faq from Faq faq where :entityId = faq.entityId"
			+ " and (:faqCategoryId is null or :faqCategoryId = faq.faqSubCategory.faqCategory.id)"
			+ " and (:faqSubCategoryId is null or :faqSubCategoryId = faq.faqSubCategory.id)"
			+ " and (:searchKeyword is null or (faq.title LIKE %:searchKeyword% or faq.description LIKE %:searchKeyword% "
			+ " or faq.description LIKE %:searchKeyword% or faq.faqSubCategory.name LIKE %:searchKeyword% "
			+ " or faq.faqSubCategory.faqCategory.name LIKE %:searchKeyword%))")
	public Page<Faq> getFaqList(String entityId, String faqCategoryId, String faqSubCategoryId, String searchKeyword,
			Pageable pageable);
}
