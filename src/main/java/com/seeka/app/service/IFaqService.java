package com.seeka.app.service;

import java.util.List;

import com.seeka.app.dto.FaqRequestDto;
import com.seeka.app.dto.FaqResponseDto;
import com.seeka.app.exception.ValidationException;

public interface IFaqService {

	void addFaq(String userId,FaqRequestDto faqRequestDto) throws ValidationException;

	void updateFaq(String userId,FaqRequestDto faqRequestDto, String faqId) throws ValidationException;

	void deleteFaq(String userId,String faqId) throws ValidationException;

	List<FaqResponseDto> getFaqList(Integer startIndex, Integer pageSize, String faqCategoryId, String faqSubCategoryId, String sortByField,
			String sortByType, String searchKeyword);

	int getFaqCount(String faqCategoryId, String faqSubCategoryId, String searchKeyword);

	FaqResponseDto getFaqDetail(String userId,String faqId);
	
	List<FaqResponseDto> getFaqListBasedOnEntityId (String userId, String entityId,String caller);
	
	public void deleteFaqById (String id) throws ValidationException;

}
