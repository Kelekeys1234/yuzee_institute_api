package com.yuzee.app.processor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseIntake;
import com.yuzee.app.bean.CourseLanguage;
import com.yuzee.app.bean.OffCampusCourse;
import com.yuzee.app.dao.OffCampusCourseDao;
import com.yuzee.app.dto.CourseDeliveryModesDto;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.OffCampusCourseRequestDto;
import com.yuzee.app.dto.OffCampusCourseResponseDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OffCampusCourseProcessor {

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private OffCampusCourseDao offCampusCourseDao;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private TimingProcessor timingProcessor;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional(rollbackOn = Throwable.class)
	public OffCampusCourseResponseDto saveOffCampusCourse(String userId,
			OffCampusCourseRequestDto offCampusCourseRequestDto)
			throws ValidationException, CommonInvokeException, NotFoundException {
		log.debug("inside OffCampusCourseProcessor.saveOffCampusCourse");

		offCampusCourseRequestDto.getCourseRequestDto().setOffCampusCourse(true);

		Course course = courseProcessor.prepareCourseModelFromCourseRequest(null,
				offCampusCourseRequestDto.getCourseRequestDto());
		OffCampusCourse offCampusCourse = modelMapper.map(offCampusCourseRequestDto, OffCampusCourse.class);
		offCampusCourse.setCourse(course);

		offCampusCourse.setCreatedBy(userId);
		offCampusCourse.setUpdatedBy(userId);

		offCampusCourse.setCreatedOn(new Date());
		offCampusCourse.setUpdatedOn(new Date());
		offCampusCourse = offCampusCourseDao.saveOrUpdate(offCampusCourse);
		OffCampusCourseResponseDto responseDto = convertOffCampusCourseToOffCampusCourseResponseDto(offCampusCourse);
		responseDto.getCourseResponseDto()
				.setCourseTimings(timingProcessor.saveUpdateTimings(userId,
						offCampusCourseRequestDto.getCourseRequestDto().getCourseTimings(),
						responseDto.getCourseResponseDto().getId()));
		return responseDto;
	}

	@Transactional(rollbackOn = Throwable.class)
	public OffCampusCourseResponseDto updateOffCampusCourse(String userId, String offCampusCourseId,
			OffCampusCourseRequestDto offCampusCourseRequestDto)
			throws ValidationException, CommonInvokeException, NotFoundException {
		log.debug("inside OffCampusCourseProcessor.updateOffCampusCourse");

		getOffCampusCourseById(offCampusCourseId); // only to check if off campus exists

		offCampusCourseRequestDto.getCourseRequestDto().setOffCampusCourse(true);

		Course course = courseProcessor.prepareCourseModelFromCourseRequest(null,
				offCampusCourseRequestDto.getCourseRequestDto());
		OffCampusCourse offCampusCourse = modelMapper.map(offCampusCourseRequestDto, OffCampusCourse.class);
		offCampusCourse.setCourse(course);

		offCampusCourse.setUpdatedBy(userId);
		offCampusCourse.setUpdatedOn(new Date());

		offCampusCourse = offCampusCourseDao.saveOrUpdate(offCampusCourse);
		OffCampusCourseResponseDto responseDto = convertOffCampusCourseToOffCampusCourseResponseDto(offCampusCourse);
		responseDto.getCourseResponseDto()
				.setCourseTimings(timingProcessor.saveUpdateTimings(userId,
						offCampusCourseRequestDto.getCourseRequestDto().getCourseTimings(),
						responseDto.getCourseResponseDto().getId()));
		return responseDto;
	}

	public void deleteOffCampusCourse(String userId, String offCampusCourseId) {
		log.debug("inside OffCampusCourseProcessor.updateOffCampusCourse");
		// TODO: user has access to delete off campus
		offCampusCourseDao.deleteById(offCampusCourseId);
	}

	@Transactional(rollbackOn = Throwable.class)
	public PaginationResponseDto getOffCampusCoursesByInstituteId(String instituteId, Integer pageNumber,
			Integer pageSize) throws NotFoundException {
		log.debug("inside OffCampusCourseController.getOffCampusCoursesByInstituteId");
		Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
		Page<OffCampusCourse> offCampusCoursePage = offCampusCourseDao.getOffCampusCoursesByInstituteId(instituteId,
				pageRequest);
		List<OffCampusCourse> offCampusCourses = offCampusCoursePage.getContent();

		List<OffCampusCourseResponseDto> offCampusCourseResponseDtos = offCampusCourses.stream()
				.map(e -> convertOffCampusCourseToOffCampusCourseResponseDto(e)).collect(Collectors.toList());

		try {
			log.info("Calling Storage service to fetch course images");
			;
			List<StorageDto> storageDTOList = storageHandler
					.getStorages(
							offCampusCourseResponseDtos.stream().map(e -> e.getCourseResponseDto().getId())
									.collect(Collectors.toList()),
							EntityTypeEnum.COURSE, Arrays.asList(EntitySubTypeEnum.COVER_PHOTO));
			if (!CollectionUtils.isEmpty(storageDTOList)) {
				Map<String, StorageDto> storagesMap = storageDTOList.stream()
						.collect(Collectors.toMap(StorageDto::getEntityId, e -> e));
				offCampusCourseResponseDtos.stream().forEach(e -> e.getCourseResponseDto()
						.setStorageList(Arrays.asList(storagesMap.get(e.getCourseResponseDto().getId()))));
			}
		} catch (NotFoundException | InvokeException e) {
			log.error("Error invoking Storage service exception {}", e);
		}
		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) offCampusCoursePage.getTotalElements()).intValue(), offCampusCourseResponseDtos);
	}

	private OffCampusCourseResponseDto convertOffCampusCourseToOffCampusCourseResponseDto(
			OffCampusCourse offCampusCourse) {
		OffCampusCourseResponseDto offCampusCourseResponseDto = modelMapper.map(offCampusCourse,
				OffCampusCourseResponseDto.class);
		Course course = offCampusCourse.getCourse();
		CourseResponseDto courseResponse = new CourseResponseDto();
		courseResponse.setId(course.getId());
		courseResponse.setName(course.getName());
		courseResponse.setCourseRanking(course.getWorldRanking());
		courseResponse.setStars(Double.valueOf(course.getStars() == null ? 0 : course.getStars()));
		courseResponse.setInstituteId(course.getInstitute().getId());
		courseResponse.setInstituteName(course.getInstitute().getName());
		courseResponse.setCurrencyCode(course.getCurrency());
		courseResponse.setCourseDescription(course.getDescription());
		if (!ObjectUtils.isEmpty(offCampusCourse.getLatitude())) {
			courseResponse.setLatitude(offCampusCourse.getLatitude());
		}
		if (!ObjectUtils.isEmpty(offCampusCourse.getLongitude())) {
			courseResponse.setLongitude(offCampusCourse.getLongitude());
		}

		if (!CollectionUtils.isEmpty(course.getCourseDeliveryModes())) {
			courseResponse.setCourseDeliveryModes(course.getCourseDeliveryModes().stream()
					.map(e -> modelMapper.map(e, CourseDeliveryModesDto.class)).collect(Collectors.toList()));
		}

		List<CourseLanguage> courseLanguages = course.getCourseLanguages();
		if (!CollectionUtils.isEmpty(courseLanguages)) {
			log.info("courseLanguage is fetched from DB, hence adding englishEligibilities in response");
			courseResponse.setLanguage(
					courseLanguages.stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
		}

		List<CourseIntake> courseIntakes = course.getCourseIntakes();
		if (!CollectionUtils.isEmpty(courseIntakes)) {
			log.info("courseIntake is fetched from DB, hence adding englishEligibilities in response");
			courseResponse
					.setIntake(courseIntakes.stream().map(CourseIntake::getIntakeDates).collect(Collectors.toList()));
		}
		offCampusCourseResponseDto.setCourseResponseDto(courseResponse);
		return offCampusCourseResponseDto;
	}

	@Transactional(rollbackOn = Throwable.class)
	public OffCampusCourseResponseDto getOffCampusCourseResponseDtoById(String offCampusCourseId)
			throws NotFoundException {
		OffCampusCourseResponseDto offCampusCourseResponseDto = convertOffCampusCourseToOffCampusCourseResponseDto(
				getOffCampusCourseById(offCampusCourseId));
		try {
			log.info("Calling Storage service to fetch course images");
			;
			List<StorageDto> storageDTOList = storageHandler.getStorages(
					offCampusCourseResponseDto.getCourseResponseDto().getId(), EntityTypeEnum.COURSE,
					EntitySubTypeEnum.COVER_PHOTO);
			offCampusCourseResponseDto.getCourseResponseDto().setStorageList(storageDTOList);
			offCampusCourseResponseDto.getCourseResponseDto()
					.setCourseTimings(timingProcessor.getTimingRequestDtoByEntityTypeAndEntityId(EntityTypeEnum.COURSE,
							offCampusCourseResponseDto.getCourseResponseDto().getId()));
		} catch (NotFoundException | InvokeException e) {
			log.error("Error invoking Storage service exception {}", e);
		}
		return offCampusCourseResponseDto;
	}

	private OffCampusCourse getOffCampusCourseById(String offCampusCourseId) throws NotFoundException {
		Optional<OffCampusCourse> optionalEntity = offCampusCourseDao.getById(offCampusCourseId);
		if (!optionalEntity.isPresent()) {
			log.error("OffCampusCourse not exists against id: {}", offCampusCourseId);
			throw new NotFoundException("OffCampusCourse not exists against id: " + offCampusCourseId);
		}
		return optionalEntity.get();
	}
}