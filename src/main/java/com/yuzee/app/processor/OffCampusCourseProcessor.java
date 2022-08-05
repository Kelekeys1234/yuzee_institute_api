package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseLanguage;
import com.yuzee.app.bean.OffCampusCourse;
import com.yuzee.app.dao.OffCampusCourseDao;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.institute.OffCampusCourseDto;
import com.yuzee.common.lib.dto.storage.StorageDto;
import com.yuzee.common.lib.enumeration.EntitySubTypeEnum;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.StorageHandler;
import com.yuzee.common.lib.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OffCampusCourseProcessor {

	@Autowired
	private OffCampusCourseDao offCampusCourseDao;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TimingProcessor timingProcessor;

	@Transactional(rollbackOn = Throwable.class)
	public PaginationResponseDto getOffCampusCoursesByInstituteId(String instituteId, Integer pageNumber,
			Integer pageSize) throws NotFoundException {
		log.debug("inside OffCampusCourseController.getOffCampusCoursesByInstituteId");
		Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
		Page<OffCampusCourse> offCampusCoursePage = offCampusCourseDao.getOffCampusCoursesByInstituteId(instituteId,
				pageRequest);
		List<OffCampusCourse> offCampusCourses = offCampusCoursePage.getContent();

		List<CourseResponseDto> courseResponseDtos = offCampusCourses.stream()
				.map(e -> convertCourseToCourseResponseDto(e.getCourse())).collect(Collectors.toList());
		List<String> courseIds = courseResponseDtos.stream().map(CourseResponseDto::getId).collect(Collectors.toList());
		List<TimingRequestDto> allCourseTimings = timingProcessor
				.getTimingRequestDtoByEntityTypeAndEntityIdIn(EntityTypeEnum.COURSE, courseIds);

		try {
			log.info("Calling Storage service to fetch course images");
			List<StorageDto> storageDTOList = storageHandler.getStorages(courseIds, EntityTypeEnum.COURSE,
					Arrays.asList(EntitySubTypeEnum.LOGO, EntitySubTypeEnum.COVER_PHOTO));
			if (!CollectionUtils.isEmpty(storageDTOList)) {
				courseResponseDtos.stream().forEach(e -> {
					e.setStorageList(storageDTOList.stream().filter(s -> s.getEntityId().equals(e.getId()))
							.collect(Collectors.toList()));
					List<TimingRequestDto> courseTimings = allCourseTimings.stream()
							.filter(t -> t.getEntityId().equals(e.getId())).collect(Collectors.toList());
					e.setCourseTimings(new ValidList<>(courseTimings));
				});
			}
		} catch (NotFoundException | InvokeException e) {
			log.error("Error invoking Storage service exception {}", e);
		}
		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) offCampusCoursePage.getTotalElements()).intValue(), courseResponseDtos);
	}

	public CourseResponseDto convertCourseToCourseResponseDto(Course course) {
		CourseResponseDto courseResponse = modelMapper.map(course, CourseResponseDto.class);
		courseResponse.setId(course.getId());
		courseResponse.setName(course.getName());
		courseResponse.setCourseRanking(course.getWorldRanking());
		courseResponse.setStars(Double.valueOf(course.getStars() == null ? 0 : course.getStars()));
		// courseResponse.setInstituteId(course.getInstitute().getId().toString());
		// courseResponse.setInstituteName(course.getInstitute().getName());
		courseResponse.setCurrencyCode(course.getCurrency());
		courseResponse.setCourseDescription(course.getDescription());
		courseResponse.setPhoneNumber(course.getPhoneNumber());
		courseResponse.setWebsite(course.getWebsite());
		courseResponse.setEmail(course.getEmail());

		List<CourseLanguage> courseLanguages = new ArrayList<>();
//		courseLanguages.add(course.getCourseLanguages());
		if (!CollectionUtils.isEmpty(courseLanguages)) {
			log.info("courseLanguage is fetched from DB, hence adding englishEligibilities in response");
			courseResponse.setLanguage(
					courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
		}

		if (course.getOffCampusCourse() != null) {
			courseResponse.setOffCampusCourse(modelMapper.map(course.getOffCampusCourse(), OffCampusCourseDto.class));
		}
		return courseResponse;
	}
}