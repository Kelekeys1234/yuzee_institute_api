package com.seeka.app.service;

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

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentStatus;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.constant.BasicStatus;
import com.seeka.app.constant.NotificationType;
import com.seeka.app.dao.IEnrollmentDao;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserDto;
import com.seeka.app.enumeration.EnrollmentStatusType;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.ValidationException;

@Service
@Transactional(rollbackFor = Throwable.class)
public class EnrollmentService implements IEnrollmentService {

	@Autowired
	private IEnrollmentDao iEnrollmentDao;

	@Autowired
	private IInstituteService iInstituteService;

	@Autowired
	private ICourseService iCourseService;

	@Autowired
	private IInstituteTypeService iInstituteTypeService;

	@Autowired
	private ICountryService iCountryService;

	@Autowired
	private IUsersService iUsersService;

	@Autowired
	private IStorageService iStorageService;

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
		Institute institute = iInstituteService.get(enrollmentDto.getInstituteId());
		if (institute == null) {
			throw new ValidationException("Institute not found for id: " + enrollmentDto.getInstituteId());
		} else {
			enrollment.setInstitute(institute);
		}

		Course course = iCourseService.getCourseData(enrollmentDto.getCourseId());
		if (course == null) {
			throw new ValidationException("Course not found for id: " + enrollmentDto.getCourseId());
		} else {
			enrollment.setCourse(course);
		}

		InstituteType instituteType = iInstituteTypeService.get(enrollmentDto.getInstituteTypeId());
		if (instituteType == null) {
			throw new ValidationException("Institute type not found for id: " + enrollmentDto.getInstituteTypeId());
		} else {
			enrollment.setInstituteType(instituteType);
		}

		if (enrollmentDto.getCountryId() != null) {
			Country country = iCountryService.get(enrollmentDto.getCountryId());
			if (country == null) {
				throw new ValidationException("Country not found for id: " + enrollmentDto.getCountryId());
			} else {
				enrollment.setCountry(country);
			}
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
		Institute institute = iInstituteService.get(enrollmentDto.getInstituteId());
		if (institute == null) {
			throw new ValidationException("Institute not found for id: " + enrollmentDto.getInstituteId());
		} else {
			existingEnrollment.setInstitute(institute);
		}

		Course course = iCourseService.getCourseData(enrollmentDto.getCourseId());
		if (course == null) {
			throw new ValidationException("Course not found for id: " + enrollmentDto.getCourseId());
		} else {
			existingEnrollment.setCourse(course);
		}

		InstituteType instituteType = iInstituteTypeService.get(enrollmentDto.getInstituteTypeId());
		if (instituteType == null) {
			throw new ValidationException("Institute type not found for id: " + enrollmentDto.getInstituteTypeId());
		} else {
			existingEnrollment.setInstituteType(instituteType);
		}

		if (enrollmentDto.getCountryId() != null) {
			Country country = iCountryService.get(enrollmentDto.getCountryId());
			if (country == null) {
				throw new ValidationException("Country not found for id: " + enrollmentDto.getCountryId());
			} else {
				existingEnrollment.setCountry(country);
			}
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

		final com.seeka.app.constant.EnrollmentStatus newStatus = com.seeka.app.constant.EnrollmentStatus.getByValue(enrollmentStatusDto.getStatus());
		final com.seeka.app.constant.EnrollmentStatus oldStatus = com.seeka.app.constant.EnrollmentStatus.getByValue(enrollment.getStatus());

		List<BasicStatus<com.seeka.app.constant.EnrollmentStatus>> nextStatusList = null;
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
					+ com.seeka.app.constant.EnrollmentStatus.getByValue(enrollmentStatus.getStatus()).getDisplayValue();
			iUsersService.sendPushNotification(enrollmentStatus.getEnrollment().getUserId(), message, NotificationType.ENROLLMENT.name());
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

		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(enrollmentId, null, "en");
		enrollmentResponseDto.setEnrollmentImages(storageDTOList);

		enrollmentResponseDto.setInstituteId(enrollment.getInstitute().getId());
		enrollmentResponseDto.setInstituteName(enrollment.getInstitute().getName());
		enrollmentResponseDto.setCourseId(enrollment.getCourse().getId());
		enrollmentResponseDto.setCourseName(enrollment.getCourse().getName());
		enrollmentResponseDto.setInstituteTypeId(enrollment.getInstituteType().getId());
		enrollmentResponseDto.setInstituteTypeName(enrollment.getInstituteType().getName());
		if (enrollment.getCountry() != null) {
			enrollmentResponseDto.setCountryId(enrollment.getCountry().getId());
			enrollmentResponseDto.setCountryName(enrollment.getCountry().getName());
		}
		if (enrollment.getUserId() != null) {
			UserDto userDto = iUsersService.getUserById(enrollment.getUserId());
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
			if (enrollment.getCountry() != null) {
				enrollmentResponseDto.setCountryId(enrollment.getCountry().getId());
				enrollmentResponseDto.setCountryName(enrollment.getCountry().getName());
			}
			enrollmentResponseDto.setEnrollmentImages(null);

			EnrollmentStatus enrollmentStatus = iEnrollmentDao.getEnrollmentStatusDetailBasedOnFilter(enrollment.getId(), enrollment.getStatus());
			if (enrollmentStatus != null) {
				enrollmentResponseDto.setDeadLine(enrollmentStatus.getDeadLine());
			}
			if (enrollment.getUserId() != null) {
				UserDto userDto = iUsersService.getUserById(enrollment.getUserId());
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
				if (enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.SUBMITTED.name())) {
					notAssignedCount.getAndIncrement();
				} else if ((enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.SEEKA_REVIEWED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.PREPARED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.INSTITUTE_SUBMITTED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.INSTITUTE_REVIEWED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.INSTITUTE_OFFERED.name()))
						|| (enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.APPLICANT_APPROVED.name()))) {
					assignedCount.getAndIncrement();
				} else if (enrollment.getStatus().equalsIgnoreCase(com.seeka.app.constant.EnrollmentStatus.APPROVED.name())) {
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
