package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.FaqCategory;
import com.seeka.app.bean.FaqSubCategory;
import com.seeka.app.dao.IFaqSubCategoryDao;
import com.seeka.app.dto.FaqResponseDto;
import com.seeka.app.dto.FaqSubCategoryDto;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class FaqSubCategoryService implements IFaqSubCategoryService {

	@Autowired
	private IFaqSubCategoryDao iFaqSubCategoryDao;

	@Autowired
	private IFaqCategoryService iFaqCategoryService;

	@Autowired
	private IFaqService iFaqService;

	@Override
	public void addFaqSubCategory(final FaqSubCategoryDto faqSubCategoryDto) throws ValidationException {
		/**
		 * Check same name other category exists or not.
		 */
		FaqSubCategory existingFaqSubCategory = iFaqSubCategoryDao.getFaqSubCategoryBasedOnName(faqSubCategoryDto.getName(),
				faqSubCategoryDto.getFaqCategoryId(), null);
		if (existingFaqSubCategory != null) {
			throw new ValidationException("same active sub category exists for same category");
		}
		FaqSubCategory faqSubCategory = new FaqSubCategory();
		BeanUtils.copyProperties(faqSubCategoryDto, faqSubCategory);
		FaqCategory faqCategory = iFaqCategoryService.getFaqCategoryDetail(faqSubCategoryDto.getFaqCategoryId());
		if (faqCategory == null) {
			throw new ValidationException("faq Category not found for id: " + faqSubCategoryDto.getFaqCategoryId());
		}
		faqSubCategory.setFaqCategory(faqCategory);
		faqSubCategory.setIsActive(true);
		faqSubCategory.setCreatedBy("API");
		faqSubCategory.setUpdatedBy("API");
		faqSubCategory.setCreatedOn(new Date());
		faqSubCategory.setUpdatedOn(new Date());
		iFaqSubCategoryDao.saveFaqSubCategory(faqSubCategory);
	}

	@Override
	public void updateFaqSubCategory(final FaqSubCategoryDto faqSubCategoryDto, final String faqSubCategoryId) throws ValidationException {
		/**
		 * Check same name other category exists or not.
		 */
		FaqSubCategory existingFaqSubCategory = iFaqSubCategoryDao.getFaqSubCategoryBasedOnName(faqSubCategoryDto.getName(),
				faqSubCategoryDto.getFaqCategoryId(), faqSubCategoryId);
		if (existingFaqSubCategory != null) {
			throw new ValidationException("same active sub category exists for same category");
		}
		/**
		 * Fetch existing category based on id
		 */
		existingFaqSubCategory = iFaqSubCategoryDao.getFaqSubCategoryDetail(faqSubCategoryId);
		if (existingFaqSubCategory == null) {
			throw new ValidationException("Sub Category not found for id: " + faqSubCategoryId);
		}
		existingFaqSubCategory.setName(faqSubCategoryDto.getName());
		FaqCategory faqCategory = iFaqCategoryService.getFaqCategoryDetail(faqSubCategoryDto.getFaqCategoryId());
		if (faqCategory == null) {
			throw new ValidationException("faq Category not found for id: " + faqSubCategoryDto.getFaqCategoryId());
		}
		existingFaqSubCategory.setFaqCategory(faqCategory);
		existingFaqSubCategory.setUpdatedBy("API");
		existingFaqSubCategory.setUpdatedOn(new Date());
		iFaqSubCategoryDao.updateFaqSubCategory(existingFaqSubCategory);

	}

	@Override
	public void deleteFaqSubCategory(final String faqSubCategoryId) throws ValidationException {
		/**
		 * Fetch existing category based on id
		 */
		FaqSubCategory existingFaqSubCategory = iFaqSubCategoryDao.getFaqSubCategoryDetail(faqSubCategoryId);
		if (existingFaqSubCategory == null) {
			throw new ValidationException("Sub Category not found for id: " + faqSubCategoryId);
		}
		existingFaqSubCategory.setIsActive(false);
		existingFaqSubCategory.setUpdatedBy("API");
		existingFaqSubCategory.setUpdatedOn(new Date());
		iFaqSubCategoryDao.updateFaqSubCategory(existingFaqSubCategory);

		List<FaqResponseDto> faqResponseDtos = iFaqService.getFaqList(null, null, null, faqSubCategoryId, null, null, null);
		for (FaqResponseDto faqResponseDto : faqResponseDtos) {
			iFaqService.deleteFaqById(faqResponseDto.getId());
		}
	}

	@Override
	public List<FaqSubCategoryDto> getFaqSubCategoryList(final Integer startIndex, final Integer pageSize, final String faqCategoryId) {
		List<FaqSubCategory> faqSubCategories = iFaqSubCategoryDao.getFaqSubCategoryList(startIndex, pageSize, faqCategoryId);
		List<FaqSubCategoryDto> faqSubCategoryDtos = new ArrayList<>();
		for (FaqSubCategory faqSubCategory : faqSubCategories) {
			FaqSubCategoryDto faqSubCategoryDto = new FaqSubCategoryDto();
			BeanUtils.copyProperties(faqSubCategory, faqSubCategoryDto);
			faqSubCategoryDto.setFaqCategoryId(faqSubCategory.getFaqCategory().getId());
			faqSubCategoryDto.setFaqCategoryName(faqSubCategory.getFaqCategory().getName());
			faqSubCategoryDtos.add(faqSubCategoryDto);
		}
		return faqSubCategoryDtos;
	}

	@Override
	public int getFaqSubCategoryCount(final String faqCategoryId) {
		return iFaqSubCategoryDao.getFaqSubCategoryCount(faqCategoryId);
	}

	@Override
	public FaqSubCategoryDto getFaqSubCategoryDetail(final String faqSubCategoryId) throws ValidationException {
		FaqSubCategory faqSubCategory = iFaqSubCategoryDao.getFaqSubCategoryDetail(faqSubCategoryId);
		if (faqSubCategory == null) {
			throw new ValidationException("Sub Category not found for id: " + faqSubCategoryId);
		}
		FaqSubCategoryDto faqSubCategoryDto = new FaqSubCategoryDto();
		BeanUtils.copyProperties(faqSubCategory, faqSubCategoryDto);
		faqSubCategoryDto.setFaqCategoryId(faqSubCategory.getFaqCategory().getId());
		faqSubCategoryDto.setFaqCategoryName(faqSubCategory.getFaqCategory().getName());
		return faqSubCategoryDto;
	}

}
