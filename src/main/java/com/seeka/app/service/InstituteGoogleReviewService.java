package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteGoogleReview;
import com.seeka.app.dao.IInstituteGoogleReviewDao;
import com.seeka.app.dto.GoogleReviewDto;

@Service(value = "iInstituteGoogleReviewService")
@Transactional(rollbackFor = Throwable.class)
public class InstituteGoogleReviewService implements IInstituteGoogleReviewService {

	@Autowired
	private IInstituteGoogleReviewDao iInstituteGoogleReviewDao;

	@Autowired
	private IInstituteService iInstituteService;

	@Override
	public void addInstituteGoogleReview() {
		RestTemplate restTemplate = new RestTemplate();
		List<Institute> instituteList = iInstituteService.getAllInstitute();
		int count = 0;
		for (Institute institute : instituteList) {
			if (count > 100) {
				break;
			}
			InstituteGoogleReview instituteGoogleReview = fetchInformationFromGoogle(restTemplate, institute.getName());
			if (instituteGoogleReview != null) {
				InstituteGoogleReview existingInstituteGoogleReview = iInstituteGoogleReviewDao.getInstituteGoogleReviewDetail(institute.getId());
				if (existingInstituteGoogleReview != null) {
					instituteGoogleReview.setId(existingInstituteGoogleReview.getId());
					instituteGoogleReview.setInstituteId(institute.getId());
					instituteGoogleReview.setCreatedBy(existingInstituteGoogleReview.getCreatedBy());
					instituteGoogleReview.setCreatedOn(existingInstituteGoogleReview.getCreatedOn());
					instituteGoogleReview.setUpdatedBy("API");
					instituteGoogleReview.setUpdatedOn(new Date());
					iInstituteGoogleReviewDao.update(instituteGoogleReview);
				} else {
					instituteGoogleReview.setInstituteId(institute.getId());
					instituteGoogleReview.setCreatedBy("API");
					instituteGoogleReview.setCreatedOn(new Date());
					iInstituteGoogleReviewDao.save(instituteGoogleReview);
				}
			}
			count++;
		}

	}

	private InstituteGoogleReview fetchInformationFromGoogle(final RestTemplate restTemplate, final String instituteName) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/findplacefromtext/json")
				.queryParam("input", instituteName).queryParam("inputtype", "textquery")
				.queryParam("fields", "formatted_address,name,rating,geometry,user_ratings_total").queryParam("key", "AIzaSyBDbS0-PvZuLd6b7kDxebYGenlyLlBb6_Q");

		ResponseEntity<GoogleReviewDto> result = restTemplate.getForEntity(builder.build().toUri(), GoogleReviewDto.class);
		System.out.println(result.getBody());
		GoogleReviewDto googleReviewDto = result.getBody();
		if (!googleReviewDto.getCandidates().isEmpty()) {
			InstituteGoogleReview instituteGoogleReview = new InstituteGoogleReview();
//			instituteGoogleReview.setAddress(googleReviewDto.getCandidates().get(0).getFormattedAddress());
			instituteGoogleReview.setName(googleReviewDto.getCandidates().get(0).getName());
			if (googleReviewDto.getCandidates().get(0).getRating() != null) {
				instituteGoogleReview.setReviewStar(googleReviewDto.getCandidates().get(0).getRating());
			} else {
				instituteGoogleReview.setReviewStar(0d);
			}
			if (googleReviewDto.getCandidates().get(0).getUserRatingsTotal() != null) {
				instituteGoogleReview.setTotalReviews(googleReviewDto.getCandidates().get(0).getUserRatingsTotal());
			} else {
				instituteGoogleReview.setTotalReviews(0);
			}

			instituteGoogleReview.setLatitute(googleReviewDto.getCandidates().get(0).getGeometry().getLocation().getLat());
			instituteGoogleReview.setLongitude(googleReviewDto.getCandidates().get(0).getGeometry().getLocation().getLng());
			return instituteGoogleReview;
		}

		return null;
	}

	@Override
	public InstituteGoogleReview getInstituteGoogleReviewDetail(final BigInteger instituteId) {
		return iInstituteGoogleReviewDao.getInstituteGoogleReviewDetail(instituteId);
	}

	@Override
	public List<InstituteGoogleReview> getInstituteGoogleReview(final Integer pageNumber, final Integer pageSize) {
		int startIndex = (pageNumber - 1) * pageSize;
		return iInstituteGoogleReviewDao.getInstituteGoogleReview(startIndex, pageSize);
	}

	@Override
	public int getCountOfGooglereview() {
		return iInstituteGoogleReviewDao.getCountOfGooglereview();
	}

}
