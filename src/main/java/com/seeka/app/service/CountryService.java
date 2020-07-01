package com.seeka.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.seeka.app.constant.Type;
import com.seeka.app.dao.CourseDAO;
import com.seeka.app.dao.InstituteDao;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseResponseDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.NearestCoursesDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.repository.CourseRepository;
import com.seeka.app.repository.InstituteRepository;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@Service
@Transactional
@CommonsLog
public class CountryService implements ICountryService {

	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	private InstituteRepository instituteRepository;
	
	@Autowired
	private IStorageService iStorageService;
	
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private InstituteDao iInstituteDAO;
	
	@Override
	public Map<String, Object> getCourseCountry() {
		Map<String, Object> response = new HashMap<>();
		List<CountryDto> countries = new ArrayList<CountryDto>();
		try {
			countries = courseDAO.getCourseCountry();
			if (countries != null && !countries.isEmpty()) {
				response.put("status", HttpStatus.OK.value());
				response.put("message", "Country fetched successfully");
				response.put("courses", countries);
			} else {
				response.put("status", HttpStatus.NOT_FOUND.value());
				response.put("message", "Country not found");
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	@Override
	public NearestCoursesDto getCourseByCountryName(String countryName, Integer pageNumber, Integer pageSize) throws NotFoundException {
		log.debug("Inside getCourseByCountryName() method");
		Integer startIndex = PaginationUtil.getStartIndex(pageNumber, pageSize);
		List<CourseResponseDto> nearestCourseResponse = new ArrayList<>();
		log.info("fetching courses from DB for countryName "+ countryName);
		List<CourseResponseDto> nearestCourseDTOs = courseDAO.getCourseByCountryName(pageNumber, pageSize, countryName);
		Long totalCount = courseRepository.getTotalCountOfCourseByCountryName(countryName);
		if(!CollectionUtils.isEmpty(nearestCourseDTOs)) {
			log.info("get data of courses for countryName, so start iterating data");
			nearestCourseDTOs.stream().forEach(nearestCourseDTO -> {
				CourseResponseDto nearestCourse = new CourseResponseDto();
				BeanUtils.copyProperties(nearestCourseDTO, nearestCourse);
				log.info("going to fetch logo for courses from storage service for courseID "+nearestCourseDTO.getId());
				try {
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(nearestCourseDTO.getId(), 
							ImageCategory.COURSE.toString(), Type.LOGO.name(),"en");
					nearestCourse.setStorageList(storageDTOList);
				} catch (ValidationException e) {
					log.error("Error while fetching logos from storage service"+e);
				}
				nearestCourseResponse.add(nearestCourse);
			});
		}
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount.intValue());
		NearestCoursesDto nearestCoursesPaginationDto = new NearestCoursesDto();
		nearestCoursesPaginationDto.setNearestCourses(nearestCourseResponse);
		nearestCoursesPaginationDto.setPageNumber(paginationUtilDto.getPageNumber());
		nearestCoursesPaginationDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		nearestCoursesPaginationDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		nearestCoursesPaginationDto.setTotalPages(paginationUtilDto.getTotalPages());
		nearestCoursesPaginationDto.setTotalCount(totalCount.intValue());
		return nearestCoursesPaginationDto;
	}

	@Override
	public NearestInstituteDTO getInstituteByCountryName(String countryName, Integer pageNumber,Integer pageSize) throws NotFoundException {
		log.debug("Inside getInstituteByCountryName() method");
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setMaxSizePerPage(pageSize);
		log.info("fetching institutes from DB for countryName "+ countryName);
		List<InstituteResponseDto> nearestInstituteDTOs = new ArrayList<>();
		List<InstituteResponseDto> institutesFromDB = iInstituteDAO.getAllInstitutesByFilter(courseSearchDto, "countryName", 
					null, countryName, pageNumber, null, null, null, null, null, null);
		Integer totalCount = instituteRepository.getTotalCountOfInstituteByCountryName(countryName);
		if(!CollectionUtils.isEmpty(institutesFromDB)) {
			log.info("institutes found in DB for countryName "+ countryName + " so start iterating data");
			institutesFromDB.stream().forEach(institute -> {
				InstituteResponseDto nearestInstitute = new InstituteResponseDto();
				BeanUtils.copyProperties(institute, nearestInstitute);
				log.info("going to fetch institute logo from storage service having instituteID "+institute.getId());
				try {
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(institute.getId(), 
							ImageCategory.INSTITUTE.toString(), Type.LOGO.name(),"en");
					nearestInstitute.setStorageList(storageDTOList);
				} catch (ValidationException e) {
					log.error("Error while fetching logos from storage service"+e);
				}
				nearestInstituteDTOs.add(nearestInstitute);
			});
		}
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(pageNumber, pageSize, totalCount);
		NearestInstituteDTO institutePaginationResponseDto = new NearestInstituteDTO();
		institutePaginationResponseDto.setNearestInstitutes(nearestInstituteDTOs);
		institutePaginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		institutePaginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		institutePaginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		institutePaginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		institutePaginationResponseDto.setTotalCount(totalCount);
		return institutePaginationResponseDto;
	}
}
