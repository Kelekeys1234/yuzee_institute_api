package com.yuzee.app.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.app.dto.InstituteElasticSearchDto;
import com.yuzee.app.handler.ElasticHandler;

public class ElasticInstituteExportItemWriter implements ItemWriter<InstituteElasticSearchDto> {
	
	@Autowired
	private ElasticHandler elasticHandler;

	@Override
	public void write(List<? extends InstituteElasticSearchDto> items) throws Exception {
		List<InstituteElasticSearchDto> instituteElasticDtos = new ArrayList<>();
		instituteElasticDtos.addAll(items);
		elasticHandler.saveUpdateInstitutes(instituteElasticDtos);
	}

	

}
