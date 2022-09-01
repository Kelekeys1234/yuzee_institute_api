package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.bean.CourseFunding;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.CourseFundingDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dto.CourseFundingRequestWrapper;
import com.yuzee.common.lib.dto.institute.CourseFundingDto;
import com.yuzee.common.lib.exception.ForbiddenException;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.exception.ValidationException;
import com.yuzee.common.lib.util.Utils;
import com.yuzee.local.config.MessageTranslator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseFundingProcessor {
	@Autowired
	private CourseDao courseDao;

	@Autowired
	private CourseFundingDao courseFundingDao;

	@Autowired
	private InstituteDao instituteDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CourseProcessor courseProcessor;

	@Autowired
	private CommonProcessor commonProcessor;

	@Autowired
	private MessageTranslator messageTranslator;

	@Transactional
	public List<String> addFundingToAllInstituteCourses(String userId, String instituteId, List<String> fundingNameId)
			throws ValidationException, NotFoundException, InvokeException {
		log.info("inside CourseFundingProcessor.addFundingToAllInstituteCourses");
		Institute institute = instituteDao.get(UUID.fromString(instituteId));
		List<Course> courseToBeNotified = new ArrayList<>();
		if (institute != null) {

			commonProcessor.getFundingsByFundingNameIds(fundingNameId, true);

			List<Course> instituteCourses = courseDao.findByInstituteId(instituteId);
		
			 if(ObjectUtils.isEmpty(institute)) {
				 log.error(messageTranslator.toLocale("course_funding.institute.id.invalid", instituteId, Locale.US));
					throw new NotFoundException(messageTranslator.toLocale("course_funding.institute.id.invalid", instituteId));
			 }
			    
			    	  for(Course instituteCourse:instituteCourses) {
			    		  instituteCourse.setCourseFundings(fundingNameId);
			    		  courseDao.addUpdateCourse(instituteCourse);
			    		  courseToBeNotified.add(instituteCourse);
			    	  }
			    	 
			    		commonProcessor.notifyCourseUpdates("COURSE_CONTENT_UPDATED", courseToBeNotified);
		}
		return fundingNameId;
	}

	@Transactional
	public void saveCourseFundings(String userId, String courseId, CourseFundingRequestWrapper request)
			throws NotFoundException, ValidationException, InvokeException {
		log.info("inside CourseFundingProcessor.saveCourseFundings");
		List<CourseFundingDto> courseFundingDto=request.getCourseFundingDtos();
		Course course = courseDao.get(courseId);
	   
		log.info("checking course with id exist");
		if (!ObjectUtils.isEmpty(course)) {
		  log.info("inserting courseFunding inside fundingId");
			List<String>fundingId=course.getCourseFundings();
			
			courseFundingDto.stream().forEach(e->{
				 fundingId.addAll(e.getFundingNameId().stream().collect(Collectors.toList()));
				
			 });
				List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
				coursesToBeSavedOrUpdated.add(course);
				if (!CollectionUtils.isEmpty(request.getLinkedCourseIds())) {
					List<String> dtosToReplicate = fundingId.stream()
							.map(e -> modelMapper.map(e, String.class)).collect(Collectors.toList());
					coursesToBeSavedOrUpdated
							.addAll(replicateCourseFundings(userId, request.getLinkedCourseIds(), dtosToReplicate));
				}
				courseDao.saveAll(coursesToBeSavedOrUpdated);

	
		} else {
			log.error(messageTranslator.toLocale("course_funding.course.id.invalid", courseId, Locale.US));
			throw new NotFoundException(messageTranslator.toLocale("course_funding.course.id.invalid", courseId));
		}
	}

	@Transactional
	public void deleteCourseFundingsByFundingNameIds(String userId, String courseId, List<String> fundingNameIds,
			List<String> linkedCourseIds) throws NotFoundException, ValidationException {
		log.info("inside CourseFundingProcessor.deleteCourseFundingsByFundingNameIds");
		Course course = courseDao.get(courseId);
		  log.info("inserting courseFunding inside fundingId");
		  List<String>fundingId=course.getCourseFundings();
		  
		  log.info("checking if course with Id exist");
			if (!ObjectUtils.isEmpty(course)) { 
				log.info("removing fundingNameId");
               fundingId.removeIf(x->fundingNameIds.contains(x));
        	  course.setCourseFundings(fundingId);
          
            	
            	log.info("coursesToBeAdded");
                List<Course> coursesToBeSavedOrUpdated = new ArrayList<>();
                 coursesToBeSavedOrUpdated.add(course);
       			if (!CollectionUtils.isEmpty(linkedCourseIds)) {
       				List<String> dtosToReplicate = fundingId.stream()
       						.map(e -> modelMapper.map(e, String.class)).collect(Collectors.toList());
       				coursesToBeSavedOrUpdated.addAll(replicateCourseFundings(userId, linkedCourseIds, dtosToReplicate));
       				
       			}
       			courseDao.saveAll(coursesToBeSavedOrUpdated);
            
            }else {
    			log.error(messageTranslator.toLocale("course_funding.course.id.invalid", courseId, Locale.US));
    			throw new NotFoundException(messageTranslator.toLocale("course_funding.course.id.invalid", courseId));

            } 
	
		}

	private List<Course> replicateCourseFundings(String userId, List<String> courseIds,
			List<String> courseFundingId) throws ValidationException, NotFoundException {
		log.info("inside courseProcessor.replicateCourseFundings");
		List<Course> courses = courseProcessor.validateAndGetCourseByIds(courseIds);
		if (!CollectionUtils.isEmpty(courseIds)) {
		courses.stream().forEach(course->{
			List<String> courseFunding = course.getCourseFundings();
			if(CollectionUtils.isEmpty(courseFundingId)){
				courseFunding.clear();
			}
			else{
				courseFunding.removeIf(e->Utils.contains(courseFundingId.stream().collect(Collectors.toList()), e));
                courseFundingId.stream().forEach(dto->{
    				Optional<String> existingCourseFunding=courseFunding.stream().filter(e->e.equals(dto)).findAny();
    				List<String> newFundingId=new ArrayList<>();
    				newFundingId.add(existingCourseFunding.get());
    				course.setCourseFundings(newFundingId);
                }
                
                		);
			}
		});
			
		
	}
		return courses;
}}