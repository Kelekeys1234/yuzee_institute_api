package com.yuzee.app.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import com.yuzee.app.dao.CoursePaymentDao;
import com.yuzee.app.dto.CoursePaymentDto;
import com.yuzee.app.dto.CoursePaymentItemDto;
import com.yuzee.app.exception.ForbiddenException;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.ElasticHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CoursePaymentProcessor {
	@Autowired
	private CoursePaymentDao coursePaymentDao;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private ElasticHandler elasticHandler;
	
	@Autowired
	private ConversionProcessor conversionProcessor;
	
	@Transactional
	public void saveUpdateCoursePayment(String userId, String courseId, @Valid CoursePaymentDto coursePaymentDto)
			throws ForbiddenException, NotFoundException, ValidationException {
		log.info("inside CoursePaymentProcessor.saveUpdateCoursePayment");
		Course course = getCourseById(courseId);
		if (!course.getCreatedBy().equals(userId)) {
			log.error("no access to add course payment");
			throw new ForbiddenException("no access to add course payment");
		} else {
			CoursePayment coursePayment = course.getCoursePayment();
			if (ObjectUtils.isEmpty(coursePayment)) {
				coursePayment = new CoursePayment();
			}
			List<CoursePaymentItemDto> paymentItemDtos = coursePaymentDto.getPaymentItems();
			List<CoursePaymentItem> dbCoursePaymentItems = coursePayment.getPaymentItems();

			log.info("remove the payment items not present in request");
			dbCoursePaymentItems
					.removeIf(e -> paymentItemDtos.stream().noneMatch(t -> e.getName().equalsIgnoreCase(t.getName())));
			final CoursePayment finalCoursePayment = coursePayment;
			paymentItemDtos.stream().forEach(e -> {
				Optional<CoursePaymentItem> existingCoursePaymentItemOp = dbCoursePaymentItems.stream()
						.filter(t -> e.getName().equalsIgnoreCase(t.getName())).findAny();
				CoursePaymentItem coursePaymentItem = new CoursePaymentItem();
				String existingId = null;
				if (existingCoursePaymentItemOp.isPresent()) {
					log.info("payment item already present so going to update");
					coursePaymentItem = existingCoursePaymentItemOp.get();
					existingId = coursePaymentItem.getId();
				}
				BeanUtils.copyProperties(e, coursePaymentItem);
				coursePaymentItem.setId(existingId);
				coursePaymentItem.setCoursePayment(finalCoursePayment);
				coursePaymentItem.setAuditFields(userId);
				if (StringUtils.isEmpty(coursePaymentItem.getId())) {
					dbCoursePaymentItems.add(coursePaymentItem);
				}
			});
			coursePayment.setDescription(coursePaymentDto.getDescription());
			coursePayment.setCourse(course);
			coursePayment.setAuditFields(userId);
			log.info("going to save record in db");
			coursePaymentDao.save(coursePayment);
			log.info("Calling elastic service to save/update course on elastic index having courseId: ", courseId);
			elasticHandler.saveUpdateData(Arrays.asList(conversionProcessor.convertToCourseDTOElasticSearchEntity(course)));
		}
	}

	@Transactional
	public void deleteCoursePayment(String userId, String courseId)
			throws ForbiddenException, NotFoundException, InternalServerException {
		Course course = getCourseById(courseId);
		if (!course.getCreatedBy().equals(userId)) {
			log.error("no access to delete course payment");
			throw new ForbiddenException("no access to delete course payment");
		}
		CoursePayment coursePayment = course.getCoursePayment();
		if (!ObjectUtils.isEmpty(coursePayment)) {
			course.setCoursePayment(null);
			try {
				courseDao.addUpdateCourse(course);
				log.info("Calling elastic service to save/update course on elastic index having courseId: ", courseId);
				elasticHandler.saveUpdateData(Arrays.asList(conversionProcessor.convertToCourseDTOElasticSearchEntity(course)));
			} catch (ValidationException e) {
				log.error(e.getMessage());
				throw new InternalServerException("error deleting course payment");
			}
		} else {
			log.error("Course Payment not found against course");
			throw new NotFoundException("Course Payment not found against course");
		}
	}

	private Course getCourseById(String courseId) throws NotFoundException {
		Course course = courseDao.get(courseId);
		if (ObjectUtils.isEmpty(course)) {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
		return course;
	}
}
