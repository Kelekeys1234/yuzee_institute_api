package com.yuzee.app.processor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
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

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.Level;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipEligibleNationality;
import com.yuzee.app.bean.ScholarshipIntake;
import com.yuzee.app.bean.ScholarshipLanguage;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.LevelDao;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.dto.ScholarshipElasticDto;
import com.yuzee.app.dto.ScholarshipIntakeDto;
import com.yuzee.app.dto.ScholarshipLevelCountDto;
import com.yuzee.app.dto.ScholarshipRequestDto;
import com.yuzee.app.dto.ScholarshipResponseDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.StudentCategory;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.ElasticHandler;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.repository.ScholarshipRepository;
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
	private LevelDao levelDAO;

	@Autowired
	private ElasticHandler elasticHandler;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private InstituteDao instituteDao;

	@Autowired
	private ModelMapper modelMapper;

	private Scholarship dtoToModel(final String userId, final ScholarshipRequestDto scholarshipDto,
			final String existingScholarshipId) throws ValidationException {
		final AtomicReference<Scholarship> atomicSchoarship = new AtomicReference<>(new Scholarship());
		if (!StringUtils.isEmpty(existingScholarshipId)) {
			atomicSchoarship.set(getScholarshipFomDb(existingScholarshipId));
		}
		BeanUtils.copyProperties(scholarshipDto, atomicSchoarship.get());
		atomicSchoarship.get().setId(existingScholarshipId);
		atomicSchoarship.get().setAuditFields(userId,
				StringUtils.isEmpty(existingScholarshipId) ? null : atomicSchoarship.get());
		if (scholarshipDto.getLevelId() != null) {
			log.info("LevelId is not nll, hence fetching level data from DB");
			Level level = levelDAO.getLevel(scholarshipDto.getLevelId());
			if (level == null) {
				log.error("Level not found for id: {}", scholarshipDto.getLevelId());
				throw new ValidationException("Level not found for id: " + scholarshipDto.getLevelId());
			}
			atomicSchoarship.get().setLevel(level);
		}

		if (!ObjectUtils.isEmpty(scholarshipDto.getInstituteId())) {
			log.info("CourseId is not null hence fetching course data from DB");
			Institute institute = instituteDao.get(scholarshipDto.getInstituteId());
			if (ObjectUtils.isEmpty(institute)) {
				log.error("Institute not found for id: {}", scholarshipDto.getInstituteId());
				throw new ValidationException("Institute not found for id :" + scholarshipDto.getInstituteId());
			}
			atomicSchoarship.get().setInstitute(institute);
		}

		List<ScholarshipIntake> dbIntakes = atomicSchoarship.get().getScholarshipIntakes();
		if (!CollectionUtils.isEmpty(scholarshipDto.getIntakes())) {
			log.info("Scholarship intakes is not null, start iterating data");

			scholarshipDto.getIntakes().stream().forEach(intake -> {

				// intakes to be updated
				Optional<ScholarshipIntake> existingIntakeOptional = dbIntakes.stream()
						.filter(e -> e.getStudentCategory().name().equals(intake.getStudentCategory())
								&& e.getIntakeDate().getTime() == intake.getIntakeDate().getTime())
						.findAny();

				final AtomicReference<String> scholarshipIntakeId = new AtomicReference<>(null);
				ScholarshipIntake scholarshipIntake = new ScholarshipIntake();
				if (existingIntakeOptional.isPresent()) {
					scholarshipIntake = existingIntakeOptional.get();
					scholarshipIntakeId.set(scholarshipIntake.getId());
				}
				scholarshipIntake.setId(scholarshipIntakeId.get());
				scholarshipIntake.setIntakeDate(intake.getIntakeDate());
				scholarshipIntake.setDeadline(intake.getDeadline());
				scholarshipIntake.setStudentCategory(StudentCategory.valueOf(intake.getStudentCategory()));
				scholarshipIntake.setAuditFields(userId,
						StringUtils.isEmpty(scholarshipIntakeId.get()) ? null : scholarshipIntake);
				scholarshipIntake.setScholarship(atomicSchoarship.get());
				if (StringUtils.isEmpty(scholarshipIntakeId.get())) {
					dbIntakes.add(scholarshipIntake);
				}
			});
		} else if (!CollectionUtils.isEmpty(dbIntakes)) {
			dbIntakes.clear();
		}
		// intakes to be removed
		dbIntakes.removeIf(e -> !ifIntakeExistsInDtoList(scholarshipDto.getIntakes(), e));

		List<ScholarshipLanguage> dbLanguages = atomicSchoarship.get().getScholarshipLanguages();

		if (!CollectionUtils.isEmpty(scholarshipDto.getLanguages())) {
			log.info("Scholarship languages is not null, start iterating data");

			scholarshipDto.getLanguages().stream().forEach(language -> {

				Optional<ScholarshipLanguage> existingLanguageOptional = dbLanguages.stream()
						.filter(e -> e.getName().equalsIgnoreCase(language)).findAny();

				final AtomicReference<String> scholarshipLanguageId = new AtomicReference<>(null);
				ScholarshipLanguage scholarshipLanguage = new ScholarshipLanguage();
				if (existingLanguageOptional.isPresent()) {
					scholarshipLanguage = existingLanguageOptional.get();
					scholarshipLanguageId.set(scholarshipLanguage.getId());
				}
				scholarshipLanguage.setId(scholarshipLanguageId.get());
				scholarshipLanguage.setName(language);
				scholarshipLanguage.setAuditFields(userId,
						StringUtils.isEmpty(scholarshipLanguageId.get()) ? null : scholarshipLanguage);
				scholarshipLanguage.setScholarship(atomicSchoarship.get());
				if (StringUtils.isEmpty(scholarshipLanguageId.get())) {
					dbLanguages.add(scholarshipLanguage);
				}

			});
			dbLanguages.removeIf(e -> !Util.containsIgnoreCase(scholarshipDto.getLanguages(), e.getName()));
		}

		List<ScholarshipEligibleNationality> dbNationalitites = atomicSchoarship.get()
				.getScholarshipEligibleNationalities();

		if (!CollectionUtils.isEmpty(scholarshipDto.getEligibleNationalities())) {
			log.info("Scholarship eligible nationalitites is not null, start iterating data");

			scholarshipDto.getEligibleNationalities().stream().forEach(countryName -> {

				Optional<ScholarshipEligibleNationality> existingNationalityOptional = dbNationalitites.stream()
						.filter(e -> e.getCountryName().equalsIgnoreCase(countryName)).findAny();

				final AtomicReference<String> eligibleNationalityId = new AtomicReference<>(null);
				ScholarshipEligibleNationality eligibleNationality = new ScholarshipEligibleNationality();
				if (existingNationalityOptional.isPresent()) {
					eligibleNationality = existingNationalityOptional.get();
					eligibleNationalityId.set(eligibleNationality.getId());
				}
				eligibleNationality.setAuditFields(userId,
						StringUtils.isEmpty(eligibleNationalityId.get()) ? null : eligibleNationality);
				eligibleNationality.setCountryName(countryName);
				eligibleNationality.setScholarship(atomicSchoarship.get());
				if (StringUtils.isEmpty(eligibleNationalityId.get())) {
					dbNationalitites.add(eligibleNationality);
				}
			});
			dbNationalitites.removeIf(
					e -> !Util.containsIgnoreCase(scholarshipDto.getEligibleNationalities(), e.getCountryName()));
		}
		return atomicSchoarship.get();

	}

	private ScholarshipElasticDto prepareElasticDtoFromModel(Scholarship scholarship) {
		ScholarshipElasticDto scholarshipElasticDto = new ScholarshipElasticDto();
		log.info("Copying data from DTO class to elasticSearch DTO class");
		BeanUtils.copyProperties(scholarship, scholarshipElasticDto);
		scholarshipElasticDto
				.setCountryName(scholarship.getCountryName() != null ? scholarship.getCountryName() : null);
		scholarshipElasticDto
				.setInstituteName(scholarship.getInstitute() != null ? scholarship.getInstitute().getName() : null);
		scholarshipElasticDto.setLevelName(scholarship.getLevel() != null ? scholarship.getLevel().getName() : null);
		scholarshipElasticDto.setLevelCode(scholarship.getLevel() != null ? scholarship.getLevel().getCode() : null);
		scholarshipElasticDto.setAmount(scholarship.getScholarshipAmount());
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
		Scholarship scholarship = scholarshipDAO.saveScholarship(dtoToModel(userId, scholarshipDto, null));

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
		Scholarship scholarship = scholarshipDAO.saveScholarship(dtoToModel(userId, scholarshipDto, scholarshipId));

		log.info("Calling elastic search service to update existing scholarship data in DB");
		elasticHandler.updateScholarshipOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_SCHOLARSHIP,
				EntityTypeEnum.SCHOLARSHIP.name().toLowerCase(), prepareElasticDtoFromModel(scholarship),
				IConstant.ELASTIC_SEARCH);
	}

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
					scholarshipResponseDTOs.stream().map(e -> e.getId()).collect(Collectors.toList()),
					EntityTypeEnum.SCHOLARSHIP, EntitySubTypeEnum.MEDIA);
			scholarshipResponseDTOs.stream().forEach(e -> e.setMedia(storageDTOList.stream()
					.filter(f -> e.getId().equals(f.getEntityId())).collect(Collectors.toList())));
		} catch (NotFoundException | InvokeException e) {
			log.error(e.getMessage());
		}

		return PaginationUtil.calculatePaginationAndPrepareResponse(PaginationUtil.getStartIndex(pageNumber, pageSize),
				pageSize, ((Long) scholarshipsPage.getTotalElements()).intValue(), scholarshipResponseDTOs);
	}

	public void deleteScholarship(final String scholarshipId) throws ValidationException {
		log.debug("Inside deleteScholarship() method");
		scholarshipDAO.deleteScholarship(scholarshipId);
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

		responseDto.setLanguages(scholarship.getScholarshipLanguages().stream().map(ScholarshipLanguage::getName)
				.collect(Collectors.toList()));
		return responseDto;
	}

	private boolean ifIntakeExistsInDtoList(List<ScholarshipIntakeDto> inakes, ScholarshipIntake intake) {
		AtomicBoolean result = new AtomicBoolean();
		inakes.stream().forEach(e -> {
			if (e.getIntakeDate().getTime() == intake.getIntakeDate().getTime()
					&& e.getStudentCategory().equalsIgnoreCase(intake.getStudentCategory().name())) {
				result.set(true);
			}
		});
		return result.get();
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
