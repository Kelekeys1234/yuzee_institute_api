package com.yuzee.app.util;

import java.util.Arrays;

import org.apache.commons.lang3.EnumUtils;

import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.StudentCategory;
import com.yuzee.app.enumeration.TimingType;
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
}
