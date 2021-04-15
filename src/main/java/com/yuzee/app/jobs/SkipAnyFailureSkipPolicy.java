package com.yuzee.app.jobs;

import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SkipAnyFailureSkipPolicy implements SkipPolicy {

	@Value("${spring.batch.job.max-skip}")
	private int maxSkip;
	
	@Override
	public boolean shouldSkip(Throwable exception, int skipCount) {
		return true;
	}
	
}