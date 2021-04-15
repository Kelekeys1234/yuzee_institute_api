package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.dto.uploader.FacultyCSVDto;
import com.yuzee.app.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FacultyItemProcessor implements ItemProcessor<FacultyCSVDto, Faculty> {

	@Override
	public Faculty process(FacultyCSVDto facultyCsvDto) throws Exception {
		log.info("Creating faculty model for faculty name {}", facultyCsvDto.getName());
		return new Faculty(facultyCsvDto.getName(), facultyCsvDto.getDescription(), true, DateUtil.getUTCdatetimeAsDate(), DateUtil.getUTCdatetimeAsDate(), null, "AUTO", "AUTO", false);
	}

}
