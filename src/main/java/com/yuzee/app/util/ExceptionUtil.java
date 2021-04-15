package com.yuzee.app.util;

import java.util.Objects;

public class ExceptionUtil {
	private ExceptionUtil() {}
	public static Throwable findCauseUsingPlainJava(Throwable throwable) {
	    Objects.requireNonNull(throwable);
	    Throwable rootCause = throwable;
	    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
	        rootCause = rootCause.getCause();
	    }
	    return rootCause;
	}
}
