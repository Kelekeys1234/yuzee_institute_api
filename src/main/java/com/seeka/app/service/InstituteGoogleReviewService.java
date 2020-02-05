package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.InstituteGoogleReview;
import com.seeka.app.dao.IInstituteGoogleReviewDao;
import com.seeka.app.dto.InstituteGoogleReviewDto;

@Service(value = "iInstituteGoogleReviewService")
@Transactional(rollbackFor = Throwable.class)
public class InstituteGoogleReviewService implements IInstituteGoogleReviewService {

	@Autowired
	private IInstituteGoogleReviewDao iInstituteGoogleReviewDao;

	@Override
	public List<InstituteGoogleReviewDto> getInstituteGoogleReview(final BigInteger instituteId, final Integer startIndex, final Integer pageSize) {
		List<InstituteGoogleReview> instituteGoogleReviews = iInstituteGoogleReviewDao.getInstituteGoogleReview(instituteId, startIndex, pageSize);
		List<InstituteGoogleReviewDto> instituteGoogleReviewDtos = new ArrayList<>();
		for (InstituteGoogleReview instituteGoogleReview : instituteGoogleReviews) {
			InstituteGoogleReviewDto instituteGoogleReviewDto = new InstituteGoogleReviewDto();
			BeanUtils.copyProperties(instituteGoogleReview, instituteGoogleReviewDto);
			if (instituteGoogleReview.getInstitute() != null) {
				instituteGoogleReviewDto.setInstituteId(instituteGoogleReview.getInstitute().getId());
				instituteGoogleReviewDto.setInstituteName(instituteGoogleReview.getInstitute().getName());
			}
			if (instituteGoogleReview.getCountry() != null) {
				instituteGoogleReviewDto.setCountryId(instituteGoogleReview.getCountry().getId());
				instituteGoogleReviewDto.setCountryName(instituteGoogleReview.getCountry().getName());
			}
			instituteGoogleReviewDtos.add(instituteGoogleReviewDto);
		}
		return instituteGoogleReviewDtos;

	}

	@Override
	public int getCountInstituteGoogleReview(final BigInteger instituteId) {
		return iInstituteGoogleReviewDao.getCountOfGooglereview(instituteId);
	}

	@Override
	public Double getInstituteAvgGoogleReview(final String instituteId) {
		return iInstituteGoogleReviewDao.getInstituteAvgGoogleReview(instituteId);
	}

	@Override
	public Map<String, Double> getInstituteAvgGoogleReviewForList(final List<String> instituteIdList) {
		return iInstituteGoogleReviewDao.getInstituteAvgGoogleReviewForList(instituteIdList);
	}

}
