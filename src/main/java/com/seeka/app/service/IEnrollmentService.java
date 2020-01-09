package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.EnrollmentStatus;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.exception.ValidationException;

public interface IEnrollmentService {

	EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto) throws ValidationException;

	EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto, BigInteger enrollmentId) throws ValidationException;

	EnrollmentStatus updateEnrollmentStatus(EnrollmentStatusDto enrollmentStatusDto, BigInteger userId) throws ValidationException;

	EnrollmentResponseDto getEnrollmentDetail(BigInteger enrollmentId) throws ValidationException;

	List<EnrollmentStatusDto> getEnrollmentStatusDetail(BigInteger enrollmentId);

	List<EnrollmentResponseDto> getEnrollmentList(final BigInteger userId, final BigInteger courseId, final BigInteger instituteId,
			final BigInteger enrollmentId, final String status, final Date updatedOn, final Integer startIndex, final Integer pageSize);

	int countOfEnrollment();

	void sentEnrollmentNotification(EnrollmentStatus enrollmentStatus, BigInteger userId) throws ValidationException;

}
