package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Enrollment;
import com.seeka.app.bean.EnrollmentImage;
import com.seeka.app.bean.EnrollmentStatus;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.dao.IEnrollmentDao;
import com.seeka.app.dto.EnrollmentDto;
import com.seeka.app.dto.EnrollmentImageDto;
import com.seeka.app.dto.EnrollmentResponseDto;
import com.seeka.app.dto.EnrollmentStatusDto;
import com.seeka.app.dto.UserDto;
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
	private UsersService usersService;

	@Value("${s3.url}")
	private String s3URL;

	@Override
	public EnrollmentDto addEnrollment(final EnrollmentDto enrollmentDto) throws ValidationException {
		Enrollment enrollment = new Enrollment();
		BeanUtils.copyProperties(enrollmentDto, enrollment);
		enrollment.setStatus("SUBMITTED");
		enrollment.setCreatedBy("API");
		enrollment.setCreatedOn(new Date());
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
		if (enrollmentDto.getEnrollmentImages() != null) {
			for (EnrollmentImageDto enrollmentImageDto : enrollmentDto.getEnrollmentImages()) {
				EnrollmentImage enrollmentImage = new EnrollmentImage();
				BeanUtils.copyProperties(enrollmentImageDto, enrollmentImage);
				enrollmentImage.setEnrollment(enrollment);
				iEnrollmentDao.saveEnrollmentImage(enrollmentImage);
			}
		}

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
	public void updateEnrollmentStatus(final EnrollmentStatusDto enrollmentStatusDto) throws ValidationException {
		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentStatusDto.getEnrollmentId());
		if (enrollment == null) {
			throw new ValidationException("enrollment not found for id" + enrollmentStatusDto.getEnrollmentId());
		}
		enrollment.setStatus(enrollmentStatusDto.getStatus());
		enrollment.setUpdatedBy("API");
		enrollment.setUpdatedOn(new Date());
		enrollment.setEnrollmentImages(new ArrayList<>());
		iEnrollmentDao.updateEnrollment(enrollment);
		EnrollmentStatus enrollmentStatus = new EnrollmentStatus();
		enrollmentStatus.setEnrollment(enrollment);
		enrollmentStatus.setCreatedBy("API");
		enrollmentStatus.setCreatedOn(new Date());
		enrollmentStatus.setStatus(enrollmentStatusDto.getStatus());
		enrollmentStatus.setDeadLine(enrollmentStatusDto.getDeadLine());
		iEnrollmentDao.saveEnrollmentStatus(enrollmentStatus);
	}

	@Override
	public EnrollmentResponseDto getEnrollmentDetail(final BigInteger enrollmentId) {
		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentId);
		EnrollmentResponseDto enrollmentResponseDto = new EnrollmentResponseDto();
		BeanUtils.copyProperties(enrollment, enrollmentResponseDto);
		List<EnrollmentImageDto> EnrollmentimageList = new ArrayList<>();
		for (EnrollmentImage enrollmentImage : enrollment.getEnrollmentImages()) {
			EnrollmentImageDto enrollmentImageDto = new EnrollmentImageDto();
			BeanUtils.copyProperties(enrollmentImage, enrollmentImageDto);
			enrollmentImageDto.setImageURL(s3URL + enrollmentImage.getImageName());
			EnrollmentimageList.add(enrollmentImageDto);
		}
		enrollmentResponseDto.setEnrollmentImages(EnrollmentimageList);
		enrollmentResponseDto.setInstituteId(enrollment.getInstitute().getId());
		enrollmentResponseDto.setInstituteName(enrollment.getInstitute().getName());
		enrollmentResponseDto.setCourseId(enrollment.getCourse().getId());
		enrollmentResponseDto.setCourseName(enrollment.getCourse().getName());
		enrollmentResponseDto.setInstituteTypeId(enrollment.getInstituteType().getId());
		enrollmentResponseDto.setInstituteTypeName(enrollment.getInstituteType().getName());
		enrollmentResponseDto.setCountryId(enrollment.getCountry().getId());
		enrollmentResponseDto.setCountryName(enrollment.getCountry().getName());
		UserDto userDto = usersService.getUserById(enrollment.getUserId());
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
	public List<EnrollmentResponseDto> getEnrollmentList() {
		List<Enrollment> enrollmenList = iEnrollmentDao.getEnrollmentList();
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
			UserDto userDto = usersService.getUserById(enrollment.getUserId());
			enrollmentResponseDto.setUserName(userDto.getFirstName() + " " + userDto.getLastName());
			enrollmentResponseDto.setCitizenship(userDto.getCitizenship());
			resultList.add(enrollmentResponseDto);
		}
		return resultList;
	}

	@Override
	public void saveEnrollmentImage(final BigInteger enrollmentId, final String subCategory, final String imageName) throws ValidationException {
		Enrollment enrollment = iEnrollmentDao.getEnrollment(enrollmentId);
		if (enrollment == null) {
			throw new ValidationException("enrollment not found for id" + enrollmentId);
		}
		EnrollmentImage enrollmentImage = new EnrollmentImage();
		enrollmentImage.setImageName(imageName);
		enrollmentImage.setImageType(subCategory);
		enrollmentImage.setEnrollment(enrollment);
		iEnrollmentDao.saveEnrollmentImage(enrollmentImage);
	}

	@Override
	public void removeEnrollmentImage(final BigInteger enrollmentImageId) {
		iEnrollmentDao.removeEnrollmentImage(enrollmentImageId);
	}

}
