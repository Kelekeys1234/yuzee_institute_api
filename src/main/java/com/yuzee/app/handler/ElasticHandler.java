package com.yuzee.app.handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.dto.ArticleElasticSearchDto;
import com.yuzee.app.dto.CourseDTOElasticSearch;
import com.yuzee.app.dto.ElasticSearchBulkWrapperDto;
import com.yuzee.app.dto.ElasticSearchDTO;
import com.yuzee.app.dto.InstituteElasticSearchDTO;
import com.yuzee.app.dto.ScholarshipElasticDto;
import com.yuzee.app.enumeration.EntityTypeEnum;
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
	
	public void saveUpdateData(List<CourseDTOElasticSearch> courseList) {
		List<ElasticSearchDTO> entities = courseList.stream().map(e -> {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(IConstant.ELASTIC_SEARCH_INDEX);
			elasticSearchDto.setType(EntityTypeEnum.COURSE.name());
			elasticSearchDto.setEntityId(String.valueOf(e.getId()));
			elasticSearchDto.setObject(e);
			return elasticSearchDto;

		}).collect(Collectors.toList());
		saveDataOnElasticSearchInBulk(new ElasticSearchBulkWrapperDto(entities));
	}

	public void saveDataOnElasticSearchInBulk(ElasticSearchBulkWrapperDto elasticSearchBulkWrapperDto) {
		StringBuilder path = new StringBuilder();
		path.append("http://" + IConstant.ELASTIC_SEARCH_BULK);
		restTemplate.postForEntity(path.toString(), elasticSearchBulkWrapperDto, Object.class);
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

	public void saveUpdateScholarship(final ScholarshipElasticDto scholarshipDto) {
		ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
		elasticSearchDto.setIndex(IConstant.ELASTIC_SEARCH_INDEX);
		elasticSearchDto.setType(EntityTypeEnum.SCHOLARSHIP.name());
		elasticSearchDto.setEntityId(String.valueOf(scholarshipDto.getId()));
		elasticSearchDto.setObject(scholarshipDto);
		System.out.println(elasticSearchDto);
		restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
	}

	public void deleteScholarshipOnElasticSearch(final String elasticSearchIndex, final String type, final ScholarshipElasticDto scholarshipDto,
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
