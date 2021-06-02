package com.yuzee.app.util;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.enumeration.TimingType;
import com.yuzee.common.lib.exception.RuntimeValidationException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtil {

	private ValidationUtil() {

	}

	public static void validatTimingType(String timingType) throws ValidationException {
		if (!EnumUtils.isValidEnum(TimingType.class, timingType)) {
			log.error("timing_type must be in one of the following {}", Utils.getEnumNamesAsString(TimingType.class));
			throw new ValidationException(
					"timing_type must be in one of the following: " + Utils.getEnumNamesAsString(TimingType.class));

		}
	}

	public static void validateTimingDtoFromCourseRequest(CourseRequest courseRequest) {
		if (!ObjectUtils.isEmpty(courseRequest) && !CollectionUtils.isEmpty(courseRequest.getCourseTimings())) {
			courseRequest.getCourseTimings().forEach(e -> {
				try {
					com.yuzee.common.lib.util.ValidationUtil.validatEntityType(e.getEntityType());
					ValidationUtil.validatTimingType(e.getTimingType());
				} catch (ValidationException e1) {
					throw new RuntimeValidationException(e1);
				}
			});
		}
	}
}
