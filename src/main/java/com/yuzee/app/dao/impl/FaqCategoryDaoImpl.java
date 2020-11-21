package com.yuzee.app.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.FaqCategory;
import com.yuzee.app.dao.FaqCategoryDao;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.repository.FaqCategoryRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class FaqCategoryDaoImpl implements FaqCategoryDao {

	@Autowired
	private FaqCategoryRepository faqCategoryRepository;

	@Override
	public Optional<FaqCategory> getById(String faqCategoryId) {
		return faqCategoryRepository.findById(faqCategoryId);
	}

	@Override
	public void saveOrUpdate(FaqCategory faqCategory) throws ValidationException {
		try {
			faqCategoryRepository.save(faqCategory);
		} catch (DataIntegrityViolationException ex) {
			log.error("faq category already present with name: {}", faqCategory.getName());
			throw new ValidationException("faq category already present with name: " + faqCategory.getName());
		}
	}

	@Override
	public Page<FaqCategory> findAll(Pageable pageable) {
		return faqCategoryRepository.findAll(pageable);
	}

	@Override
	public void deleteById(String faqCategoryId) {
		try {
			faqCategoryRepository.deleteById(faqCategoryId);
		} catch (EmptyResultDataAccessException ex) {
			log.error(ex.getMessage());
		}
	}

}
