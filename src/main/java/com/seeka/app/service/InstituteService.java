package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.bean.AccrediatedDetail;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteDomesticRankingHistory;
import com.seeka.app.bean.InstituteIntake;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.constant.Type;
import com.seeka.app.dao.AccrediatedDetailDao;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.InstituteDAO;
import com.seeka.app.dao.InstituteDomesticRankingHistoryDAO;
import com.seeka.app.dao.InstituteWorldRankingHistoryDAO;
import com.seeka.app.dao.ServiceDetailsDAO;
import com.seeka.app.dto.AccrediatedDetailDto;
import com.seeka.app.dto.AdvanceSearchDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ElasticSearchDTO;
import com.seeka.app.dto.InstituteDomesticRankingHistoryDto;
import com.seeka.app.dto.InstituteElasticSearchDTO;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteGetRequestDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.LatLongDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.dto.PaginationResponseDto;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.enumeration.SeekaEntityType;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.repository.InstituteRepository;
import com.seeka.app.util.CDNServerUtil;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

import lombok.extern.apachecommons.CommonsLog;

@Service
@Transactional
@CommonsLog
public class InstituteService implements IInstituteService {

	@Autowired
	private InstituteDAO dao;

	@Autowired
	private InstituteWorldRankingHistoryDAO institudeWorldRankingHistoryDAO;

	@Autowired
	private InstituteDomesticRankingHistoryDAO instituteDomesticRankingHistoryDAO;

	@Autowired
	private ServiceDetailsDAO serviceDetailsDAO;

	@Autowired
	private IStorageService iStorageService;

	@Value("${s3.url}")
	private String s3URL;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ICourseDAO courseDao;

	@Autowired
	private ElasticSearchService elasticSearchService;

//	@Autowired
//	private IFacultyService iFacultyService;
//
//	@Autowired
//	private LevelService levelService;

	@Autowired
	private AccrediatedDetailDao accrediatedDetailDao;
	
	@Value("${min.radius}")
	private Integer minRadius;
	
	@Value("${max.radius}")
	private Integer maxRadius;
	
	@Autowired
	private InstituteRepository instituteRepository;
	
	@Override
	public void save(final Institute institute) {
		Date today = new Date();
		institute.setCreatedOn(today);
		institute.setUpdatedOn(today);
		dao.save(institute);
	}

	@Override
	public void update(final Institute institute) {
		Date today = new Date();
		institute.setUpdatedOn(today);
		dao.update(institute);
	}

	@Override
	public Institute get(final String id) {
		return dao.get(id);
	}

	@Override
	public List<String> getTopInstituteIdByCountry(final String countryId/* , Long startIndex, Long pageSize */) {
		return dao.getTopInstituteByCountry(countryId/* , startIndex, pageSize */);
	}

	@Override
	public List<String> getRandomInstituteIdByCountry(final List<String> countryIdList/* , Long startIndex, Long pageSize */) {
		return dao.getRandomInstituteByCountry(countryIdList/* , startIndex, pageSize */);
	}

	@Override
	public List<Institute> getAll() {
		return dao.getInstituteCampusWithInstitue();
	}

	@Override
	public List<InstituteSearchResultDto> getInstitueBySearchKey(final String searchKey) {
		return dao.getInstitueBySearchKey(searchKey);
	}

	@Override
	public List<InstituteResponseDto> getAllInstitutesByFilter(final CourseSearchDto filterObj, final String sortByField, final String sortByType,
			final String searchKeyword, final Integer startIndex, final String cityId, final String instituteTypeId, final Boolean isActive,
			final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking) {
		return dao.getAllInstitutesByFilter(filterObj, sortByField, sortByType, searchKeyword, startIndex, cityId, instituteTypeId, isActive, updatedOn,
				fromWorldRanking, toWorldRanking);
	}

	@Override
	public InstituteResponseDto getInstituteByID(final String instituteId) {
		return dao.getInstituteByID(instituteId);
	}

	@Override
	public List<InstituteResponseDto> getAllInstituteByID(final Collection<String> listInstituteId) throws ValidationException {
		List<Institute> inistituteList = dao.getAllInstituteByID(listInstituteId);
		List<InstituteResponseDto> instituteResponseDTOList = new ArrayList<>();
		for (Institute institute : inistituteList) {
			InstituteResponseDto instituteResponseDTO = new InstituteResponseDto();
			BeanUtils.copyProperties(institute, instituteResponseDTO);
			if (institute.getCountryName() != null) {
				instituteResponseDTO.setCountryName(institute.getCountryName());
			}
			if (institute.getCityName() != null) {
				instituteResponseDTO.setCityName(institute.getCityName());
			}
			instituteResponseDTO.setWorldRanking(institute.getWorldRanking());
			instituteResponseDTO.setLocation(institute.getLatitude() + "," + institute.getLongitude());
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteResponseDTO.getId(), ImageCategory.INSTITUTE.toString(), null, "en");
			instituteResponseDTO.setStorageList(storageDTOList);
			instituteResponseDTOList.add(instituteResponseDTO);
		}
		return instituteResponseDTOList;
	}

	@Override
	public List<InstituteResponseDto> getInstitudeByCityId(final String cityId) {
		log.debug("Inside getInstitudeByCityId() method");
		List<InstituteResponseDto> instituteResponseDtos = new ArrayList<>();
		log.info("fetching institutes from DB having cityName = "+ cityId);
		List<Institute> institutes = instituteRepository.findByCityName(cityId);
		if(!CollectionUtils.isEmpty(institutes)) {
			
			institutes.stream().forEach(institute -> {
				InstituteResponseDto instituteResponseDto = new InstituteResponseDto();
				log.info("Converting bean class to DTO class using beanUtils");
				BeanUtils.copyProperties(institute, instituteResponseDto);
				instituteResponseDtos.add(instituteResponseDto);
			});
		}
		return instituteResponseDtos;
	}

	@Override
	public List<InstituteResponseDto> getInstituteByListOfCityId(final String cityId) {
		String[] citiesArray = cityId.split(",");
		String tempList = "";
		for (String id : citiesArray) {
			tempList = tempList + "," + "'" + id + "'";
		}
		return dao.getInstituteByListOfCityId(tempList.substring(1, tempList.length()));
	}

	@Override
	public void saveInstitute(final List<InstituteRequestDto> instituteRequests) {
		log.debug("Inside save() method");
		try {
			List<InstituteElasticSearchDTO> instituteElasticDtoList = new ArrayList<>();
			log.info("start iterating data coming in request");
			for (InstituteRequestDto instituteRequest : instituteRequests) {
				log.info("Going to save institute in DB");
				Institute institute = saveInstitute(instituteRequest, null);
				InstituteElasticSearchDTO instituteElasticSearchDto = new InstituteElasticSearchDTO();
				if (instituteRequest.getDomesticRanking() != null) {
					log.info("if domesticRanking is not null then going to record domesticRankingHistory");
					saveDomesticRankingHistory(institute, null);
				}
				if (instituteRequest.getWorldRanking() != null) {
					log.info("if worldRanking is not null then going to record worldRankingHistory");
					saveWorldRankingHistory(institute, null);
				}
				log.info("Copying institute data from instituteBean to elasticSearchDTO");
				BeanUtils.copyProperties(institute, instituteElasticSearchDto);
				instituteElasticSearchDto.setCountryName(institute.getCountryName() != null ? institute.getCountryName() : null);
				instituteElasticSearchDto.setCityName(institute.getCityName() != null ? institute.getCityName() : null);
				instituteElasticSearchDto.setInstituteTypeName(institute.getInstituteType() != null ? institute.getInstituteType() : null);
				instituteElasticSearchDto.setIntakes(instituteRequest.getIntakes());
				instituteElasticDtoList.add(instituteElasticSearchDto);
			}
			log.info("Calling elasticSearch Service to add new institutes on elastic index");
			elasticSearchService.saveInsituteOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_INSTITUTE, SeekaEntityType.INSTITUTE.name().toLowerCase(),
					instituteElasticDtoList, IConstant.ELASTIC_SEARCH);
		} catch (Exception exception) {
			log.error("Exception while saving institutes having exception = "+exception);
		}
	}

	private void saveWorldRankingHistory(final Institute institute, final Institute oldInstitute) {
		log.debug("Inside saveWorldRankingHistory() method");
		InstituteWorldRankingHistory worldRanking = new InstituteWorldRankingHistory();
		worldRanking.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		worldRanking.setCreatedBy(institute.getCreatedBy());
		worldRanking.setWorldRanking(institute.getWorldRanking());
		if (oldInstitute != null) {
			log.info("Saving worldRanking for already existing institute having instituteId = "+ oldInstitute.getId());
			worldRanking.setInstitute(oldInstitute);
		} else {
			log.info("Saving worldRanking for new institute having instituteId = "+ institute.getId());
			worldRanking.setInstitute(institute);
		}
		log.info("Calling DAO layer to save world ranking history in DB");
		institudeWorldRankingHistoryDAO.save(worldRanking);

	}

	private void saveDomesticRankingHistory(final Institute institute, final Institute oldInstitute) {
		log.debug("Inside saveDomesticRankingHistory() method");
		InstituteDomesticRankingHistory domesticRanking = new InstituteDomesticRankingHistory();
		domesticRanking.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		domesticRanking.setCreatedBy(institute.getCreatedBy());
		domesticRanking.setDomesticRanking(institute.getDomesticRanking());
		if (oldInstitute != null) {
			log.info("Saving domesticRanking for already existing institute having instituteId = "+ oldInstitute.getId());
			domesticRanking.setInstitute(oldInstitute);
		} else {
			log.info("Saving domesticRanking for new institute having instituteId = "+ institute.getId());
			domesticRanking.setInstitute(institute);
		}
		log.info("Calling DAO layer to save domestic ranking history in DB");
		instituteDomesticRankingHistoryDAO.save(domesticRanking);
	}

	@Override
	public void updateInstitute(final List<InstituteRequestDto> instituteRequests, @Valid final String id) {
		log.debug("Inside updateInstitute() method");
		try {
			List<InstituteElasticSearchDTO> instituteElasticDtoList = new ArrayList<>();
			log.info("Start iterating institute coming in request");
			for (InstituteRequestDto instituteRequest : instituteRequests) {
				log.info("fetching institute from DB having instituteId = "+id);
				Institute oldInstitute = dao.get(id);
				Institute newInstitute = new Institute();
				InstituteElasticSearchDTO instituteElasticSearchDto = new InstituteElasticSearchDTO();
				log.info("Copying bean class to DTO class");
				BeanUtils.copyProperties(instituteRequest, newInstitute);
				if (instituteRequest.getDomesticRanking() != null && !instituteRequest.getDomesticRanking().equals(oldInstitute.getDomesticRanking())) {
					log.info("DomesticRanking is not null hence saving domesticRanking History");
					saveDomesticRankingHistory(newInstitute, oldInstitute);
				}
				if (instituteRequest.getWorldRanking() != null && !instituteRequest.getWorldRanking().equals(oldInstitute.getWorldRanking())) {
					log.info("WorldRanking is not null hence saving worldRanking History");
					saveWorldRankingHistory(newInstitute, oldInstitute);
				}
				log.info("Start updating institute for instituteId ="+id);
				Institute institute = saveInstitute(instituteRequest, id);
				/*if (instituteRequest.getInstituteMedias() != null && !instituteRequest.getInstituteMedias().isEmpty()) {
					saveInstituteYoutubeVideos(instituteRequest.getInstituteMedias(), institute);
				}*/
				/*if (instituteRequest.getFacultyIds() != null && !instituteRequest.getFacultyIds().isEmpty()) {
					Map<String, String> facultyIdNameMap = saveFacultyLevel(institute, instituteRequest.getFacultyIds());
					List<String> facultyNames = (List<String>) facultyIdNameMap.values();
					instituteElasticSearchDto.setFacultyNames(facultyNames);
				}*/
				/*if (instituteRequest.getLevelIds() != null && !instituteRequest.getLevelIds().isEmpty()) {
					Map<String, String> levelNameLevelCodeMap = saveInstituteLevel(institute, instituteRequest.getLevelIds());
					instituteElasticSearchDto.setLevelName(new ArrayList<>(levelNameLevelCodeMap.keySet()));
					instituteElasticSearchDto.setLevelCode(new ArrayList<>(levelNameLevelCodeMap.values()));
				}*/
				log.info("Copying DTO class to elasticSearch DTO");
				BeanUtils.copyProperties(instituteRequest, instituteElasticSearchDto);
				instituteElasticSearchDto.setCountryName(institute.getCountryName() != null ? institute.getCountryName() : null);
				instituteElasticSearchDto.setCityName(institute.getCityName() != null ? institute.getCityName() : null);
				instituteElasticSearchDto.setInstituteTypeName(institute.getInstituteType() != null ? institute.getInstituteType() : null);
				instituteElasticSearchDto.setIntakes(instituteRequest.getIntakes());
				instituteElasticDtoList.add(instituteElasticSearchDto);
			}
			log.info("Calling elastic service to save instiutes on index");
			elasticSearchService.updateInsituteOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_INSTITUTE, SeekaEntityType.INSTITUTE.name().toLowerCase(),
					instituteElasticDtoList, IConstant.ELASTIC_SEARCH);
		} catch (Exception exception) {
			log.error("Exception while updating institute having exception ="+exception);
		}
	}

	private Institute saveInstitute(@Valid final InstituteRequestDto instituteRequest, final String id) throws ValidationException {
		log.debug("Inside saveInstitute() method");
		Institute institute = null;
		if (id != null) {
			log.info("if instituteId in not null, then getInstitute from DB for id = "+id);
			institute = dao.get(id);
		} else {
			log.info("instituteId is null, creating object and setting values in it");
			institute = new Institute();
			institute.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			institute.setCreatedBy(instituteRequest.getCreatedBy());
		}
		institute.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		institute.setUpdatedBy(instituteRequest.getUpdatedBy());
		institute.setName(instituteRequest.getName());
		institute.setDescription(instituteRequest.getDescription());
		if (instituteRequest.getCountryName() != null) {
			institute.setCountryName(instituteRequest.getCountryName());
		} else {
			log.error("CountryName is required");
			throw new ValidationException("countryName is required");
		}
		if (instituteRequest.getCityName() != null) {
			institute.setCityName(instituteRequest.getCityName());
		} else {
			log.error("CityName is required");
			throw new ValidationException("cityName is required");
		}

		if (instituteRequest.getInstituteType() != null) {
			 institute.setInstituteType(instituteRequest.getInstituteType());
		} else {
			log.error("InstituteType is required like University, School, College etc");
			throw new ValidationException("InstituteType is required like University, School, College etc");
		}

		institute.setIsActive(true);
		institute.setDomesticRanking(instituteRequest.getDomesticRanking());
		institute.setWorldRanking(instituteRequest.getWorldRanking());
		institute.setWebsite(instituteRequest.getWebsite());
		if (instituteRequest.getInstituteCategoryTypeId() != null) {
			institute.setInstituteCategoryType(getInstituteCategoryType(instituteRequest.getInstituteCategoryTypeId()));
		} else {
			log.error("instituteCategoryTypeId is required");
			throw new ValidationException("instituteCategoryTypeId is required");
		}

		institute.setAddress(instituteRequest.getAddress());
		institute.setEmail(instituteRequest.getEmail());
		institute.setPhoneNumber(instituteRequest.getPhoneNumber());
		institute.setLatitude(instituteRequest.getLatitude());
		institute.setLongitude(instituteRequest.getLongitude());
		institute.setTotalStudent(instituteRequest.getTotalStudent());
		institute.setOpeningFrom(instituteRequest.getOpeningFrom());
		institute.setOpeningTo(instituteRequest.getOpeningTo());
		institute.setCampusName(instituteRequest.getCampusName());
		institute.setEnrolmentLink(instituteRequest.getEnrolmentLink());
		institute.setTuitionFessPaymentPlan(instituteRequest.getTuitionFessPaymentPlan());
		institute.setScholarshipFinancingAssistance(instituteRequest.getScholarshipFinancingAssistance());
		institute.setAvgCostOfLiving(instituteRequest.getAvgCostOfLiving());
		institute.setWhatsNo(instituteRequest.getWhatsNo());
		institute.setAboutInfo(instituteRequest.getAboutInfo());
		institute.setCourseStart(instituteRequest.getCourseStart());
		if (id != null) {
			log.info("if instituteId is not null then going to update institute for id ="+id);
			dao.update(institute);
			// Adding data in Elastic DTO to save it on elasticSearch index
			log.info("updation is done now adding data in elasticSearch DTO to add it on elastic indices for entityId = "+id);
			InstituteElasticSearchDTO instituteElasticDto = new InstituteElasticSearchDTO();
			List<InstituteElasticSearchDTO> instituteElasticDTOList = new ArrayList<>();
			BeanUtils.copyProperties(institute, instituteElasticDto);
			instituteElasticDTOList.add(instituteElasticDto);
		} else {
			log.info("instituteId is null hence adding new institute in DB");
			dao.save(institute);
		}
		if (instituteRequest.getOfferService() != null && !instituteRequest.getOfferService().isEmpty()) {
			log.info("offer service is not null hence going to save institute service in DB");
			saveInstituteService(institute, instituteRequest.getOfferService());
		}
		if (instituteRequest.getAccreditation() != null && !instituteRequest.getAccreditation().isEmpty()) {
			log.info("accrediation detail is not null hence going to save institute accrediation details in DB");
			saveAccreditedInstituteDetails(institute, instituteRequest.getAccreditationDetails());
		}
		if (instituteRequest.getIntakes() != null && !instituteRequest.getIntakes().isEmpty()) {
			log.info("intakes is not null hence going to save institute intakes in DB");
			saveIntakesInstituteDetails(institute, instituteRequest.getIntakes());
		}
		return institute;
	}

	private void saveIntakesInstituteDetails(final Institute institute, final List<String> intakes) {
		log.debug("Inside saveIntakesInstituteDetails() method");
		log.info("deleting existing instituteIntakes from DB for institute having instituteId = "+institute.getId());
		dao.deleteInstituteIntakeById(institute.getId());
		for (String intakeId : intakes) {
			log.info("Start iterating new intakes which are coming in request");
			InstituteIntake instituteIntake = new InstituteIntake();
			instituteIntake.setInstitute(institute);
			instituteIntake.setIntake(intakeId);
			log.info("Calling DAO layer to save new intakes in DB for institute having instituteId ="+institute.getId());
			dao.saveInstituteIntake(instituteIntake);
		}
	}

	private void saveAccreditedInstituteDetails(final Institute institute, final List<AccrediatedDetailDto> accreditation) {
		log.debug("Inside saveAccreditedInstituteDetails() method");
		log.info("deleting existing accrediatedDetails from DB for institute having instituteId = "+institute.getId());
		accrediatedDetailDao.deleteAccrediationDetailByEntityId(institute.getId());
		if(!CollectionUtils.isEmpty(accreditation)) {
			log.info("Start iterating new accrediation coming in request, if it is not null");
			for (AccrediatedDetailDto accreditedInstituteDetail2 : accreditation) {
				AccrediatedDetail accreditedInstituteDetail = new AccrediatedDetail();
				accreditedInstituteDetail.setEntityId(institute.getId());
				accreditedInstituteDetail.setEntityType("INSTITUTE");
				accreditedInstituteDetail.setAccrediatedName(accreditedInstituteDetail2.getAccrediatedName());
				accreditedInstituteDetail.setAccrediatedWebsite(accreditedInstituteDetail2.getAccrediatedWebsite());
				accreditedInstituteDetail.setCreatedBy("API");
				accreditedInstituteDetail.setCreatedOn(new Date());
				log.info("Calling DAO layer to save accrediatedDetails in DB for institute ="+institute.getId());
				accrediatedDetailDao.addAccrediatedDetail(accreditedInstituteDetail);
			}
		}
	}

	private void saveInstituteService(final Institute institute, final List<String> offerService) {
		log.debug("Inside saveInstituteService() method");
		log.info("deleting existing instituteService from DB having instituteId = "+institute.getId());
		dao.deleteInstituteService(institute.getId());
		log.info("start iterating new offerServices coming in request for institute");
		for (String id : offerService) {
			log.info("fetching service from DB having by serviceId coming in request = "+id);
			com.seeka.app.bean.Service service = serviceDetailsDAO.get(id);
			com.seeka.app.bean.InstituteService instituteServiceDetails = new com.seeka.app.bean.InstituteService();
			instituteServiceDetails.setInstitute(institute);
			instituteServiceDetails.setServiceName(service.getName());
			instituteServiceDetails.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			instituteServiceDetails.setIsActive(true);
			instituteServiceDetails.setCreatedBy("AUTO");
			instituteServiceDetails.setUpdatedBy("AUTO");
			instituteServiceDetails.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
			log.info("Calling DAO layer to save institute service in DB");
			dao.saveInstituteserviceDetails(instituteServiceDetails);
		}
	}

	private InstituteCategoryType getInstituteCategoryType(final String instituteCategoryTypeId) {
		return dao.getInstituteCategoryType(instituteCategoryTypeId);
	}

	@Override
	public PaginationResponseDto getAllInstitute(final Integer pageNumber, final Integer pageSize) {
		log.debug("Inside getAllInstitute() method");
		PaginationResponseDto paginationResponseDto = new PaginationResponseDto();
		try {
			log.info("Fetching total count of institutes from DB");
			int totalCount = dao.findTotalCount();
			int startIndex;
			log.info("Calculating startIndex based of pageNumber nad pageSize");
			if (pageNumber > 1) {
				startIndex = (pageNumber - 1) * pageSize + 1;
			} else {
				startIndex = pageNumber;
			}
			log.info("Calculating pagination based on startIndex, pageSize and totalCount");
			PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			log.info("Fetching all institutes from DB based on pagination");
			List<InstituteGetRequestDto> institutes = dao.getAll(startIndex, pageSize);
			List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
			if(!CollectionUtils.isEmpty(institutes)) {
				log.info("Institues are not null from DB so start iterating data");
				institutes.stream().forEach(institute -> {
					log.info("start getting institute details");
					InstituteGetRequestDto instituteGetRequestDto = new InstituteGetRequestDto();
					log.info("copying bean class to DTO and fetching institute videos based on countryName and instituteName");
					BeanUtils.copyProperties(institute, instituteGetRequestDto);
					instituteGetRequestDto.setInstituteYoutubes(getInstituteYoutube(institute.getCountryName(), institute.getName()));
					instituteGetRequestDtos.add(instituteGetRequestDto);
				});
			}
			log.info("setting values into pagination DTO with data and pagination value");
			paginationResponseDto.setInstitutes(instituteGetRequestDtos);
			paginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
			paginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
			paginationResponseDto.setTotalCount(totalCount);
			paginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
			paginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		} catch (Exception exception) {
			log.error("Exception while fetching all institutes from DB having exception = "+exception);
		}
		return paginationResponseDto;
	}

	private InstituteGetRequestDto getInstitute(final Institute institute) {
		log.debug("Inside getInstitute() method");
		InstituteGetRequestDto dto = new InstituteGetRequestDto();
		log.info("Converting bean class response into DTO class response");
		dto.setId(institute.getId());
		dto.setCityName(institute.getCityName());
		dto.setCountryName(institute.getCountryName());
		dto.setInstituteType(institute.getInstituteType());
		dto.setName(institute.getName());
		log.info("fetching institute details from DB for instituteId = "+institute.getId());
//		dto.setInstituteDetails(getInstituteDetails(institute.getId()));
		log.info("fetching institute videos from DB having countryName = "+institute.getCountryName() + " and instituteName = "+institute.getName());
		dto.setInstituteYoutubes(getInstituteYoutube(institute.getCountryName(), institute.getName()));
		log.info("Get total course count from DB for instituteId = "+institute.getId());
		dto.setCourseCount(dao.getCourseCount(institute.getId()));
		return dto;
	}

	private List<String> getInstituteYoutube(final String countryName, final String instituteName) {
		log.debug("Inside getInstituteYoutube() method");
		List<String> images = new ArrayList<>();
		if (countryName != null) {
			log.info("if countryName is not null then adding images in list");
			for (int i = 1; i <= 20; i++) {
				images.add(CDNServerUtil.getInstituteImages(countryName, instituteName, i));
			}
		}
		return images;
	}

	/*private List<InstituteDetailsGetRequest> getInstituteDetails(final String id) {
		log.debug("Inside getInstituteDetails() method");
		List<InstituteDetailsGetRequest> instituteDetailsGetRequests = new ArrayList<>();
		log.info("fetching institutes from DB having instituteId = "+id);
		Institute dto = dao.get(id);
		InstituteDetailsGetRequest instituteDetail = new InstituteDetailsGetRequest();
		if (dto.getLatitude() != null) {
			instituteDetail.setLatitude(String.valueOf(dto.getLatitude()));
		}
		if (dto.getLongitude() != null) {
			instituteDetail.setLongitude(String.valueOf(dto.getLongitude()));
		}
		instituteDetail.setTotalStudent(dto.getTotalStudent());
		instituteDetail.setWorldRanking(dto.getWorldRanking());
		instituteDetail.setEmail(dto.getEmail());
		instituteDetail.setPhoneNumber(dto.getPhoneNumber());
		instituteDetail.setWebsite(dto.getWebsite());
		instituteDetail.setAddress(dto.getAddress());
		instituteDetail.setAvgCostOfLiving(dto.getAvgCostOfLiving());
		instituteDetailsGetRequests.add(instituteDetail);
		return instituteDetailsGetRequests;
	}*/

	@Override
	public List<InstituteRequestDto> getById(final String id) throws ValidationException {
		log.debug("Inside getById() method");
		InstituteRequestDto instituteRequestDto = null;
		List<InstituteRequestDto> instituteRequestDtos = new ArrayList<>();
		log.info("Fetching institute from DB for instituteId ="+id);
		Institute institute = dao.get(id);
		if (institute == null) {
			log.error("No institute found in DB for instituteId =" +id);
			throw new ValidationException("Institute not found for id" + id);
		}
		log.info("Converting bean to request DTO class");
		instituteRequestDto = CommonUtil.convertInstituteBeanToInstituteRequestDto(institute);
		log.info("fetching institute services from DB fro instituteID = "+id);
		instituteRequestDto.setOfferService(getOfferServices(id));
		instituteRequestDto.setOfferServiceName(getOfferServiceNames(id));
		log.info("fetching accrediation Details from DB fro instituteID = "+id);
		instituteRequestDto.setAccreditationDetails(getAccreditationName(id));
		log.info("fetching institute intakes from DB fro instituteID = "+id);
		instituteRequestDto.setIntakes(getIntakes(id));
//		instituteRequestDto.setFacultyIds(getFacultyLevelData(id));
//		instituteRequestDto.setFacultyNames(getFacultyByInstituteId(id));
//		instituteRequestDto.setLevelIds(getInstituteLevelData(id));
//		instituteRequestDto.setLevelNames(getInstituteLevelName(id));
		
		if (institute.getInstituteCategoryType() != null) {
			log.info("Adding institute category type in final Response");
			instituteRequestDto.setInstituteCategoryTypeId(institute.getInstituteCategoryType().getId());
		}
		instituteRequestDtos.add(instituteRequestDto);
		return instituteRequestDtos;
	}

	/*private List<String> getFacultyByInstituteId(final String id) {
		return iFacultyService.getFacultyNameByInstituteId(id);
	}

	private List<String> getInstituteLevelName(final String id) {
		return levelService.getAllLevelNameByInstituteId(id);
	}*/

	private List<String> getIntakes(@Valid final String id) {
		return dao.getIntakesById(id);
	}
	
	private List<AccrediatedDetailDto> getAccreditationName(@Valid final String id) {
		List<AccrediatedDetailDto> accrediatedDetailDtos = new ArrayList<>();
		List<AccrediatedDetail> accrediatedDetails = accrediatedDetailDao.getAccrediationDetailByEntityId(id);
		accrediatedDetails.stream().forEach(accrediatedDetail -> {
			AccrediatedDetailDto accrediatedDetailDto = new AccrediatedDetailDto();
			accrediatedDetailDto.setId(accrediatedDetail.getId());
			accrediatedDetailDto.setEntityId(accrediatedDetail.getEntityId());
			accrediatedDetailDto.setEntityType(accrediatedDetail.getEntityType());
			accrediatedDetailDto.setAccrediatedName(accrediatedDetail.getAccrediatedName());
			accrediatedDetailDto.setAccrediatedWebsite(accrediatedDetail.getAccrediatedWebsite());
			accrediatedDetailDtos.add(accrediatedDetailDto);
		});
		return accrediatedDetailDtos;
	}

	private List<String> getOfferServices(final String id) {
		return serviceDetailsDAO.getServicesById(id);
	}

	private List<String> getOfferServiceNames(final String id) {
		return serviceDetailsDAO.getServiceNameByInstituteId(id);
	}
	
	@Override
	public List<InstituteGetRequestDto> searchInstitute(@Valid final String searchText) {
		log.debug("Inside searchInstitute() method");
		List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
		try {
			log.info("Making sqlQuery to search institute based on passed searchString ="+searchText);
			String sqlQuery = "select inst.id, inst.name , inst.country_name , inst.city_name, inst.institute_type FROM institute inst where inst.is_active = 1  ";
			String countryName = searchText.split(",")[0];
			String name = searchText.split(",")[1];
			String cityName = searchText.split(",")[2];
			String worldRanking = searchText.split(",")[3];
			String instituteType = searchText.split(",")[4];
			String postDate = searchText.split(",")[5];
			if (countryName != null && !countryName.isEmpty()) {
				log.info("adding countryName in sql Query");
				sqlQuery += " and inst.country_name = '" + countryName + "'";
			} else if (name != null && !name.isEmpty()) {
				log.info("adding institue name in sql Query");
				sqlQuery += " and inst.name = '" + name + "'";
			} else if (cityName != null && !cityName.isEmpty()) {
				log.info("adding countryName in sql Query");
				sqlQuery += " and inst.city_name = '" + cityName + "'";
			} else if (worldRanking != null && !worldRanking.isEmpty()) {
				log.info("adding worldRanking in sql Query");
				sqlQuery += " and inst.world_ranking = " + Integer.valueOf(worldRanking);
			} else if (instituteType != null && !instituteType.isEmpty()) {
				log.info("adding instituteType in sql Query");
				sqlQuery += " and inst.institute_type = '" + instituteType + "'";
			} else if(postDate != null && !postDate.isEmpty()) {
				
			}
			log.info("Calling DAO layer to search institute based on searchText");
			List<Institute> institutes = dao.searchInstitute(sqlQuery);
			if(!CollectionUtils.isEmpty(institutes)) {
				log.info("institutes coming in response, hence start iterating institutes ande getting institutes details");
				institutes.stream().forEach(institute -> {
					instituteGetRequestDtos.add(getInstitute(institute));
				});
			}
		} catch (Exception exception) {
			log.error("Exception while searching Institutes having exception ="+exception);
		}
		return instituteGetRequestDtos;
	}

	@Override
	public PaginationResponseDto instituteFilter(final InstituteFilterDto instituteFilterDto) {
		log.debug("Inside instituteFilter() method");
		PaginationResponseDto paginationInstituteResponseDto = new PaginationResponseDto();
		try {
			List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
			log.info("fetching total count of institutes from DB");
			int totalCount = dao.findTotalCountFilterInstitute(instituteFilterDto);
			int startIndex = (instituteFilterDto.getPageNumber() - 1) * instituteFilterDto.getMaxSizePerPage();
			PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, instituteFilterDto.getMaxSizePerPage(), totalCount);
			log.info("fetching insitutes from DB based on passed filters and startIndex and pageNumber");
			List<Institute> institutes = dao.instituteFilter(startIndex, instituteFilterDto.getMaxSizePerPage(), instituteFilterDto);
			if(!CollectionUtils.isEmpty(institutes)) {
				log.info("if institutes are not coming nul from DB thewn start iterating to find institute details");
				institutes.stream().forEach(institute -> {
					instituteGetRequestDtos.add(getInstitute(institute));
				});
				log.info("Setting values in pagination response DTO with institutes coming from DB");
				paginationInstituteResponseDto.setInstitutes(instituteGetRequestDtos);
				paginationInstituteResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
				paginationInstituteResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
				paginationInstituteResponseDto.setTotalCount(totalCount);
				paginationInstituteResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
				paginationInstituteResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
			} else {
				log.error("No institutes found in DB");
				throw new NotFoundException("No Institute found in DB");
			}
		} catch (Exception exception) {
			log.error("Exception while filtering institues based on passed filters, having exception = "+exception);
		}
		return paginationInstituteResponseDto;
	}

	@Override
	public PaginationResponseDto autoSearch(final Integer pageNumber, final Integer pageSize, final String searchKey) {
		log.debug("Inside autoSearch() method");
		PaginationResponseDto paginationInstituteResponseDto = new PaginationResponseDto();
		try {
			log.info("fetching total count from DB for searchKey = "+ searchKey);
			int totalCount = dao.findTotalCountForInstituteAutosearch(searchKey);
			int startIndex = (pageNumber - 1) * pageSize;
			log.info("Calculating pagination havinbg startIndex "+ startIndex + " and totalCount "+ totalCount);
			PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			log.info("Fetching institutes from DB based on pageSize and having searchKey = "+ searchKey);
			List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
			List<InstituteGetRequestDto> institutes = dao.autoSearch(startIndex, pageSize, searchKey);
			for (InstituteGetRequestDto institute : institutes) {
				log.info("Start iterating institutes and set values in DTO for instituteId = " + institute.getId());
				InstituteGetRequestDto instituteGetRequestDto = new InstituteGetRequestDto();
				log.info("copying bean class to DTO and fetching institute videos based on countryName and instituteName");
				BeanUtils.copyProperties(institute, instituteGetRequestDto);
				instituteGetRequestDto.setInstituteYoutubes(getInstituteYoutube(institute.getCountryName(), institute.getName()));
				instituteGetRequestDtos.add(instituteGetRequestDto);
				
				log.info("Setting values in pagination response DTO with institutes coming from DB");
				paginationInstituteResponseDto.setInstitutes(instituteGetRequestDtos);
				paginationInstituteResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
				paginationInstituteResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
				paginationInstituteResponseDto.setTotalCount(totalCount);
				paginationInstituteResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
				paginationInstituteResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
			}
		} catch (Exception exception) {
			log.error("Exception while searching Institutes having exception = "+exception);
		}
		return paginationInstituteResponseDto;
	}

	@Override
	public List<Institute> getAllInstitute() {
		return dao.getAll();
	}

	@Override
	public List<InstituteCategoryType> getAllCategories() {
		return dao.getAllCategories();
	}

	@Override
	public void deleteInstitute(final String id) throws ValidationException {
		Institute institute = dao.get(id);
		if (institute != null) {
			institute.setIsActive(false);
			institute.setIsDeleted(true);
			institute.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
			dao.update(institute);
		} else {
			throw new ValidationException("Institute not found for id" + id);
		}
	}

	@Override
	public List<Institute> ratingWiseInstituteListByCountry(final String countryName) {
		return dao.ratingWiseInstituteListByCountry(countryName);
	}

	@Override
	public List<String> getInstituteIdsBasedOnGlobalRanking(final Long startIndex, final Long pageSize) {
		return dao.getInstituteIdsBasedOnGlobalRanking(startIndex, pageSize);
	}

	@Override
	public List<String> getInstituteIdsFromCountry(final List<String> distinctCountryIds) {

		List<String> instituteIds = dao.getInstitudeByCountry(distinctCountryIds);
		return instituteIds;
	}

	@Override
	public int getCountOfInstitute(final CourseSearchDto courseSearchDto, final String searchKeyword, final String cityId, final String instituteTypeId,
			final Boolean isActive, final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking) {
		return dao.getCountOfInstitute(courseSearchDto, searchKeyword, cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking, toWorldRanking);
	}

	public void saveInsituteOnElasticSearch(final String elasticSearchIndex, final String type, final List<InstituteElasticSearchDTO> instituteList,
			final String elasticSearchName) {
		for (InstituteElasticSearchDTO insitute : instituteList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(insitute.getId()));
			elasticSearchDto.setObject(insitute);
			System.out.println(elasticSearchDto);
			ResponseEntity<Object> object = restTemplate.postForEntity("http://" + IConstant.ELASTIC_SEARCH_URL, elasticSearchDto, Object.class);
			System.out.println(object);
		}
	}

	public void updateInsituteOnElasticSearch(final String elasticSearchIndex, final String type, final List<InstituteElasticSearchDTO> instituteList,
			final String elasticSearchName) {
		for (InstituteElasticSearchDTO insitute : instituteList) {
			ElasticSearchDTO elasticSearchDto = new ElasticSearchDTO();
			elasticSearchDto.setIndex(elasticSearchIndex);
			elasticSearchDto.setType(type);
			elasticSearchDto.setEntityId(String.valueOf(insitute.getId()));
			elasticSearchDto.setObject(insitute);
			System.out.println(elasticSearchDto);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<ElasticSearchDTO> httpEntity = new HttpEntity<>(elasticSearchDto, headers);
			ResponseEntity<Object> object = restTemplate.exchange("http://" + IConstant.ELASTIC_SEARCH_URL, HttpMethod.PUT, httpEntity, Object.class,
					new Object[] {});
			System.out.println(object);
		}
	}

	@Override
	public Integer getTotalCourseCountForInstitute(final String instituteId) {
		return courseDao.getTotalCourseCountForInstitute(instituteId);
	}

	@Override
	@Transactional
	public List<InstituteDomesticRankingHistoryDto> getHistoryOfDomesticRanking(final String instituteId) {
//		List<InstituteDomesticRankingHistory> instituteDomesticRankingHistory = new ArrayList<>();
//		List<InstituteDomesticRankingHistory> domesticRankingHistories = 
//		domesticRankingHistories.stream().forEach(domesticRankingHistory -> {
//			//setting null so that it will not give lazyInitialize exception
//			domesticRankingHistory.setInstitute(null);
//			InstituteDomesticRankingHistory domesticRankingHistoryObj = new InstituteDomesticRankingHistory();
//			BeanUtils.copyProperties(domesticRankingHistory, domesticRankingHistoryObj);
//			Optional<Institute> instituteFromDB = instituteRepository.findById(instituteId);
//			domesticRankingHistoryObj.setInstitute(instituteFromDB.get());
//			instituteDomesticRankingHistory.add(domesticRankingHistoryObj);
//		});
		List<InstituteDomesticRankingHistoryDto> domesticRankingHistoryObj = new ArrayList<>();
		List<InstituteDomesticRankingHistory> domesticRankingHistories = instituteDomesticRankingHistoryDAO.getHistoryOfDomesticRanking(instituteId);
		domesticRankingHistories.stream().forEach(domesticRankingHistory -> {
			InstituteDomesticRankingHistoryDto domesticRankingHistoryDto = new InstituteDomesticRankingHistoryDto();
			domesticRankingHistoryDto.setDomesticRanking(domesticRankingHistory.getDomesticRanking());
			domesticRankingHistoryObj.add(domesticRankingHistoryDto);
		});
		return domesticRankingHistoryObj;
	}

	@Override
	public InstituteWorldRankingHistory getHistoryOfWorldRanking(final String instituteId) {
		return institudeWorldRankingHistoryDAO.getHistoryOfWorldRanking(instituteId);
	}

	@Override
	public Map<String, Integer> getDomesticRanking(final List<String> courseIdList) {
		Map<String, Integer> courseDomesticRanking = dao.getDomesticRanking(courseIdList);
		return courseDomesticRanking;
	}

	@Override
	public NearestInstituteDTO getNearestInstituteList(AdvanceSearchDto courseSearchDto)
			throws Exception {
	    log.debug("Inside getNearestInstituteList() method");
		Boolean runMethodAgain = true;
		Integer initialRadius = courseSearchDto.getInitialRadius();
		Integer increaseRadius = 25, totalCount = 0;
		List<InstituteResponseDto> nearestInstituteList = new ArrayList<>();
		log.info("start getting nearest institutes from DB for latitude " + courseSearchDto.getLatitude()
					+ " and longitude " + courseSearchDto.getLongitude() + " and initial radius is " + initialRadius);
		List<InstituteResponseDto> nearestInstituteDTOs = dao.getNearestInstituteListForAdvanceSearch(courseSearchDto);
		totalCount = dao.getTotalCountOfNearestInstitutes(courseSearchDto.getLatitude(),courseSearchDto.getLongitude(), initialRadius);
		while (runMethodAgain) {
			if (initialRadius != maxRadius && CollectionUtils.isEmpty(nearestInstituteDTOs)) {
				log.info("institute is null for initial old radius "+initialRadius + "hence increase initial radius by "+increaseRadius);
				runMethodAgain = true;
				initialRadius = initialRadius + increaseRadius;
				log.info("institute is null for initial old radius so fetching institutes for new radius "+initialRadius);
				courseSearchDto.setInitialRadius(initialRadius);
				nearestInstituteDTOs = dao.getNearestInstituteListForAdvanceSearch(courseSearchDto);
				totalCount = dao.getTotalCountOfNearestInstitutes(courseSearchDto.getLatitude(),courseSearchDto.getLongitude(), initialRadius);
			} else {
				log.info("institutes found for new radius "+initialRadius + " hence start iterating data");
				runMethodAgain = false;
				for (InstituteResponseDto nearestInstituteDTO : nearestInstituteDTOs) {
					InstituteResponseDto nearestInstitute = new InstituteResponseDto();
					BeanUtils.copyProperties(nearestInstituteDTO, nearestInstitute);
					nearestInstitute.setDistance(Double.valueOf(initialRadius));
					log.info("going to fetch logo for institute from sotrage service for institutueID "+nearestInstituteDTO.getId());
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(nearestInstituteDTO.getId(), 
							ImageCategory.INSTITUTE.toString(), Type.LOGO.name(),"en");
					nearestInstitute.setStorageList(storageDTOList);
					nearestInstituteList.add(nearestInstitute);
				}
			}
		}
		Integer startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
		NearestInstituteDTO institutePaginationResponseDto = new NearestInstituteDTO();
		institutePaginationResponseDto.setNearestInstitutes(nearestInstituteList);
		institutePaginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		institutePaginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		if(initialRadius != maxRadius) {
			institutePaginationResponseDto.setHasNextPage(true);
		} else {
			institutePaginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		}
		institutePaginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		institutePaginationResponseDto.setTotalCount(totalCount);
		return institutePaginationResponseDto;
	}

	@Override
	public List<InstituteResponseDto> getDistinctInstituteList(Integer startIndex, Integer pageSize, String instituteName) {
		return dao.getDistinctInstituteListByName(startIndex, pageSize, instituteName);
	}

	@Override
	public int getDistinctInstituteCount(String instituteName) {
		return dao.getDistinctInstituteCountByName(instituteName);
	}

	@Override
	public NearestInstituteDTO getInstitutesUnderBoundRegion(Integer pageNumber, Integer pageSize, List<LatLongDto> latLongDtos) throws ValidationException {
		log.debug("Inside getInstitutesUnderBoundRegion() method");
		List<InstituteResponseDto> nearestInstituteList = new ArrayList<>();
		log.info("finding center of bounded by for user passed latitude and longitude");
		LatLongDto centerLatAndLong = CommonUtil.getCenterByLatituteAndLongitude(latLongDtos);
		log.info("finding radius for centerLatitude "+ centerLatAndLong.getLatitude() + "and centerLongitude "+centerLatAndLong.getLongitude());
		int radius = (int) (6371 * Math.acos(
		        Math.sin(latLongDtos.get(0).getLatitude()) * Math.sin(latLongDtos.get(1).getLatitude())
		        + Math.cos(latLongDtos.get(0).getLatitude()) * Math.cos(latLongDtos.get(1).getLatitude()) 
		        * Math.cos(latLongDtos.get(0).getLongitude() - latLongDtos.get(1).getLongitude())));
		log.info("fetching nearest institutes having latitude "+ centerLatAndLong.getLatitude() +"and longitude "+ centerLatAndLong.getLongitude() +
					" and radius is "+ radius);
		List<InstituteResponseDto> nearestInstituteDTOs = dao.getNearestInstituteList(pageNumber, pageSize, centerLatAndLong.getLatitude(),
				centerLatAndLong.getLongitude(), radius);
		
		Integer totalCount = dao.getTotalCountOfNearestInstitutes(centerLatAndLong.getLatitude(),centerLatAndLong.getLongitude(), radius);
		if(!CollectionUtils.isEmpty(nearestInstituteDTOs)) {
			log.info("institutes found, start iterating data into list");
			nearestInstituteDTOs.stream().forEach(nearestInstituteDTO -> {
				InstituteResponseDto nearestInstitute = new InstituteResponseDto();
				nearestInstitute.setDistance(Double.valueOf(radius));
				BeanUtils.copyProperties(nearestInstituteDTO, nearestInstitute);
				try {
					log.info("calling storage service to fetch logos for institute for instituteID "+nearestInstituteDTO.getId());
					List<StorageDto> storageDTOList = iStorageService.getStorageInformation(nearestInstituteDTO.getId(), 
							ImageCategory.INSTITUTE.toString(), Type.LOGO.name(),"en");
					nearestInstitute.setStorageList(storageDTOList);
				} catch (ValidationException e) {
					log.error("Error while fetching logos from storage service"+e);
				}
				nearestInstituteList.add(nearestInstitute);
			});
		} else {
			log.warn("No institutes found for latitude"+ centerLatAndLong.getLatitude() +"and longitude "+ centerLatAndLong.getLongitude() +
					" and radius is "+radius);
		}
		
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(pageNumber, pageSize, totalCount);
		NearestInstituteDTO institutePaginationResponseDto = new NearestInstituteDTO();
		institutePaginationResponseDto.setNearestInstitutes(nearestInstituteList);
		institutePaginationResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
		institutePaginationResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
		institutePaginationResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
		institutePaginationResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
		institutePaginationResponseDto.setTotalCount(totalCount);
		return institutePaginationResponseDto;
	}
}
