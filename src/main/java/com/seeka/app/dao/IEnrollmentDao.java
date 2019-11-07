package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentStatus;

public interface IEnrollmentDao {

	void addEnrollment(Enrollment enrollment);

	void saveEnrollmentStatus(EnrollmentStatus enrollmentStatus);

	Enrollment getEnrollment(BigInteger enrollmentId);

	void updateEnrollment(Enrollment enrollment);

	List<EnrollmentStatus> getEnrollmentStatusDetail(BigInteger enrollmentId);

	List<Enrollment> getEnrollmentList(BigInteger userId, BigInteger courseId, BigInteger instituteId, BigInteger enrollmentId, String status, Date updatedOn,
			Integer startIndex, Integer pageSize, Boolean isArchive);

	int countOfEnrollment(BigInteger userId, BigInteger courseId, BigInteger instituteId, BigInteger enrollmentId, String status, Date updatedOn);

	EnrollmentStatus getEnrollmentStatusDetailBasedOnFilter(BigInteger enrollmentId, String status);

}
