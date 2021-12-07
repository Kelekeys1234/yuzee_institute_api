package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseDeliveryModes;
import com.yuzee.app.bean.CoursePrerequisite;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CoursePreRequisiteRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseDeliveryModesDto;
import com.yuzee.common.lib.dto.institute.CoursePreRequisiteDto;
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
public class CoursePrerequisiteProcessor {

	@Autowired
	private CourseProcessor courseProcessor;
	
	@Autowired
	private CommonProcessor commonProcessor;
	
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MessageTranslator messageTranslator;

	@Transactional
	public List<CoursePreRequisiteDto> saveUpdatePreRequisites(String userId, String courseId,
			@Valid CoursePreRequisiteRequestWrapper request)
			throws NotFoundException, ValidationException, InternalServerException {
		log.info("inside CoursePrerequisiteProcessor.saveUpdatePreRequisites");
		List<CoursePreRequisiteDto> coursePreRequisiteDtos = request.getCoursePreRequisiteDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			List<CoursePrerequisite> coursePrerequisiteBeforeUpdate = course.getCoursePrerequisites().stream().map(deliveryMode -> {
				CoursePrerequisite clone = new CoursePrerequisite();
				BeanUtils.copyProperties(deliveryMode, clone);
				return clone;
			}).collect(Collectors.toList());
			

			log.info("preparing map of exsiting course delivery modes");
			Map<String, CoursePrerequisite> existingCoursePrerequisiteMap = course.getCoursePrerequisites().stream()
					.collect(Collectors.toMap(CoursePrerequisite::getId, e -> e));

			List<CoursePrerequisite> coursePrerequisites = course.getCoursePrerequisites();

			log.info("loop the requested list to collect the entitities to be saved/updated");
			coursePreRequisiteDtos.stream().forEach(e -> {
				CoursePrerequisite coursePrerequisite = new CoursePrerequisite();
				if (!StringUtils.isEmpty(e.getId())) {
					log.info(
							"entityId is present so going to see if it is present in db if yes then we have to update it");
					coursePrerequisite = existingCoursePrerequisiteMap.get(e.getId());
					if (ObjectUtils.isEmpty(coursePrerequisite)) {
						log.error(messageTranslator.toLocale("pre-requisite.id.invalid", e.getId(), Locale.US));
						throw new RuntimeNotFoundException(messageTranslator.toLocale("pre-requisite.id.invalid", e.getId(), Locale.US));
					}
				}

				coursePrerequisite.setCourse(course);
				coursePrerequisite.setDescription(e.getDescription());
				coursePrerequisite.setAuditFields(userId);
				if (StringUtils.isEmpty(e.getId())) {
					coursePrerequisites.add(coursePrerequisite);
				}
			});
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			
			coursesToBeSavedOrUpdated = courseDao.saveAll(coursesToBeSavedOrUpdated);
			log.info("Send notification for course content updates");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
			return coursesToBeSavedOrUpdated.get(0).getCoursePrerequisites().stream().map(e->modelMapper.map(e,CoursePreRequisiteDto.class)).toList();
			
		} else {
			log.error(messageTranslator.toLocale("course.id.invalid", courseId));
			throw new NotFoundException(messageTranslator.toLocale("course.id.invalid", courseId));
		}
	}
	
	@Transactional
	public void deleteByPreRequisiteIds(String userId, String courseId, List<String> preRequisiteIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CoursePrerequisiteProcessor.deleteByPreRequisiteIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CoursePrerequisite> coursePrerequisite = course.getCoursePrerequisites();
		if (coursePrerequisite.stream().map(CoursePrerequisite::getId).collect(Collectors.toSet())
				.containsAll(preRequisiteIds)) {
			if (coursePrerequisite.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more course_delivery_modes");
				throw new ForbiddenException("no access to delete one more course_delivery_modes");
			}
			coursePrerequisite.removeIf(e -> Utils.contains(preRequisiteIds, e.getId()));
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			courseDao.saveAll(coursesToBeSavedOrUpdated);
			
			log.info("Notify course information changed");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);
			
			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error(messageTranslator.toLocale("pre-requisite.ids.invalid",Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("pre-requisite.ids.invalid"));
		}
	}

	public static boolean contains(List<CourseDeliveryModesDto> lst, CourseDeliveryModes target) {
		return lst.stream().anyMatch(e -> e.getDeliveryType().equalsIgnoreCase(target.getDeliveryType())
				&& e.getStudyMode().equalsIgnoreCase(target.getStudyMode()));
	}
}
