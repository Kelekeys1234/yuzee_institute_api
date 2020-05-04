package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.AccrediatedDetail;
import com.seeka.app.dao.AccrediatedDetailDao;
import com.seeka.app.dto.AccrediatedDetailDto;
import com.seeka.app.exception.NotFoundException;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class AccrediatedDetailProcessor {

	@Autowired
	private AccrediatedDetailDao accrediatedDetailDao;

	public String addAccrediatedDetail(AccrediatedDetailDto accrediatedDetailDto) {
		log.info("Checking accrediation is already exists or not" + accrediatedDetailDto.getEntityId());
		AccrediatedDetail accrediatedDetailFromDB = accrediatedDetailDao.getAccrediationBiNameAndEntityId(accrediatedDetailDto.getName(), 
				accrediatedDetailDto.getEntityId());
		if(ObjectUtils.isEmpty(accrediatedDetailFromDB)) {
			log.info("Accrediation is not present in DB hence adding accrediation for entityId " + accrediatedDetailDto.getEntityId());
			AccrediatedDetail accrediatedDetail = new AccrediatedDetail();
			accrediatedDetail.setAccrediatedName(accrediatedDetailDto.getName());
			accrediatedDetail.setAccrediatedWebsite(accrediatedDetailDto.getWebsiteLink());
			accrediatedDetail.setEntityId(accrediatedDetailDto.getEntityId());
			accrediatedDetail.setEntityType(accrediatedDetailDto.getEntityType());
			accrediatedDetail.setCreatedBy("API");
			accrediatedDetail.setCreatedDate(new Date());
			log.info("going to save accrediated details in DB for entityId " + accrediatedDetailDto.getEntityId());
			return accrediatedDetailDao.addAccrediatedDetail(accrediatedDetail);
		} else {
			log.info("Accrediation is already present for given entityId "+accrediatedDetailDto.getEntityId()+ " and for accrediatedName "+accrediatedDetailDto.getName());
			return accrediatedDetailFromDB.getId();
		}
	}
	
	public List<AccrediatedDetailDto> getAccrediationDetailByEntityId(String entityId) throws NotFoundException {
		List<AccrediatedDetailDto> accrediatedDetailDtoList = new ArrayList<>();
		log.info("Get accrediation for entityId " + entityId);
		List<AccrediatedDetail> accrediatedDetails = accrediatedDetailDao.getAccrediationDetailByEntityId(entityId);
		log.info("Checking if accrediatedDetail is present in DB or not for entityId " + entityId);
		if(!CollectionUtils.isEmpty(accrediatedDetails)) {
			log.info("AccrediatedDetail is present in DB start fetching value");
			accrediatedDetails.stream().forEach(accrediatedDetail -> {
				AccrediatedDetailDto accrediatedDetailDto = new AccrediatedDetailDto();
				accrediatedDetailDto.setId(accrediatedDetail.getId());
				accrediatedDetailDto.setEntityId(accrediatedDetail.getEntityId());
				accrediatedDetailDto.setEntityType(accrediatedDetail.getEntityType());
				accrediatedDetailDto.setName(accrediatedDetail.getAccrediatedName());
				accrediatedDetailDto.setWebsiteLink(accrediatedDetail.getAccrediatedWebsite());
				accrediatedDetailDtoList.add(accrediatedDetailDto);
			});
		} else {
			log.warn("AccrediatedDetail is not found for entityId "+entityId);
		}
		return accrediatedDetailDtoList;
	}
}
