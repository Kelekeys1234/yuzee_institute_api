package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.exception.ValidationException;

public interface IEnrollmentService {

	EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto) throws ValidationException;

	EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto, BigInteger enrollmentId) throws ValidationException;

	void updateEnrollmentStatus(EnrollmentStatusDto enrollmentStatusDto, BigInteger userId) throws ValidationException;

	EnrollmentResponseDto getEnrollmentDetail(BigInteger enrollmentId);

	List<EnrollmentStatusDto> getEnrollmentStatusDetail(BigInteger enrollmentId);

	List<EnrollmentResponseDto> getEnrollmentList(final BigInteger userId, final BigInteger courseId, final BigInteger instituteId,
			final BigInteger enrollmentId, final String status, final Date updatedOn, final Integer startIndex, final Integer pageSize);

	void saveEnrollmentImage(BigInteger categoryId, String subCategory, String imageName) throws ValidationException;

	String removeEnrollmentImage(BigInteger enrollmentImageId);

	int countOfEnrollment();

}
