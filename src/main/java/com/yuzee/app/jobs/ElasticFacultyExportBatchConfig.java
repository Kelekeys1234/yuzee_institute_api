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

import com.yuzee.app.bean.Faculty;
import com.yuzee.common.lib.dto.institute.FacultyDto;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.job.SkipAnyFailureSkipPolicy;

@Configuration
@EnableBatchProcessing
public class ElasticFacultyExportBatchConfig {
	
	@StepScope
	@Bean("facultyJpaItemReader")
	public MongoItemReader<Faculty> facultyJpaItemReader(@Autowired MongoTemplate mongoTemplate) {
		  MongoItemReader<Faculty> reader = new MongoItemReader<Faculty>();
		    reader.setTemplate(mongoTemplate);
		    reader.setPageSize(1);
	    return reader;
	}
	
	@Bean
	public ElasticFacultyExportItemProcessor elasticFacultyExportItemProcessor() {
		return new ElasticFacultyExportItemProcessor();
	}

	@Bean("elasticFacultyExportItemWriter")
	public ElasticFacultyExportItemWriter elasticFacultyExportItemWriter() {
		return new ElasticFacultyExportItemWriter();
	}

	@Bean("exportFacultyToElastic")
	public Job exportFacultyToElasticJob(JobBuilderFactory jobs ,@Qualifier("exportFacultyToElasticStep") Step exportFacultyToElasticStep) {
		return jobs.get("exportFacultyToElastic").incrementer(new RunIdIncrementer())
				.start(exportFacultyToElasticStep)
				.build();
	}

	@Bean("exportFacultyToElasticStep")
	public Step exportFacultyToElasticStep(StepBuilderFactory stepBuilderFactory,@Qualifier("facultyJpaItemReader") ItemReader<Faculty> facultyJpaItemReader,
			@Qualifier("elasticFacultyExportItemWriter")  ElasticFacultyExportItemWriter elasticFacultyExportItemWriter, ElasticFacultyExportItemProcessor elasticFacultyExportItemProcessor,
			 SkipAnyFailureSkipPolicy skipPolicy) {
		return stepBuilderFactory.get("exportFacultyToElastic").<Faculty, FacultyDto>chunk(1).reader(facultyJpaItemReader)
				.faultTolerant().skipPolicy(skipPolicy).noRollback(InvokeException.class)
				.processor(elasticFacultyExportItemProcessor).writer(elasticFacultyExportItemWriter).build();
	}

}
