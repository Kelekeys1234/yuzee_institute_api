package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseDeliveryModesDao;
import com.yuzee.app.dto.CourseDeliveryModeRequestWrapper;
import com.yuzee.app.dto.CourseDeliveryModesDto;
import com.yuzee.app.dto.CurrencyRateDto;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.RuntimeNotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.CommonHandler;
import com.yuzee.app.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CourseDeliveryModesProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CommonHandler commonHandler;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CourseDeliveryModesDao courseDeliveryModesDao;

	@Autowired
	private CommonProcessor commonProcessor;

	public List<CourseDeliveryModesDto> getCourseDeliveryModesByCourseId(String courseId) {
		log.debug("Inside getCourseDeliveryModesByCourseId() method");
		List<CourseDeliveryModesDto> courseDeliveryModesResponse = new ArrayList<>();
		log.info("Fetching courseDeliveryModes from DB for courseId = " + courseId);
		List<CourseDeliveryModes> courseDeliveryModesFromDB = courseDeliveryModesDao
				.getCourseDeliveryModesByCourseId(courseId);
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
			throws NotFoundException, ValidationException, InternalServerException, CommonInvokeException {
		log.info("inside CourseDeliveryModesProcessor.saveUpdateCourseDeliveryModes");
		List<CourseDeliveryModesDto> courseDeliveryModeDtos = request.getCourseDelieveryModeDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			List<CourseDeliveryModes> courseDeliveryModesBeforeUpdate = course.getCourseDeliveryModes().stream().map(deliveryMode -> {
				CourseDeliveryModes clone = new CourseDeliveryModes();
				BeanUtils.copyProperties(deliveryMode, clone);
				return clone;
			}).collect(Collectors.toList());
			

			log.info("preparing map of exsiting course delivery modes");
			Map<String, CourseDeliveryModes> existingCourseDeliveryModesMap = course.getCourseDeliveryModes().stream()
					.collect(Collectors.toMap(CourseDeliveryModes::getId, e -> e));

			CurrencyRateDto currencyRate = null;
			if (StringUtils.isEmpty(course.getCurrency())) {
				log.error("course currency should not be null");
				throw new InternalServerException("course currency should not be null");
			} else {
				currencyRate = commonHandler.getCurrencyRateByCurrencyCode(course.getCurrency());
				if (ObjectUtils.isEmpty(currencyRate)) {
					log.error("error in getting currency rate");
					throw new InternalServerException("error in getting currency rate");
				}
			}
			final CurrencyRateDto finalCurrencyRate = currencyRate;
			List<CourseDeliveryModes> courseDeliveryModes = course.getCourseDeliveryModes();

			log.info("loop the requested list to collect the entitities to be saved/updated");
			courseDeliveryModeDtos.stream().forEach(e -> {
				CourseDeliveryModes courseDeliveryMode = new CourseDeliveryModes();
				if (!StringUtils.isEmpty(e.getId())) {
					log.info(
							"entityId is present so going to see if it is present in db if yes then we have to update it");
					courseDeliveryMode = existingCourseDeliveryModesMap.get(e.getId());
					if (ObjectUtils.isEmpty(courseDeliveryMode)) {
						log.error("invalid course delivery mode id : {}", e.getId());
						throw new RuntimeNotFoundException("invalid course delivery mode id : " + e.getId());
					}
				}

				if (e.getDomesticFee() != null) {
					log.info("converting domestic fee into usdDomestic fee having conversionRate = ",
							finalCurrencyRate.getConversionRate());
					Double convertedRate = e.getDomesticFee() / finalCurrencyRate.getConversionRate();
					if (convertedRate != null) {
						courseDeliveryMode.setUsdDomesticFee(convertedRate);
					}
				}
				if (e.getInternationalFee() != null) {
					log.info("converting international fee into usdInternational fee having conversionRate = ",
							finalCurrencyRate.getConversionRate());
					Double convertedRate = e.getInternationalFee() / finalCurrencyRate.getConversionRate();
					if (convertedRate != null) {
						courseDeliveryMode.setUsdInternationalFee(convertedRate);
					}
				}

				courseDeliveryMode.setCourse(course);
				log.info("Adding additional infos like deliveryType, studyMode etc");
				courseDeliveryMode.setDeliveryType(e.getDeliveryType());
				courseDeliveryMode.setDomesticFee(e.getDomesticFee());
				courseDeliveryMode.setInternationalFee(e.getInternationalFee());
				courseDeliveryMode.setStudyMode(e.getStudyMode());
				courseDeliveryMode.setDuration(e.getDuration());
				courseDeliveryMode.setDurationTime(e.getDurationTime());
				courseDeliveryMode.setCourse(course);
				courseDeliveryMode.setAuditFields(userId);
				if (StringUtils.isEmpty(e.getId())) {
					courseDeliveryModes.add(courseDeliveryMode);
				}
			});
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
				List<CourseDeliveryModesDto> dtosToReplicate = courseDeliveryModes.stream()
						.map(e -> modelMapper.map(e, CourseDeliveryModesDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseDeliveryModes(userId, request.getLinkedCourseIds(), dtosToReplicate));
			}
			
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			if(!courseDeliveryModesBeforeUpdate.equals(courseDeliveryModes)) {
				log.info("Notify course information changed");
				final String notificationType = commonProcessor.checkIfPriceChanged(courseDeliveryModesBeforeUpdate, courseDeliveryModes) ? "COURSE_PRICE_CHANGED" : "COURSE_CONTENT_UPDATED";
				commonProcessor.notifyCourseUpdates(notificationType, coursesToBeSavedOrUpdated);
			}
			
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}
	
	@Transactional
	public void deleteByCourseDeliveryModeIds(String userId, String courseId, List<String> deliveryModeIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseDeliveryModesProcessor.deleteByCourseDeliveryModeIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseDeliveryModes> courseDeliveryModes = course.getCourseDeliveryModes();
		if (courseDeliveryModes.stream().map(CourseDeliveryModes::getId).collect(Collectors.toSet())
				.containsAll(deliveryModeIds)) {
			if (courseDeliveryModes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more course_delivery_modes");
				throw new ForbiddenException("no access to delete one more course_delivery_modes");
			}
			courseDeliveryModes.removeIf(e -> Util.contains(deliveryModeIds, e.getId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
				List<CourseDeliveryModesDto> dtosToReplicate = courseDeliveryModes.stream()
						.map(e -> modelMapper.map(e, CourseDeliveryModesDto.class)).collect(Collectors.toList());
				coursesToBeSavedOrUpdated
						.addAll(replicateCourseDeliveryModes(userId, linkedCourseIds, dtosToReplicate));
			}
			
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			log.info("Notify course information changed");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("one or more invalid course_delivery_mode_ids");
			throw new NotFoundException("one or more invalid course_delivery_mode_ids");
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
								.filter(e -> e.getDeliveryType().equalsIgnoreCase(dto.getDeliveryType())
										&& e.getStudyMode().equalsIgnoreCase(dto.getStudyMode()))
								.findAny();
						CourseDeliveryModes courseDeliveryMode = new CourseDeliveryModes();
						if (existingCourseDeliveryModeOp.isPresent()) {
							courseDeliveryMode = existingCourseDeliveryModeOp.get();
						}
						courseDeliveryMode.setCourse(course);
						log.info("Adding additional infos like deliveryType, studyMode etc");

						courseDeliveryMode.setDeliveryType(dto.getDeliveryType());
						courseDeliveryMode.setDomesticFee(dto.getDomesticFee());
						courseDeliveryMode.setUsdDomesticFee(dto.getUsdDomesticFee());
						courseDeliveryMode.setInternationalFee(dto.getInternationalFee());
						courseDeliveryMode.setUsdInternationalFee(dto.getUsdInternationalFee());
						courseDeliveryMode.setStudyMode(dto.getStudyMode());
						courseDeliveryMode.setDuration(dto.getDuration());
						courseDeliveryMode.setDurationTime(dto.getDurationTime());
						courseDeliveryMode.setCourse(course);
						if (StringUtils.isEmpty(courseDeliveryMode.getId())) {
							courseDeliveryModes.add(courseDeliveryMode);
						}
						courseDeliveryMode.setAuditFields(userId);
					});
				}
			});
			return courses;
		}
		return new ArrayList<>();
	}

	public static boolean contains(List<CourseDeliveryModesDto> lst, CourseDeliveryModes target) {
		return lst.stream().anyMatch(e -> e.getDeliveryType().equalsIgnoreCase(target.getDeliveryType())
				&& e.getStudyMode().equalsIgnoreCase(target.getStudyMode()));
	}
}
