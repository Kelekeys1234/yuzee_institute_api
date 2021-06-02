package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseInstitute;
import com.yuzee.app.bean.CoursePayment;
import com.yuzee.app.bean.CourseScholarship;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseInstituteDao;
import com.yuzee.app.dao.TimingDao;
import com.yuzee.app.dto.CourseLinkedInstituteDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.UnLinkInsituteDto;
import com.yuzee.app.util.CommonUtil;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.RuntimeValidationException;
import com.yuzee.common.lib.exception.ValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseInstituteProcessor {
	@Autowired
	CourseDao courseDao;

	@Autowired
	CourseProcessor courseProcessor;

	@Autowired
	InstituteProcessor instituteProcessor;

	@Autowired
	CourseInstituteDao courseInstituteDao;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private TimingDao timingDao;

	@Transactional
	public void createLinks(String userId, String courseId, List<String> instituteIds)
			throws NotFoundException, ValidationException {
		log.info("Inside CourseInstituteProcessor.createLinks");
		log.info("user: {} is going to create the copy course: {} for the instituteIds: {}", userId, courseId,
				instituteIds);

		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<Institute> institutes = instituteProcessor.validateAndGetInstituteByIds(instituteIds);
		Set<String> linkedInstituteIds = getLinkedInstituteCourses(courseId).keySet();
		if (instituteIds.stream().anyMatch(e -> e.equals(course.getInstitute().getId()))) {
			log.error("one or more instituteIds are of same course institute");
			throw new ValidationException("one or more instituteIds are of same course institute");
		}
		if (!Collections.disjoint(linkedInstituteIds, instituteIds)) {
			log.error("one or more institutes are already linked with courseId: {}", courseId);
			throw new ValidationException("one or more institutes are already linked with courseId: " + courseId);
		}
		List<CourseInstitute> courseInstitutes = new ArrayList<>();
		// TODO: check the access of institutes against userId
		institutes.stream().forEach(e -> {
			Course copiedCourse = modelMapper.map(course, Course.class);
			copiedCourse.setId(null);
			if (!ObjectUtils.isEmpty(copiedCourse.getOffCampusCourse())) {
				copiedCourse.getOffCampusCourse().setId(null);
				copiedCourse.getOffCampusCourse().setCourse(copiedCourse);
			}
			if (!ObjectUtils.isEmpty(copiedCourse.getCoursePayment())) {
				copiedCourse.getCoursePayment().setId(null);
				copiedCourse.getCoursePayment().setCourse(copiedCourse);
				final CoursePayment finalCoursePayment = copiedCourse.getCoursePayment();
				copiedCourse.getCoursePayment().getPaymentItems().stream().forEach(s -> {
					s.setId(null);
					s.setCoursePayment(finalCoursePayment);
				});
			}
			copiedCourse.setInstitute(e);
			copiedCourse.setAuditFields(userId);
			final Course finalCopiedCourse = copiedCourse;
			copiedCourse.getCourseDeliveryModes().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			copiedCourse.getCourseEnglishEligibilities().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			copiedCourse.getCourseIntakes().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			copiedCourse.getCourseLanguages().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			copiedCourse.getCourseMinRequirements().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
				d.getCourseMinRequirementSubjects().stream().forEach(s -> {
					s.setId(null);
					s.setCourseMinRequirement(d);
				});
			});
			copiedCourse.getCoursePrerequisites().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			copiedCourse.getCourseCareerOutcomes().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			copiedCourse.getCourseSubjects().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			copiedCourse.getCourseFundings().stream().forEach(d -> {
				d.setId(null);
				d.setCourse(finalCopiedCourse);
			});
			if (!ObjectUtils.isEmpty(copiedCourse.getCourseScholarship())) {
				copiedCourse.getCourseScholarship().setId(null);
				copiedCourse.getCourseScholarship().setCourse(copiedCourse);
				final CourseScholarship finalCourseScholarship = copiedCourse.getCourseScholarship();
				copiedCourse.getCourseScholarship().getScholarshipItems().stream().forEach(s -> {
					s.setId(null);
					s.setCourseScholarship(finalCourseScholarship);
				});
			}
			try {
				copiedCourse = courseDao.addUpdateCourse(copiedCourse);
			} catch (ValidationException e1) {
				log.error(
						"one of the institute is already managing same kind of institute. So cant link at the moment.");
				throw new RuntimeValidationException(
						"one of the institute is already managing same kind of institute. So cant link at the moment.");
			}

			CourseInstitute courseInstitute = new CourseInstitute();
			courseInstitute.setAuditFields(userId);
			courseInstitute.setSourceCourse(course);
			courseInstitute.setSourceInstitute(course.getInstitute());
			CourseInstitute existingSecondaryCourse = courseInstituteDao.findByDestinationCourseId(courseId);
			if (!ObjectUtils.isEmpty(existingSecondaryCourse)) {
				courseInstitute.setSourceCourse(existingSecondaryCourse.getSourceCourse());
				courseInstitute.setSourceInstitute(existingSecondaryCourse.getSourceInstitute());
			}
			courseInstitute.setDestinationCourse(copiedCourse);
			courseInstitute.setDestinationInstitute(e);
			courseInstitutes.add(courseInstitute);
		});
		courseInstituteDao.saveAll(courseInstitutes);

	}

	@Transactional(readOnly = true)
	public List<CourseLinkedInstituteDto> getLinkedInstitutes(String userId, String courseId) throws Exception {
		log.info("Inside CourseInstituteProcessor.getLinkedInstitutes for courseId: {}", courseId);
		log.info("userId: {}, courseId: {}", userId, courseId);
		List<CourseLinkedInstituteDto> linkedInstituteResponse = new ArrayList<>();
		Map<String, Course> linkedInstituteCourses = getLinkedInstituteCourses(courseId);
		if (!linkedInstituteCourses.isEmpty()) {
			List<InstituteResponseDto> instituteResponseDtos = instituteProcessor
					.getInstitutesByIdList(linkedInstituteCourses.keySet().stream().collect(Collectors.toList()));
			if (!CollectionUtils.isEmpty(instituteResponseDtos)) {
				instituteResponseDtos.stream().forEach(e -> {
					CourseLinkedInstituteDto dto = new CourseLinkedInstituteDto();
					Course course = linkedInstituteCourses.get(e.getId());
					if (course.getCreatedBy().equals(userId)) {
						dto.setHasCourseEditAccess(true);
					}
					dto.setCourseId(course.getId());
					dto.setInstitute(e);
					linkedInstituteResponse.add(dto);
				});
			}
		}
		return linkedInstituteResponse;
	}

	private Map<String, Course> getLinkedInstituteCourses(String courseId) throws NotFoundException {
		log.info("inside CourseInstituteProcessor.getLinkedInstituteIds");
		log.info("courseId: {}", courseId);
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		List<CourseInstitute> courseInstitutes = courseInstituteDao.findLinkedInstitutes(courseId);
		Map<String, Course> linkedInstitutesMap = new HashMap<>();
		courseInstitutes.stream().forEach(e -> {
			linkedInstitutesMap.put(e.getSourceInstitute().getId(), e.getDestinationCourse());
			linkedInstitutesMap.put(e.getDestinationInstitute().getId(), e.getSourceCourse());
		});
		linkedInstitutesMap.remove(course.getInstitute().getId());
		return linkedInstitutesMap;
	}

	@Transactional
	public void unLinkInstitutes(String userId, String courseId, @Valid List<UnLinkInsituteDto> request)
			throws NotFoundException {
		log.info("inside CourseInstituteProcessor.getLinkedInstituteIds");
		log.info("PARAMETERS ====> userId: {} ,courseId: {}, request: {}", userId, courseId, request);
		Course course = courseProcessor.validateAndGetCourseById(courseId);
		CommonUtil.validateEditAccess(userId, course);
		Map<String, Boolean> deleteCourseRequestMap = request.stream()
				.collect(Collectors.toMap(UnLinkInsituteDto::getCourseId, UnLinkInsituteDto::isDeleteCourse));
		Map<String, Course> requestCourses = courseProcessor
				.validateAndGetCourseByIds(deleteCourseRequestMap.keySet().stream().collect(Collectors.toList()))
				.stream().collect(Collectors.toMap(Course::getId, e -> e));
		List<CourseInstitute> linkedInstitutes = courseInstituteDao.findLinkedInstitutes(courseId);

		List<CourseInstitute> linkedCoursesTobeDeleted = new ArrayList<>();
		List<Course> coursesTobeDeleted = new ArrayList<>();
		request.stream().forEach(unLinkInstituteDto -> {
			Course requestedCourse = requestCourses.get(unLinkInstituteDto.getCourseId());
			if (deleteCourseRequestMap.get(requestedCourse.getId()).booleanValue()) {
				CommonUtil.validateEditAccess(userId, requestedCourse);
			}
			log.info("process to delete (un-link) the requested course : {}", unLinkInstituteDto.getCourseId());
			if (CollectionUtils.isEmpty(linkedInstitutes)) {
				log.error("CourseId: {} is not lnked with the cureent course", unLinkInstituteDto.getCourseId());
				throw new RuntimeValidationException(
						"CourseId: " + unLinkInstituteDto.getCourseId() + " is not lnked with the cureent course");
			} else {
				log.debug("going to see if course to be unLinked is linked as primart or secondary course");
				Optional<CourseInstitute> secondaryCourse = linkedInstitutes.stream()
						.filter(e -> e.getDestinationCourse().getId().equals(unLinkInstituteDto.getCourseId()))
						.findAny();
				CourseInstitute toBeDeleted = null;
				if (secondaryCourse.isPresent()) {
					log.debug("course is secondary course");
					toBeDeleted = secondaryCourse.get();
				} else {
					log.debug("course is primary course");
					Course firstSecondayCourse = linkedInstitutes.get(0).getDestinationCourse();
					linkedInstitutes.stream().forEach(e -> {
						e.setSourceCourse(firstSecondayCourse);
						e.setSourceInstitute(firstSecondayCourse.getInstitute());
					});
					toBeDeleted = linkedInstitutes.get(0);
				}
				linkedCoursesTobeDeleted.add(toBeDeleted);
				if (deleteCourseRequestMap.get(unLinkInstituteDto.getCourseId()).booleanValue()) {
					coursesTobeDeleted.add(requestedCourse);
				}
			}
		});
		if (!CollectionUtils.isEmpty(linkedCoursesTobeDeleted)) {
			courseInstituteDao.deleteAll(linkedCoursesTobeDeleted);
		}
		if (!CollectionUtils.isEmpty(coursesTobeDeleted)) {
			timingDao.deleteByEntityTypeAndEntityIdIn(EntityTypeEnum.COURSE,
					coursesTobeDeleted.stream().map(Course::getId).collect(Collectors.toList()));
			courseDao.deleteAll(coursesTobeDeleted);
		}
	}

	@Transactional
	public void removeAllLinksWithInstitutes(Course course) {
		log.info("inside CourseInstiuteProcessor.removeAllLinksWithInstitutes");
		if (!ObjectUtils.isEmpty(course)) {
			List<CourseInstitute> linkedInstitutes = courseInstituteDao.findLinkedInstitutes(course.getId());
			if (!CollectionUtils.isEmpty(linkedInstitutes)) {
				log.debug("going to see if course to be unLinked is linked as primart or secondary course");
				Optional<CourseInstitute> secondaryCourse = linkedInstitutes.stream()
						.filter(e -> e.getDestinationCourse().getId().equals(course.getId())).findAny();
				CourseInstitute toBeDeleted = null;
				if (secondaryCourse.isPresent()) {
					log.debug("course is secondary course");
					toBeDeleted = secondaryCourse.get();
				} else {
					log.debug("course is primary course");
					Course firstSecondayCourse = linkedInstitutes.get(0).getDestinationCourse();
					linkedInstitutes.stream().forEach(e -> {
						e.setSourceCourse(firstSecondayCourse);
						e.setSourceInstitute(firstSecondayCourse.getInstitute());
					});
					toBeDeleted = linkedInstitutes.get(0);
				}
				courseInstituteDao.deleteAll(Arrays.asList(toBeDeleted));
			}
		}
	}
}
