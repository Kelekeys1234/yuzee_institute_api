package com.yuzee.app.dao;

import java.util.Date;
import java.util.List;

import com.yuzee.app.bean.Enrollment;
import com.yuzee.app.bean.EnrollmentStatus;

public interface IEnrollmentDao {

	void addEnrollment(Enrollment enrollment);

	void saveEnrollmentStatus(EnrollmentStatus enrollmentStatus);

	Enrollment getEnrollment(String enrollmentId);

	void updateEnrollment(Enrollment enrollment);

	List<EnrollmentStatus> getEnrollmentStatusDetail(String enrollmentId);

	List<Enrollment> getEnrollmentList(String userId, String courseId, String instituteId, String enrollmentId, String status, Date updatedOn,
			Integer startIndex, Integer pageSize, Boolean isArchive, String sortByField, String sortByType, String searchKeyword);

	int countOfEnrollment(String userId, String courseId, String instituteId, String enrollmentId, String status, Date updatedOn,
			String searchKeyword);

	EnrollmentStatus getEnrollmentStatusDetailBasedOnFilter(String enrollmentId, String status);

	List<Enrollment> getAllEnrollment();

}
