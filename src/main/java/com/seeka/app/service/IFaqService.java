package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.FaqRequestDto;
import com.seeka.app.dto.FaqResponseDto;
import com.seeka.app.exception.ValidationException;

public interface IFaqService {

	void addFaq(FaqRequestDto faqRequestDto) throws ValidationException;

	void updateFaq(FaqRequestDto faqRequestDto, String faqId) throws ValidationException;

	void deleteFaq(String faqId) throws ValidationException;

	List<FaqResponseDto> getFaqList(Integer startIndex, Integer pageSize, String faqCategoryId, String faqSubCategoryId, String sortByField,
			String sortByType, String searchKeyword);

	int getFaqCount(String faqCategoryId, String faqSubCategoryId, String searchKeyword);

	FaqResponseDto getFaqDetail(String faqId);

}
