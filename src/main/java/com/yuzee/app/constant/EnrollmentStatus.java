package com.yuzee.app.constant;

import java.util.HashMap;
import java.util.Map;

public enum EnrollmentStatus implements BasicStatus<EnrollmentStatus> {

	SUBMITTED("SUBMITTED", "SUBMITTED"), SEEKA_REVIEWED("SEEKA_REVIEWED", "SEEKA REVIEWED"), PREPARED("PREPARED", "PREPARED"),
	INSTITUTE_SUBMITTED("INSTITUTE_SUBMITTED", "INSTITUTE SUBMITTED"), INSTITUTE_REVIEWED("INSTITUTE_REVIEWED", "INSTITUTE REVIEWED"),
	INSTITUTE_OFFERED("INSTITUTE_OFFERED", "INSTITUTE OFFERED"), APPLICANT_APPROVED("APPLICANT_APPROVED", "APPLICANT APPROVED"),
	APPROVED("APPROVED", "APPROVED"), REJECTED("REJECTED", "REJECTED");

	String statusValue;
	String displayValue;

	public String getValue() {
		return statusValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	EnrollmentStatus(final String statusValue, final String displayValue) {
		this.statusValue = statusValue;
		this.displayValue = displayValue;
	}

	private static final Map<String, EnrollmentStatus> STATUS_TYPE_MAP = new HashMap<>();
	static {
		for (final EnrollmentStatus TaskTypeEnum : values()) {
			STATUS_TYPE_MAP.put(TaskTypeEnum.getValue(), TaskTypeEnum);
		}
	}

	public static EnrollmentStatus getByValue(final String value) {
		return STATUS_TYPE_MAP.get(value);
	}

	@Override
	public String getStatusValue() {
		return statusValue;
	}

	@Override
	public BasicStatus<EnrollmentStatus>[] nextStatus() {
		EnrollmentStatus[] nextStatus = null;
		switch (this) {

		case SUBMITTED:
			nextStatus = new EnrollmentStatus[] { SEEKA_REVIEWED, REJECTED };
			break;
		case SEEKA_REVIEWED:
			nextStatus = new EnrollmentStatus[] { PREPARED, REJECTED };
			break;
		case PREPARED:
			nextStatus = new EnrollmentStatus[] { INSTITUTE_SUBMITTED, REJECTED };
			break;
		case INSTITUTE_SUBMITTED:
			nextStatus = new EnrollmentStatus[] { INSTITUTE_REVIEWED, REJECTED };
			break;
		case INSTITUTE_REVIEWED:
			nextStatus = new EnrollmentStatus[] { INSTITUTE_OFFERED, REJECTED };
			break;
		case INSTITUTE_OFFERED:
			nextStatus = new EnrollmentStatus[] { APPLICANT_APPROVED, REJECTED };
			break;
		case APPLICANT_APPROVED:
			nextStatus = new EnrollmentStatus[] { APPROVED, REJECTED };
			break;
		default:
			break;
		}

		return nextStatus;
	}

}
