package com.yuzee.app.util;

import java.util.Arrays;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.StudentCategory;
import com.yuzee.app.enumeration.TimingType;
import com.yuzee.app.exception.RuntimeValidationException;
import com.yuzee.app.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtil {

	private ValidationUtil() {

	}

	public static void validateStudentCategory(String studentCategory) throws ValidationException {

		if (!EnumUtils.isValidEnumIgnoreCase(StudentCategory.class, studentCategory)) {
			String studentCategories = Arrays.toString(Util.getEnumNames(StudentCategory.class));
			log.error("student_category must be in one of the following: ", studentCategories);
			throw new ValidationException("student_category must be in one of the following: " + studentCategories);
		}
	}

	public static void validatEntityType(String entityType) throws ValidationException {
		if (!EnumUtils.isValidEnum(EntityTypeEnum.class, entityType)) {
			log.error("entity_type must be in one of the following {}", CommonUtil.getEnumNames(EntityTypeEnum.class));
			throw new ValidationException(
					"entity_type must be in one of the following: " + CommonUtil.getEnumNames(EntityTypeEnum.class));

		}
	}

	public static void validatTimingType(String timingType) throws ValidationException {
		if (!EnumUtils.isValidEnum(TimingType.class, timingType)) {
			log.error("timing_type must be in one of the following {}", CommonUtil.getEnumNames(TimingType.class));
			throw new ValidationException(
					"timing_type must be in one of the following: " + CommonUtil.getEnumNames(TimingType.class));

		}
	}
	
	public static void validateTimingDtoFromCourseRequest(CourseRequest courseRequest) {
		if (!ObjectUtils.isEmpty(courseRequest) && !CollectionUtils.isEmpty(courseRequest.getCourseTimings())) {
			courseRequest.getCourseTimings().forEach(e -> {
				try {
					ValidationUtil.validatEntityType(e.getEntityType());
					ValidationUtil.validatTimingType(e.getTimingType());
				} catch (ValidationException e1) {
					throw new RuntimeValidationException(e1);
				}
			});
		}
	}
}
