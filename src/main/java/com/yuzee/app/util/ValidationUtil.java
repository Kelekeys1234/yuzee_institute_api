package com.yuzee.app.util;

import java.util.Arrays;

import org.apache.commons.lang3.EnumUtils;

import com.yuzee.app.enumeration.StudentCategory;
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
}
