package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.Faq;
import com.seeka.app.bean.FaqCategory;
import com.seeka.app.bean.FaqSubCategory;
import com.seeka.app.dao.IFaqDao;
import com.seeka.app.dao.IFaqSubCategoryDao;
import com.seeka.app.dto.FaqRequestDto;
import com.seeka.app.dto.FaqResponseDto;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class FaqService implements IFaqService {

	@Autowired
	private IFaqCategoryService iFaqCategoryService;

	@Autowired
	private IFaqSubCategoryDao iFaqSubCategoryDao;

	@Autowired
	private IFaqDao iFaqDao;

	@Override
	public void addFaq(String userId ,final FaqRequestDto faqRequestDto) throws ValidationException {
		if (!faqRequestDto.getEntityType().equalsIgnoreCase("PLATFORM")) {
			//TODO validate user ID passed in request have access to modify resource
		}
		Faq faq = new Faq();
		BeanUtils.copyProperties(faqRequestDto, faq);
		if (faqRequestDto.getFaqCategoryId() != null) {
			FaqCategory faqCategory = iFaqCategoryService.getFaqCategoryDetail(faqRequestDto.getFaqCategoryId());
			if (faqCategory == null) {
				throw new ValidationException("faq Category not found for id: " + faqRequestDto.getFaqCategoryId());
			} else if (faqCategory.getIsActive() != null && !faqCategory.getIsActive()) {
				throw new ValidationException("faq Category not found for id: " + faqRequestDto.getFaqCategoryId());
			} else {
				faq.setFaqCategory(faqCategory);
			}
		}
		if (faqRequestDto.getFaqSubCategoryId() != null) {
			FaqSubCategory faqSubCategory = iFaqSubCategoryDao.getFaqSubCategoryDetail(faqRequestDto.getFaqSubCategoryId());
			if (faqSubCategory == null) {
				throw new ValidationException("faq Sub Category not found for id: " + faqRequestDto.getFaqSubCategoryId());
			} else if (faqSubCategory.getIsActive() != null && !faqSubCategory.getIsActive()) {
				throw new ValidationException("faq Sub Category not found for id: " + faqRequestDto.getFaqSubCategoryId());
			} else {
				faq.setFaqSubCategory(faqSubCategory);
			}
		}

		faq.setIsActive(true);
		faq.setCreatedBy("API");
		faq.setUpdatedBy("API");
		faq.setCreatedOn(new Date());
		faq.setUpdatedOn(new Date());
		iFaqDao.save(faq);

	}

	@Override
	public void updateFaq(String userId ,final FaqRequestDto faqRequestDto, final String faqId) throws ValidationException {
		//TODO validate user ID passed in request have access to modify resource
		Faq existingFaq = iFaqDao.getFaqDetail(faqId);
		if (ObjectUtils.isEmpty(existingFaq)) {
			throw new ValidationException("Faq not found for id: " + faqId);
		}
		BeanUtils.copyProperties(faqRequestDto, existingFaq);
		if (faqRequestDto.getFaqCategoryId() != null) {
			FaqCategory faqCategory = iFaqCategoryService.getFaqCategoryDetail(faqRequestDto.getFaqCategoryId());
			if (faqCategory == null || !faqCategory.getIsActive()) {
				throw new ValidationException("faq Category not found for id: " + faqRequestDto.getFaqCategoryId());
			}else {
				existingFaq.setFaqCategory(faqCategory);
			}
		}
		if (faqRequestDto.getFaqSubCategoryId() != null) {
			FaqSubCategory faqSubCategory = iFaqSubCategoryDao.getFaqSubCategoryDetail(faqRequestDto.getFaqSubCategoryId());
			if (faqSubCategory == null || !faqSubCategory.getIsActive()) {
				throw new ValidationException("faq Sub Category not found for id: " + faqRequestDto.getFaqSubCategoryId());
			} else {
				existingFaq.setFaqSubCategory(faqSubCategory);
			}
		}

		existingFaq.setUpdatedBy("API");
		existingFaq.setUpdatedOn(new Date());
		iFaqDao.update(existingFaq);
	}

	@Override
	public void deleteFaq(String userId ,final String faqId) throws ValidationException {
		//TODO validate user ID passed in request have access to modify resource
		
		/**
		 * Fetch existing FAQ based on id
		 */
		Faq existingFaq = iFaqDao.getFaqDetail(faqId);
		if (existingFaq == null) {
			throw new ValidationException("Faq not found for id: " + faqId);
		}
		existingFaq.setIsActive(false);
		existingFaq.setUpdatedBy("API");
		existingFaq.setUpdatedOn(new Date());
		iFaqDao.update(existingFaq);
	}

	@Override
	public List<FaqResponseDto> getFaqList(final Integer startIndex, final Integer pageSize, final String faqCategoryId, final String faqSubCategoryId,
			final String sortByField, final String sortByType, final String searchKeyword) {
		List<Faq> faqList = iFaqDao.getFaqList(startIndex, pageSize, faqCategoryId, faqSubCategoryId, sortByField, sortByType, searchKeyword);
		List<FaqResponseDto> faqResponseDtos = new ArrayList<>();
		for (Faq faq : faqList) {
			FaqResponseDto faqResponseDto = new FaqResponseDto();
			BeanUtils.copyProperties(faq, faqResponseDto);
			if (faq.getFaqCategory() != null) {
				faqResponseDto.setFaqCategoryId(faq.getFaqCategory().getId());
				faqResponseDto.setFaqCategoryName(faq.getFaqCategory().getName());
			}
			if (faq.getFaqSubCategory() != null) {
				faqResponseDto.setFaqSubCategoryId(faq.getFaqSubCategory().getId());
				faqResponseDto.setFaqSubCategoryName(faq.getFaqSubCategory().getName());
			}

			faqResponseDtos.add(faqResponseDto);
		}
		return faqResponseDtos;
	}

	@Override
	public int getFaqCount(final String faqCategoryId, final String faqSubCategoryId, final String searchKeyword) {
		return iFaqDao.getFaqCount(faqCategoryId, faqSubCategoryId, searchKeyword);
	}

	@Override
	public FaqResponseDto getFaqDetail(String userId ,final String faqId) {
		//TODO validate user ID passed in request have access to modify resource
		Faq faq = iFaqDao.getFaqDetail(faqId);
		FaqResponseDto faqResponseDto = new FaqResponseDto();
		BeanUtils.copyProperties(faq, faqResponseDto);
		if (faq.getFaqCategory() != null) {
			faqResponseDto.setFaqCategoryId(faq.getFaqCategory().getId());
			faqResponseDto.setFaqCategoryName(faq.getFaqCategory().getName());
		}
		if (faq.getFaqSubCategory() != null) {
			faqResponseDto.setFaqSubCategoryId(faq.getFaqSubCategory().getId());
			faqResponseDto.setFaqSubCategoryName(faq.getFaqSubCategory().getName());
		}
		return faqResponseDto;
	}

	@Override
	public List<FaqResponseDto> getFaqListBasedOnEntityId(String userId, String entityId, String caller) {
		if (caller.equalsIgnoreCase("PRIVATE")) {
			//TODO validate user ID passed in request have access to modify resource
		}
		List<FaqResponseDto> faqResponseDtos = new ArrayList<>();
		List<Faq> faqList = iFaqDao.getFaqBasedOnEntityId(entityId);
		if (!CollectionUtils.isEmpty(faqList)) {
			faqList.stream().forEach(faq -> {
				FaqResponseDto faqResponseDto = new FaqResponseDto();
				BeanUtils.copyProperties(faq, faqResponseDto);
				if (faq.getFaqCategory() != null) {
					faqResponseDto.setFaqCategoryId(faq.getFaqCategory().getId());
					faqResponseDto.setFaqCategoryName(faq.getFaqCategory().getName());
				}
				if (faq.getFaqSubCategory() != null) {
					faqResponseDto.setFaqSubCategoryId(faq.getFaqSubCategory().getId());
					faqResponseDto.setFaqSubCategoryName(faq.getFaqSubCategory().getName());
				}

				faqResponseDtos.add(faqResponseDto);
			});
		}
		return faqResponseDtos;
	}

	@Override
	public void deleteFaqById(String id) throws ValidationException {
		Faq existingFaq = iFaqDao.getFaqDetail(id);
		if (existingFaq == null) {
			throw new ValidationException("Faq not found for id: " +  id);
		}
		existingFaq.setIsActive(false);
		existingFaq.setUpdatedBy("API");
		existingFaq.setUpdatedOn(new Date());
		iFaqDao.update(existingFaq);
		
	}

}
