package com.yuzee.app.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.FaqSubCategory;
import com.yuzee.app.dao.FaqSubCategoryDao;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.repository.FaqSubCategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class FaqSubCategoryDaoImpl implements FaqSubCategoryDao {

	@Autowired
	private FaqSubCategoryRepository faqSubCategoryRepository;

	@Override
	public Optional<FaqSubCategory> getById(String faqSubCategoryId) {
		return faqSubCategoryRepository.findById(faqSubCategoryId);
	}

	@Override
	public void saveOrUpdate(FaqSubCategory faqSubCategory) throws ValidationException {
		try {
			faqSubCategoryRepository.save(faqSubCategory);
		} catch (Exception ex) {
			log.error("faq sub category already present with name: ", faqSubCategory.getName());
			throw new ValidationException("faq sub category already present with name: " + faqSubCategory.getName());
		}
	}

	@Override
	public Page<FaqSubCategory> findByCategoryId(String faqCategoryId, Pageable pageable) {
		return faqSubCategoryRepository.findByFaqCategoryId(faqCategoryId, pageable);
	}

	@Override
	public void deleteById(String faqSubCategoryId) {
		try {
			faqSubCategoryRepository.deleteById(faqSubCategoryId);
		} catch (EmptyResultDataAccessException ex) {
			log.error(ex.getMessage());
		}
	}
}
