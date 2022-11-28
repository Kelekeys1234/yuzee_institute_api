package com.yuzee.app.jobs;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.yuzee.app.bean.Course;
import com.yuzee.common.lib.dto.institute.CourseSyncDTO;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.job.SkipAnyFailureSkipPolicy;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j
public class ElasticCourseExportBatchConfig {
	
	@StepScope
	@Bean("courseJpaItemReader")
	public MongoItemReader<Course> courseJpaItemReader(@Autowired MongoTemplate mongoTemplate) {
		log.info("inside ElasticCourseExportBatchConfig.courseJpaItemReader");
		  MongoItemReader<Course> reader = new MongoItemReader<Course>();
		    reader.setTemplate(mongoTemplate);
		    reader.setPageSize(1);
	    return reader;
	}
	
	@Bean
	public ElasticCourseExportItemProcessor elasticCourseExportItemProcessor() {
		return new ElasticCourseExportItemProcessor();
	}

	@Bean("elasticCourseExportItemWriter")
	public ElasticCourseExportItemWriter elasticCourseExportItemWriter() {
		log.info("inside ElasticCourseExportBatchConfig.elasticCourseExportItemWriter");
		return new ElasticCourseExportItemWriter();
	}

	@Bean("exportCourseToElastic")
	public Job exportCourseToElasticJob(JobBuilderFactory jobs ,@Qualifier("exportCourseToElasticStep") Step exportCourseToElasticStep) {
		log.info("inside ElasticCourseExportBatchConfig.exportCourseToElasticJob");
		return jobs.get("exportCourseToElastic").incrementer(new RunIdIncrementer())
				.start(exportCourseToElasticStep)
				.build();
	}

	@Bean("exportCourseToElasticStep")
	public Step exportCourseToElasticStep(StepBuilderFactory stepBuilderFactory,@Qualifier("courseJpaItemReader") ItemReader<Course> courseJpaItemReader,
			@Qualifier("elasticCourseExportItemWriter")  ElasticCourseExportItemWriter elasticCourseExportItemWriter, ElasticCourseExportItemProcessor elasticCourseExportItemProcessor,
			 SkipAnyFailureSkipPolicy skipPolicy) {
		
		log.info("inside ElasticCourseExportBatchConfig.exportCourseToElasticStep");
		return stepBuilderFactory.get("exportCourseToElasticStep").<Course, CourseSyncDTO>chunk(50).reader(courseJpaItemReader)
				.faultTolerant().skipPolicy(skipPolicy).noRollback(InvokeException.class)
				.processor(elasticCourseExportItemProcessor).writer(elasticCourseExportItemWriter).build();
	}

}
