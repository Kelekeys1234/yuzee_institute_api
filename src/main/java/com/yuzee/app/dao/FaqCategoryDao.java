package com.yuzee.app.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yuzee.app.bean.FaqCategory;
import com.yuzee.app.exception.ValidationException;

public interface FaqCategoryDao {

	Optional<FaqCategory> getById(String faqCategoryId);

	void saveOrUpdate(FaqCategory faqCategory) throws ValidationException;

	Page<FaqCategory> findAll(Pageable pageable);

	void deleteById(String faqCategoryId);
}