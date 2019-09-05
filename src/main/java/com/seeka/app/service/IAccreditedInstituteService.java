package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.AccreditedInstitute;
import com.seeka.app.dto.AccreditedInstituteRequestDto;
import com.seeka.app.exception.ValidationException;

public interface IAccreditedInstituteService {

	AccreditedInstitute addAccreditedInstitute(AccreditedInstituteRequestDto accreditedInstituteRequestDto) throws ValidationException;

	List<AccreditedInstitute> getAccreditedInstituteList(Integer pageNumber, Integer pageSize);

	AccreditedInstitute getAccreditedInstituteDetail(BigInteger accreditedInstituteId);

	void addAccreditedLogo(MultipartFile file, AccreditedInstitute accreditedInstitute);

	AccreditedInstitute updateAccreditedInstitute(BigInteger accreditedInstituteId, AccreditedInstituteRequestDto accreditedInstituteRequestDto)
			throws ValidationException;

	void deleteAccreditedInstitute(BigInteger accreditedInstituteId) throws ValidationException;
	
	List<AccreditedInstituteRequestDto> getAllAccreditedInstitutes();

}
