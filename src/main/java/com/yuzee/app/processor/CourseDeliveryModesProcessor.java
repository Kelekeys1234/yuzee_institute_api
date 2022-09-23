package com.yuzee.app.processor;

import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseDeliveryModeFunding;
import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.bean.CourseFees;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CourseDeliveryModeRequestWrapper;
import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.ValidList;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModeFundingDto;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModesDto;
import com.yuzee.common.lib.dto.institute.CourseFeesDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CourseDeliveryModesProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	@Lazy
	private CourseProcessor courseProcessor;

	@Autowired
	private MessageTranslator messageTranslator;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommonProcessor commonProcessor;

	public List<CourseDeliveryModesDto> getCourseDeliveryModesByCourseId(String courseId) {
		log.debug("Inside getCourseDeliveryModesByCourseId() method");
		List<CourseDeliveryModesDto> courseDeliveryModesResponse = new ArrayList<>();
		log.info("Fetching courseDeliveryModes from DB for courseId = " + courseId);

		List<CourseDeliveryModes> courseDeliveryModesFromDB= null;

		if (!CollectionUtils.isEmpty(courseDeliveryModesFromDB)) {
			log.info("Additional Info is not null, then start iterating list");
			courseDeliveryModesFromDB.stream().forEach(courseDeliveryMode -> {
				CourseDeliveryModesDto courseDeliveryModesDto = new CourseDeliveryModesDto();
				log.info("Copying Bean class values to DTO class using beanUtils");
				BeanUtils.copyProperties(courseDeliveryMode, courseDeliveryModesDto);
				courseDeliveryModesDto.setCourseId(courseId);
				courseDeliveryModesResponse.add(courseDeliveryModesDto);
			});
		}
		return courseDeliveryModesResponse;
	}

	@Transactional
	public void saveUpdateCourseDeliveryModes(String userId, String courseId,
			@Valid CourseDeliveryModeRequestWrapper request)
			throws NotFoundException, ValidationException, InternalServerException {
		log.info("inside CourseDeliveryModesProcessor.saveUpdateCourseDeliveryModes");
		List<CourseDeliveryModesDto> courseDeliveryModeDtos = request.getCourseDelieveryModeDtos();
		if (courseDao.existsById(courseId)) {
			Course course = courseDao.get(courseId);
			List<CourseDeliveryModes> courseDeliveryModesBeforeUpdate = course.getCourseDeliveryModes().stream()
					.map(deliveryMode -> {
						CourseDeliveryModes clone = new CourseDeliveryModes();
						BeanUtils.copyProperties(deliveryMode, clone);
						return clone;
					}).collect(Collectors.toList());

			List<CourseDeliveryModes> courseDeliveryModes = course.getCourseDeliveryModes();

			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseDeliveryModeDtos.stream().forEach(e -> {
				CourseDeliveryModes courseDeliveryMode = new CourseDeliveryModes();

				BeanUtils.copyProperties(e, courseDeliveryMode);
				saveUpdateCourseFees(userId, courseDeliveryMode, e.getFees());
				saveUpdateCourseDeliveryModeFunding(userId, courseDeliveryMode, e.getFundings());

				courseDeliveryModes.add(courseDeliveryMode);

			});
			courseDeliveryModes.removeIf(e -> !contains(courseDeliveryModeDtos, e));

			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseDeliveryModesDto> dtosToReplicate = courseDeliveryModes.stream()
						.map(e -> modelMapper.map(e, CourseDeliveryModesDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseDeliveryModes(userId, request.getLinkedCourseIds(), dtosToReplicate));
			}

			courseDao.saveAll(coursesToBeSavedOrUpdated);

			if (!courseDeliveryModesBeforeUpdate.equals(courseDeliveryModes)) {
				log.info("Notify course information changed");
				final String notificationType = commonProcessor.checkIfPriceChanged(courseDeliveryModesBeforeUpdate,
						courseDeliveryModes) ? "COURSE_PRICE_CHANGED" : "COURSE_CONTENT_UPDATED";
				commonProcessor.notifyCourseUpdates(notificationType, coursesToBeSavedOrUpdated);
			}

			// commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error(messageTranslator.toLocale("course.id.invalid", Locale.US));
			throw new ValidationException(messageTranslator.toLocale("course.id.invalid"));
		}
	}

	@Transactional
	public void deleteByCourseDeliveryModeIds(String userId, String courseId, List<String> deliveryModeIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseDeliveryModesProcessor.deleteByCourseDeliveryModeIds");
		if (courseDao.existsById(courseId)) {
			Course course = courseDao.get(courseId);
			List<CourseDeliveryModes> courseDeliveryModes = course.getCourseDeliveryModes();

			if (!CollectionUtils.isEmpty(courseDeliveryModes)) {
				courseDeliveryModes.clear();
			}

			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);

			courseDao.saveAll(coursesToBeSavedOrUpdated);

			log.info("Notify course information changed");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

			// commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);

		} else {
			log.error(messageTranslator.toLocale("course.id.invalid", Locale.US));
			throw new ValidationException(messageTranslator.toLocale("course.id.invalid"));
		}

	}

	private List<Course> replicateCourseDeliveryModes(String userId, List<String> courseIds,
			List<CourseDeliveryModesDto> courseDeliveryModeDtos) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseDeliveryModes");
		if (!CollectionUtils.isEmpty(courseIds)) {
			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
			courses.stream().forEach(course -> {
				List<CourseDeliveryModes> courseDeliveryModes = course.getCourseDeliveryModes();
				if (CollectionUtils.isEmpty(courseDeliveryModeDtos)) {
					courseDeliveryModes.clear();
				} else {
					courseDeliveryModes.removeIf(e -> !contains(courseDeliveryModeDtos, e));
					courseDeliveryModeDtos.stream().forEach(dto -> {
						Optional<CourseDeliveryModes> existingCourseDeliveryModeOp = courseDeliveryModes.stream()
								.filter(e -> e.getDeliveryType().equalsIgnoreCase(dto.getDeliveryType())).findAny();
						CourseDeliveryModes courseDeliveryMode = new CourseDeliveryModes();
						boolean flage = false;
						if (existingCourseDeliveryModeOp.isPresent()) {
							courseDeliveryMode = existingCourseDeliveryModeOp.get();
							flage = true;
						}
						log.info("Adding additional infos like deliveryType, studyMode etc");

						saveUpdateCourseFees(userId, courseDeliveryMode, dto.getFees());
						saveUpdateCourseDeliveryModeFunding(userId, courseDeliveryMode, dto.getFundings());
						BeanUtils.copyProperties(dto, courseDeliveryMode);
						if (!flage) {
							courseDeliveryModes.add(courseDeliveryMode);

						}

					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}

	public static boolean contains(List<CourseDeliveryModesDto> lst, CourseDeliveryModes target) {
		return lst.stream()
				.anyMatch(t -> t.getDeliveryType().equalsIgnoreCase(target.getDeliveryType())
						&& t.getStudyMode().equalsIgnoreCase(target.getStudyMode())
						&& t.getIsGovernmentEligible().equals(target.getIsGovernmentEligible())
						&& t.getAccessibility().equalsIgnoreCase(target.getAccessibility())
						&& t.getDurationTime().equalsIgnoreCase(target.getDurationTime())
						&& t.getDuration().equals(target.getDuration()));
	}

	public void saveUpdateCourseFees(String userId, CourseDeliveryModes deliveryMode, List<CourseFeesDto> feesDtos) {
		if (!CollectionUtils.isEmpty(feesDtos)) {
			List<CourseFees> fees = deliveryMode.getFees();
			feesDtos.stream().forEach(dto -> {
				CourseFees model = new CourseFees();
				Optional<CourseFees> existingCourseFess = fees.stream()
						.filter(e -> e.getName().equalsIgnoreCase(dto.getName())).findAny();
				boolean flage = false;
				if (existingCourseFess.isPresent()) {
					model = existingCourseFess.get();
					flage = true;
				}

				BeanUtils.copyProperties(dto, model);
				if (!flage) {
					fees.add(model);

				}

			});
			fees.removeIf(e -> !contains(feesDtos, e));

		} else {
			deliveryMode.getFees().clear();
		}
	}

	public static boolean contains(List<CourseFeesDto> feesDtos, CourseFees target) {
		return feesDtos.stream().anyMatch(e -> e.getName().equalsIgnoreCase(target.getName()));

	}

	public void saveUpdateCourseDeliveryModeFunding(String userId, CourseDeliveryModes deliveryMode,
			List<CourseDeliveryModeFundingDto> deliveryModeFundingDtos) {
		if (!CollectionUtils.isEmpty(deliveryModeFundingDtos)) {
			List<CourseDeliveryModeFunding> fundings = deliveryMode.getFundings();

			deliveryModeFundingDtos.stream().forEach(dto -> {

				CourseDeliveryModeFunding model = new CourseDeliveryModeFunding();
				Optional<CourseDeliveryModeFunding> existingCourseDeliveryModeFundingOp = fundings.stream()
						.filter(e -> e.getName().equalsIgnoreCase(dto.getName())).findAny();
				boolean flage = false;
				if (existingCourseDeliveryModeFundingOp.isPresent()) {
					model = existingCourseDeliveryModeFundingOp.get();
					flage = true;
				}

				BeanUtils.copyProperties(dto, model);
				if (!flage) {
					fundings.add(model);

				}

			});
			fundings.removeIf(e -> !contains(deliveryModeFundingDtos, e));

		} else {
			deliveryMode.getFundings().clear();
		}
		
	}

	public static boolean contains(List<CourseDeliveryModeFundingDto> deliveryModeFundingDtos, CourseDeliveryModeFunding target) {
		return deliveryModeFundingDtos.stream().anyMatch(e -> e.getName().equalsIgnoreCase(target.getName()));

	}
}
