package com.yuzee.app.processor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CoursePayment;
import com.yuzee.app.bean.CoursePaymentItem;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.common.lib.dto.institute.CoursePaymentDto;
import com.yuzee.common.lib.dto.institute.CoursePaymentItemDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InternalServerException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CoursePaymentProcessor {
	
	@Autowired
	private CourseDao courseDao;

	@Autowired
	private PublishSystemEventHandler publishSystemEventHandler;

	@Autowired
	private CommonProcessor commonProcessor;

	@Autowired
	private ConversionProcessor conversionProcessor;

	@Autowired
	private MessageTranslator messageTranslator;

	@Transactional
	public void saveCoursePayment(String userId, String courseId, @Valid CoursePaymentDto coursePaymentDto)
			throws ForbiddenException, NotFoundException, ValidationException {
		log.info("inside CoursePaymentProcessor.saveUpdateCoursePayment");
		Course course = getCourseById(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error(messageTranslator.toLocale("courses.notfound", Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("courses.notfound"));
		} else {
			log.info("saving coursePaymentItems");
			List<CoursePaymentItem> paymentItems = coursePaymentDto.getPaymentItems().stream().map(e->new CoursePaymentItem(e.getName(),e.getAmount(),new Date(),new Date(),userId,userId)).collect(Collectors.toList());
			CoursePayment coursePayment = new CoursePayment();
			coursePayment.setDescription(coursePaymentDto.getDescription());
			coursePayment.setPaymentItems(paymentItems);
			coursePayment.setAuditFields(userId);
			coursePayment.setCreatedBy(courseId);
			coursePayment.setCreatedOn(new Date());
	        course.setCoursePayment(coursePayment);
	        courseDao.addUpdateCourse(course);
	        
		publishSystemEventHandler.syncCourses(Arrays.asList(conversionProcessor.convertToCourseSyncDTOSyncDataEntity(course)));
		}
	}
	

	@Transactional
	public void deleteCoursePayment(String userId, String courseId)
			throws ForbiddenException, NotFoundException, InternalServerException {
		Course course = getCourseById(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error(messageTranslator.toLocale("courses.notfound", Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("courses.notfound"));
		}
		CoursePayment coursePayment = course.getCoursePayment();
		if (!ObjectUtils.isEmpty(coursePayment)) {
			course.setCoursePayment(null);
			try {
				courseDao.addUpdateCourse(course);

				log.info("Send notification for course content updates");
				commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", Arrays.asList(course));

				log.info("Calling elastic service to save/update course on elastic index having courseId: ", courseId);
				// publishSystemEventHandler.syncCourses(Arrays.asList(conversionProcessor.convertToCourseSyncDTOSyncDataEntity(course)));
			} catch (ValidationException e) {
				log.error(messageTranslator.toLocale("course_payment.delete.error", Locale.US));
				throw new InternalServerException(messageTranslator.toLocale("course_payment.delete.error"));
			}
		} else {
			log.error(messageTranslator.toLocale("course_payment.notfound", Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course_payment.notfound"));
		}
	}

	private Course getCourseById(String courseId) throws NotFoundException {
		Course course = courseDao.get(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error(messageTranslator.toLocale("course_payment.course.id.invalid", courseId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course_payment.course.id.invalid", courseId));
		}
		
		return course;
	}
}
