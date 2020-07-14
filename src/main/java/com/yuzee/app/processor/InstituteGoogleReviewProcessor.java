package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.InstituteGoogleReview;
import com.yuzee.app.dao.InstituteGoogleReviewDao;
import com.yuzee.app.dto.InstituteGoogleReviewDto;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Transactional(rollbackFor = Throwable.class)
@Service(value = "instituteGoogleReviewProcessor")
public class InstituteGoogleReviewProcessor {

	@Autowired
	private InstituteGoogleReviewDao instituteGoogleReviewDao;

	public List<InstituteGoogleReviewDto> getInstituteGoogleReview(final String instituteId, final Integer startIndex, final Integer pageSize) {
		log.debug("Inside getInstituteGoogleReview() method");
		
		log.info("Fetching institute google review from DB having instituteId = "+instituteId);
		List<InstituteGoogleReview> instituteGoogleReviews = instituteGoogleReviewDao.getInstituteGoogleReview(instituteId, startIndex, pageSize);
		List<InstituteGoogleReviewDto> instituteGoogleReviewDtos = new ArrayList<>();
		if(!CollectionUtils.isEmpty(instituteGoogleReviewDtos)) {
			log.info("Google reviews fetched from DB, start iterating data to make response");
			instituteGoogleReviews.stream().forEach(instituteGoogleReview -> {
				InstituteGoogleReviewDto instituteGoogleReviewDto = new InstituteGoogleReviewDto();
				log.info("Copying Bean class data to DTO class");
				BeanUtils.copyProperties(instituteGoogleReview, instituteGoogleReviewDto);
				if (instituteGoogleReview.getInstitute() != null) {
					log.info("Institute is not null, adding value of instituteId and institute name in response");
					instituteGoogleReviewDto.setInstituteId(instituteGoogleReview.getInstitute().getId());
					instituteGoogleReviewDto.setInstituteName(instituteGoogleReview.getInstitute().getName());
				}
				if (instituteGoogleReview.getCountryName() != null) {
					log.info("countryName is present, adding value of county name in response");
					instituteGoogleReviewDto.setCountryName(instituteGoogleReview.getCountryName());
				}
				instituteGoogleReviewDtos.add(instituteGoogleReviewDto);
			
			});
		}
		return instituteGoogleReviewDtos;

	}

	public int getCountInstituteGoogleReview(final String instituteId) {
		log.info("Fetching total count of google review from DB having instituteId = "+instituteId);
		return instituteGoogleReviewDao.getCountOfGooglereview(instituteId);
	}

	public Double getInstituteAvgGoogleReview(final String instituteId) {
		log.info("Fetching average google review from DB having instituteId = "+instituteId);
		return instituteGoogleReviewDao.getInstituteAvgGoogleReview(instituteId);
	}

	public Map<String, Double> getInstituteAvgGoogleReviewForList(final List<String> instituteIdList) {
		log.info("Fetching average google review from DB having for multiple instituteIds");
		return instituteGoogleReviewDao.getInstituteAvgGoogleReviewForList(instituteIdList);
	}
}
