package com.seeka.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteGoogleReview;
import com.seeka.app.dao.InstituteGoogleReviewDao;
import com.seeka.app.dto.InstituteGoogleReviewDto;

@Transactional(rollbackFor = Throwable.class)
@Service(value = "instituteGoogleReviewProcessor")
public class InstituteGoogleReviewProcessor {

	@Autowired
	private InstituteGoogleReviewDao instituteGoogleReviewDao;

	public List<InstituteGoogleReviewDto> getInstituteGoogleReview(final String instituteId, final Integer startIndex, final Integer pageSize) {
		List<InstituteGoogleReview> instituteGoogleReviews = instituteGoogleReviewDao.getInstituteGoogleReview(instituteId, startIndex, pageSize);
		List<InstituteGoogleReviewDto> instituteGoogleReviewDtos = new ArrayList<>();
		for (InstituteGoogleReview instituteGoogleReview : instituteGoogleReviews) {
			InstituteGoogleReviewDto instituteGoogleReviewDto = new InstituteGoogleReviewDto();
			BeanUtils.copyProperties(instituteGoogleReview, instituteGoogleReviewDto);
			if (instituteGoogleReview.getInstitute() != null) {
				instituteGoogleReviewDto.setInstituteId(instituteGoogleReview.getInstitute().getId());
				instituteGoogleReviewDto.setInstituteName(instituteGoogleReview.getInstitute().getName());
			}
			if (instituteGoogleReview.getCountryName() != null) {
				instituteGoogleReviewDto.setCountryName(instituteGoogleReview.getCountryName());
			}
			instituteGoogleReviewDtos.add(instituteGoogleReviewDto);
		}
		return instituteGoogleReviewDtos;

	}

	public int getCountInstituteGoogleReview(final String instituteId) {
		return instituteGoogleReviewDao.getCountOfGooglereview(instituteId);
	}

	public Double getInstituteAvgGoogleReview(final String instituteId) {
		return instituteGoogleReviewDao.getInstituteAvgGoogleReview(instituteId);
	}

	public Map<String, Double> getInstituteAvgGoogleReviewForList(final List<String> instituteIdList) {
		return instituteGoogleReviewDao.getInstituteAvgGoogleReviewForList(instituteIdList);
	}


}
