package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.CareerJob;
import com.yuzee.app.bean.CareerJobCourseSearchKeyword;
import com.yuzee.app.bean.CareerJobSkill;
import com.yuzee.app.bean.CareerJobSubject;
import com.yuzee.app.bean.CareerJobType;
import com.yuzee.app.bean.CareerJobWorkingStyle;
import com.yuzee.app.bean.RelatedCareer;
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
import com.yuzee.app.dto.RelatedCareerDto;
import com.yuzee.app.dto.UserViewCourseDto;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.enumeration.TransactionTypeEnum;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.handler.ViewTransactionHandler;
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

	@Autowired
	private ViewTransactionHandler viewTransacationHandler;
	
	@Autowired
	private ModelMapper modelMapper;

	public PaginationResponseDto getCareerJobSkills(String levelId, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobSkills() method");
		List<CareerJobSkillDto> careerJobSkillDtos = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		log.info("Extracting career job skills from DB for levelId ", levelId);
		
		Page<CareerJobSkill> careerJobSkillsFromDB = careerTestDao.getCareerJobSkills(levelId, pageable);
		int totalCount = 0;
		if (!CollectionUtils.isEmpty(careerJobSkillsFromDB.getContent())) {
			totalCount = ((Long) careerJobSkillsFromDB.getTotalElements()).intValue();
			log.info("Career JobSkills fetched from DB, start iterating data to make final response");
			careerJobSkillsFromDB.getContent().stream().forEach(careerJobSkill -> {
				CareerJobSkillDto careerJobSkillDto = new CareerJobSkillDto(careerJobSkill.getId(),
						careerJobSkill.getSkill(), careerJobSkill.getCareerJobs().getId());
				careerJobSkillDtos.add(careerJobSkillDto);
			});
		}
		
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobSkillDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}

	public PaginationResponseDto getCareerJobWorkingStyles(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobWorkingStyles() method");
		List<CareerJobWorkingStyleDto> careerJobWorkingStyleDtos = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job workingStyles from DB");
			Page<CareerJobWorkingStyle> careerJobWorkingStyles = careerTestDao.getCareerJobWorkingStyle(jobIds,
					pageable);
			totalCount = ((Long) careerJobWorkingStyles.getTotalElements()).intValue();
			if (!CollectionUtils.isEmpty(careerJobWorkingStyles.getContent())) {
				log.info("Career Working Style fetched from DB, start iterating data");
				careerJobWorkingStyles.getContent().stream().forEach(careerJobWorkingStyle -> {
					log.info("Start adding values in DTO class");
					CareerJobWorkingStyleDto careerJobWorkingStyleDto = new CareerJobWorkingStyleDto(
							careerJobWorkingStyle.getId(), careerJobWorkingStyle.getWorkStyle(),
							careerJobWorkingStyle.getCareerJobs().getId());
					careerJobWorkingStyleDtos.add(careerJobWorkingStyleDto);
				});
			}
		}
		
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobWorkingStyleDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}

	public PaginationResponseDto getCareerJobSubjects(List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobSubjects() method");
		List<CareerJobSubjectDto> careerJobSubjectDtos = new ArrayList<>();

		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job Subjects from DB.");
			Page<CareerJobSubject> careerJobSubjects = careerTestDao.getCareerJobSubject(jobIds, pageable);
			totalCount = ((Long) careerJobSubjects.getTotalElements()).intValue();
			if (!CollectionUtils.isEmpty(careerJobSubjects.getContent())) {
				log.info("Career JobSubjects fetched from DB, start iterating data");
				careerJobSubjects.getContent().stream().forEach(careerJobSubject -> {
					log.info("Start adding values in DTO class");
					CareerJobSubjectDto careerJobSubjectDto = new CareerJobSubjectDto(careerJobSubject.getId(),
							careerJobSubject.getSubject(), careerJobSubject.getCareerJobs().getId());
					careerJobSubjectDtos.add(careerJobSubjectDto);
				});
			}
		}
		
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
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
			totalCount = ((Long) careerJobTypesPage.getTotalElements()).intValue();

			List<CareerJobType> careerJobTypes = careerJobTypesPage.getContent();
			if (!CollectionUtils.isEmpty(careerJobTypes)) {
				log.info("Career Job Types fetched from DB, start iterating data");
				careerJobTypes.stream().forEach(careerJob -> {

					List<String> careerjobIds = careerTestDao.getCareerJobIdsByJobTypeId(careerJob.getId()).stream()
							.map(e -> e.getJobId()).collect(Collectors.toList());

					CareerJobTypeDto careerJobTypeDto = new CareerJobTypeDto(careerJob.getId(), careerJob.getJobType(),
							careerjobIds);
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

	public PaginationResponseDto getCareerJobs(String userId, List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobs() method");
		List<CareerJobDto> careerJobDtos = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career jobs from DB");
			Page<CareerJob> careerJobs = careerTestDao.getCareerJob(jobIds, pageable);
			log.info("Extracting total Count of jobs from DB");
			totalCount = ((Long) careerJobs.getTotalElements()).intValue();
			if (!CollectionUtils.isEmpty(careerJobs.getContent())) {
				log.info("Career Jobs fetched from DB, start iterating data");
				careerJobs.getContent().stream().forEach(careerJob -> {
					CareerJobDto careerJobDto = modelMapper.map(careerJob, CareerJobDto.class);
					try {
						UserViewCourseDto viewTransactionDto = viewTransacationHandler.getUserViewedCourseByEntityIdAndTransactionType(userId, EntityTypeEnum.CAREER_JOB.name(),
								careerJob.getId(), TransactionTypeEnum.FAVORITE.name());
						if (!ObjectUtils.isEmpty(viewTransactionDto)) {
							careerJobDto.setFavouriteJob(true);
						}
					} catch (InvokeException e1) {
						log.error("Error invoking view transaction service");
					}
					careerJobDtos.add(careerJobDto);
				});
			}
		}
		
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}

	public PaginationResponseDto getRealtedCareers(List<String> careerIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getRealtedCareers() method");
		List<RelatedCareerDto> realtedCareerDtos = new ArrayList<>();
		
		log.info("Calculating Pageable size based on pageNumber and pageSize");
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
		Integer totalCount = 0;
		if (!CollectionUtils.isEmpty(careerIds)) {
			log.info("Extracting related careers from DB");
			Page<RelatedCareer> relatedCarrersPage = careerTestDao.getRelatedCareers(careerIds, pageable);
			totalCount = ((Long) relatedCarrersPage.getTotalElements()).intValue();
			List<RelatedCareer> realtedCarrers = relatedCarrersPage.getContent();
			if (!CollectionUtils.isEmpty(realtedCarrers)) {
				log.info("Career Jobs fetched from DB, start iterating data");
				realtedCarrers.stream().forEach(careerJob -> {
					RelatedCareerDto careerJobDto = new RelatedCareerDto(careerJob.getId(),
							careerJob.getRelatedCareer(), careerJob.getCareers().getId());
					realtedCareerDtos.add(careerJobDto);
				});
			}
		}
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		int startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(realtedCareerDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}

	public PaginationResponseDto getRelatedCourseBasedOnCareerTest(List<String> jobIds, Integer pageNumber,
			Integer pageSize) {
		log.debug("Inside getRelatedCourseBasedOnCareerTest() method");
		List<String> courseSearchkeyword = new ArrayList<>();
		log.info("Extracting job courseSearchKeyword from DB to match possible courses");
		List<CareerJobCourseSearchKeyword> careerJobCourseSearchKeywords = careerTestDao
				.getCareerJobCourseSearchKeyword(jobIds);
		int startIndex = pageNumber - 1;
		log.info("Calculating Pageable size based on pageNumber nad pageSize");

		if (!CollectionUtils.isEmpty(careerJobCourseSearchKeywords)) {
			log.info("Career courseSearchKeyword fetched, iterating data to extract search keywords");
			careerJobCourseSearchKeywords.stream().forEach(careerJobCourseSearchKeyword -> {
				log.info("Adding searchKeyword in list, to fetch all possible course having searchKeyword from DB");
				courseSearchkeyword.add(careerJobCourseSearchKeyword.getCourseSearchKeyword());
			});
		}
		log.info("Extracting total count of courses based on courseSearchKeywords");
		Integer totalCount = courseDao.getRelatedCourseBasedOnCareerTestCount(courseSearchkeyword);
		log.info("Extracting courses data based on job courseSearchKeywords from DB");
		List<CourseResponseDto> careerJobRelatedCourseDtos = courseDao
				.getRelatedCourseBasedOnCareerTest(courseSearchkeyword, startIndex, pageSize);
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobRelatedCourseDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}

	public CareerJobDto getCareerJobById(String jobId) throws NotFoundException {
		Optional<CareerJob> careerJob = careerTestDao.getCareerJob(jobId);
		if (!careerJob.isPresent()) {
			log.debug("Career Job not found with id", jobId);
			throw new NotFoundException("Career Job not found with id" + jobId);
		}
		return modelMapper.map(careerJob.get(), CareerJobDto.class);
	}
}
