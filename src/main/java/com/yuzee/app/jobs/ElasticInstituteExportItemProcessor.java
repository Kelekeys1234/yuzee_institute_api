package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.dto.InstituteElasticSearchDto;
import com.yuzee.app.processor.ConversionProcessor;

public class ElasticInstituteExportItemProcessor implements ItemProcessor<Institute, InstituteElasticSearchDto> {

	@Autowired
	private ConversionProcessor conversionProcessor;

	@Override
	public InstituteElasticSearchDto process(Institute institute) throws Exception {
		return conversionProcessor.convertToInstituteElasticDTOEntity(institute);
	}

}
