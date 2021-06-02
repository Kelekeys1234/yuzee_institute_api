package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.yuzee.app.bean.Course;
import com.yuzee.app.processor.ConversionProcessor;
import com.yuzee.common.lib.dto.institute.CourseDTOElasticSearch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticCourseExportItemProcessor implements ItemProcessor<Course, CourseDTOElasticSearch> {

	@Autowired
	private ConversionProcessor conversionProcessor;

	@Override
	public CourseDTOElasticSearch process(Course course) throws Exception {
		log.info("inside ElasticCourseExportBatchConfig.exportCourseToElasticStep");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("DTOUtills.convertToCourseDTOElasticSearchEntity");
		try {
			return conversionProcessor.convertToCourseDTOElasticSearchEntity(course);
		} finally {
			stopWatch.stop();
			log.info("DTOUtills.convertToCourseDTOElasticSearchEntity process completed in ::: {}",
					stopWatch.getTotalTimeSeconds());
		}

	}
}