package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuzee.app.bean.InstituteType;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteTypeDto;
import com.yuzee.common.lib.util.DateUtil;

public class InstituteTypeItemProcessor implements ItemProcessor<InstituteTypeDto, InstituteType> {

	@Autowired
	InstituteDao instituteDao;
	
	@Override
	public InstituteType process(InstituteTypeDto instituteTypeDto) throws Exception {
		InstituteType instituteType = new InstituteType();
		instituteType.setCountryName(instituteTypeDto.getCountryName());
		return instituteType;
	}
}
