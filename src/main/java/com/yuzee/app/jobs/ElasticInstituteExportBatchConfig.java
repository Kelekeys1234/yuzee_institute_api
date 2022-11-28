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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.bean.Institute;
import com.yuzee.common.lib.dto.institute.InstituteSyncDTO;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.job.SkipAnyFailureSkipPolicy;

@Configuration
@EnableBatchProcessing
public class ElasticInstituteExportBatchConfig {
	
	@StepScope
	@Bean("instituteJpaItemReader")
	public MongoItemReader<Institute> instituteJpaItemReader(@Autowired MongoTemplate mongoTemplate) {
		  MongoItemReader<Institute> reader = new MongoItemReader<Institute>();
		    reader.setTemplate(mongoTemplate);
		    reader.setPageSize(1);
	    return reader;
	}
	
	@Bean
	public ElasticInstituteExportItemProcessor elasticInstituteExportItemProcessor() {
		return new ElasticInstituteExportItemProcessor();
	}

	@Bean("elasticInstituteExportItemWriter")
	public ElasticInstituteExportItemWriter elasticInstituteExportItemWriter() {
		return new ElasticInstituteExportItemWriter();
	}

	@Bean("exportInstituteToElastic")
	public Job exportInstituteToElasticJob(JobBuilderFactory jobs ,@Qualifier("exportInstituteToElasticStep") Step exportInstituteToElasticStep) {
		return jobs.get("exportInstituteToElastic").incrementer(new RunIdIncrementer())
				.start(exportInstituteToElasticStep)
				.build();
	}

	@Bean("exportInstituteToElasticStep")
	public Step exportInstituteToElasticStep(StepBuilderFactory stepBuilderFactory,@Qualifier("instituteJpaItemReader") ItemReader<Institute> instituteJpaItemReader,
			@Qualifier("elasticInstituteExportItemWriter")  ElasticInstituteExportItemWriter elasticInstituteExportItemWriter, ElasticInstituteExportItemProcessor elasticInstituteExportItemProcessor,
			 SkipAnyFailureSkipPolicy skipPolicy) {
		return stepBuilderFactory.get("exportInstituteToElastic").<Institute, InstituteSyncDTO>chunk(1).reader(instituteJpaItemReader)
				.faultTolerant().skipPolicy(skipPolicy).noRollback(InvokeException.class)
				.processor(elasticInstituteExportItemProcessor).writer(elasticInstituteExportItemWriter).build();
	}

}
