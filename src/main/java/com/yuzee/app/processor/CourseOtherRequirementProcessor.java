package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.yuzee.common.lib.exception.NotFoundException;
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
	private MessageTranslator messageTranslator;
	
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
				vaccine.setAuditFields(userId);
				vaccine.setCourse(course);
				vaccine.setDescription(courseOtherRequirementDto.getVaccine().getDescription());
				vaccine.setDetails(courseOtherRequirementDto.getVaccine().getDetails());
				course.setCourseVaccineRequirement(vaccine);
			} else {
				if (!ObjectUtils.isEmpty(vaccine)) {
					vaccineDao.deleteById(vaccine.getId());;
				}
				course.setCourseVaccineRequirement(null);
			}
			
			CourseWorkExperienceRequirement workExperience = course.getCourseWorkExperienceRequirement();
			if (!ObjectUtils.isEmpty(courseOtherRequirementDto.getWorkExperience())) {
				if (ObjectUtils.isEmpty(workExperience)) {
					workExperience = new CourseWorkExperienceRequirement();
				}
				workExperience.setAuditFields(userId);
				workExperience.setCourse(course);
				workExperience.setDescription(courseOtherRequirementDto.getWorkExperience().getDescription());
				workExperience.setDuration(courseOtherRequirementDto.getWorkExperience().getDuration());
				workExperience.setDurationType(courseOtherRequirementDto.getWorkExperience().getDurationType());
				workExperience.setFields(courseOtherRequirementDto.getWorkExperience().getFields());
				course.setCourseWorkExperienceRequirement(workExperience);
			} else {
				if (!ObjectUtils.isEmpty(workExperience)) {
					workExperienceDao.deleteById(workExperience.getId());;
				}
				course.setCourseWorkExperienceRequirement(null);
			}

			CourseWorkPlacementRequirement workPlacement = course.getCourseWorkPlacementRequirement();
			if (!ObjectUtils.isEmpty(courseOtherRequirementDto.getWorkPlacement())) {
				if (ObjectUtils.isEmpty(workPlacement)) {
					workPlacement = new CourseWorkPlacementRequirement();
				}
				workPlacement.setAuditFields(userId);
				workPlacement.setCourse(course);
				workPlacement.setDescription(courseOtherRequirementDto.getWorkPlacement().getDescription());
				workPlacement.setDuration(courseOtherRequirementDto.getWorkPlacement().getDuration());
				workPlacement.setDurationType(courseOtherRequirementDto.getWorkPlacement().getDurationType());
				workPlacement.setFields(courseOtherRequirementDto.getWorkPlacement().getFields());
				course.setCourseWorkPlacementRequirement(workPlacement);
			} else {
				if (!ObjectUtils.isEmpty(workPlacement)) {
					workPlacementDao.deleteById(workPlacement.getId());;
				}
				course.setCourseWorkPlacementRequirement(null);
			}

			if (!ObjectUtils.isEmpty(courseOtherRequirementDto.getResearchProposal())) {
				CourseResearchProposalRequirement researchProposal = course.getCourseResearchProposalRequirement();
				if (ObjectUtils.isEmpty(researchProposal)) {
					researchProposal = new CourseResearchProposalRequirement();
				}
				researchProposal.setAuditFields(userId);
				researchProposal.setCourse(course);
				researchProposal.setDescription(courseOtherRequirementDto.getResearchProposal().getDescription());
				course.setCourseResearchProposalRequirement(researchProposal);
			} else {
				course.setCourseResearchProposalRequirement(null);
			}

			List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
			coursesToBeSavedOrUpdated.add(course);
			
			courseDao.saveAll(coursesToBeSavedOrUpdated);

			log.info("Send notification for course content updates");
			commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", coursesToBeSavedOrUpdated);

			commonProcessor.saveElasticCourses(coursesToBeSavedOrUpdated);
		} else {
			log.error(messageTranslator.toLocale("course.id.invalid", courseId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course.id.invalid", courseId));
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
				dto.setId(model.getId());
				dto.setDescription(model.getDescription());
				dto.setDetails(model.getDetails());
				otherRequirementDto.setVaccine(dto);
			}

			if (!ObjectUtils.isEmpty(course.getCourseWorkExperienceRequirement())) {
				CourseWorkExperienceRequirement model = course.getCourseWorkExperienceRequirement();
				CourseWorkExperienceRequirementDto dto = new CourseWorkExperienceRequirementDto();
				dto.setId(model.getId());
				dto.setDescription(model.getDescription());
				dto.setDuration(model.getDuration());
				dto.setDurationType(model.getDurationType());
				dto.setFields(model.getFields());
				otherRequirementDto.setWorkExperience(dto);
			}

			if (!ObjectUtils.isEmpty(course.getCourseWorkPlacementRequirement())) {
				CourseWorkPlacementRequirement model = course.getCourseWorkPlacementRequirement();
				CourseWorkPlacementRequirementDto dto = new CourseWorkPlacementRequirementDto();
				dto.setId(model.getId());
				dto.setDescription(model.getDescription());
				dto.setDuration(model.getDuration());
				dto.setDurationType(model.getDurationType());
				dto.setFields(model.getFields());
				otherRequirementDto.setWorkPlacement(dto);
			}

			if (!ObjectUtils.isEmpty(course.getCourseResearchProposalRequirement())) {
				CourseResearchProposalRequirement model = course.getCourseResearchProposalRequirement();
				CourseResearchProposalRequirementDto dto = new CourseResearchProposalRequirementDto();
				dto.setId(model.getId());
				dto.setDescription(model.getDescription());
				otherRequirementDto.setResearchProposal(dto);
			}
		} else {
			log.error(messageTranslator.toLocale("course.id.invalid", courseId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course.id.invalid", courseId));
		}
		return otherRequirementDto;
	}
}