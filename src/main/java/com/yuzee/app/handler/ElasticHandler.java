package com.yuzee.app.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.dto.ArticleElasticSearchDto;
import com.yuzee.app.dto.CourseDTOElasticSearch;
import com.yuzee.app.dto.ElasticSearchDTO;
import com.yuzee.app.dto.InstituteElasticSearchDTO;
import com.yuzee.app.dto.ScholarshipElasticDTO;
import com.yuzee.app.util.IConstant;

@Service
public class ElasticHandler {

	@Autowired
	private RestTemplate restTemplate;

	public void saveInsituteOnElasticSearch(final String elasticSearchIndex, final String type, final List<InstituteElasticSearchDTO> instituteList,
			final String elasticSearchName) {
		for (InstituteElasticSearchDTO insitute : instituteList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(insitute.getId()));
			elasticSearchDto.setObject(insitute);
			restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
		}
	}

	public void updateInsituteOnElasticSearch(final String elasticSearchIndex, final String type, final List<InstituteElasticSearchDTO> instituteList,
			final String elasticSearchName) {
		for (InstituteElasticSearchDTO insitute : instituteList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(insitute.getId()));
			elasticSearchDto.setObject(insitute);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Object.class,
					new Object[] {});
		}
	}

	public void deleteInsituteOnElasticSearch(final String elasticSearchIndex, final String type, final List<InstituteElasticSearchDTO> instituteList,
			final String elasticSearchName) {
		for (InstituteElasticSearchDTO insitute : instituteList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(insitute.getId()));
			elasticSearchDto.setObject(insitute);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Object.class,
					new Object[] {});
		}
	}

	/**
	 * Save Courses on Elastic search using API.
	 *
	 * @param elasticSearchIndex
	 * @param type
	 * @param courseList
	 * @param elasticSearchName
	 */
	public void saveCourseOnElasticSearch(final String elasticSearchIndex, final String type, final List<CourseDTOElasticSearch> courseList,
			final String elasticSearchName) {
		for (CourseDTOElasticSearch courseElasticSearch : courseList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(courseElasticSearch.getId()));
			elasticSearchDto.setObject(courseElasticSearch);
			restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Map.class);
		}
	}

	public Map<String, List<String>> updateCourseOnElasticSearch(final String elasticSearchIndex, final String type,
			final List<CourseDTOElasticSearch> courseList, final String elasticSearchName) {
		Map<String, List<String>> elasticSearchCourseUpdateStatusMap = new HashMap<>();
		elasticSearchCourseUpdateStatusMap.put("failed", new ArrayList<String>());
		elasticSearchCourseUpdateStatusMap.put("successful", new ArrayList<String>());
		for (CourseDTOElasticSearch courseElasticSearch : courseList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(courseElasticSearch.getId()));
			elasticSearchDto.setObject(courseElasticSearch);
			System.out.println(elasticSearchDto);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			ResponseEntity<Map> result = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Map.class,
					new Object[] {});
			Map<String, Object> responseMap = result.getBody();
			Integer status = (Integer) responseMap.get("status");
			if (status != 200) {
				elasticSearchCourseUpdateStatusMap.get("failed").add(courseElasticSearch.getId());
			} else {
				elasticSearchCourseUpdateStatusMap.get("successful").add(courseElasticSearch.getId());
			}
		}
		return elasticSearchCourseUpdateStatusMap;
	}

	public void deleteCourseOnElasticSearch(final String elasticSearchIndex, final String type, final List<CourseDTOElasticSearch> courseList,
			final String elasticSearchName) {
		for (CourseDTOElasticSearch courseElasticSearch : courseList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(courseElasticSearch.getId()));
			elasticSearchDto.setObject(courseElasticSearch);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Map.class,
					new Object[] {});
		}
	}

	public void saveArticleOnElasticSearch(final String elasticSearchIndex, final String type, final ArticleElasticSearchDto articleDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(articleDto.getId()));
		elasticSearchDto.setObject(articleDto);
		System.out.println(elasticSearchDto);
		restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
	}

	public void updateArticleOnElasticSearch(final String elasticSearchIndex, final String type, final ArticleElasticSearchDto articleDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(articleDto.getId()));
		elasticSearchDto.setObject(articleDto);
		System.out.println(elasticSearchDto);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
		restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Object.class,
				new Object[] {});
	}

	public void deleteArticleOnElasticSearch(final String elasticSearchIndex, final String type, final ArticleElasticSearchDto articleDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(articleDto.getId()));
		elasticSearchDto.setObject(articleDto);
		System.out.println(elasticSearchDto);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
		restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Object.class,
				new Object[] {});
	}

	public void saveScholarshipOnElasticSearch(final String elasticSearchIndex, final String type, final ScholarshipElasticDTO scholarshipDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(scholarshipDto.getId()));
		elasticSearchDto.setObject(scholarshipDto);
		System.out.println(elasticSearchDto);
		restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
	}

	public void updateScholarshipOnElasticSearch(final String elasticSearchIndex, final String type, final ScholarshipElasticDTO scholarshipDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(scholarshipDto.getId()));
		elasticSearchDto.setObject(scholarshipDto);
		System.out.println(elasticSearchDto);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
		restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Object.class,
				new Object[] {});
	}

	public void deleteScholarshipOnElasticSearch(final String elasticSearchIndex, final String type, final ScholarshipElasticDTO scholarshipDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(scholarshipDto.getId()));
		elasticSearchDto.setObject(scholarshipDto);
		System.out.println(elasticSearchDto);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
		restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Object.class, new Object[] {});
	}
}
