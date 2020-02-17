package com.seeka.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.bean.AccreditedInstitute;
import com.seeka.app.dto.AccreditedInstituteDto;
import com.seeka.app.dto.AccreditedInstituteRequestDto;
import com.seeka.app.exception.ValidationException;

public interface IAccreditedInstituteService {

	AccreditedInstitute addAccreditedInstitute(AccreditedInstituteRequestDto accreditedInstituteRequestDto) throws ValidationException;

	List<AccreditedInstituteDto> getAccreditedInstituteList(Integer pageNumber, Integer pageSize) throws ValidationException;

	AccreditedInstituteDto getAccreditedInstituteDetail(String accreditedInstituteId) throws ValidationException;

	void addAccreditedLogo(MultipartFile file, AccreditedInstitute accreditedInstitute);

	AccreditedInstitute updateAccreditedInstitute(String accreditedInstituteId, AccreditedInstituteRequestDto accreditedInstituteRequestDto)
			throws ValidationException;

	void deleteAccreditedInstitute(String accreditedInstituteId) throws ValidationException;

	List<AccreditedInstituteRequestDto> getAllAccreditedInstitutes();

}
