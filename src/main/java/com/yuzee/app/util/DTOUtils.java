package com.yuzee.app.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseLanguage;
import com.yuzee.app.bean.Institute;
import com.yuzee.common.lib.dto.institute.CourseSyncDTO;
import com.yuzee.common.lib.dto.institute.InstituteSyncDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DTOUtils {
	private DTOUtils() {
	}

	public static InstituteSyncDTO convertToInstituteElasticSearchDTOEntity(Institute institute) {
		log.info("inside DTOUtils.convertToInstituteElasticSearchDTOEntity");
		ModelMapper modelMapper = new ModelMapper();
		InstituteSyncDTO instituteElaticDto = modelMapper.map(institute, InstituteSyncDTO.class);
		instituteElaticDto.setIntakes(institute.getInstituteIntakes());
		return instituteElaticDto;
	}

	public static CourseSyncDTO convertToCourseDTOElasticSearchEntity(Course course) {
		log.info("inside DTOUtils.convertToCourseDTOElasticSearchEntity");
		ModelMapper modelMapper = new ModelMapper();

		Converter<List<CourseLanguage>, List<String>> courseLanguageConverter = ctx -> ctx.getSource() == null ? null
				: ctx.getSource().stream().map(courseLanguage -> courseLanguage.getLanguage())
						.collect(Collectors.toList());

		modelMapper.typeMap(Course.class, CourseSyncDTO.class).addMappings(mapper -> mapper
				.using(courseLanguageConverter).map(Course::getCourseLanguages, CourseSyncDTO::setLanguages));

		CourseSyncDTO courseElasticDto = modelMapper.map(course, CourseSyncDTO.class);

//		courseElasticDto.setInstitute(convertToInstituteElasticSearchDTOEntity(course.getInstitute()));
		return courseElasticDto;

	}

//	public static ScholarshipSyncDto convertScholarshipToScholarshipDTOElasticSearchEntity(Scholarship scholarship) {
//		log.info("inside DTOUtils.convertToCourseDTOElasticSearchEntity");
//		ModelMapper modelMapper = new ModelMapper();
//
//		ScholarshipSyncDto scholarshipElasticDto = modelMapper.map(scholarship, ScholarshipSyncDto.class);
//		scholarshipElasticDto.setLanguages(scholarship.getScholarshipLanguages().stream()
//				.map(ScholarshipLanguage::getName).collect(Collectors.toList()));
//		scholarshipElasticDto.setEligibleNationalities(scholarship.getScholarshipEligibleNationalities().stream()
//				.map(ScholarshipEligibleNationality::getCountryName).collect(Collectors.toList()));
//		scholarshipElasticDto.setCountryNames(scholarship.getScholarshipCountries().stream()
//				.map(ScholarshipCountry::getCountryName).collect(Collectors.toList()));
//		//scholarshipElasticDto.setInstitute(convertToInstituteElasticSearchDTOEntity(scholarship.getInstitute()));
//		return scholarshipElasticDto;
//	}

}