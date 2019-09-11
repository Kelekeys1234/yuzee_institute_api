package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;

import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.exception.ValidationException;

public interface IEnrollmentService {

	EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto) throws ValidationException;

	EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto, BigInteger enrollmentId) throws ValidationException;

	void updateEnrollmentStatus(EnrollmentStatusDto enrollmentStatusDto) throws ValidationException;

	EnrollmentResponseDto getEnrollmentDetail(BigInteger enrollmentId);

	List<EnrollmentStatusDto> getEnrollmentStatusDetail(BigInteger enrollmentId);

	List<EnrollmentResponseDto> getEnrollmentList();

	void saveEnrollmentImage(BigInteger categoryId, String subCategory, String imageName) throws ValidationException;

	void removeEnrollmentImage(BigInteger enrollmentImageId);

}
