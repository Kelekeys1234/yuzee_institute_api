package com.yuzee.app.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.bean.Faq;
import com.yuzee.app.dao.FaqDao;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.app.repository.FaqRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class FaqDaoImpl implements FaqDao {

	@Autowired
	private FaqRepository faqRepository;

	@Override
	public void saveOrUpdate(Faq faq) throws ValidationException {
		try {
			faqRepository.save(faq);
		} catch (DataIntegrityViolationException ex) {
			log.error("faq category already present with title, entity_id, entity_type, faq_sub_category_id");
			throw new ValidationException(
					"faq category already present with title, entity_id, entity_type, faq_sub_category_id");
		}
	}

	@Override
	public Optional<Faq> getById(String id) {
		return faqRepository.findById(id);
	}

	@Override
	public Page<Faq> getFaqList(String entityId, String faqCategoryId, String faqSubCategoryId, String searchKeyword,
			Pageable pageable) {
		return faqRepository.getFaqList(entityId, faqCategoryId, faqSubCategoryId, searchKeyword, pageable);
	}

	@Override
	public void deleteFaqById(String faqId) {
		try {
			faqRepository.deleteById(faqId);
		} catch (EmptyResultDataAccessException ex) {
			log.error(ex.getMessage());
		}
	}
}
