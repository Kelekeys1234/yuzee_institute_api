package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentStatus;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.constant.BasicStatus;
import com.seeka.app.dao.IEnrollmentDao;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.UserDto;
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

		Country country = iCountryService.get(enrollmentDto.getCountryId());
		if (country == null) {
			throw new ValidationException("Country type not found for id: " + enrollmentDto.getCountryId());
		} else {
			enrollment.setCountry(country);
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
	public EnrollmentDto updateEnrollment(final EnrollmentDto enrollmentDto, final BigInteger enrollmentId) throws ValidationException {
		Enrollment existingEnrollment = iEnrollmentDao.getEnrollment(enrollmentId);
		if (existingEnrollment == null) {
			throw new ValidationException("enrollment not found for id" + enrollmentId);
		}

		Enrollment enrollment = new Enrollment();
		BeanUtils.copyProperties(enrollmentDto, enrollment);
		enrollment.setId(enrollmentId);
		enrollment.setStatus(existingEnrollment.getStatus());
		enrollment.setCreatedBy(existingEnrollment.getCreatedBy());
		enrollment.setCreatedOn(existingEnrollment.getCreatedOn());
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

		Country country = iCountryService.get(enrollmentDto.getCountryId());
		if (country == null) {
			throw new ValidationException("Country type not found for id: " + enrollmentDto.getCountryId());
		} else {
			enrollment.setCountry(country);
		}

		iEnrollmentDao.updateEnrollment(enrollment);
		return enrollmentDto;

	}

	@Override
	public EnrollmentStatus updateEnrollmentStatus(final EnrollmentStatusDto enrollmentStatusDto, final BigInteger userId) throws ValidationException {
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
	public void sentEnrollmentNotification(final EnrollmentStatus enrollmentStatus, final BigInteger userId) throws ValidationException {
		if (userId.compareTo(enrollmentStatus.getEnrollment().getUserId()) != 0) {
			String message = "Your application status changed to "
					+ com.seeka.app.constant.EnrollmentStatus.getByValue(enrollmentStatus.getStatus()).getDisplayValue();
			iUsersService.sendPushNotification(enrollmentStatus.getEnrollment().getUserId(), message);
		} else {
			logger.info("Message trigger by user");
		}

	}

	@Override
	public EnrollmentResponseDto getEnrollmentDetail(final BigInteger enrollmentId) throws ValidationException {
		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentId);
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
		enrollmentResponseDto.setCountryId(enrollment.getCountry().getId());
		enrollmentResponseDto.setCountryName(enrollment.getCountry().getName());
		UserDto userDto = iUsersService.getUserById(enrollment.getUserId());
		enrollmentResponseDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
		enrollmentResponseDto.setCitizenship(userDto.getCitizenship());
		return enrollmentResponseDto;
	}

	@Override
	public List<EnrollmentStatusDto> getEnrollmentStatusDetail(final BigInteger enrollmentId) {
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
	public List<EnrollmentResponseDto> getEnrollmentList(final BigInteger userId, final BigInteger courseId, final BigInteger instituteId,
			final BigInteger enrollmentId, final String status, final Date updatedOn, final Integer startIndex, final Integer pageSize) {
		List<Enrollment> enrollmenList = iEnrollmentDao.getEnrollmentList(userId, courseId, instituteId, enrollmentId, status, updatedOn, startIndex, pageSize);
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
			enrollmentResponseDto.setCountryId(enrollment.getCountry().getId());
			enrollmentResponseDto.setCountryName(enrollment.getCountry().getName());
			enrollmentResponseDto.setEnrollmentImages(null);

			EnrollmentStatus enrollmentStatus = iEnrollmentDao.getEnrollmentStatusDetailBasedOnFilter(enrollment.getId(), enrollment.getStatus());
			if (enrollmentStatus != null) {
				enrollmentResponseDto.setDeadLine(enrollmentStatus.getDeadLine());
			}

			UserDto userDto = iUsersService.getUserById(enrollment.getUserId());
			enrollmentResponseDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
			enrollmentResponseDto.setCitizenship(userDto.getCitizenship());
			resultList.add(enrollmentResponseDto);
		}
		return resultList;
	}

	@Override
	public int countOfEnrollment() {
		return iEnrollmentDao.countOfEnrollment();
	}

}
