package com.yuzee.app.jobs;

import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.app.bean.Faculty;
import com.yuzee.common.lib.dto.institute.FacultyDto;

public class ElasticFacultyExportItemProcessor implements ItemProcessor<Faculty, FacultyDto> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public FacultyDto process(Faculty faculty) throws Exception {
		return modelMapper.map(faculty, FacultyDto.class);
	}
}
