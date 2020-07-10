package com.yuzee.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuzee.app.bean.FaqCategory;
import com.yuzee.app.dao.IFaqCategoryDao;
import com.yuzee.app.dto.FaqResponseDto;
import com.yuzee.app.dto.FaqSubCategoryDto;
import com.yuzee.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class FaqCategoryService implements IFaqCategoryService {

	@Autowired
	private IFaqCategoryDao iFaqCategoryDao;

	@Autowired
	private IFaqSubCategoryService iFaqSubCategoryService;

	@Autowired
	private IFaqService iFaqService;

	@Override
	public void addFaqCategory(final FaqCategory faqCategory) throws ValidationException {
		/**
		 * Check same name other category exists or not.
		 */
		FaqCategory existingFaqCategory = iFaqCategoryDao.getFaqCategoryBasedOnName(faqCategory.getName(), null);
		if (existingFaqCategory != null) {
			throw new ValidationException("same active category exists");
		}
		faqCategory.setIsActive(true);
		faqCategory.setCreatedBy("API");
		faqCategory.setUpdatedBy("API");
		faqCategory.setCreatedOn(new Date());
		faqCategory.setUpdatedOn(new Date());
		iFaqCategoryDao.saveFaqCategory(faqCategory);
	}

	@Override
	public void updateFaqCategory(final FaqCategory faqCategory, final String faqCategoryId) throws ValidationException {
		/**
		 * Check same name other category exists or not.
		 */
		FaqCategory existingFaqCategory = iFaqCategoryDao.getFaqCategoryBasedOnName(faqCategory.getName(), faqCategoryId);
		if (existingFaqCategory != null) {
			throw new ValidationException("same active category exists");
		}
		/**
		 * Fetch existing category based on id
		 */
		existingFaqCategory = iFaqCategoryDao.getFaqCategoryDetail(faqCategoryId);
		if (existingFaqCategory == null) {
			throw new ValidationException("Category not found for id" + faqCategoryId);
		}
		existingFaqCategory.setName(faqCategory.getName());
		existingFaqCategory.setUpdatedBy("API");
		existingFaqCategory.setUpdatedOn(new Date());
		iFaqCategoryDao.updateFaqCategory(existingFaqCategory);

	}

	@Override
	public void deleteFaqCategory(final String faqCategoryId) throws ValidationException {
		/**
		 * Fetch existing category based on id
		 */
		FaqCategory existingFaqCategory = iFaqCategoryDao.getFaqCategoryDetail(faqCategoryId);
		if (existingFaqCategory == null) {
			throw new ValidationException("Category not found for id" + faqCategoryId);
		}
		existingFaqCategory.setIsActive(false);
		existingFaqCategory.setUpdatedBy("API");
		existingFaqCategory.setUpdatedOn(new Date());
		iFaqCategoryDao.updateFaqCategory(existingFaqCategory);

		List<FaqSubCategoryDto> faqSubCategoryDtos = iFaqSubCategoryService.getFaqSubCategoryList(null, null, faqCategoryId);
		for (FaqSubCategoryDto faqSubCategoryDto : faqSubCategoryDtos) {
			iFaqSubCategoryService.deleteFaqSubCategory(faqSubCategoryDto.getId());
		}

		List<FaqResponseDto> faqResponseDtos = iFaqService.getFaqList(null, null, faqCategoryId, null, null, null, null);
		for (FaqResponseDto faqResponseDto : faqResponseDtos) {
			iFaqService.deleteFaqById(faqResponseDto.getId());
		}
	}

	@Override
	public List<FaqCategory> getFaqCategoryList(final Integer startIndex, final Integer pageSize) {
		return iFaqCategoryDao.getFaqCategoryList(startIndex, pageSize);
	}

	@Override
	public int getFaqCategoryCount() {
		return iFaqCategoryDao.getFaqCategoryCount();
	}

	@Override
	public FaqCategory getFaqCategoryDetail(final String faqCategoryId) {
		return iFaqCategoryDao.getFaqCategoryDetail(faqCategoryId);
	}

}
