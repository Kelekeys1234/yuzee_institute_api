package com.yuzee.app.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseLanguage;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteIntake;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipCountry;
import com.yuzee.app.bean.ScholarshipEligibleNationality;
import com.yuzee.app.bean.ScholarshipLanguage;
import com.yuzee.common.lib.dto.institute.CourseDTOElasticSearch;
import com.yuzee.common.lib.dto.institute.InstituteElasticSearchDTO;
import com.yuzee.common.lib.dto.institute.ScholarshipElasticDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DTOUtils {
	private DTOUtils() {
	}

	public static InstituteElasticSearchDTO convertToInstituteElasticSearchDTOEntity(Institute institute) {
		log.info("inside DTOUtils.convertToInstituteElasticSearchDTOEntity");
		ModelMapper modelMapper = new ModelMapper();
		InstituteElasticSearchDTO instituteElaticDto = modelMapper.map(institute, InstituteElasticSearchDTO.class);
		instituteElaticDto.setIntakes(
				institute.getInstituteIntakes().stream().map(InstituteIntake::getIntake).collect(Collectors.toList()));
		return instituteElaticDto;
	}

	public static CourseDTOElasticSearch convertToCourseDTOElasticSearchEntity(Course course) {
		log.info("inside DTOUtils.convertToCourseDTOElasticSearchEntity");
		ModelMapper modelMapper = new ModelMapper();

		Converter<List<CourseLanguage>, List<String>> courseLanguageConverter = ctx -> ctx.getSource() == null ? null
				: ctx.getSource().stream().map(courseLanguage -> courseLanguage.getLanguage())
						.collect(Collectors.toList());

		modelMapper.typeMap(Course.class, CourseDTOElasticSearch.class).addMappings(mapper -> mapper
				.using(courseLanguageConverter).map(Course::getCourseLanguages, CourseDTOElasticSearch::setLanguages));

		CourseDTOElasticSearch courseElasticDto = modelMapper.map(course, CourseDTOElasticSearch.class);

		courseElasticDto.setInstitute(convertToInstituteElasticSearchDTOEntity(course.getInstitute()));
		return courseElasticDto;

	}

	public static ScholarshipElasticDto convertScholarshipToScholarshipDTOElasticSearchEntity(Scholarship scholarship) {
		log.info("inside DTOUtils.convertToCourseDTOElasticSearchEntity");
		ModelMapper modelMapper = new ModelMapper();

		ScholarshipElasticDto scholarshipElasticDto = modelMapper.map(scholarship, ScholarshipElasticDto.class);
		scholarshipElasticDto.setLanguages(scholarship.getScholarshipLanguages().stream()
				.map(ScholarshipLanguage::getName).collect(Collectors.toList()));
		scholarshipElasticDto.setEligibleNationalities(scholarship.getScholarshipEligibleNationalities().stream()
				.map(ScholarshipEligibleNationality::getCountryName).collect(Collectors.toList()));
		scholarshipElasticDto.setCountryNames(scholarship.getScholarshipCountries().stream()
				.map(ScholarshipCountry::getCountryName).collect(Collectors.toList()));
		scholarshipElasticDto.setInstitute(convertToInstituteElasticSearchDTOEntity(scholarship.getInstitute()));
		return scholarshipElasticDto;
	}

}