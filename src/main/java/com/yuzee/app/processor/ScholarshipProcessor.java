package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Level;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipIntakes;
import com.yuzee.app.bean.ScholarshipLanguage;
import com.yuzee.app.dao.LevelDao;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.app.dto.LevelDto;
import com.yuzee.app.dto.MediaDto;
import com.yuzee.app.dto.ScholarshipCountDto;
import com.yuzee.app.dto.ScholarshipDto;
import com.yuzee.app.dto.ScholarshipElasticDTO;
import com.yuzee.app.dto.ScholarshipResponseDTO;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntityType;
import com.yuzee.app.enumeration.ImageCategory;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.ElasticHandler;
import com.yuzee.app.util.IConstant;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
@Transactional
public class ScholarshipProcessor {

	@Autowired
	private ScholarshipDao iScholarshipDAO;

	@Autowired
	private LevelDao levelDAO;

	@Autowired
	private ElasticHandler elasticHandler;
	
	@Autowired
	private StorageProcessor storageProcessor;

	public Scholarship saveScholarship(final ScholarshipDto scholarshipDto) throws ValidationException {
		log.debug("Inside saveScholarship() method");
		Scholarship scholarship = new Scholarship();
		log.info("Copying data from Bean class to DTO");
		BeanUtils.copyProperties(scholarshipDto, scholarship);
		scholarship.setIsActive(true);
		scholarship.setCreatedOn(new Date());
		scholarship.setUpdatedOn(new Date());
		scholarship.setCreatedBy("API");
		if (scholarshipDto.getLevelId() != null) {
			log.info("LevelId is not nll, hence fetching level data from DB");
			Level level = levelDAO.getLevel(scholarshipDto.getLevelId());
			if (level == null) {
				log.error("Level not found for id" + scholarshipDto.getLevelId());
				throw new ValidationException("Level not found for id" + scholarshipDto.getLevelId());
			}
			scholarship.setLevel(level);
		}
		if (scholarshipDto.getCountryName() != null) {
			scholarship.setCountryName(scholarshipDto.getCountryName());
		}
		if (scholarshipDto.getInstituteName() != null) {
			scholarship.setInstituteName(scholarshipDto.getInstituteName());
		}
		log.info("Calling DAO layer to save scholarship in DB");
		iScholarshipDAO.saveScholarship(scholarship);

		ScholarshipElasticDTO scholarshipElasticDto = new ScholarshipElasticDTO();
		log.info("Copying data from DTO class to elasticSearch DTO class");
		BeanUtils.copyProperties(scholarship, scholarshipElasticDto);
		scholarshipElasticDto.setCountryName(scholarship.getCountryName() != null ? scholarship.getCountryName() : null);
		scholarshipElasticDto.setCountryName(scholarshipDto.getCourseName());
		scholarshipElasticDto.setInstituteName(scholarship.getInstituteName() != null ? scholarship.getInstituteName() : null);
		scholarshipElasticDto.setLevelName(scholarship.getLevel() != null ? scholarship.getLevel().getName() : null);
		scholarshipElasticDto.setLevelCode(scholarship.getLevel() != null ? scholarship.getLevel().getCode() : null);
		scholarshipElasticDto.setAmount(scholarship.getScholarshipAmount());

		
		if ((scholarshipDto.getIntakes() != null) && !scholarshipDto.getIntakes().isEmpty()) {
			log.info("Scholarship intakes is not null, start iterating data");
			for (String intake : scholarshipDto.getIntakes()) {
				ScholarshipIntakes scholarshipIntakes = new ScholarshipIntakes();
				scholarshipIntakes.setScholarship(scholarship);
				scholarshipIntakes.setName(intake);
				log.info("Calling DAO layer to save scholarship intakes in DB");
				iScholarshipDAO.saveScholarshipIntake(scholarshipIntakes);
			}
		}
		if ((scholarshipDto.getLanguages() != null) && !scholarshipDto.getLanguages().isEmpty()) {
			log.info("Scholarship languages is not null, start iterating data");
			for (String language : scholarshipDto.getLanguages()) {
				ScholarshipLanguage scholarshipLanguage = new ScholarshipLanguage();
				scholarshipLanguage.setScholarship(scholarship);
				scholarshipLanguage.setName(language);
				log.info("Calling DAO layer to save scholarship language in DB");
				iScholarshipDAO.saveScholarshipLanguage(scholarshipLanguage);
			}
		}
		scholarshipElasticDto.setIntake(scholarshipDto.getIntakes());
		scholarshipElasticDto.setLanguages(scholarshipDto.getLanguages());
		
		log.info("Calling elastic search service to save data on elastic index");
		elasticHandler.saveScholarshipOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_SCHOLARSHIP,
				EntityType.SCHOLARSHIP.name().toLowerCase(), scholarshipElasticDto, IConstant.ELASTIC_SEARCH);
		return scholarship;
	}

	
	public ScholarshipResponseDTO getScholarshipById(final String id) throws ValidationException {
		log.debug("Inside getScholarshipById() method");
		ScholarshipResponseDTO scholarshipResponseDTO = new ScholarshipResponseDTO();
		log.info("Fetching scholarship from DB having scholarshipId = "+id);
		Scholarship scholarship = iScholarshipDAO.getScholarshipById(id);
		BeanUtils.copyProperties(scholarship, scholarshipResponseDTO);
		log.info("fetching ScholarshipIntakes from DB fro scholarshipId = "+id);
		List<ScholarshipIntakes> scholarshipIntakes = iScholarshipDAO.getIntakeByScholarship(id);
		if ((scholarshipIntakes != null) && !scholarshipIntakes.isEmpty()) {
			log.info("Scholarship Intakes fetched from DB, start iterating data");
			List<String> intakes = new ArrayList<>();
			for (ScholarshipIntakes schIntakes : scholarshipIntakes) {
				intakes.add(schIntakes.getName());
			}
			scholarshipResponseDTO.setIntakes(intakes);
		}
		log.info("Fetching scholarship languages from DB for scholarshipId = "+id);
		List<ScholarshipLanguage> scholarshipLanguages = iScholarshipDAO.getLanguageByScholarship(id);
		if ((scholarshipLanguages != null) && !scholarshipLanguages.isEmpty()) {
			log.info("Scholarship languages fetched from DB, start iterating data");
			List<String> languages = new ArrayList<>();
			for (ScholarshipLanguage scholarshipLanguage : scholarshipLanguages) {
				languages.add(scholarshipLanguage.getName());
			}
			scholarshipResponseDTO.setLanguages(languages);
		}
		scholarshipResponseDTO.setCountryName(scholarship.getCountryName());
		scholarshipResponseDTO.setLevelId(scholarship.getLevel().getId());
		scholarshipResponseDTO.setCountryName(scholarship.getCountryName());
		scholarshipResponseDTO.setLevelName(scholarship.getLevel().getName());
		scholarshipResponseDTO.setInstituteName(scholarship.getInstituteName());
		
		log.info("Calling Storage Service to get scholarship images having entityId = "+id);
		List<StorageDto> storageDTOList = storageProcessor.getStorageInformation(id, ImageCategory.SCHOLARSHIP.name(), null, "en");
		List<MediaDto> mediaDtos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(storageDTOList)) {
			log.info("Storage data fetched from storage service, start iterating data");
			for (StorageDto storageDto : storageDTOList) {
				MediaDto mediaDto = new MediaDto();
				mediaDto.setId(storageDto.getEntityId());
				mediaDto.setName(storageDto.getImageName());
				mediaDto.setUrl(storageDto.getImageURL());
				mediaDtos.add(mediaDto);
			}
		}
		scholarshipResponseDTO.setFiles(mediaDtos);
		return scholarshipResponseDTO;
	}

	
	public void updateScholarship(final ScholarshipDto scholarshipDto, final String scholarshipId) throws ValidationException {
		log.debug("Inside updateScholarship() method");
		
		log.info("Extratcting scholarship data from DB having scholarshipId = "+scholarshipId);
		Scholarship existingScholarship = iScholarshipDAO.getScholarshipById(scholarshipId);
		if (existingScholarship == null) {
			log.error("Scholarship not found for id" + scholarshipId);
			throw new ValidationException("Scholarship not found for id" + scholarshipId);
		}
		String createdBy = existingScholarship.getCreatedBy();
		Date createdOn = existingScholarship.getCreatedOn();
		log.info("Copying data from DTO class to Bean class");
		BeanUtils.copyProperties(scholarshipDto, existingScholarship);
		existingScholarship.setId(scholarshipId);
		existingScholarship.setCreatedBy(createdBy);
		existingScholarship.setCreatedOn(createdOn);
		existingScholarship.setUpdatedBy("API");
		existingScholarship.setUpdatedOn(new Date());
		log.info("Calling DAO layer to delete existing scholatrship intakes fro scholarshipId = "+scholarshipId);
		iScholarshipDAO.deleteScholarshipIntakes(scholarshipId);
		log.info("Calling DAO layer to delete existing scholarship languages for scholarshipId = "+scholarshipId);
		iScholarshipDAO.deleteScholarshipLanguage(scholarshipId);
		if (scholarshipDto.getLevelId() != null) {
			log.info("LevelId is not null, fetching level data from DB fro levelId = "+scholarshipDto.getLevelId());
			Level level = levelDAO.getLevel(scholarshipDto.getLevelId());
			if (level == null) {
				log.error("Level not found for id" + scholarshipDto.getLevelId());
				throw new ValidationException("Level not found for id" + scholarshipDto.getLevelId());
			}
			existingScholarship.setLevel(level);
		}
		if (scholarshipDto.getCountryName() != null) {
			existingScholarship.setCountryName(scholarshipDto.getCountryName());
		}
		if (scholarshipDto.getInstituteName() != null) {
			existingScholarship.setInstituteName(scholarshipDto.getInstituteName());
		}
		if ((scholarshipDto.getIntakes() != null) && !scholarshipDto.getIntakes().isEmpty()) {
			log.info("Scholarship Intakes is coming in request, start iterating data to save in DB");
			for (String intake : scholarshipDto.getIntakes()) {
				ScholarshipIntakes scholarshipIntakes = new ScholarshipIntakes();
				scholarshipIntakes.setScholarship(existingScholarship);
				scholarshipIntakes.setName(intake);
				log.info("Calling DAO layer to save scholarship intakes in DB");
				iScholarshipDAO.saveScholarshipIntake(scholarshipIntakes);
			}
		}
		if ((scholarshipDto.getLanguages() != null) && !scholarshipDto.getLanguages().isEmpty()) {
			log.info("Scholarship languages is coming in request, start iterating data to save in DB");
			for (String language : scholarshipDto.getLanguages()) {
				ScholarshipLanguage scholarshipLanguage = new ScholarshipLanguage();
				scholarshipLanguage.setScholarship(existingScholarship);
				scholarshipLanguage.setName(language);
				log.info("Calling DAO layer to save schloarship language in DB");
				iScholarshipDAO.saveScholarshipLanguage(scholarshipLanguage);
			}
		}
		log.info("Calling DAO layer to update existing scholarship in DB");
		iScholarshipDAO.updateScholarship(existingScholarship);
		ScholarshipElasticDTO scholarshipElasticDto = new ScholarshipElasticDTO();
		log.info("Copying data from DTO class to elastic search DTO class");
		BeanUtils.copyProperties(existingScholarship, scholarshipElasticDto);
		scholarshipElasticDto.setCountryName(existingScholarship.getCountryName() != null ? existingScholarship.getCountryName() : null);
		scholarshipElasticDto.setCountryName(existingScholarship.getCourseName());
		scholarshipElasticDto.setInstituteName(existingScholarship.getInstituteName() != null ? existingScholarship.getInstituteName() : null);
		scholarshipElasticDto.setLevelName(existingScholarship.getLevel() != null ? existingScholarship.getLevel().getName() : null);
		scholarshipElasticDto.setLevelCode(existingScholarship.getLevel() != null ? existingScholarship.getLevel().getCode() : null);
		scholarshipElasticDto.setAmount(existingScholarship.getScholarshipAmount());
		scholarshipElasticDto.setLanguages(scholarshipDto.getLanguages());
		scholarshipElasticDto.setIntake(scholarshipDto.getIntakes());
		
		log.info("Calling elastic search service to update existing scholarship data in DB");
		elasticHandler.updateScholarshipOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_SCHOLARSHIP,
				EntityType.SCHOLARSHIP.name().toLowerCase(), scholarshipElasticDto, IConstant.ELASTIC_SEARCH);
	}

	
	public List<ScholarshipResponseDTO> getScholarshipList(final Integer startIndex, final Integer pageSize, final String countryId,
			final String instituteId, final String validity, final Boolean isActive, final Date filterDate, final String searchKeyword,
			final String sortByField, final String sortByType) {
		log.debug("Inside getScholarshipList() method");
		
		log.info("Fetching scholarship data from DB based on passed filters in request");
		List<ScholarshipResponseDTO> scholarships = iScholarshipDAO.getScholarshipList(startIndex, pageSize, countryId, instituteId, validity, isActive,
				filterDate, searchKeyword, sortByField, sortByType);
		for (ScholarshipResponseDTO scholarship : scholarships) {
			log.info("Fetching scholarship intakes data from DB for scholarshipId = "+ scholarship.getId());
			List<ScholarshipIntakes> scholarshipIntakes = iScholarshipDAO.getIntakeByScholarship(scholarship.getId());
			if ((scholarshipIntakes != null) && !scholarshipIntakes.isEmpty()) {
				List<String> intakes = new ArrayList<>();
				for (ScholarshipIntakes schIntakes : scholarshipIntakes) {
					intakes.add(schIntakes.getName());
				}
				scholarship.setIntakes(intakes);
			}
			log.info("Fetching scholarship language data from DB for scholarshipId = "+ scholarship.getId());
			List<ScholarshipLanguage> scholarshipLanguages = iScholarshipDAO.getLanguageByScholarship(scholarship.getId());
			if ((scholarshipLanguages != null) && !scholarshipLanguages.isEmpty()) {
				List<String> languages = new ArrayList<>();
				for (ScholarshipLanguage scholarshipLanguage : scholarshipLanguages) {
					languages.add(scholarshipLanguage.getName());
				}
				scholarship.setLanguages(languages);
			}
		}
		return scholarships;
	}

	
	public int countScholarshipList(final String countryId, final String instituteId, final String validity, final Boolean isActive,
			final Date filterDate, final String searchKeyword) {
		return iScholarshipDAO.countScholarshipList(countryId, instituteId, validity, isActive, filterDate, searchKeyword);
	}

	
	public void deleteScholarship(final String scholarshipId) throws ValidationException {
		log.debug("Inside deleteScholarship() method");
		log.info("Fetching scholarship data from DB for shcolarshipId = "+scholarshipId);
		Scholarship existingScholarship = iScholarshipDAO.getScholarshipById(scholarshipId);
		if (existingScholarship == null) {
			log.error("Scholarship not found for id" + scholarshipId);
			throw new ValidationException("Scholarship not found for id" + scholarshipId);
		}
		existingScholarship.setIsActive(false);
		existingScholarship.setUpdatedOn(new Date());
		existingScholarship.setUpdatedBy("API");
		existingScholarship.setDeletedOn(new Date());
		log.info("Calling DAO layer to update existing scholarship data in DB");
		iScholarshipDAO.updateScholarship(existingScholarship);
	}

	
	public List<String> getScholarshipIdsByCountryId(final List<String> countryIds, final Integer limit) {
		List<String> scholarshipIds = new ArrayList<>();
		if ((limit != null) && !limit.equals(0)) {
			scholarshipIds = iScholarshipDAO.getRandomScholarShipsForCountry(countryIds, limit);
		}
		return scholarshipIds;
	}

	
	public List<ScholarshipDto> getAllScholarshipDetailsFromId(final List<String> recommendedScholarships) {
		List<Scholarship> scholarshipList = iScholarshipDAO.getAllScholarshipDetailsFromId(recommendedScholarships);
		List<ScholarshipDto> scholarshipDtoList = new ArrayList<>();
		for (Scholarship scholarship : scholarshipList) {
			ScholarshipDto scholarshipDto = new ScholarshipDto();
			BeanUtils.copyProperties(scholarship, scholarshipDto);
			scholarshipDto.setCountryName(scholarship.getCountryName() != null ? scholarship.getCountryName() : null);
			scholarshipDto.setLevelId(scholarship.getLevel() != null ? scholarship.getLevel().getId() : null);
			scholarshipDto.setLevelName(scholarship.getLevel() != null ? scholarship.getLevel().getName() : null);
			scholarshipDto.setLevelCode(scholarship.getLevel() != null ? scholarship.getLevel().getCode() : null);
			scholarshipDto.setInstituteName(scholarship.getInstituteName() != null ? scholarship.getInstituteName() : null);
			scholarshipDtoList.add(scholarshipDto);
		}
		return scholarshipDtoList;
	}

	
	public List<String> getRandomScholarShipIds(final int i) {
		return iScholarshipDAO.getRandomScholarships(i);
	}
	
	public List<ScholarshipCountDto> getScholarshipCountByLevelId(List<LevelDto> levelList) {
		log.debug("Inside getScholarshipCountByLevelId() method");
		List<ScholarshipCountDto> scholarshipCountDtos = new ArrayList<>();
		levelList.stream().forEach(level -> {
			if (!ObjectUtils.isEmpty(level) && !ObjectUtils.isEmpty(level.getId())) {
				ScholarshipCountDto scholarshipCountDto = new ScholarshipCountDto();
				log.info("Fetching scholarship count having levelId = "+level.getId());
				Long count = iScholarshipDAO.getScholarshipCountByLevelId(level.getId());
				scholarshipCountDto.setLevelName(level.getName());
				scholarshipCountDto.setCount(count);
				scholarshipCountDtos.add(scholarshipCountDto);
			}
		});
		return scholarshipCountDtos;
	}
}
