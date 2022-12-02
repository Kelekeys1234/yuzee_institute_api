package com.yuzee.app.jobs;

import java.io.IOException;

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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseCurriculum;
import com.yuzee.app.dto.uploader.CourseCsvDto;
import com.yuzee.common.lib.exception.UploaderException;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableBatchProcessing
@Slf4j(topic = "course_import")
public class CourseUploadBatchConfig {

	@Value("${spring.jdbc.batch_size}")
	private int batchSize;

    @Bean("courseItemReader")
    @StepScope
    public CourseItemReader reader(@Value("#{jobParameters['csv-file']}") String fileName) throws IOException, UploaderException, com.yuzee.common.lib.exception.IOException {
        return new CourseItemReader(fileName);
    }

    @Bean("courseItemProcessor")
    public ItemProcessor<CourseCsvDto, Course> processor() {
        return new CourseItemProcessor();
    }
 
  
    @Bean("courseItemWriter")
    @StepScope
    public MongoItemWriter<Course> writer(MongoTemplate mongoTemplate) {
		 return new MongoItemWriterBuilder<Course>().template(mongoTemplate).collection("course")
	                .build();
    }
    
    @Bean("curriculumItemWriter")
    @StepScope
    public MongoItemWriter<CourseCurriculum> curriculumItemWriter(MongoTemplate mongoTemplate) {
		 return new MongoItemWriterBuilder<CourseCurriculum>().template(mongoTemplate).collection("CourseCurriculum")
	                .build();
    }
	
    @Bean("importCourseJob")
    public Job importCourseJob(JobBuilderFactory jobs,@Qualifier("facultyStep") Step facultyStep, @Qualifier ("courseStep") Step courseStep,
    		@Qualifier("courseJobExecutionListner") JobExecutionListener courseJobExecutionListner) {
        return jobs.get("importCourseJob")
                .incrementer(new RunIdIncrementer()).listener(courseJobExecutionListner)
                .start(facultyStep).next(courseStep)
                .build();
    }
    
  
    @Bean("courseStep")
    public Step courseStep(StepBuilderFactory stepBuilderFactory, @Qualifier ("courseItemReader") ItemReader<CourseCsvDto> reader,
    		@Qualifier ("courseItemWriter") ItemWriter<Course> writer,@Qualifier ("courseItemProcessor") ItemProcessor<CourseCsvDto, Course> processor,
            CourseImportSkipPolicy skipPolicy,@Qualifier("courseItemWriterListner") ItemWriteListener<Course> courseItemWriterListner) {
        return stepBuilderFactory.get("courseStep")
                .<CourseCsvDto, Course> chunk(1)
                .reader(reader)
                .faultTolerant().skipPolicy(skipPolicy)
                .processor(processor)
                .writer(writer).listener(courseItemWriterListner)
                .build();
    }
	
    @Bean("courseItemWriterListner")
    @StepScope
    public ItemWriteListener<Course> courseItemListner() {
    	return new CourseItemWriteListener();
    }



    @Bean("courseJobExecutionListner")
    public JobExecutionListener courseJobExecutionListner () {
    	return new CourseJobExecutionListner();
    }

}