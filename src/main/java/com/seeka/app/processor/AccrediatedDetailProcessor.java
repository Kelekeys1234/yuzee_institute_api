package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.AccrediatedDetail;
import com.seeka.app.dao.AccrediatedDetailDao;
import com.seeka.app.dto.AccrediatedDetailDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IStorageService;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class AccrediatedDetailProcessor {

	@Autowired
	private AccrediatedDetailDao accrediatedDetailDao;
	
	@Autowired
	private IStorageService iStorageService;

	public AccrediatedDetailDto addAccrediatedDetail(AccrediatedDetailDto accrediatedDetailDto) {
		log.info("Checking accrediation is already exists or not" + accrediatedDetailDto.getEntityId());
		AccrediatedDetailDto detailDto = new AccrediatedDetailDto();
		AccrediatedDetail accrediatedDetailFromDB = accrediatedDetailDao.getAccrediationBiNameAndEntityId(accrediatedDetailDto.getAccrediatedName(), 
				accrediatedDetailDto.getEntityId());
		if(ObjectUtils.isEmpty(accrediatedDetailFromDB)) {
			log.info("Accrediation is not present in DB hence adding accrediation for entityId " + accrediatedDetailDto.getEntityId());
			AccrediatedDetail accrediatedDetail = new AccrediatedDetail();
			accrediatedDetail.setAccrediatedName(accrediatedDetailDto.getAccrediatedName());
			accrediatedDetail.setAccrediatedWebsite(accrediatedDetailDto.getAccrediatedWebsite());
			accrediatedDetail.setEntityId(accrediatedDetailDto.getEntityId());
			accrediatedDetail.setEntityType(accrediatedDetailDto.getEntityType());
			accrediatedDetail.setCreatedBy("API");
			accrediatedDetail.setCreatedOn(new Date());
			log.info("going to save accrediated details in DB for entityId " + accrediatedDetailDto.getEntityId());
			AccrediatedDetail savedAccrediatedDetail = accrediatedDetailDao.addAccrediatedDetail(accrediatedDetail);
			BeanUtils.copyProperties(savedAccrediatedDetail, detailDto);
			return detailDto;
		} else {
			log.info("Accrediation is already present for given entityId "+accrediatedDetailDto.getEntityId()+ " and for accrediatedName "
					 +accrediatedDetailDto.getAccrediatedName());
			BeanUtils.copyProperties(accrediatedDetailFromDB, detailDto);
			return detailDto;
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
				accrediatedDetailDto.setAccrediatedName(accrediatedDetail.getAccrediatedName());
				accrediatedDetailDto.setAccrediatedWebsite(accrediatedDetail.getAccrediatedWebsite());
				try {
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(accrediatedDetail.getId(), "ACCREDIATED", null, "en");
					accrediatedDetailDto.setStorage(storageDTOList);
				} catch (ValidationException e) {
					log.error("Error invoking storage service for accrediatedId "+accrediatedDetail.getId());
				}
				accrediatedDetailDtoList.add(accrediatedDetailDto);
			});
		} else {
			log.warn("AccrediatedDetail is not found for entityId "+entityId);
		}
		return accrediatedDetailDtoList;
	}
	
	
	public void deleteAccrediationDetailByEntityId(String entityId) {
		log.info("Delete accrediation for entityId " + entityId);
		accrediatedDetailDao.deleteAccrediationDetailByEntityId(entityId);
	}
	
	public AccrediatedDetailDto updateAccrediatedDetails(String id, AccrediatedDetailDto accrediatedDetailDto) throws NotFoundException {
		AccrediatedDetailDto detailDto = new AccrediatedDetailDto();
		log.info("Get accrediation for id " + id);
		Optional<AccrediatedDetail> optAccrediatedDetail = accrediatedDetailDao.getAccrediatedDetailById(id);
		if(optAccrediatedDetail.isPresent()) {
			log.info("if accrediation is present set values in Obj");
			optAccrediatedDetail.get().setAccrediatedName(accrediatedDetailDto.getAccrediatedName());
			optAccrediatedDetail.get().setAccrediatedWebsite(accrediatedDetailDto.getAccrediatedWebsite());
			optAccrediatedDetail.get().setEntityType(accrediatedDetailDto.getEntityType());
			optAccrediatedDetail.get().setEntityId(accrediatedDetailDto.getEntityId());
			optAccrediatedDetail.get().setUpdateddBy("API");
			optAccrediatedDetail.get().setUpdatedOn(new Date());
			log.info("Save accrediation details in DB for id "+id);
			AccrediatedDetail saveAccrediatedDetail = accrediatedDetailDao.addAccrediatedDetail(optAccrediatedDetail.get());
			BeanUtils.copyProperties(saveAccrediatedDetail, detailDto);
		} else {
			log.error("Accrediation not found for id "+id);
			throw new NotFoundException("Accrediation is not found for given id "+id);
		}
		return detailDto;
	}
}
