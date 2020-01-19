package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.dto.FaqRequestDto;
import com.seeka.app.dto.FaqResponseDto;
import com.seeka.app.exception.ValidationException;

public interface IFaqService {

	void addFaq(FaqRequestDto faqRequestDto) throws ValidationException;

	void updateFaq(FaqRequestDto faqRequestDto, BigInteger faqId) throws ValidationException;

	void deleteFaq(BigInteger faqId) throws ValidationException;

	List<FaqResponseDto> getFaqList(Integer startIndex, Integer pageSize, BigInteger faqCategoryId, BigInteger faqSubCategoryId, String sortByField,
			String sortByType, String searchKeyword);

	int getFaqCount(BigInteger faqCategoryId, BigInteger faqSubCategoryId, String searchKeyword);

	FaqResponseDto getFaqDetail(BigInteger faqId);

}
