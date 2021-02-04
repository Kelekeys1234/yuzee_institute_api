package com.yuzee.app.processor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Faculty;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.Level;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipCountry;
import com.yuzee.app.bean.ScholarshipEligibleNationality;
import com.yuzee.app.bean.ScholarshipIntake;
import com.yuzee.app.bean.ScholarshipLanguage;
import com.yuzee.app.dao.FacultyDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.LevelDao;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.ScholarshipElasticDto;
import com.yuzee.app.dto.ScholarshipIntakeDto;
import com.yuzee.app.dto.ScholarshipLevelCountDto;
import com.yuzee.app.dto.ScholarshipRequestDto;
import com.yuzee.app.dto.ScholarshipResponseDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.StudentCategory;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.ElasticHandler;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.util.IConstant;
import com.yuzee.app.util.PaginationUtil;
import com.yuzee.app.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScholarshipProcessor {

	@Autowired
	private ScholarshipDao scholarshipDAO;

	@Autowired
	private LevelDao levelDao;

	@Autowired
	private FacultyDao facultyDAO;

	@Autowired
	private ElasticHandler elasticHandler;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private InstituteDao instituteDao;

	@Autowired
	private ModelMapper modelMapper;

	private Scholarship prepareModel(final String userId, final ScholarshipRequestDto scholarshipDto,
			final String existingScholarshipId) throws ValidationException {
		Scholarship scholarship = new Scholarship();
		if (!StringUtils.isEmpty(existingScholarshipId)) {
			scholarship = getScholarshipFomDb(existingScholarshipId);
		}
		BeanUtils.copyProperties(scholarshipDto, scholarship);
		scholarship.setId(existingScholarshipId);
		scholarship.setAuditFields(userId);

		Set<Level> dbLevels = scholarship.getLevels();
		dbLevels.clear();
		if (!CollectionUtils.isEmpty(scholarshipDto.getLevelIds())) {
			log.info("Scholarship levels is not empty, start iterating data");
			List<Level> levels = levelDao.findByIdIn(scholarshipDto.getLevelIds());
			dbLevels.addAll(levels);
		}

		if (scholarshipDto.getFacultyId() != null) {
			log.info("faculty_id is not null, hence fetching faculty data from DB");
			Faculty faculty = facultyDAO.get(scholarshipDto.getFacultyId());
			if (faculty == null) {
				log.error("Faculty not found for id: {}", scholarshipDto.getFacultyId());
				throw new ValidationException("Faculty not found for id: " + scholarshipDto.getFacultyId());
			}
			scholarship.setFaculty(faculty);
		}

		if (!ObjectUtils.isEmpty(scholarshipDto.getInstituteId())) {
			log.info("CourseId is not null hence fetching course data from DB");
			Institute institute = instituteDao.get(scholarshipDto.getInstituteId());
			if (ObjectUtils.isEmpty(institute)) {
				log.error("Institute not found for id: {}", scholarshipDto.getInstituteId());
				throw new ValidationException("Institute not found for id :" + scholarshipDto.getInstituteId());
			}
			scholarship.setInstitute(institute);
		}

		final Scholarship finalScholarship = scholarship;
		List<ScholarshipIntake> dbIntakes = scholarship.getScholarshipIntakes();
		if (!CollectionUtils.isEmpty(scholarshipDto.getIntakes())) {
			log.info("Scholarship intakes is not null, start iterating data");

			scholarshipDto.getIntakes().stream().forEach(intake -> {

				// intakes to be updated
				Optional<ScholarshipIntake> existingIntakeOptional = dbIntakes.stream()
						.filter(e -> e.getStudentCategory().name().equals(intake.getStudentCategory())
								&& e.getIntakeDate().getTime() == intake.getIntakeDate().getTime())
						.findAny();

				String scholarshipIntakeId = null;
				ScholarshipIntake scholarshipIntake = new ScholarshipIntake();
				if (existingIntakeOptional.isPresent()) {
					scholarshipIntake = existingIntakeOptional.get();
					scholarshipIntakeId = scholarshipIntake.getId();
				}
				scholarshipIntake.setId(scholarshipIntakeId);
				scholarshipIntake.setIntakeDate(intake.getIntakeDate());
				scholarshipIntake.setDeadline(intake.getDeadline());
				scholarshipIntake.setStudentCategory(StudentCategory.valueOf(intake.getStudentCategory()));
				scholarshipIntake.setAuditFields(userId,
						StringUtils.isEmpty(scholarshipIntakeId) ? null : scholarshipIntake);
				scholarshipIntake.setScholarship(finalScholarship);
				if (StringUtils.isEmpty(scholarshipIntakeId)) {
					dbIntakes.add(scholarshipIntake);
				}
			});
		} else if (!CollectionUtils.isEmpty(dbIntakes)) {
			dbIntakes.clear();
		}
		// intakes to be removed
		dbIntakes.removeIf(e -> !ifIntakeExistsInDtoList(scholarshipDto.getIntakes(), e));

		List<ScholarshipLanguage> dbLanguages = scholarship.getScholarshipLanguages();

		if (!CollectionUtils.isEmpty(scholarshipDto.getLanguages())) {
			log.info("Scholarship languages is not null, start iterating data");

			scholarshipDto.getLanguages().stream().forEach(language -> {

				Optional<ScholarshipLanguage> existingLanguageOptional = dbLanguages.stream()
						.filter(e -> e.getName().equalsIgnoreCase(language)).findAny();

				String scholarshipLanguageId = null;
				ScholarshipLanguage scholarshipLanguage = new ScholarshipLanguage();
				if (existingLanguageOptional.isPresent()) {
					scholarshipLanguage = existingLanguageOptional.get();
					scholarshipLanguageId = scholarshipLanguage.getId();
				}
				scholarshipLanguage.setId(scholarshipLanguageId);
				scholarshipLanguage.setName(language);
				scholarshipLanguage.setAuditFields(userId,
						StringUtils.isEmpty(scholarshipLanguageId) ? null : scholarshipLanguage);
				scholarshipLanguage.setScholarship(finalScholarship);
				if (StringUtils.isEmpty(scholarshipLanguageId)) {
					dbLanguages.add(scholarshipLanguage);
				}

			});
			dbLanguages.removeIf(e -> !Util.containsIgnoreCase(scholarshipDto.getLanguages(), e.getName()));
		}

		List<ScholarshipCountry> dbCountries = scholarship.getScholarshipCountries();

		if (!CollectionUtils.isEmpty(scholarshipDto.getCountryNames())) {
			log.info("Scholarship country_names is not empty, start iterating data");

			scholarshipDto.getCountryNames().stream().forEach(countryName -> {

				Optional<ScholarshipCountry> existingCountryOptional = dbCountries.stream()
						.filter(e -> e.getCountryName().equalsIgnoreCase(countryName)).findAny();

				String scholarshipCountryId = null;
				ScholarshipCountry scholarshipCountry = new ScholarshipCountry();
				if (existingCountryOptional.isPresent()) {
					scholarshipCountry = existingCountryOptional.get();
					scholarshipCountryId = scholarshipCountry.getId();
				}
				scholarshipCountry.setId(scholarshipCountryId);
				scholarshipCountry.setCountryName(countryName);
				scholarshipCountry.setAuditFields(userId);
				scholarshipCountry.setScholarship(finalScholarship);
				if (StringUtils.isEmpty(scholarshipCountryId)) {
					dbCountries.add(scholarshipCountry);
				}

			});
			dbCountries.removeIf(e -> !Util.containsIgnoreCase(scholarshipDto.getCountryNames(), e.getCountryName()));
		}
		
		List<ScholarshipEligibleNationality> dbNationalitites = scholarship.getScholarshipEligibleNationalities();

		if (!CollectionUtils.isEmpty(scholarshipDto.getEligibleNationalities())) {
			log.info("Scholarship eligible nationalitites is not null, start iterating data");

			scholarshipDto.getEligibleNationalities().stream().forEach(countryName -> {

				Optional<ScholarshipEligibleNationality> existingNationalityOptional = dbNationalitites.stream()
						.filter(e -> e.getCountryName().equalsIgnoreCase(countryName)).findAny();

				String eligibleNationalityId = null;
				ScholarshipEligibleNationality eligibleNationality = new ScholarshipEligibleNationality();
				if (existingNationalityOptional.isPresent()) {
					eligibleNationality = existingNationalityOptional.get();
					eligibleNationalityId = eligibleNationality.getId();
				}
				eligibleNationality.setAuditFields(userId,
						StringUtils.isEmpty(eligibleNationalityId) ? null : eligibleNationality);
				eligibleNationality.setCountryName(countryName);
				eligibleNationality.setScholarship(finalScholarship);
				if (StringUtils.isEmpty(eligibleNationalityId)) {
					dbNationalitites.add(eligibleNationality);
				}
			});
			dbNationalitites.removeIf(
					e -> !Util.containsIgnoreCase(scholarshipDto.getEligibleNationalities(), e.getCountryName()));
		}
		return scholarship;
	}

	private ScholarshipElasticDto prepareElasticDtoFromModel(Scholarship scholarship) {
		ScholarshipElasticDto scholarshipElasticDto = new ScholarshipElasticDto();
		log.info("Copying data from DTO class to elasticSearch DTO class");
		BeanUtils.copyProperties(scholarship, scholarshipElasticDto);
		scholarshipElasticDto
				.setInstituteName(scholarship.getInstitute() != null ? scholarship.getInstitute().getName() : null);
		scholarshipElasticDto.setIntakes(scholarship.getScholarshipIntakes().stream()
				.map(e -> modelMapper.map(e, ScholarshipIntakeDto.class)).collect(Collectors.toList()));
		scholarshipElasticDto.setLanguages(scholarship.getScholarshipLanguages().stream()
				.map(ScholarshipLanguage::getName).collect(Collectors.toList()));
		return scholarshipElasticDto;
	}

	@Transactional(rollbackOn = Throwable.class)
	public String saveScholarship(final String userId, final ScholarshipRequestDto scholarshipDto)
			throws ValidationException {

		log.debug("Inside saveScholarship() method");

		log.info("Calling DAO layer to save scholarship in DB");
		Scholarship scholarship = scholarshipDAO.saveScholarship(prepareModel(userId, scholarshipDto, null));

		log.info("Calling elastic search service to save data on elastic index");
		elasticHandler.saveScholarshipOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_SCHOLARSHIP,
				EntityTypeEnum.SCHOLARSHIP.name().toLowerCase(), prepareElasticDtoFromModel(scholarship),
				IConstant.ELASTIC_SEARCH);

		return scholarship.getId();
	}

	@Transactional
	public ScholarshipResponseDto getScholarshipById(final String id) throws ValidationException {
		log.debug("Inside getScholarshipById() method");
		Scholarship scholarship = getScholarshipFomDb(id);
		ScholarshipResponseDto scholarshipResponseDTO = createScholarshipResponseDtoFromModel(scholarship);
		try {
			List<StorageDto> storageDTOList = storageHandler.getStorages(id, EntityTypeEnum.SCHOLARSHIP,
					EntitySubTypeEnum.MEDIA);
			scholarshipResponseDTO.setMedia(storageDTOList);
		} catch (NotFoundException | InvokeException e) {
			log.error(e.getMessage());
		}

		return scholarshipResponseDTO;
	}

	@Transactional(rollbackOn = Throwable.class)
	public void updateScholarship(final String userId, final ScholarshipRequestDto scholarshipDto,
			final String scholarshipId) throws ValidationException {
		log.debug("Inside updateScholarship() method");

		log.info("Calling DAO layer to save scholarship in DB");
		Scholarship scholarship = scholarshipDAO.saveScholarship(prepareModel(userId, scholarshipDto, scholarshipId));

		log.info("Calling elastic search service to update existing scholarship data in DB");
		elasticHandler.updateScholarshipOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_SCHOLARSHIP,
				EntityTypeEnum.SCHOLARSHIP.name().toLowerCase(), prepareElasticDtoFromModel(scholarship),
				IConstant.ELASTIC_SEARCH);
	}

	@Transactional
	public PaginationResponseDto getScholarshipList(final Integer pageNumber, final Integer pageSize,
			final String countryName, final String instituteId, final String searchKeyword) {
		log.debug("Inside getScholarshipList() method");

		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<Scholarship> scholarshipsPage = scholarshipDAO.getScholarshipList(countryName, instituteId, searchKeyword,
				pageable);

		List<ScholarshipResponseDto> scholarshipResponseDTOs = scholarshipsPage.getContent().stream()
				.map(this::createScholarshipResponseDtoFromModel).collect(Collectors.toList());

		try {
			List<StorageDto> storageDTOList = storageHandler.getStorages(
					scholarshipResponseDTOs.stream().map(ScholarshipResponseDto::getId).collect(Collectors.toList()),
					EntityTypeEnum.SCHOLARSHIP, EntitySubTypeEnum.MEDIA);
			scholarshipResponseDTOs.stream().forEach(e -> e.setMedia(storageDTOList.stream()
					.filter(f -> e.getId().equals(f.getEntityId())).collect(Collectors.toList())));
		} catch (NotFoundException | InvokeException e) {
			log.error(e.getMessage());
		}

		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) scholarshipsPage.getTotalElements()).intValue(), scholarshipResponseDTOs);
	}

	@Transactional(rollbackOn = Throwable.class)
	public void deleteScholarship(final String userId, final String scholarshipId)
			throws ValidationException, NotFoundException, InvokeException {
		log.debug("Inside deleteScholarship() method");
		Scholarship scholarship = getScholarshipFomDb(scholarshipId);
		if (!scholarship.getCreatedBy().equals(userId)) {
			log.error("User has no access to delete scholarship.");
			throw new ValidationException("User has no access to delete scholarship.");
		}
		scholarshipDAO.deleteScholarship(scholarshipId);
		storageHandler.deleteStorageBasedOnEntityId(scholarshipId);
	}

	public List<ScholarshipLevelCountDto> getScholarshipCountByLevel() {
		log.debug("Inside getScholarshipCountByLevelId() method");
		return scholarshipDAO.getScholarshipCountGroupByLevel();
	}

	@Transactional
	public List<ScholarshipResponseDto> getScholarshipByIds(List<String> scholarshipIds) {
		log.debug("Inside getScholarshipByIds() in ScholarshipProcessor method");
		List<Scholarship> scholarships = scholarshipDAO.getScholarshipByIds(scholarshipIds);
		return scholarships.stream().map(this::createScholarshipResponseDtoFromModel).collect(Collectors.toList());
	}

	private ScholarshipResponseDto createScholarshipResponseDtoFromModel(Scholarship scholarship) {
		ScholarshipResponseDto responseDto = modelMapper.map(scholarship, ScholarshipResponseDto.class);
		responseDto.setEligibleNationalities(scholarship.getScholarshipEligibleNationalities().stream()
				.map(ScholarshipEligibleNationality::getCountryName).collect(Collectors.toList()));

		responseDto.setCountryNames(scholarship.getScholarshipCountries().stream().map(ScholarshipCountry::getCountryName)
				.collect(Collectors.toList()));
		
		responseDto.setLanguages(scholarship.getScholarshipLanguages().stream().map(ScholarshipLanguage::getName)
				.collect(Collectors.toList()));
		return responseDto;
	}

	private boolean ifIntakeExistsInDtoList(List<ScholarshipIntakeDto> inakes, ScholarshipIntake intake) {
		return inakes.stream().anyMatch(e -> e.getIntakeDate().getTime() == intake.getIntakeDate().getTime()
				&& e.getStudentCategory().equalsIgnoreCase(intake.getStudentCategory().name()));
	}

	private Scholarship getScholarshipFomDb(String scholarshipId) throws ValidationException {
		Optional<Scholarship> scholarshipOptional = scholarshipDAO.getScholarshipById(scholarshipId);
		if (scholarshipOptional.isPresent()) {
			return scholarshipOptional.get();
		} else {
			log.error("Scholarship not found for id: {}", scholarshipId);
			throw new ValidationException("Scholarship not found for id: " + scholarshipId);
		}
	}
}
