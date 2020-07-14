package com.yuzee.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.Enrollment;
import com.yuzee.app.bean.EnrollmentStatus;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteType;
import com.yuzee.app.constant.BasicStatus;
import com.yuzee.app.constant.NotificationType;
import com.yuzee.app.dao.IEnrollmentDao;
import com.yuzee.app.dto.EnrollmentDto;
import com.yuzee.app.dto.EnrollmentResponseDto;
import com.yuzee.app.dto.EnrollmentStatusDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.dto.UserDto;
import com.yuzee.app.enumeration.EnrollmentStatusType;
import com.yuzee.app.enumeration.ImageCategory;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.IdentityHandler;
import com.yuzee.app.processor.CourseProcessor;
import com.yuzee.app.processor.InstituteProcessor;
import com.yuzee.app.processor.InstituteTypeProcessor;
import com.yuzee.app.processor.StorageProcessor;

@Service
@Transactional(rollbackFor = Throwable.class)
public class EnrollmentService implements IEnrollmentService {

	@Autowired
	private IEnrollmentDao iEnrollmentDao;

	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private InstituteTypeProcessor instituteTypeProcessor;

	@Autowired
	private IdentityHandler identityHandler;

	@Autowired
	private StorageProcessor iStorageService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public EnrollmentDto addEnrollment(final EnrollmentDto enrollmentDto) throws ValidationException {
		Enrollment enrollment = new Enrollment();
		BeanUtils.copyProperties(enrollmentDto, enrollment);
		enrollment.setStatus("SUBMITTED");
		enrollment.setCreatedBy("API");
		enrollment.setCreatedOn(new Date());
		enrollment.setUpdatedBy("API");
		enrollment.setUpdatedOn(new Date());
		Institute institute = instituteProcessor.get(enrollmentDto.getInstituteId());
		if (institute == null) {
			throw new ValidationException("Institute not found for id: " + enrollmentDto.getInstituteId());
		} else {
			enrollment.setInstitute(institute);
		}

		Course course = courseProcessor.getCourseData(enrollmentDto.getCourseId());
		if (course == null) {
			throw new ValidationException("Course not found for id: " + enrollmentDto.getCourseId());
		} else {
			enrollment.setCourse(course);
		}

		InstituteType instituteType = instituteTypeProcessor.getInstituteType(enrollmentDto.getInstituteTypeId());
		if (instituteType == null) {
			throw new ValidationException("Institute type not found for id: " + enrollmentDto.getInstituteTypeId());
		} else {
			enrollment.setInstituteType(instituteType);
		}

		if (enrollmentDto.getCountryId() != null) {
			enrollment.setCountryName(enrollmentDto.getCountryId());
		}

		iEnrollmentDao.addEnrollment(enrollment);
		enrollmentDto.setId(enrollment.getId());

		EnrollmentStatus enrollmentStatus = new EnrollmentStatus();
		enrollmentStatus.setEnrollment(enrollment);
		enrollmentStatus.setCreatedBy("API");
		enrollmentStatus.setCreatedOn(new Date());
		enrollmentStatus.setStatus("SUBMITTED");
		iEnrollmentDao.saveEnrollmentStatus(enrollmentStatus);
		return enrollmentDto;
	}

	@Override
	public EnrollmentDto updateEnrollment(final EnrollmentDto enrollmentDto, final String enrollmentId) throws ValidationException {
		Enrollment existingEnrollment = iEnrollmentDao.getEnrollment(enrollmentId);
		if (existingEnrollment == null) {
			throw new ValidationException("enrollment not found for id" + enrollmentId);
		}
		String existingStatus = existingEnrollment.getStatus();
		String createdBy = existingEnrollment.getCreatedBy();
		Date createdOn = existingEnrollment.getCreatedOn();
		BeanUtils.copyProperties(enrollmentDto, existingEnrollment);
		existingEnrollment.setState(existingStatus);
		existingEnrollment.setCreatedBy(createdBy);
		existingEnrollment.setCreatedOn(createdOn);
		existingEnrollment.setUpdatedBy("API");
		existingEnrollment.setUpdatedOn(new Date());
		Institute institute = instituteProcessor.get(enrollmentDto.getInstituteId());
		if (institute == null) {
			throw new ValidationException("Institute not found for id: " + enrollmentDto.getInstituteId());
		} else {
			existingEnrollment.setInstitute(institute);
		}

		Course course = courseProcessor.getCourseData(enrollmentDto.getCourseId());
		if (course == null) {
			throw new ValidationException("Course not found for id: " + enrollmentDto.getCourseId());
		} else {
			existingEnrollment.setCourse(course);
		}

		InstituteType instituteType = instituteTypeProcessor.getInstituteType(enrollmentDto.getInstituteTypeId());
		if (instituteType == null) {
			throw new ValidationException("Institute type not found for id: " + enrollmentDto.getInstituteTypeId());
		} else {
			existingEnrollment.setInstituteType(instituteType);
		}

		if (enrollmentDto.getCountryId() != null) {
			existingEnrollment.setCountryName(enrollmentDto.getCountryId() );
		}

		iEnrollmentDao.updateEnrollment(existingEnrollment);
		return enrollmentDto;

	}

	@Override
	public EnrollmentStatus updateEnrollmentStatus(final EnrollmentStatusDto enrollmentStatusDto, final String userId) throws ValidationException {
		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentStatusDto.getEnrollmentId());
		if (enrollment == null) {
			throw new ValidationException("enrollment not found for id" + enrollmentStatusDto.getEnrollmentId());
		}

		final com.yuzee.app.constant.EnrollmentStatus newStatus = com.yuzee.app.constant.EnrollmentStatus.getByValue(enrollmentStatusDto.getStatus());
		final com.yuzee.app.constant.EnrollmentStatus oldStatus = com.yuzee.app.constant.EnrollmentStatus.getByValue(enrollment.getStatus());

		List<BasicStatus<com.yuzee.app.constant.EnrollmentStatus>> nextStatusList = null;
		if (oldStatus.nextStatus() != null) {
			nextStatusList = Arrays.asList(oldStatus.nextStatus());
		}

		if (nextStatusList == null || !nextStatusList.contains(newStatus)) {
			throw new ValidationException(enrollmentStatusDto.getStatus() + " status is not allowed after " + enrollment.getStatus());
		}

		enrollment.setStatus(enrollmentStatusDto.getStatus());
		enrollment.setUpdatedBy("API");
		enrollment.setUpdatedOn(new Date());
		iEnrollmentDao.updateEnrollment(enrollment);
		EnrollmentStatus enrollmentStatus = new EnrollmentStatus();
		enrollmentStatus.setEnrollment(enrollment);
		enrollmentStatus.setCreatedBy("API");
		enrollmentStatus.setCreatedOn(new Date());
		enrollmentStatus.setStatus(enrollmentStatusDto.getStatus());
		enrollmentStatus.setDeadLine(enrollmentStatusDto.getDeadLine());
		iEnrollmentDao.saveEnrollmentStatus(enrollmentStatus);

		return enrollmentStatus;
	}

	@Override
	public void sentEnrollmentNotification(final EnrollmentStatus enrollmentStatus, final String userId) throws ValidationException {
		if (!userId.equals(enrollmentStatus.getEnrollment().getUserId())) {
			String message = "Your application status changed to "
					+ com.yuzee.app.constant.EnrollmentStatus.getByValue(enrollmentStatus.getStatus()).getDisplayValue();
			identityHandler.sendPushNotification(enrollmentStatus.getEnrollment().getUserId(), message, NotificationType.ENROLLMENT.name());
		} else {
			logger.info("Message trigger by user");
		}

	}

	@Override
	public EnrollmentResponseDto getEnrollmentDetail(final String enrollmentId) throws ValidationException {
		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentId);
		if (enrollment == null) {
			throw new ValidationException("Enrollment not found for id :" + enrollmentId);
		}
		EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
		BeanUtils.copyProperties(enrollment, enrollmentResponseDto);

		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(enrollmentId, ImageCategory.ENROLLMENT.name(), null, "en");
		enrollmentResponseDto.setEnrollmentImages(storageDTOList);

		enrollmentResponseDto.setInstituteId(enrollment.getInstitute().getId());
		enrollmentResponseDto.setInstituteName(enrollment.getInstitute().getName());
		enrollmentResponseDto.setCourseId(enrollment.getCourse().getId());
		enrollmentResponseDto.setCourseName(enrollment.getCourse().getName());
		enrollmentResponseDto.setInstituteTypeId(enrollment.getInstituteType().getId());
		enrollmentResponseDto.setInstituteTypeName(enrollment.getInstituteType().getName());
		if (enrollment.getCountryName() != null) {
			enrollmentResponseDto.setCountryName(enrollment.getCountryName());
		}
		if (enrollment.getUserId() != null) {
			UserDto userDto = identityHandler.getUserById(enrollment.getUserId());
			enrollmentResponseDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
			enrollmentResponseDto.setCitizenship(userDto.getCitizenship());
		}

		return enrollmentResponseDto;
	}

	@Override
	public List<EnrollmentStatusDto> getEnrollmentStatusDetail(final String enrollmentId) {
		List<EnrollmentStatus> enrollmentStatusList = iEnrollmentDao.getEnrollmentStatusDetail(enrollmentId);
		List<EnrollmentStatusDto> resultList = new ArrayList<>();
		for (EnrollmentStatus enrollmentStatus : enrollmentStatusList) {
			EnrollmentStatusDto enrollmentStatusDto = new EnrollmentStatusDto();
			BeanUtils.copyProperties(enrollmentStatus, enrollmentStatusDto);
			enrollmentStatusDto.setEnrollmentId(enrollmentId);
			resultList.add(enrollmentStatusDto);
		}
		return resultList;
	}

	@Override
	public List<EnrollmentResponseDto> getEnrollmentList(final String userId, final String courseId, final String instituteId,
			final String enrollmentId, final String status, final Date updatedOn, final Integer startIndex, final Integer pageSize, final Boolean isArchive,
			final String sortByField, final String sortByType, final String searchKeyword) throws ValidationException {
		List<Enrollment> enrollmenList = iEnrollmentDao.getEnrollmentList(userId, courseId, instituteId, enrollmentId, status, updatedOn, startIndex, pageSize,
				isArchive, sortByField, sortByType, searchKeyword);
		List<EnrollmentResponseDto> resultList = new ArrayList<>();
		for (Enrollment enrollment : enrollmenList) {
			EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
			BeanUtils.copyProperties(enrollment, enrollmentResponseDto);
			enrollmentResponseDto.setInstituteId(enrollment.getInstitute().getId());
			enrollmentResponseDto.setInstituteName(enrollment.getInstitute().getName());
			enrollmentResponseDto.setCourseId(enrollment.getCourse().getId());
			enrollmentResponseDto.setCourseName(enrollment.getCourse().getName());
			enrollmentResponseDto.setInstituteTypeId(enrollment.getInstituteType().getId());
			enrollmentResponseDto.setInstituteTypeName(enrollment.getInstituteType().getName());
			if (enrollment.getCountryName() != null) {
				enrollmentResponseDto.setCountryName(enrollment.getCountryName());
			}
			enrollmentResponseDto.setEnrollmentImages(null);

			EnrollmentStatus enrollmentStatus = iEnrollmentDao.getEnrollmentStatusDetailBasedOnFilter(enrollment.getId(), enrollment.getStatus());
			if (enrollmentStatus != null) {
				enrollmentResponseDto.setDeadLine(enrollmentStatus.getDeadLine());
			}
			if (enrollment.getUserId() != null) {
				UserDto userDto = identityHandler.getUserById(enrollment.getUserId());
				enrollmentResponseDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
				enrollmentResponseDto.setCitizenship(userDto.getCitizenship());
			}
			resultList.add(enrollmentResponseDto);
		}
		return resultList;
	}

	@Override
	public int countOfEnrollment(final String userId, final String courseId, final String instituteId, final String enrollmentId,
			final String status, final Date updatedOn, final String searchKeyword) {
		return iEnrollmentDao.countOfEnrollment(userId, courseId, instituteId, enrollmentId, status, updatedOn, searchKeyword);
	}

	@Override
	public void archiveEnrollment(final String enrollmentId, final boolean isArchive) throws ValidationException {
		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentId);
		if (enrollment == null) {
			throw new ValidationException("Enrollment not found for id :" + enrollmentId);
		}
		enrollment.setIsArchive(isArchive);
		enrollment.setUpdatedBy("API");
		enrollment.setUpdatedOn(new Date());
		iEnrollmentDao.updateEnrollment(enrollment);
	}

	@Override
	public Map<String, AtomicLong> getEnrollmentStatus() {
		List<Enrollment> enrollments = iEnrollmentDao.getAllEnrollment();
		Map<String, AtomicLong> enrollmentStatusCount = new HashMap<String, AtomicLong>();

		AtomicLong completedCount = new AtomicLong(0);
		AtomicLong assignedCount = new AtomicLong(0);
		AtomicLong notAssignedCount = new AtomicLong(0);
		if(!CollectionUtils.isEmpty(enrollments)) {
			enrollments.stream().forEach(enrollment -> {
				if (enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.SUBMITTED.name())) {
					notAssignedCount.getAndIncrement();
				} else if ((enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.SEEKA_REVIEWED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.PREPARED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.INSTITUTE_SUBMITTED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.INSTITUTE_REVIEWED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.INSTITUTE_OFFERED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.APPLICANT_APPROVED.name()))) {
					assignedCount.getAndIncrement();
				} else if (enrollment.getStatus().equalsIgnoreCase(com.yuzee.app.constant.EnrollmentStatus.APPROVED.name())) {
					completedCount.getAndIncrement();
				}
			});
		}
		enrollmentStatusCount.put(EnrollmentStatusType.NOT_ASSIGNED.name(), notAssignedCount);
		enrollmentStatusCount.put(EnrollmentStatusType.ASSIGNED.name(), assignedCount);
		enrollmentStatusCount.put(EnrollmentStatusType.COMPLETED.name(), completedCount);
		return enrollmentStatusCount;
	}
}
