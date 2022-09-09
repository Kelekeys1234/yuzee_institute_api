package com.yuzee.app.processor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CoursePrerequisiteProcessor {

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	public List<CoursePreRequisiteDto> saveUpdatePreRequisites(String userId, String courseId,
			@Valid CoursePreRequisiteRequestWrapper request)
			throws NotFoundException, ValidationException, InternalServerException {
		log.info("inside CoursePrerequisiteProcessor.saveUpdatePreRequisites");
		List<CoursePreRequisiteDto> coursePreRequisiteDtos = request.getCoursePreRequisiteDtos();
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {
			List<CoursePrerequisite> coursePrerequisiteBeforeUpdate = course.getCoursePrerequisites().stream()
					.map(deliveryMode -> {
						CoursePrerequisite clone = new CoursePrerequisite();
						try {
							BeanUtils.copyProperties(deliveryMode, clone);
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						return clone;
					}).collect(Collectors.toList());

			log.info("preparing map of exsiting course delivery modes");
		
			List<String> coursePrerequisites = course.getCoursePrerequisites();

			log.info("loop the requested list to collect the entitities to be saved/updated");
			
			coursePrerequisites.addAll(request.getCoursePreRequisiteDtos().stream().map(e->e.getDescription()).collect(Collectors.toList()));
			course.setCoursePrerequisites(coursePrerequisites);
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			coursesToBeSavedOrUpdated = courseDao.saveAll(coursesToBeSavedOrUpdated);
			return coursesToBeSavedOrUpdated.get(0).getCoursePrerequisites().stream()
					.map(e -> modelMapper.map(e, CoursePreRequisiteDto.class)).toList();

		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	@Transactional
	public void deleteByPreRequisiteIds(String userId, String courseId, List<String> deliveryModeIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CoursePrerequisiteProcessor.deleteByPreRequisiteIds");
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<String> coursePrerequisite = course.getCoursePrerequisites();
		if(!CollectionUtils.isEmpty(coursePrerequisite)) {
			course.setCoursePrerequisites(null);
		}
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);

			courseDao.saveAll(coursesToBeSavedOrUpdated);

			log.info("Notify course information changed");
		
	}

	public static boolean contains(List<CourseDeliveryModesDto> lst, CourseDeliveryModes target) {
		return lst.stream().anyMatch(e -> e.getDeliveryType().equalsIgnoreCase(target.getDeliveryType())
				&& e.getStudyMode().equalsIgnoreCase(target.getStudyMode()));
	}
}
