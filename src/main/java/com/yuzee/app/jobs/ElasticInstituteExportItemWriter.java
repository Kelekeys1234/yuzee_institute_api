package com.yuzee.app.jobs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.common.lib.dto.institute.InstituteElasticSearchDTO;
import com.yuzee.common.lib.handler.ElasticHandler;

public class ElasticInstituteExportItemWriter implements ItemWriter<InstituteElasticSearchDTO> {
	
	@Autowired
	private ElasticHandler elasticHandler;

	@Override
	public void write(List<? extends InstituteElasticSearchDTO> items) throws Exception {
		List<InstituteElasticSearchDTO> instituteElasticDtos = new ArrayList<>();
		instituteElasticDtos.addAll(items);
		elasticHandler.saveUpdateInstitutes(instituteElasticDtos);
	}

	

}
