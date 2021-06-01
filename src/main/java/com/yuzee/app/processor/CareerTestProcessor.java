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
import com.yuzee.app.dto.JobDto;
import com.yuzee.app.dto.RelatedCareerDto;
import com.yuzee.common.lib.dto.PaginationResponseDto;
import com.yuzee.common.lib.dto.PaginationUtilDto;
import com.yuzee.common.lib.dto.transaction.UserViewCourseDto;
import com.yuzee.common.lib.enumeration.EntityTypeEnum;
import com.yuzee.common.lib.enumeration.TransactionTypeEnum;
import com.yuzee.common.lib.exception.InvokeException;
import com.yuzee.common.lib.exception.NotFoundException;
import com.yuzee.common.lib.handler.ViewTransactionHandler;
import com.yuzee.common.lib.util.PaginationUtil;

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
	
	public PaginationResponseDto getCareerJobSkills(Integer pageNumber, Integer pageSize, String levelId, String jobId ) {
		log.debug("Inside getCareerJobSkills() method");
		List<CareerJobSkillDto> careerJobSkillDtos = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		log.info("Extracting career job skills from DB for levelId ", levelId);
		
		Page<CareerJobSkill> careerJobSkillsFromDB = careerTestDao.getCareerJobSkills(levelId, jobId, pageable);
		Long totalCount = 0L;
		if (!CollectionUtils.isEmpty(careerJobSkillsFromDB.getContent())) {
			totalCount = careerJobSkillsFromDB.getTotalElements();
			log.info("Career JobSkills fetched from DB, start iterating data to make final response");
			careerJobSkillsFromDB.getContent().stream().forEach(careerJobSkill -> {
				CareerJobSkillDto careerJobSkillDto = new CareerJobSkillDto(careerJobSkill.getId(),
						careerJobSkill.getSkill(), careerJobSkill.getDescription(), careerJobSkill.getCareerJobs().getId());
				careerJobSkillDtos.add(careerJobSkillDto);
			});
		}
		
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
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
		Long totalCount = 0L;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job workingStyles from DB");
			Page<CareerJobWorkingStyle> careerJobWorkingStyles = careerTestDao.getCareerJobWorkingStyle(jobIds,
					pageable);
			totalCount = careerJobWorkingStyles.getTotalElements();
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
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
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
		Long totalCount = 0L;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job Subjects from DB.");
			Page<CareerJobSubject> careerJobSubjects = careerTestDao.getCareerJobSubject(jobIds, pageable);
			totalCount = careerJobSubjects.getTotalElements();
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
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
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
		Long totalCount = 0L;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career job types from DB");
			Page<CareerJobType> careerJobTypesPage = careerTestDao.getCareerJobType(jobIds, pageable);
			totalCount = careerJobTypesPage.getTotalElements();

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
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobTypeDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return paginationResponseDto;
	}

	public PaginationResponseDto getCareerJobsByName(String userId, String name, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobs() method");
		List<JobDto> jobDtos = new ArrayList<>();
		Long totalCount = 0L;
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		
		Page<CareerJob> careerJobs = careerTestDao.getCareerJobByName(name, pageable);
		log.info("Extracting total Count of jobs from DB");
		totalCount = careerJobs.getTotalElements();
		if (!CollectionUtils.isEmpty(careerJobs.getContent())) {
			log.info("Career Jobs fetched from DB, start iterating data");
			careerJobs.getContent().stream().forEach(careerJob -> {
				jobDtos.add(new JobDto(careerJob.getId(), careerJob.getJob(), careerJob.getJobDescription()));
			});
		}
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		return new PaginationResponseDto(jobDtos, totalCount,
				paginationUtilDto.getPageNumber(), paginationUtilDto.isHasPreviousPage(),
				paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
	}
	
	public PaginationResponseDto getCareerJobs(String userId, List<String> jobIds, Integer pageNumber, Integer pageSize) {
		log.debug("Inside getCareerJobs() method");
		List<CareerJobDto> careerJobDtos = new ArrayList<>();
		
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Long totalCount = 0L;
		if (!CollectionUtils.isEmpty(jobIds)) {
			log.info("Extracting career jobs from DB");
			Page<CareerJob> careerJobs = careerTestDao.getCareerJob(jobIds, pageable);
			log.info("Extracting total Count of jobs from DB");
			totalCount = careerJobs.getTotalElements();
			if (!CollectionUtils.isEmpty(careerJobs.getContent())) {
				log.info("Career Jobs fetched from DB, start iterating data");
				careerJobs.getContent().stream().forEach(careerJob -> {
					CareerJobDto careerJobDto = modelMapper.map(careerJob, CareerJobDto.class);
					try {
						UserViewCourseDto viewTransactionDto = viewTransacationHandler.getUserViewedCourseByEntityIdAndTransactionType(userId, EntityTypeEnum.JOBS.name(),
								careerJob.getId(), TransactionTypeEnum.FAVORITE.name());
						if (!ObjectUtils.isEmpty(viewTransactionDto)) {
							careerJobDto.setFavouriteJob(true);
						}
					} catch (InvokeException e1) {
						log.error("Error invoking view transaction service",e1);
					}
					careerJobDtos.add(careerJobDto);
				});
			}
		}
		
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
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
		Long totalCount = 0L;
		if (!CollectionUtils.isEmpty(careerIds)) {
			log.info("Extracting related careers from DB");
			Page<RelatedCareer> relatedCarrersPage = careerTestDao.getRelatedCareers(careerIds, pageable);
			totalCount = relatedCarrersPage.getTotalElements();
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
		Long startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
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
		Long startIndex = Long.valueOf(pageNumber - 1);
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
				.getRelatedCourseBasedOnCareerTest(courseSearchkeyword, startIndex.intValue(), pageSize);
		log.info("Calculating pagination based on startIndex, pageSize and pageNumber");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto(careerJobRelatedCourseDtos, Long.valueOf(totalCount),
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
