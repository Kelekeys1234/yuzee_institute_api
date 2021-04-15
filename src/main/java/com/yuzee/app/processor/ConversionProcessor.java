package com.yuzee.app.processor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseLanguage;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteCategoryType;
import com.yuzee.app.bean.InstituteEnglishRequirements;
import com.yuzee.app.bean.InstituteFacility;
import com.yuzee.app.bean.InstituteIntake;
import com.yuzee.app.bean.InstituteService;
import com.yuzee.app.bean.Scholarship;
import com.yuzee.app.bean.ScholarshipCountry;
import com.yuzee.app.bean.ScholarshipEligibleNationality;
import com.yuzee.app.bean.ScholarshipLanguage;
import com.yuzee.app.dao.TimingDao;
import com.yuzee.app.dto.CourseDTOElasticSearch;
import com.yuzee.app.dto.InstituteElasticSearchDto;
import com.yuzee.app.dto.InstituteEnglishRequirementsElasticDto;
import com.yuzee.app.dto.ScholarshipElasticDto;
import com.yuzee.app.dto.TimingDto;
import com.yuzee.app.enumeration.EntityTypeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConversionProcessor {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TimingDao timingDao;

	@PostConstruct
	private void postConstrut() {
		Converter<List<InstituteEnglishRequirements>, List<InstituteEnglishRequirementsElasticDto>> instituteEnglishRequirementElasticDtoConverter = ctx -> ctx
				.getSource() == null
						? null
						: ctx.getSource().stream()
								.map(instituteEnglishRequirement -> new InstituteEnglishRequirementsElasticDto(
										instituteEnglishRequirement.getId(), instituteEnglishRequirement.getExamName(),
										instituteEnglishRequirement.getReadingMarks(),
										instituteEnglishRequirement.getWritingMarks(),
										instituteEnglishRequirement.getOralMarks(),
										instituteEnglishRequirement.getListningMarks()))
								.collect(Collectors.toList());

		Converter<List<InstituteFacility>, List<String>> instituteFacilityConverter = ctx -> ctx.getSource() == null
				? null
				: ctx.getSource().stream().map(instituteFacility -> instituteFacility.getService().getName())
						.collect(Collectors.toList());

		Converter<List<InstituteService>, List<String>> instituteServiceConverter = ctx -> ctx.getSource() == null
				? null
				: ctx.getSource().stream().map(instituteService -> instituteService.getService().getName())
						.collect(Collectors.toList());

		Converter<List<InstituteIntake>, List<String>> instituteIntakeConverter = ctx -> ctx.getSource() == null ? null
				: ctx.getSource().stream().map(instituteIntake -> instituteIntake.getIntake())
						.collect(Collectors.toList());

		Converter<InstituteCategoryType, String> instituteTypeConverter = ctx -> ctx.getSource() == null ? null
				: ctx.getSource().getName().toString();

		modelMapper.typeMap(Institute.class, InstituteElasticSearchDto.class)
				.addMappings(mapper -> mapper.using(instituteEnglishRequirementElasticDtoConverter).map(
						Institute::getInstituteEnglishRequirements,
						InstituteElasticSearchDto::setInstituteEnglishRequirements));

		modelMapper.typeMap(Institute.class, InstituteElasticSearchDto.class)
				.addMappings(mapper -> mapper.using(instituteFacilityConverter).map(Institute::getInstituteFacilities,
						InstituteElasticSearchDto::setInstituteFacilities));

		modelMapper.typeMap(Institute.class, InstituteElasticSearchDto.class)
				.addMappings(mapper -> mapper.using(instituteServiceConverter).map(Institute::getInstituteServices,
						InstituteElasticSearchDto::setInstituteServices));

		modelMapper.typeMap(Institute.class, InstituteElasticSearchDto.class)
				.addMappings(mapper -> mapper.using(instituteIntakeConverter).map(Institute::getInstituteIntakes,
						InstituteElasticSearchDto::setInstituteIntakes));

		modelMapper.typeMap(Institute.class, InstituteElasticSearchDto.class)
				.addMappings(mapper -> mapper.using(instituteTypeConverter).map(Institute::getInstituteCategoryType,
						InstituteElasticSearchDto::setInstituteCategory));
	}

	public CourseDTOElasticSearch convertToCourseDTOElasticSearchEntity(Course course) {
		log.info("inside DTOUtils.convertToCourseDTOElasticSearchEntity");
		CourseDTOElasticSearch courseElasticDto = modelMapper.map(course, CourseDTOElasticSearch.class);

		courseElasticDto.setLanguages(
				course.getCourseLanguages().stream().map(CourseLanguage::getLanguage).collect(Collectors.toList()));
		courseElasticDto.setCourseType(course.getCourseType().name());
		courseElasticDto.setCourseTimings(
				timingDao.findByEntityTypeAndEntityIdIn(EntityTypeEnum.COURSE, Arrays.asList(course.getId())).stream()
						.map(e -> modelMapper.map(e, TimingDto.class)).collect(Collectors.toList()));
		return courseElasticDto;
	}

	public InstituteElasticSearchDto convertToInstituteElasticDTOEntity(Institute institute) {
		log.info("inside DTOUtils.convertToInstituteElasticDTOEntity");
		return modelMapper.map(institute, InstituteElasticSearchDto.class);
	}

	public ScholarshipElasticDto convertScholarshipToScholarshipDTOElasticSearchEntity(Scholarship scholarship) {
		log.info("inside DTOUtils.convertToCourseDTOElasticSearchEntity");
		ScholarshipElasticDto scholarshipElasticDto = modelMapper.map(scholarship, ScholarshipElasticDto.class);
		scholarshipElasticDto.setLanguages(scholarship.getScholarshipLanguages().stream()
				.map(ScholarshipLanguage::getName).collect(Collectors.toList()));
		scholarshipElasticDto.setEligibleNationalities(scholarship.getScholarshipEligibleNationalities().stream()
				.map(ScholarshipEligibleNationality::getCountryName).collect(Collectors.toList()));
		scholarshipElasticDto.setCountryNames(scholarship.getScholarshipCountries().stream()
				.map(ScholarshipCountry::getCountryName).collect(Collectors.toList()));
		return scholarshipElasticDto;
	}

}
