package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.dao.CareerTestDao;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dto.CareerJobDto;
import com.yuzee.app.dto.CareerJobSkillDto;
import com.yuzee.app.dto.CareerJobSubjectDto;
import com.yuzee.app.dto.CareerJobTypeDto;
import com.yuzee.app.dto.CareerJobWorkingStyleDto;
import com.yuzee.app.dto.CourseResponseDto;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CareerTestProcessor {

	@Autowired
	private CareerTestDao careerTestDao;
	
	@Autowired
	private CourseDao courseDao;
	
	public PaginationResponseDto getCareerJobSkills(String levelId, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobSkills() method");
		List<CareerJobSkillDto> careerJobSkillDtos = new ArrayList<>();
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		log.info("Extracting career job levels from DB for levelId " + levelId);
		List<CareerJobSkill> careerJobSkillsFromDB = careerTestDao.getCareerJobSkills(levelId, startIndex, pageSize);
		log.info("Fetching totalCount of careerJobLevels for jobIds " + levelId);
		int totalCount = 0;
		if (!CollectionUtils.isEmpty(careerJobSkillsFromDB)) {
			totalCount = careerTestDao.getCareerJobSkillCount(levelId);
			log.info("Career JobSkills fetched from DB, start iterating data to make final response");
			careerJobSkillsFromDB.stream().forEach(careerJobSkill -> {
				CareerJobSkillDto careerJobSkillDto = new CareerJobSkillDto(careerJobSkill.getId(),
						careerJobSkill.getSkill(), careerJobSkill.getCareerJobs().getId());
				careerJobSkillDtos.add(careerJobSkillDto);
			});
		}
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobSkillDtos, totalCount, paginationUtilDto.getPageNumber(), 
				paginationUtilDto.isHasPreviousPage(), paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}

	public PaginationResponseDto getCareerJobWorkingStyles(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobWorkingStyles() method");
		List<CareerJobWorkingStyleDto> careerJobWorkingStyleDtos = new ArrayList<>();
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		log.info("Calculating Pageable size based on pageNumber nad pageSize");
		Pageable pageable = PageRequest.of(startIndex, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job workingStyles from DB for jobId " + jobIds);
			List<CareerJobWorkingStyle> careerJobWorkingStyles = careerTestDao.getCareerJobWorkingStyle(jobIds, pageable);
			log.info("Extracting total Count of workingStyles from DB having jobId "+ jobIds);
			totalCount = careerTestDao.getCareerJobWorkingStyleCount(jobIds);
			if (!CollectionUtils.isEmpty(careerJobWorkingStyles)) {
				log.info("Career Working Style fetched from DB, start iterating data");
				careerJobWorkingStyles.stream().forEach(careerJobWorkingStyle -> {
					log.info("Start adding values in DTO class");
					CareerJobWorkingStyleDto careerJobWorkingStyleDto = new CareerJobWorkingStyleDto(careerJobWorkingStyle.getId(), 
							careerJobWorkingStyle.getWorkStyle(), careerJobWorkingStyle.getCareerJobs().getId());
					careerJobWorkingStyleDtos.add(careerJobWorkingStyleDto);
				});
			}
		}
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobWorkingStyleDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}
	
	public PaginationResponseDto getCareerJobSubjects(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobSubjects() method");
		List<CareerJobSubjectDto> careerJobSubjectDtos = new ArrayList<>();
		int startIndex = pageNumber - 1;
		log.info("Calculating Pageable size based on pageNumber nad pageSize");
		Pageable pageable = PageRequest.of(startIndex, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job Subjects from DB for jobId " + jobIds);
			List<CareerJobSubject> careerJobSubjects = careerTestDao.getCareerJobSubject(jobIds, pageable);
			log.info("Extracting total Count of jobSubjects from DB having jobId "+ jobIds);
			totalCount = careerTestDao.getCareerJobSubjectCount(jobIds);
			if (!CollectionUtils.isEmpty(careerJobSubjects)) {
				log.info("Career JobSubjects fetched from DB, start iterating data");
				careerJobSubjects.stream().forEach(careerJobSubject -> {
					log.info("Start adding values in DTO class");
					CareerJobSubjectDto careerJobSubjectDto = new CareerJobSubjectDto(careerJobSubject.getId(), careerJobSubject.getSubject(),
							careerJobSubject.getCareerJobs().getId());
					careerJobSubjectDtos.add(careerJobSubjectDto);
				});
			}
		}
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobSubjectDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}
	
	public PaginationResponseDto getCareerJobTypes(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobTypes() method");
		List<CareerJobTypeDto> careerJobTypeDtos = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job types from DB");
			Page<CareerJobType> careerJobTypesPage = careerTestDao.getCareerJobType(jobIds, pageable);
			log.info("Extracting total Count of jobs from DB having jobId ", jobIds);
			totalCount = ((Long) careerJobTypesPage.getTotalElements()).intValue();

			List<CareerJobType> careerJobTypes = careerJobTypesPage.getContent();
			if (!CollectionUtils.isEmpty(careerJobTypes)) {
				log.info("Career Job Types fetched from DB, start iterating data");
				careerJobTypes.stream().forEach(careerJob -> {
					CareerJobTypeDto careerJobTypeDto = new CareerJobTypeDto(careerJob.getId(), careerJob.getJobType());
					careerJobTypeDtos.add(careerJobTypeDto);
				});
			}
		}
		
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobTypeDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}
	
	public PaginationResponseDto getCareerJobs(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobs() method");
		List<CareerJobDto> careerJobDtos = new ArrayList<>();
		int startIndex = pageNumber - 1;
		log.info("Calculating Pageable size based on pageNumber nad pageSize");
		Pageable pageable = PageRequest.of(startIndex, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career jobs from DB for jobId " + jobIds);
			List<CareerJob> careerJobs = careerTestDao.getCareerJob(jobIds, pageable);
			log.info("Extracting total Count of jobs from DB having jobId "+ jobIds);
			totalCount = careerTestDao.getCareerJobCount(jobIds);
			if (!CollectionUtils.isEmpty(careerJobs)) {
				log.info("Career Jobs fetched from DB, start iterating data");
				careerJobs.stream().forEach(careerJob -> {
					CareerJobDto careerJobDto = new CareerJobDto(careerJob.getId(), careerJob.getJob(), careerJob.getJobDescription());
					careerJobDtos.add(careerJobDto);
				});
			}
		}
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}
	
	public PaginationResponseDto getRelatedCourseBasedOnCareerTest(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getRelatedCourseBasedOnCareerTest() method");
		List<String> courseSearchkeyword = new ArrayList<>();
		log.info("Extracting job courseSearchKeyword from DB to match possible courses");
		List<CareerJobCourseSearchKeyword> careerJobCourseSearchKeywords = careerTestDao.getCareerJobCourseSearchKeyword(jobIds);
		int startIndex = pageNumber - 1;
		log.info("Calculating Pageable size based on pageNumber nad pageSize");
		
		if(!CollectionUtils.isEmpty(careerJobCourseSearchKeywords)) {
			log.info("Career courseSearchKeyword fetched, iterating data to extract search keywords");
			careerJobCourseSearchKeywords.stream().forEach(careerJobCourseSearchKeyword -> {
				log.info("Adding searchKeyword in list, to fetch all possible course having searchKeyword from DB");
				courseSearchkeyword.add(careerJobCourseSearchKeyword.getCourseSearchKeyword());
			});
		}
		log.info("Extracting total count of courses based on courseSearchKeywords");
		Integer totalCount = courseDao.getRelatedCourseBasedOnCareerTestCount(courseSearchkeyword);
		log.info("Extracting courses data based on job courseSearchKeywords from DB");
		List<CourseResponseDto> careerJobRelatedCourseDtos = courseDao.getRelatedCourseBasedOnCareerTest(courseSearchkeyword, 
				startIndex, pageSize);
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobRelatedCourseDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}
}
