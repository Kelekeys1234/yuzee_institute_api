package com.yuzee.app.jobs;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.uploader.ServiceCsvDto;
import com.yuzee.common.lib.util.DateUtil;

public class ServiceItemProcessor implements ItemProcessor<ServiceCsvDto, Service> {

	@Autowired
	InstituteDao instituteDao;
	
	@Override
	public Service process(ServiceCsvDto serviceDto) throws Exception {
		Service service = new Service();
		service.setName(StringUtils.trimLeadingWhitespace(StringUtils.trimTrailingWhitespace(serviceDto.getServiceName())));
		service.setDescription(StringUtils.trimLeadingWhitespace(StringUtils.trimTrailingWhitespace(serviceDto.getDescription())));
		service.setCreatedBy("AUTO");
		service.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		service.setUpdatedBy("AUTO");
		service.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		return service;
	}
}
