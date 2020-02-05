package com.seeka.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.seeka.app.bean.EnrollmentStatus;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.exception.ValidationException;

public interface IEnrollmentService {

	EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto) throws ValidationException;

	EnrollmentDto updateEnrollment(EnrollmentDto enrollmentDto, String enrollmentId) throws ValidationException;

	EnrollmentStatus updateEnrollmentStatus(EnrollmentStatusDto enrollmentStatusDto, String userId) throws ValidationException;

	EnrollmentResponseDto getEnrollmentDetail(String enrollmentId) throws ValidationException;

	List<EnrollmentStatusDto> getEnrollmentStatusDetail(String enrollmentId);

	List<EnrollmentResponseDto> getEnrollmentList(final String userId, final String courseId, final String instituteId,
			final String enrollmentId, final String status, final Date updatedOn, final Integer startIndex, final Integer pageSize, Boolean isArchive,
			String sortByField, String sortByType, String searchKeyword) throws ValidationException;

	int countOfEnrollment(String userId, String courseId, String instituteId, String enrollmentId, String status, Date updatedOn,
			String searchKeyword);

	void sentEnrollmentNotification(EnrollmentStatus enrollmentStatus, String userId) throws ValidationException;

	void archiveEnrollment(String enrollmentId, boolean isArchive) throws ValidationException;

	Map<String, AtomicLong> getEnrollmentStatus();

}
