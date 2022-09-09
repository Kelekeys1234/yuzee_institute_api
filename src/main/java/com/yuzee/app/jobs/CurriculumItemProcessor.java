package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;

import com.yuzee.app.bean.CourseCurriculum;
import com.yuzee.common.lib.dto.institute.CourseCurriculumDto;
import com.yuzee.common.lib.util.DateUtil;

public class CurriculumItemProcessor implements ItemProcessor<CourseCurriculumDto, CourseCurriculum> {

	@Override
	public CourseCurriculum process(CourseCurriculumDto courseCurriculumDto) throws Exception {
		CourseCurriculum courseCurriculum = new CourseCurriculum();
		courseCurriculum.setName(courseCurriculumDto.getName());
		courseCurriculum.setIsActive(true);
		
		return courseCurriculum;
	}
	
}