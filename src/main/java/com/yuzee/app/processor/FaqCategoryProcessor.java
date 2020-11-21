package com.yuzee.app.processor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.FaqCategory;
import com.yuzee.app.dao.FaqCategoryDao;
import com.yuzee.app.dto.FaqCategoryDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FaqCategoryProcessor {

	@Autowired
	private FaqCategoryDao faqCategoryDao;

	@Autowired
	ModelMapper modelMapper;

	public void addFaqCategory(final String userId, final FaqCategoryDto faqCategoryDto) throws ValidationException {
		log.info("inside FaqCategoryProcessor.addFaqCategory");
		FaqCategory faqCategory = modelMapper.map(faqCategoryDto, FaqCategory.class);
		faqCategory.setCreatedBy(userId);
		faqCategory.setUpdatedBy(userId);
		faqCategory.setCreatedOn(new Date());
		faqCategory.setUpdatedOn(new Date());
		faqCategoryDao.saveOrUpdate(faqCategory);
	}

	public void updateFaqCategory(final String userId, final String faqCategoryId, final FaqCategoryDto faqCategory)
			throws ValidationException {
		log.debug("inside FaqCategoryProcessor.updateFaqCategory");
		FaqCategory existingFaqCategory = getFaqCategoryById(faqCategoryId);
		existingFaqCategory.setName(faqCategory.getName());
		existingFaqCategory.setUpdatedBy(userId);
		existingFaqCategory.setUpdatedOn(new Date());
		faqCategoryDao.saveOrUpdate(existingFaqCategory);
	}

	public FaqCategoryDto getFaqCategory(final String faqCategoryId) throws ValidationException {
		log.debug("inside FaqCategoryProcessor.getFaqCatgory");
		return modelMapper.map(getFaqCategoryById(faqCategoryId), FaqCategoryDto.class);
	}

	public PaginationResponseDto getFaqCategories(final Integer pageNumber, final Integer pageSize) {
		log.debug("inside FaqCategoryProcessor.getFaqCatgories");
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

		log.info("calling db to fetch faq caregories for pageNumber: {} and pageSize: {}", pageNumber - 1, pageSize);
		Page<FaqCategory> faqCategoriesPage = faqCategoryDao.findAll(pageable);
		int totalRecords = ((Long) faqCategoriesPage.getTotalElements()).intValue();
		List<FaqCategoryDto> faqCategoryDtos = faqCategoriesPage.getContent().stream()
				.map(e -> modelMapper.map(e, FaqCategoryDto.class)).collect(Collectors.toList());
		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, totalRecords, faqCategoryDtos);
	}

	public void deleteFaqCategory(final String faqCategoryId) {
		faqCategoryDao.deleteById(faqCategoryId);
	}

	private FaqCategory getFaqCategoryById(String faqCategoryId) throws ValidationException {
		Optional<FaqCategory> faqCategoryOptional = faqCategoryDao.getById(faqCategoryId);
		if (!faqCategoryOptional.isPresent()) {
			log.error("Faq Category not found for id: {}", faqCategoryId);
			throw new ValidationException("Faq Category not found for id: " + faqCategoryId);
		}
		return faqCategoryOptional.get();
	}
}
