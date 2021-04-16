package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.processor.ConversionProcessor;
import com.yuzee.common.lib.dto.institute.InstituteElasticSearchDTO;

public class ElasticInstituteExportItemProcessor implements ItemProcessor<Institute, InstituteElasticSearchDTO> {

	@Autowired
	private ConversionProcessor conversionProcessor;

	@Override
	public InstituteElasticSearchDTO process(Institute institute) throws Exception {
		return conversionProcessor.convertToInstituteElasticDTOEntity(institute);
	}

}
