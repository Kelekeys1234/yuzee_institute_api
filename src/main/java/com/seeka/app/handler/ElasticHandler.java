package com.seeka.app.handler;

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

import com.seeka.app.dto.ArticleElasticSearchDto;
import com.seeka.app.dto.CourseDTOElasticSearch;
import com.seeka.app.dto.ElasticSearchDTO;
import com.seeka.app.dto.InstituteElasticSearchDTO;
import com.seeka.app.dto.ScholarshipElasticDTO;
import com.seeka.app.util.IConstant;

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
			System.out.println(elasticSearchDto);
			ResponseEntity<Object> object = restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
			System.out.println(object);
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
			System.out.println(elasticSearchDto);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			ResponseEntity<Object> object = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Object.class,
					new Object[] {});
			System.out.println(object);
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
			System.out.println(elasticSearchDto);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			ResponseEntity<Object> object = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Object.class,
					new Object[] {});
			System.out.println(object);
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
			System.out.println(elasticSearchDto);
			ResponseEntity<Map> object = restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Map.class);
			System.out.println(object);
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
			System.out.println("Response is --- ");
			System.out.println(result);
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
			System.out.println(elasticSearchDto);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			ResponseEntity<Map> result = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Map.class,
					new Object[] {});
			Map<String, Object> responseMap = result.getBody();
			Integer status = (Integer) responseMap.get("status");
			System.out.println("Response is --- ");
			System.out.println(result);
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
		ResponseEntity<Object> object = restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
		System.out.println(object);
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
		ResponseEntity<Object> object = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Object.class,
				new Object[] {});
		System.out.println(object);
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
		ResponseEntity<Object> object = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Object.class,
				new Object[] {});
		System.out.println(object);
	}

	public void saveScholarshipOnElasticSearch(final String elasticSearchIndex, final String type, final ScholarshipElasticDTO scholarshipDto,
			final String elasticSearchName) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(elasticSearchIndex);
		elasticSearchDto.setType(type);
		elasticSearchDto.setEntityId(String.valueOf(scholarshipDto.getId()));
		elasticSearchDto.setObject(scholarshipDto);
		System.out.println(elasticSearchDto);
		ResponseEntity<Object> object = restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
		System.out.println(object);
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
		ResponseEntity<Object> object = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Object.class,
				new Object[] {});
		System.out.println(object);
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
		ResponseEntity<Object> object = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.DELETE, httpEntity, Object.class,
				new Object[] {});
		System.out.println(object);
	}
}
