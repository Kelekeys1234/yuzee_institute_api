package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.seeka.app.bean.Level;
import com.seeka.app.bean.Scholarship;
import com.seeka.app.bean.ScholarshipIntakes;
import com.seeka.app.bean.ScholarshipLanguage;
import com.seeka.app.dao.ILevelDAO;
import com.seeka.app.dao.IScholarshipDAO;
import com.seeka.app.dto.MediaDto;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.ScholarshipElasticDTO;
import com.seeka.app.dto.ScholarshipResponseDTO;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.enumeration.SeekaEntityType;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class ScholarshipService implements IScholarshipService {

	@Autowired
	private IScholarshipDAO iScholarshipDAO;

//	@Autowired
//	private ICountryDAO iCountryDAO;

	@Autowired
	private ILevelDAO iLevelDAO;

	@Autowired
	private ElasticSearchService elasticSearchService;
	
	@Autowired
	private IStorageService iStorageService;

	private static Logger LOGGER = LoggerFactory.getLogger(ScholarshipService.class);

	@Override
	public Scholarship saveScholarship(final ScholarshipDto scholarshipDto) throws ValidationException {
		Scholarship scholarship = new Scholarship();
		BeanUtils.copyProperties(scholarshipDto, scholarship);
		scholarship.setIsActive(true);
		scholarship.setCreatedOn(new Date());
		scholarship.setUpdatedOn(new Date());
		scholarship.setCreatedBy("API");
		scholarship.setUpdatedBy("API");
		if (scholarshipDto.getLevelId() != null) {
			Level level = iLevelDAO.get(scholarshipDto.getLevelId());
			if (level == null) {
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
		iScholarshipDAO.saveScholarship(scholarship);

		ScholarshipElasticDTO scholarshipElasticDto = new ScholarshipElasticDTO();
		BeanUtils.copyProperties(scholarship, scholarshipElasticDto);
		scholarshipElasticDto.setCountryName(scholarship.getCountryName() != null ? scholarship.getCountryName() : null);
		scholarshipElasticDto.setCountryName(scholarshipDto.getCourseName());
		scholarshipElasticDto.setInstituteName(scholarship.getInstituteName() != null ? scholarship.getInstituteName() : null);
		scholarshipElasticDto.setLevelName(scholarship.getLevel() != null ? scholarship.getLevel().getName() : null);
		scholarshipElasticDto.setLevelCode(scholarship.getLevel() != null ? scholarship.getLevel().getCode() : null);
		scholarshipElasticDto.setAmount(scholarship.getScholarshipAmount());

		if ((scholarshipDto.getIntakes() != null) && !scholarshipDto.getIntakes().isEmpty()) {
			for (String intake : scholarshipDto.getIntakes()) {
				ScholarshipIntakes scholarshipIntakes = new ScholarshipIntakes();
				scholarshipIntakes.setScholarship(scholarship);
				scholarshipIntakes.setName(intake);
				iScholarshipDAO.saveScholarshipIntake(scholarshipIntakes);
			}
		}
		if ((scholarshipDto.getLanguages() != null) && !scholarshipDto.getLanguages().isEmpty()) {
			for (String language : scholarshipDto.getLanguages()) {
				ScholarshipLanguage scholarshipLanguage = new ScholarshipLanguage();
				scholarshipLanguage.setScholarship(scholarship);
				scholarshipLanguage.setName(language);
				iScholarshipDAO.saveScholarshipLanguage(scholarshipLanguage);
			}
		}
		scholarshipElasticDto.setIntake(scholarshipDto.getIntakes());
		scholarshipElasticDto.setLanguages(scholarshipDto.getLanguages());
		
		elasticSearchService.saveScholarshipOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_SCHOLARSHIP,
				SeekaEntityType.SCHOLARSHIP.name().toLowerCase(), scholarshipElasticDto, IConstant.ELASTIC_SEARCH);
		return scholarship;
	}

	@Override
	public ScholarshipResponseDTO getScholarshipById(final String id) throws ValidationException {
		ScholarshipResponseDTO scholarshipResponseDTO = new ScholarshipResponseDTO();
		Scholarship scholarship = iScholarshipDAO.getScholarshipById(id);
		BeanUtils.copyProperties(scholarship, scholarshipResponseDTO);
		List<ScholarshipIntakes> scholarshipIntakes = iScholarshipDAO.getIntakeByScholarship(id);
		if ((scholarshipIntakes != null) && !scholarshipIntakes.isEmpty()) {
			List<String> intakes = new ArrayList<>();
			for (ScholarshipIntakes schIntakes : scholarshipIntakes) {
				intakes.add(schIntakes.getName());
			}
			scholarshipResponseDTO.setIntakes(intakes);
		}
		List<ScholarshipLanguage> scholarshipLanguages = iScholarshipDAO.getLanguageByScholarship(id);
		if ((scholarshipLanguages != null) && !scholarshipLanguages.isEmpty()) {
			List<String> languages = new ArrayList<>();
			for (ScholarshipLanguage scholarshipLanguage : scholarshipLanguages) {
				languages.add(scholarshipLanguage.getName());
			}
			scholarshipResponseDTO.setLanguages(languages);
		}
		scholarshipResponseDTO.setCountryId(scholarship.getCountryName());
		scholarshipResponseDTO.setLevelId(scholarship.getLevel().getId());
		scholarshipResponseDTO.setCountryName(scholarship.getCountryName());
		scholarshipResponseDTO.setLevelName(scholarship.getLevel().getName());
		scholarshipResponseDTO.setInstituteName(scholarship.getInstituteName());
		
		List<StorageDto> storageDTOList = iStorageService.getStorageInformation(id, ImageCategory.SCHOLARSHIP.name(), null, "en");
		List<MediaDto> mediaDtos = new ArrayList<>();
		for (StorageDto storageDto : storageDTOList) {
			MediaDto mediaDto = new MediaDto();
			mediaDto.setId(storageDto.getEntityId());
			mediaDto.setName(storageDto.getImageName());
			mediaDto.setUrl(storageDto.getImageURL());
			mediaDtos.add(mediaDto);
		}
		scholarshipResponseDTO.setFiles(mediaDtos);
		return scholarshipResponseDTO;
	}

	@Override
	public void updateScholarship(final ScholarshipDto scholarshipDto, final String scholarshipId) throws ValidationException {
		Scholarship existingScholarship = iScholarshipDAO.getScholarshipById(scholarshipId);
		if (existingScholarship == null) {
			throw new ValidationException("Scholarship not found for id" + scholarshipId);
		}
		String createdBy = existingScholarship.getCreatedBy();
		Date createdOn = existingScholarship.getCreatedOn();
		BeanUtils.copyProperties(scholarshipDto, existingScholarship);
		existingScholarship.setId(scholarshipId);
		existingScholarship.setCreatedBy(createdBy);
		existingScholarship.setCreatedOn(createdOn);
		existingScholarship.setUpdatedBy("API");
		existingScholarship.setUpdatedOn(new Date());
		iScholarshipDAO.deleteScholarshipIntakes(scholarshipId);
		iScholarshipDAO.deleteScholarshipLanguage(scholarshipId);
		if (scholarshipDto.getLevelId() != null) {
			Level level = iLevelDAO.get(scholarshipDto.getLevelId());
			if (level == null) {
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
			for (String intake : scholarshipDto.getIntakes()) {
				ScholarshipIntakes scholarshipIntakes = new ScholarshipIntakes();
				scholarshipIntakes.setScholarship(existingScholarship);
				scholarshipIntakes.setName(intake);
				iScholarshipDAO.saveScholarshipIntake(scholarshipIntakes);
			}
		}
		if ((scholarshipDto.getLanguages() != null) && !scholarshipDto.getLanguages().isEmpty()) {
			for (String language : scholarshipDto.getLanguages()) {
				ScholarshipLanguage scholarshipLanguage = new ScholarshipLanguage();
				scholarshipLanguage.setScholarship(existingScholarship);
				scholarshipLanguage.setName(language);
				iScholarshipDAO.saveScholarshipLanguage(scholarshipLanguage);
			}
		}
		iScholarshipDAO.updateScholarship(existingScholarship);
		ScholarshipElasticDTO scholarshipElasticDto = new ScholarshipElasticDTO();
		BeanUtils.copyProperties(existingScholarship, scholarshipElasticDto);
		scholarshipElasticDto.setCountryName(existingScholarship.getCountryName() != null ? existingScholarship.getCountryName() : null);
		scholarshipElasticDto.setCountryName(existingScholarship.getCourseName());
		scholarshipElasticDto.setInstituteName(existingScholarship.getInstituteName() != null ? existingScholarship.getInstituteName() : null);
		scholarshipElasticDto.setLevelName(existingScholarship.getLevel() != null ? existingScholarship.getLevel().getName() : null);
		scholarshipElasticDto.setLevelCode(existingScholarship.getLevel() != null ? existingScholarship.getLevel().getCode() : null);
		scholarshipElasticDto.setAmount(existingScholarship.getScholarshipAmount());
		scholarshipElasticDto.setLanguages(scholarshipDto.getLanguages());
		scholarshipElasticDto.setIntake(scholarshipDto.getIntakes());
		
		elasticSearchService.updateScholarshipOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_SCHOLARSHIP,
				SeekaEntityType.SCHOLARSHIP.name().toLowerCase(), scholarshipElasticDto, IConstant.ELASTIC_SEARCH);
	}

	@Override
	public List<ScholarshipResponseDTO> getScholarshipList(final Integer startIndex, final Integer pageSize, final String countryId,
			final String instituteId, final String validity, final Boolean isActive, final Date filterDate, final String searchKeyword,
			final String sortByField, final String sortByType) {
		List<ScholarshipResponseDTO> scholarships = iScholarshipDAO.getScholarshipList(startIndex, pageSize, countryId, instituteId, validity, isActive,
				filterDate, searchKeyword, sortByField, sortByType);
		for (ScholarshipResponseDTO scholarship : scholarships) {
			List<ScholarshipIntakes> scholarshipIntakes = iScholarshipDAO.getIntakeByScholarship(scholarship.getId());
			if ((scholarshipIntakes != null) && !scholarshipIntakes.isEmpty()) {
				List<String> intakes = new ArrayList<>();
				for (ScholarshipIntakes schIntakes : scholarshipIntakes) {
					intakes.add(schIntakes.getName());
				}
				scholarship.setIntakes(intakes);
			}
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

	@Override
	public int countScholarshipList(final String countryId, final String instituteId, final String validity, final Boolean isActive,
			final Date filterDate, final String searchKeyword) {
		return iScholarshipDAO.countScholarshipList(countryId, instituteId, validity, isActive, filterDate, searchKeyword);
	}

	@Override
	public void deleteScholarship(final String scholarshipId) throws ValidationException {
		Scholarship existingScholarship = iScholarshipDAO.getScholarshipById(scholarshipId);
		if (existingScholarship == null) {
			throw new ValidationException("Scholarship not found for id" + scholarshipId);
		}
		existingScholarship.setIsActive(false);
		existingScholarship.setUpdatedOn(new Date());
		existingScholarship.setUpdatedBy("API");
		existingScholarship.setDeletedOn(new Date());
		iScholarshipDAO.updateScholarship(existingScholarship);
	}

	@Override
	public List<String> getScholarshipIdsByCountryId(final List<String> countryIds, final Integer limit) {
		List<String> scholarshipIds = new ArrayList<>();
		if ((limit != null) && !limit.equals(0)) {
			scholarshipIds = iScholarshipDAO.getRandomScholarShipsForCountry(countryIds, limit);
		}
		return scholarshipIds;
	}

	@Override
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

	@Override
	public List<String> getRandomScholarShipIds(final int i) {
		return iScholarshipDAO.getRandomScholarships(i);
	}
	
	public Map<String, Object> getScholarshipCountByLevelId(List<Level> levelList) {
		Map<String, Object> map = new HashMap<>();
		levelList.stream().forEach(level -> {
			BigInteger count = null;
			if (!ObjectUtils.isEmpty(level) && !ObjectUtils.isEmpty(level.getId())) {
				count = iScholarshipDAO.getScholarshipCountByLevelId(level.getId());
				map.put(level.getName(), count);
			}
		});
		return map;
	}
}
