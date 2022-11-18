package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteCampus;
import com.yuzee.app.dao.InstituteCampusDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.InstituteCampusDto;
import com.yuzee.app.util.CommonUtil;
import com.yuzee.common.lib.dto.institute.TimingDto;
import com.yuzee.common.lib.exception.ConstraintVoilationException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstituteCampusProcessor {

	@Autowired
	TimingProcessor timingProcessor;

	@Autowired
	@Lazy
	InstituteProcessor instituteProcessor;

	@Autowired
	InstituteDao instituteDao;

	@Autowired
	InstituteCampusDao instituteCampusDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MessageTranslator messageTranslator;

	@Transactional
	public void addCampus(String userId, String instituteId, List<String> campusInstituteIds)
			throws NotFoundException, ValidationException {
		log.info("Inside InstituteCampusProcessor.createCampus");

		campusInstituteIds.add(instituteId);

		Institute institutes = instituteDao.get(instituteId);

		campusInstituteIds.removeIf(e -> e.equals(instituteId));
		Set<String> dbCampusInstituteIds = getInstituteCampuses(userId, instituteId).stream()
				.map(InstituteCampusDto::getId).collect(Collectors.toSet());
		// String dbCampusInstituteId= getInstituteCampuses(userId, instituteId);
		if (campusInstituteIds.stream().anyMatch(e -> e.equals(instituteId))) {
			log.error(messageTranslator.toLocale("institute.id.same", Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute.id.same"));
		}
		if (!Collections.disjoint(dbCampusInstituteIds, campusInstituteIds)) {
			log.error(messageTranslator.toLocale("institute.already_campus", Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute.already_campus"));
		}
		List<InstituteCampus> campuses = new ArrayList<>();
		List<Institute> campuse = new ArrayList<>();
		campuse.add(institutes);
		campuse.forEach(e -> {
			InstituteCampus instituteCampus = new InstituteCampus();
			instituteCampus.setId(UUID.randomUUID().toString());
			instituteCampus.setAuditFields(userId);
			instituteCampus.setSourceInstitute(institutes);
			instituteCampus.setDestinationInstitute(e);
			campuses.add(instituteCampus);
		});
		if (!ObjectUtils.isEmpty(institutes)) {
			institutes.setInstituteCampuses(campuses);
			instituteDao.addUpdateInstitute(institutes);
		}
		instituteCampusDao.saveAll(campuses);
	}

	@Transactional(rollbackFor = { ConstraintVoilationException.class, Exception.class })
	public List<InstituteCampusDto> getInstituteCampuses(String userId, List<Institute> institutes, String instituteId)
			throws NotFoundException {
		log.debug("inside InstituteCampusProcessor.getInstituteCampuses method.");
		List<InstituteCampus> instituteCampuses = null;
		List<InstituteCampusDto> instituteCampusDtos = null;
		Institute institute = instituteDao.get(instituteId);
		if (!ObjectUtils.isEmpty(institute)) {
			instituteCampuses = instituteCampusDao.findInstituteCampuses(UUID.fromString(instituteId));
			if (!CollectionUtils.isEmpty(instituteCampuses)) {
				instituteCampuses.forEach(e -> {
					if (!ObjectUtils.isEmpty(e.getDestinationInstitute())) {
						if (institutes.stream().noneMatch(in -> in.getId().equals(e.getSourceInstitute().getId()))) {
							institutes.add(e.getSourceInstitute());
						}
					}
					if (!ObjectUtils.isEmpty(e.getSourceInstitute())) {
						if (!ObjectUtils.isEmpty(e.getSourceInstitute())) {
							if (institutes.stream()
									.noneMatch(in -> in.getId().equals(e.getDestinationInstitute().getId()))) {
								institutes.add(e.getDestinationInstitute());
							}
						}
					}
				});
				instituteCampusDtos = institutes.stream().map(e -> {
					InstituteCampusDto campusDto = modelMapper.map(e, InstituteCampusDto.class);
					campusDto.setCampusName(e.getName());
					campusDto.setHasEditAccess(e.getCreatedBy().equals(userId));
					return campusDto;
				}).collect(Collectors.toList());
				instituteCampusDtos.forEach(e -> {
					TimingDto instituteTimingResponseDto = timingProcessor.getTimingResponseDtoByInstituteId(e.getId());
					e.setInstituteTimings(
							CommonUtil.convertTimingResponseDtoToDayTimingDto(instituteTimingResponseDto));
				});
			}
			return instituteCampusDtos;
		} else {
			log.error(messageTranslator.toLocale("institute.id.invalid", instituteId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute.id.invalid", instituteId));
		}
	}

	@Transactional(rollbackFor = { ConstraintVoilationException.class, Exception.class })
	public List<InstituteCampusDto> getInstituteCampuses(String userId, String instituteId) throws NotFoundException {
		log.debug("inside InstituteCampusProcessor.getInstituteCampuses method.");
		Institute institute = instituteDao.get(instituteId);
		if (!ObjectUtils.isEmpty(institute)) {
			List<InstituteCampus> instituteCampuses = instituteCampusDao
					.findInstituteCampuses(UUID.fromString(instituteId));

			List<Institute> institutes = new ArrayList<>();
			instituteCampuses.forEach(e -> {
				if (institutes.stream().noneMatch(in -> in.getId().equals(e.getSourceInstitute().getId()))) {
					institutes.add(e.getSourceInstitute());
				}
				if (institutes.stream().noneMatch(in -> in.getId().equals(e.getDestinationInstitute().getId()))) {
					institutes.add(e.getDestinationInstitute());
				}
			});

			List<InstituteCampusDto> instituteCampusDtos = institutes.stream().map(e -> {
				InstituteCampusDto campusDto = modelMapper.map(e, InstituteCampusDto.class);
				campusDto.setCampusName(e.getName());
				if (e.getCreatedBy().equals(userId)) {
					campusDto.setHasEditAccess(true);
				} else {
					campusDto.setHasEditAccess(false);
				}
				return campusDto;
			}).collect(Collectors.toList());

			instituteCampusDtos.forEach(e -> {
				TimingDto instituteTimingResponseDto = timingProcessor.getTimingResponseDtoByInstituteId(e.getId());
				e.setInstituteTimings(CommonUtil.convertTimingResponseDtoToDayTimingDto(instituteTimingResponseDto));

			});
			return instituteCampusDtos;
		} else {
			log.error(messageTranslator.toLocale("institute.id.invalid", instituteId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("institute.id.invalid", instituteId));
		}
	}

	@Transactional
	public void removeCampuses(String userId, String instituteId, @Valid List<String> instituteIds)
			throws NotFoundException {
		log.info("inside InstituteCampusProcessor.getLinkedInstituteIds");
		instituteIds.add(instituteId);
		List<Institute> institutes = instituteProcessor.validateAndGetInstituteByIds(instituteIds);
		institutes.removeIf(e -> e.getId().toString().equals(instituteId));
		instituteIds.removeIf(e -> e.equals(instituteId));
		if (instituteIds.stream().anyMatch(e -> e.equals(instituteId))) {
			log.error(messageTranslator.toLocale("institute.id.same", Locale.US));
			throw new ValidationException(messageTranslator.toLocale("institute.id.same"));
		}
		List<InstituteCampus> deleteList = new ArrayList<>();
		List<InstituteCampus> instituteCampuses = instituteCampusDao
				.findInstituteCampuses(UUID.fromString(instituteId));
		institutes.forEach(campus -> {
			log.debug("going to see if institute to be unLinked is linked as primary or secondary");
			Optional<InstituteCampus> secondaryLink = instituteCampuses.stream()
					.filter(e -> e.getDestinationInstitute().getId().equals(campus.getId())).findAny();
			InstituteCampus toBeDeleted = null;
			if (secondaryLink.isPresent()) {
				log.debug("institute is secondary institute");
				toBeDeleted = secondaryLink.get();
			} else {
				log.debug("institute is primary institute");
				Institute firstSecondaryInstitute = instituteCampuses.get(0).getDestinationInstitute();
				instituteCampuses.forEach(e -> {
					e.setSourceInstitute(firstSecondaryInstitute);
				});
				toBeDeleted = instituteCampuses.get(0);
			}
			deleteList.add(toBeDeleted);
		});

	}
}
