package com.yuzee.app.processor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.Faq;
import com.yuzee.app.bean.FaqSubCategory;
import com.yuzee.app.dao.FaqDao;
import com.yuzee.app.dao.FaqSubCategoryDao;
import com.yuzee.app.dto.FaqRequestDto;
import com.yuzee.app.dto.FaqResponseDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FaqProcessor {

	@Autowired
	private FaqDao faqDao;

	@Autowired
	private FaqSubCategoryDao faqSubCategoryDao;

	@Autowired
	ModelMapper modelMapper;

	public void addFaq(String userId, final FaqRequestDto faqRequestDto) throws ValidationException {
		log.debug("inside FaqProcessor.addFaq");
		Faq faq = modelMapper.map(faqRequestDto, Faq.class);
		setFaqSubCategoryInFaq(faq, faqRequestDto);
		faq.setCreatedBy(userId);
		faq.setUpdatedBy(userId);
		faq.setCreatedOn(new Date());
		faq.setUpdatedOn(new Date());
		faqDao.saveOrUpdate(faq);
	}

	public void updateFaq(String userId, final String faqId, final FaqRequestDto faqRequestDto)
			throws ValidationException {
		log.debug("inside FaqProcessor.updateFaq");

		Faq existingFaq = getFaqById(faqId);

		Faq newFaq = modelMapper.map(faqRequestDto, Faq.class);

		newFaq.setId(existingFaq.getId());
		newFaq.setCreatedBy(existingFaq.getCreatedBy());
		newFaq.setCreatedOn(existingFaq.getCreatedOn());
		if (!StringUtils.equals(existingFaq.getFaqSubCategory().getId(), faqRequestDto.getFaqSubCategoryId())) {
			setFaqSubCategoryInFaq(newFaq, faqRequestDto);
		}
		newFaq.setUpdatedBy(userId);
		newFaq.setUpdatedOn(new Date());
		faqDao.saveOrUpdate(newFaq);
	}

	public void deleteFaq(final String faqId) throws ValidationException {
		log.debug("inside FaqProcessor.deleteFaq");
		faqDao.deleteFaqById(faqId);
	}

	@Transactional
	public PaginationResponseDto getFaqList(final String entityId, final Integer pageNumber, final Integer pageSize,
			final String faqCategoryId, final String faqSubCategoryId, final String searchKeyword) {
		log.debug("inside FaqProcessor.getFaqList");
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

		log.info("calling db to fetch faq caregories for pageNumber: {} and pageSize: {}", pageNumber - 1, pageSize);
		Page<Faq> faqsPage = faqDao.getFaqList(entityId, faqCategoryId, faqSubCategoryId, searchKeyword, pageable);

		int totalRecords = ((Long) faqsPage.getTotalElements()).intValue();

		List<FaqResponseDto> faqResponseDtos = faqsPage.getContent().stream()
				.map(e -> modelMapper.map(e, FaqResponseDto.class)).collect(Collectors.toList());

		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, totalRecords, faqResponseDtos);
	}

	@Transactional
	public FaqResponseDto getFaqDetail(final String faqId) throws ValidationException {
		log.debug("inside FaqProcessor.getFaqDetail");
		return modelMapper.map(getFaqById(faqId), FaqResponseDto.class);
	}

	public void deleteFaqById(String id) throws ValidationException {
		log.debug("inside FaqProcessor.deleteFaqById");
		faqDao.deleteFaqById(id);
	}

	private void setFaqSubCategoryInFaq(Faq faq, FaqRequestDto faqRequestDto) throws ValidationException {
		FaqSubCategory faqSubCategory = getFaqSubCategoryById(faqRequestDto.getFaqSubCategoryId());
		faq.setFaqSubCategory(faqSubCategory);
	}

	private Faq getFaqById(String faqId) throws ValidationException {
		Optional<Faq> faqOptional = faqDao.getById(faqId);
		if (!faqOptional.isPresent()) {
			log.error("Faq not found for id: {}", faqId);
			throw new ValidationException("Faq not found for id: " + faqId);
		}
		return faqOptional.get();
	}

	private FaqSubCategory getFaqSubCategoryById(String faqSubCategoryId) throws ValidationException {
		Optional<FaqSubCategory> faqSubCategoryOptional = faqSubCategoryDao.getById(faqSubCategoryId);
		if (!faqSubCategoryOptional.isPresent()) {
			log.error("Faq Sub Category not found for id: {}", faqSubCategoryId);
			throw new ValidationException("Faq Sub Category not found for id: " + faqSubCategoryId);
		}
		return faqSubCategoryOptional.get();
	}
}
