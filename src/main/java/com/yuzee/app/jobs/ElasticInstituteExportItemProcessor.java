package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.processor.ConversionProcessor;
import com.yuzee.common.lib.dto.institute.InstituteSyncDTO;

public class ElasticInstituteExportItemProcessor implements ItemProcessor<Institute, InstituteSyncDTO> {

	@Autowired
	private ConversionProcessor conversionProcessor;

	@Override
	public InstituteSyncDTO process(Institute institute) throws Exception {
		return conversionProcessor.convertToInstituteInstituteSyncDTOSynDataEntity(institute);
	}

}
