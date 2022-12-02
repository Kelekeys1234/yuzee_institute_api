package com.yuzee.app.processor;

import java.util.ArrayList;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseResearchProposalRequirement;
import com.yuzee.app.bean.CourseVaccineRequirement;
import com.yuzee.app.bean.CourseWorkExperienceRequirement;
import com.yuzee.app.bean.CourseWorkPlacementRequirement;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseVaccineRequirementDao;
import com.yuzee.app.dao.CourseWorkExperienceRequirementDao;
import com.yuzee.app.dao.CourseWorkPlacementRequirementDao;
import com.yuzee.app.dto.CourseOtherRequirementDto;
import com.yuzee.app.dto.CourseResearchProposalRequirementDto;
import com.yuzee.app.dto.CourseVaccineRequirementDto;
import com.yuzee.app.dto.CourseWorkExperienceRequirementDto;
import com.yuzee.app.dto.CourseWorkPlacementRequirementDto;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.common.VaccinationDto;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.handler.CommonHandler;
import com.yuzee.local.config.MessageTranslator;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseOtherRequirementProcessor {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CommonProcessor commonProcessor;

	@Autowired
	private CourseVaccineRequirementDao vaccineDao;

	@Autowired
	private CourseWorkExperienceRequirementDao workExperienceDao;

	@Autowired
	private CourseWorkPlacementRequirementDao workPlacementDao;

	@Autowired
	private CommonHandler commonHandler;

	@Autowired
	MessageTranslator messageTranslator;

	@Transactional
	public void saveOrUpdateOtherRequirements(String userId, String courseId,
			@Valid CourseOtherRequirementDto courseOtherRequirementDto) {
		log.info("inside CourseOtherRequirementProcessor.saveOrUpdateOtherRequirements");
		Course course = courseDao.get(courseId);
		if (!ObjectUtils.isEmpty(course)) {

			CourseVaccineRequirement vaccine = course.getCourseVaccineRequirement();

			if (!ObjectUtils.isEmpty(courseOtherRequirementDto.getVaccine())) {
				if (ObjectUtils.isEmpty(vaccine)) {
					vaccine = new CourseVaccineRequirement();
				}

				vaccine.setDescription(courseOtherRequirementDto.getVaccine().getDescription());
				if (!CollectionUtils.isEmpty(courseOtherRequirementDto.getVaccine().getVaccination())) {
					Set<String> vaccinationIds = courseOtherRequirementDto.getVaccine().getVaccination().stream()
							.map(vaccination -> {
								return vaccination.get_id().toString();
							}).collect(Collectors.toSet());
					PaginationResponseDto<List<VaccinationDto>> vaccinationPaggination = commonHandler
							.getVaccinationByFilters(1, vaccinationIds.size(), vaccinationIds);
//					if (CollectionUtils.isEmpty(vaccinationPaggination.getResponse())
//							|| vaccinationPaggination.getResponse().size() != vaccinationIds.size()) {
//						log.error(messageTranslator.toLocale("vaccination.ids.invalid", Locale.US));
//						throw new ValidationException(messageTranslator.toLocale("vaccination.ids.invalid"));
//					}
					vaccine.setVaccinationIds(vaccinationIds);
				} else if (!CollectionUtils.isEmpty(vaccine.getVaccinationIds())) {
					vaccine.getVaccinationIds().clear();
				}
				course.setCourseVaccineRequirement(vaccine);
			} else {
				course.setCourseVaccineRequirement(null);
			}

			CourseWorkExperienceRequirement workExperience = course.getCourseWorkExperienceRequirement();
			if (!ObjectUtils.isEmpty(courseOtherRequirementDto.getWorkExperience())) {
				if (ObjectUtils.isEmpty(workExperience)) {
					workExperience = new CourseWorkExperienceRequirement();
					workExperience.setDescription(courseOtherRequirementDto.getWorkExperience().getDescription());
					workExperience.setDuration(courseOtherRequirementDto.getWorkExperience().getDuration());
					workExperience.setDurationType(courseOtherRequirementDto.getWorkExperience().getDurationType());
					workExperience.setFields(courseOtherRequirementDto.getWorkExperience().getFields());
					course.setCourseWorkExperienceRequirement(workExperience);
				} else {
					workExperience.setDescription(courseOtherRequirementDto.getWorkExperience().getDescription());
					workExperience.setDuration(courseOtherRequirementDto.getWorkExperience().getDuration());
					workExperience.setDurationType(courseOtherRequirementDto.getWorkExperience().getDurationType());
					workExperience.setFields(courseOtherRequirementDto.getWorkExperience().getFields());
				}

			} else {

				course.setCourseWorkExperienceRequirement(null);
			}

			CourseWorkPlacementRequirement workPlacement = course.getCourseWorkPlacementRequirement();
			if (!ObjectUtils.isEmpty(courseOtherRequirementDto.getWorkPlacement())) {
				if (ObjectUtils.isEmpty(workPlacement)) {
					workPlacement = new CourseWorkPlacementRequirement();
					workPlacement.setDescription(courseOtherRequirementDto.getWorkPlacement().getDescription());
					workPlacement.setDuration(courseOtherRequirementDto.getWorkPlacement().getDuration());
					workPlacement.setDurationType(courseOtherRequirementDto.getWorkPlacement().getDurationType());
					workPlacement.setFields(courseOtherRequirementDto.getWorkPlacement().getFields());
					course.setCourseWorkPlacementRequirement(workPlacement);

				} else {
					workPlacement.setDescription(courseOtherRequirementDto.getWorkPlacement().getDescription());
					workPlacement.setDuration(courseOtherRequirementDto.getWorkPlacement().getDuration());
					workPlacement.setDurationType(courseOtherRequirementDto.getWorkPlacement().getDurationType());
					workPlacement.setFields(courseOtherRequirementDto.getWorkPlacement().getFields());

				}
			} else {
				course.setCourseWorkPlacementRequirement(null);
			}
			course.setCourseResearchProposalRequirement(null);
			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			log.info("Send notification for course content updates");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
	}

	@Transactional
	public CourseOtherRequirementDto getOtherRequirements(String userId, String courseId) {
		log.info("inside CourseOtherRequirementProcessor.getOtherRequirements");
		Course course = courseDao.get(courseId);
		CourseOtherRequirementDto otherRequirementDto = new CourseOtherRequirementDto();
		if (!ObjectUtils.isEmpty(course)) {

			if (!ObjectUtils.isEmpty(course.getCourseVaccineRequirement())) {
				CourseVaccineRequirement model = course.getCourseVaccineRequirement();
				CourseVaccineRequirementDto dto = new CourseVaccineRequirementDto();

				dto.setDescription(model.getDescription());
				if (!CollectionUtils.isEmpty(model.getVaccinationIds())) {
					var wrapperObject = new Object() {
						Map<UUID, String> mapOfVaccinationIdwithName = null;
					};
					PaginationResponseDto<List<VaccinationDto>> vaccinationPaggination = commonHandler
							.getVaccinationByFilters(1, model.getVaccinationIds().size(), model.getVaccinationIds());

					if (!CollectionUtils.isEmpty(vaccinationPaggination.getResponse())) {
						wrapperObject.mapOfVaccinationIdwithName = vaccinationPaggination.getResponse().stream()
								.collect(Collectors.toMap(VaccinationDto::get_id, VaccinationDto::getName));
					}

					dto.setVaccination(model.getVaccinationIds().stream().map(vaccine -> {
						VaccinationDto vaccinationDto = new VaccinationDto();
						vaccinationDto.set_id(UUID.fromString(vaccine));
						if (!MapUtils.isEmpty(wrapperObject.mapOfVaccinationIdwithName)
								&& wrapperObject.mapOfVaccinationIdwithName.containsKey(UUID.fromString(vaccine))) {
							vaccinationDto
									.setName(wrapperObject.mapOfVaccinationIdwithName.get(UUID.fromString(vaccine)));
						}

						return vaccinationDto;
					}).collect(Collectors.toSet()));
				}

				otherRequirementDto.setVaccine(dto);
			}

			if (!ObjectUtils.isEmpty(course.getCourseWorkExperienceRequirement())) {
				CourseWorkExperienceRequirement model = course.getCourseWorkExperienceRequirement();
				CourseWorkExperienceRequirementDto dto = new CourseWorkExperienceRequirementDto();

				dto.setDescription(model.getDescription());
				dto.setDuration(model.getDuration());
				dto.setDurationType(model.getDurationType());
				dto.setFields(model.getFields());
				otherRequirementDto.setWorkExperience(dto);
			}

			if (!ObjectUtils.isEmpty(course.getCourseWorkPlacementRequirement())) {
				CourseWorkPlacementRequirement model = course.getCourseWorkPlacementRequirement();
				CourseWorkPlacementRequirementDto dto = new CourseWorkPlacementRequirementDto();

				dto.setDescription(model.getDescription());
				dto.setDuration(model.getDuration());
				dto.setDurationType(model.getDurationType());
				dto.setFields(model.getFields());
				otherRequirementDto.setWorkPlacement(dto);
			}


		} else {
			log.error("invalid course id: {}", courseId);
			throw new NotFoundException("invalid course id: " + courseId);
		}
		return otherRequirementDto;
	}

//	private List<Course> replicateCourseIntakes(String userId, List<String> courseIds,
//			List<CourseIntakeDto> courseIntakeDtos) throws ValidationException, NotFoundException {
//		log.info("inside courseProcessor.replicateCourseIntakes");
//		List<Date> intakeDates = courseIntakeDtos.stream().map(CourseIntakeDto::getIntakeDate)
//				.collect(Collectors.toList());
//		if (!CollectionUtils.isEmpty(courseIds)) {
//			List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
//			courses.stream().forEach(course -> {
//				List<CourseIntake> courseIntakes = course.getCourseIntakes();
//				if (CollectionUtils.isEmpty(courseIntakeDtos)) {
//					courseIntakes.clear();
//				} else {
//					courseIntakes.removeIf(e -> !Utils.contains(intakeDates, e.getIntakeDate()));
//					courseIntakeDtos.stream().forEach(dto -> {
//						Optional<CourseIntake> existingIntakeOp = courseIntakes.stream().filter(
//								e -> e.getIntakeDate().toInstant().compareTo(dto.getIntakeDate().toInstant()) == 0)
//								.findAny();
//						CourseIntake courseIntake = null;
//						if (existingIntakeOp.isPresent()) {
//							courseIntake = existingIntakeOp.get();
//						} else {
//							courseIntake = new CourseIntake();
//							courseIntake.setCourse(course);
//							courseIntake.setIntakeDate(dto.getIntakeDate());
//							courseIntakes.add(courseIntake);
//						}
//						courseIntake.setAuditFields(userId);
//					});
//				}
//			});
//			return courses;
//		}
//		return new ArrayList<>();
//	}
}