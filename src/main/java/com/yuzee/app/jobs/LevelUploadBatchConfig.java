package com.yuzee.app.jobs;

import java.io.IOException;


import javax.validation.ConstraintViolationException;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.yuzee.app.bean.Level;
import com.yuzee.common.lib.dto.institute.LevelDto;
import com.yuzee.common.lib.exception.UploaderException;
import com.yuzee.common.lib.job.SkipAnyFailureSkipPolicy;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j(topic = "level_import")
public class LevelUploadBatchConfig {
	@Value("${spring.jdbc.batch_size}")
	private int batchSize;

	@Bean("levelItemReader")
	@StepScope
	public LevelItemReader reader(@Value("#{jobParameters['csv-file']}") String fileName)
			throws IOException, UploaderException, com.yuzee.common.lib.exception.IOException {
		return new LevelItemReader(fileName);
	}

	@Bean("levelItemProcessor")
	public ItemProcessor<LevelDto, Level> processor() {
		return new LevelItemProcessor();
	}

	@Bean("levelItemWriter")
	@StepScope
	public MongoItemWriter<Level> writer(@Autowired MongoTemplate mongoTemplate) {
		   return new MongoItemWriterBuilder<Level>().template(mongoTemplate).collection("level")
	                .build();
	}

	@Bean("importlevelJob")
	public Job importLevelJob(JobBuilderFactory jobs, @Qualifier("levelStep") Step levelStep , @Qualifier("levelJobExecutionListener") JobExecutionListener levelJobExecutionListener) {
		return jobs.get("importlevelJob").incrementer(new RunIdIncrementer()).listener(levelJobExecutionListener).flow(levelStep)
				.end().build();
	}

	@Bean("levelStep")
	public Step levelStep(StepBuilderFactory stepBuilderFactory,@Qualifier("levelItemReader") ItemReader<LevelDto> reader,
			 SkipAnyFailureSkipPolicy skipPolicy,MongoItemWriter<Level> writer)
			 {
		return stepBuilderFactory.get("levelStep").<LevelDto, Level>chunk(batchSize).reader(reader).faultTolerant()
				.skipPolicy(skipPolicy).noRollback(ConstraintViolationException.class).processor(processor()).writer(writer).listener(levelItemWriteListner())
				.build();
	}
	
	@Bean("levelItemWriterListner")
	@StepScope
	public ItemWriteListener<Level> levelItemWriteListner() {
		return new LevelItemWriteListner();
	}
	
	@Bean("levelJobExecutionListener")
	public JobExecutionListener levelJobExecutionListener () {
		return new LevelJobExecutionListner();
	}
}
