package com.yuzee.app.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.common.lib.dto.institute.FacultyDto;
import com.yuzee.common.lib.handler.PublishSystemEventHandler;

public class ElasticFacultyExportItemWriter implements ItemWriter<FacultyDto> {
	
	@Autowired
	private PublishSystemEventHandler publishSystemEventHandler;

	@Override
	public void write(List<? extends FacultyDto> items) throws Exception {
		List<FacultyDto> facultyDtos = new ArrayList<>();
		facultyDtos.addAll(items);
		publishSystemEventHandler.syncFaculties(facultyDtos);
	}
}
