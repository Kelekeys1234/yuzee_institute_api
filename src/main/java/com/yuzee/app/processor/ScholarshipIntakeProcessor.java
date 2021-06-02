package com.yuzee.app.processor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipIntake;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.app.dao.ScholarshipIntakeDao;
import com.yuzee.common.lib.dto.institute.ScholarshipIntakeDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeNotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.ElasticHandler;
import com.yuzee.common.lib.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScholarshipIntakeProcessor {

	@Autowired
	ScholarshipDao scholarshipDao;

	@Autowired
	private ScholarshipIntakeDao scholarshipIntakeDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ElasticHandler elasticHandler;
	
	@Autowired
	private ConversionProcessor conversionProcessor;
	
	@Transactional
	public void saveUpdateScholarshipIntakes(String userId, String scholarshipId,
			@Valid List<ScholarshipIntakeDto> scholarshipIntakeDtos) throws NotFoundException, ValidationException {
		log.info("inside ScholarshipIntakeDao.saveUpdateScholarshipIntakes");
		Scholarship scholarship = validateAndGetScholarship(scholarshipId);
		log.info("getting the ids of entitities to be updated");
		Set<String> updateRequestIds = scholarshipIntakeDtos.stream().filter(e -> !StringUtils.isEmpty(e.getId()))
				.map(ScholarshipIntakeDto::getId).collect(Collectors.toSet());

		log.info("verfiy if ids exists against scholarship");
		Map<String, ScholarshipIntake> existingScholarshipIntakesMap = scholarshipIntakeDao
				.findByScholarshipIdAndIdIn(scholarshipId, updateRequestIds.stream().collect(Collectors.toList()))
				.stream().collect(Collectors.toMap(ScholarshipIntake::getId, e -> e));

		List<ScholarshipIntake> scholarshipIntakes = scholarship.getScholarshipIntakes();

		log.info("loop the requested list to collect the entitities to be saved/updated");
		scholarshipIntakeDtos.stream().forEach(e -> {
			ScholarshipIntake scholarshipIntake = new ScholarshipIntake();
			if (!StringUtils.isEmpty(e.getId())) {
				log.info("entityId is present so going to see if it is present in db if yes then we have to update it");
				scholarshipIntake = existingScholarshipIntakesMap.get(e.getId());
				if (scholarshipIntake == null) {
					log.error("invalid scholarship intake id : {}", e.getId());
					throw new RuntimeNotFoundException("invalid scholarship intake id : " + e.getId());
				}
			}
			scholarshipIntake = modelMapper.map(e, ScholarshipIntake.class);
			scholarshipIntake.setScholarship(scholarship);
			scholarshipIntake.setAuditFields(userId);
			scholarshipIntakes.add(scholarshipIntake);
		});
		scholarshipIntakeDao.saveAll(scholarshipIntakes);
		scholarship.setScholarshipIntakes(scholarshipIntakes);
		log.info("Calling elastic search service to save data on elastic index");
		elasticHandler
				.saveUpdateScholarship(conversionProcessor.convertScholarshipToScholarshipDTOElasticSearchEntity(scholarship));
	}

	@Transactional
	public void deleteByScholarshipIntakeIds(String userId, String courseId, List<String> scholarshipIntakeIds)
			throws NotFoundException, ForbiddenException {
		log.info("inside ScholarshipIntakeDao.deleteByScholarshipIntakeIds");
		Scholarship scholarship = validateAndGetScholarship(courseId);
		
		List<ScholarshipIntake> scholarshipIntakes = scholarshipIntakeDao.findByScholarshipIdAndIdIn(courseId,
				scholarshipIntakeIds);
		if (scholarshipIntakeIds.size() == scholarshipIntakes.size()) {
			if (scholarshipIntakes.stream().anyMatch(e -> !e.getCreatedBy().equals(userId))) {
				log.error("no access to delete one more scholarship intakes");
				throw new ForbiddenException("no access to delete one more scholarship intakes");
			}
			scholarship.getScholarshipIntakes().removeIf(e->Utils.contains(scholarshipIntakeIds, e.getId()));
			scholarshipIntakeDao.deleteByScholarshipIdAndIdIn(courseId, scholarshipIntakeIds);
			log.info("Calling elastic search service to save data on elastic index");
			elasticHandler
					.saveUpdateScholarship(conversionProcessor.convertScholarshipToScholarshipDTOElasticSearchEntity(scholarship));
		} else {
			log.error("one or more invalid scholarship_intakes_ids");
			throw new NotFoundException("one or more invalid scholarship_intakes_ids");
		}
	}

	private Scholarship validateAndGetScholarship(String scholarshipId) throws NotFoundException {
		Optional<Scholarship> scholarshipOp = scholarshipDao.getScholarshipById(scholarshipId);
		if (scholarshipOp.isPresent()) {
			return scholarshipOp.get();
		} else {
			log.error("invalid scholarship id: {}", scholarshipId);
			throw new NotFoundException("invalid scholarship id: " + scholarshipId);
		}
	}
}
