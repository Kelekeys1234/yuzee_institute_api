package com.seeka.app.controller.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * Generate custom response for success and error
 *
 * @author SeekADegree
 *
 */
public class GenericResponseHandlers {

	private static final String DATA_CONST = "data";
	private static final String TOTAL_COUNT_CONST = "totalCount";
	private static final String STATUS_CODE_CONST = "status";
	private static final String MESSAGE_CONST = "message";
	private final Object data;
	private final HttpStatus status;
	private final String message;
	private final Integer totalCount;

	private GenericResponseHandlers(final Builder builder) {
		this.data = builder.data;
		this.message = builder.message;
		this.status = builder.status;
		this.totalCount = builder.totalCount;
	}

	public static class Builder {
		private Object data;
		private HttpStatus status;
		private String message;
		private Integer totalCount;

		public Builder setData(final Object data) {
			this.data = data;
			return this;
		}

		public Builder setStatus(final HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder setMessage(final String message) {
			this.message = message;
			return this;
		}

		public Builder setTotalCount(final Integer totalCount) {
			this.totalCount = totalCount;
			return this;
		}

		public ResponseEntity<Object> create() {
			final GenericResponseHandlers handler = new GenericResponseHandlers(this);
			final Map<String, Object> responseMap = new HashMap<>(4);
			responseMap.put(STATUS_CODE_CONST, handler.status.value());
			responseMap.put(MESSAGE_CONST, handler.message);
			if (handler.data != null) {
				responseMap.put(DATA_CONST, handler.data);
			}
			if (handler.totalCount != null) {
				responseMap.put(TOTAL_COUNT_CONST, handler.totalCount);
			}
			return new ResponseEntity<>(responseMap, handler.status);
		}

	}
}
