package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.dto.FundingResponseDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.InternalServerException;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.handler.EligibilityHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommonProcessor {
	@Autowired
	private InstituteProcessor instituteProcessor;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private EligibilityHandler eligibilityHandler;

	public List<StorageDto> getEntityGallery(String entityType, String entityId)
			throws NotFoundException, InternalServerException {
		if (EntityTypeEnum.COURSE.name().equals(entityType)) {
			return courseProcessor.getCourseGallery(entityId);
		} else if (EntityTypeEnum.INSTITUTE.name().equals(entityType)) {
			return instituteProcessor.getInstituteGallery(entityId);
		}
		return new ArrayList<>();
	}

	public Map<String, FundingResponseDto> validateFundingNameIds(List<String> fundingNameIds)
			throws NotFoundException {
		log.info("inside getFundingMapByFundingNameIds");
		Map<String, FundingResponseDto> map = new HashMap<>();
		if (!CollectionUtils.isEmpty(fundingNameIds)) {
			try {
				List<FundingResponseDto> dtos = eligibilityHandler.getFundingByFundingNameId(fundingNameIds);
				if (!CollectionUtils.isEmpty(dtos)) {
					map = dtos.stream().collect(Collectors.toMap(FundingResponseDto::getFundingNameId, e -> e));
				}
				if (map.size() != fundingNameIds.size()) {
					log.error("funding_name_id not found");
					throw new NotFoundException("funding_name_id not found");
				}
			} catch (InvokeException e1) {
				log.error("error invoking eligibility service so could'nt check if it funding_name_ids really exists");
			}
		}
		return map;
	}
}
