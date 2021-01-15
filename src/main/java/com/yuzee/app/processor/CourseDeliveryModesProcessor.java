package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.yuzee.app.dto.CourseDeliveryModesDto;
import com.yuzee.app.dto.CurrencyRateDto;
import com.yuzee.app.exception.CommonInvokeException;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.CommonHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CourseDeliveryModesProcessor {

	@Autowired
	private CourseDeliveryModesDao courseDeliveryModesDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CommonHandler commonHandler;

	public List<CourseDeliveryModesDto> getCourseDeliveryModesByCourseId(String courseId) {
		log.debug("Inside getCourseDeliveryModesByCourseId() method");
		List<CourseDeliveryModesDto> courseDeliveryModesResponse = new ArrayList<>();
		log.info("Fetching copurseAdditionalInfo from DB for courseId = " + courseId);
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

	public void saveUpdateCourseDeliveryModes(String userId, String courseId,
			@Valid List<CourseDeliveryModesDto> courseDeliveryModeDtos)
			throws NotFoundException, ValidationException, InternalServerException, CommonInvokeException {
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			Set<String> updateRequestIds = courseDeliveryModeDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
					.map(CourseDeliveryModesDto::getId).collect(Collectors.toSet());

			Map<String, CourseDeliveryModes> existingCourseDeliveryModesMap = courseDeliveryModesDao
					.findByIdIn(updateRequestIds.stream().collect(Collectors.toList())).stream()
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
			List<CourseDeliveryModes> courseDeliveryModes = new ArrayList<>();
			courseDeliveryModeDtos.stream().forEach(e -> {
				CourseDeliveryModes existingCourseDeliveryMode = existingCourseDeliveryModesMap.get(e.getId());
				CourseDeliveryModes courseDeliveryMode = new CourseDeliveryModes();
				if (!ObjectUtils.isEmpty(existingCourseDeliveryMode)) {
					courseDeliveryMode = existingCourseDeliveryMode;
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
				if (StringUtils.isEmpty(courseDeliveryMode.getId())) {
					courseDeliveryMode.setAuditFields(userId, null);
				} else {
					courseDeliveryMode.setAuditFields(userId, courseDeliveryMode);
				}
				courseDeliveryModes.add(courseDeliveryMode);
			});
			courseDeliveryModesDao.saveAll(courseDeliveryModes);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	public void deleteByCourseDeliveryModeIds(String userId, List<String> intakeIds)
			throws NotFoundException, ForbiddenException {
		List<CourseDeliveryModes> courseDeliveryModes = courseDeliveryModesDao.findByIdIn(intakeIds);
		if (intakeIds.size() != courseDeliveryModes.size()) {
			if (courseDeliveryModes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more course_delivery_modes");
				throw new ForbiddenException("no access to delete one more course_delivery_modes");
			}
			courseDeliveryModesDao.deleteByIdIn(intakeIds);
		} else {
			log.error("one or more invalid course_delivery_mode_ids");
			throw new NotFoundException("one or more invalid course_delivery_mode_ids");
		}
	}
}
