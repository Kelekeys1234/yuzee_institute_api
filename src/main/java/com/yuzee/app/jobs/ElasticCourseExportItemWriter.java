package com.yuzee.app.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.yuzee.app.dto.CourseDTOElasticSearch;
import com.yuzee.app.handler.ElasticHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticCourseExportItemWriter implements ItemWriter<CourseDTOElasticSearch> {

	@Autowired
	private ElasticHandler elasticHandler;

	@Override
	public void write(List<? extends CourseDTOElasticSearch> items) throws Exception {
		log.info("inside ElasticCourseExportItemWriter.write");
		List<CourseDTOElasticSearch> courseDTOElasticSearchs = new ArrayList<>();
		courseDTOElasticSearchs.addAll(items);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start("elasticSearchProcessor.saveCourseOnElasticSearch");
		elasticHandler.saveUpdateData(courseDTOElasticSearchs);
		stopWatch.stop();
		log.info("elasticSearchProcessor.saveCourseOnElasticSearch process completed in :::  {}",
				stopWatch.getTotalTimeSeconds());
	}
}
