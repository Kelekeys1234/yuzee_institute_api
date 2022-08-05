package com.yuzee.app.util;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.dto.CourseRequest;
import com.yuzee.app.dto.TimingRequestDto;
import com.yuzee.app.enumeration.TimingType;
import com.yuzee.common.lib.exception.RuntimeValidationException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtil {

	private ValidationUtil() {

	}

	private static MessageTranslator staticMessageTranslator;

	@Autowired
	private MessageTranslator messageTranslator;

	@PostConstruct
	private void init() {
		staticMessageTranslator = messageTranslator;
	}

	public static void validatTimingType(String timingType) throws ValidationException {
		if (!EnumUtils.isValidEnum(TimingType.class, timingType)) {
			log.error(staticMessageTranslator.toLocale("validation.type", Utils.getEnumNamesAsString(TimingType.class),
					Locale.US));
			throw new ValidationException(
					staticMessageTranslator.toLocale("validation.type", Utils.getEnumNamesAsString(TimingType.class)));

		}
	}

	public static void validateTimingDtoFromCourseRequest(CourseRequest courseRequest) {
		if (!ObjectUtils.isEmpty(courseRequest) && !CollectionUtils.isEmpty(courseRequest.getCourseTimings())) {
			// courseRequest.getCourseTimings().forEach(e -> {
			for (TimingRequestDto e : courseRequest.getCourseTimings()) {
				try {
					com.yuzee.common.lib.util.ValidationUtil.validatEntityType(e.getEntityType());
					ValidationUtil.validatTimingType(e.getTimingType());
				} catch (ValidationException e1) {
					throw new RuntimeValidationException(e1);
				}
			}
			;
		}
	}
}
