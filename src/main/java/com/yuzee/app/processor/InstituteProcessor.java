package com.yuzee.app.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.yuzee.app.bean.AccrediatedDetail;
import com.yuzee.app.bean.Institute;
import com.yuzee.app.bean.InstituteCategoryType;
import com.yuzee.app.bean.InstituteDomesticRankingHistory;
import com.yuzee.app.bean.InstituteIntake;
import com.yuzee.app.bean.InstituteWorldRankingHistory;
import com.yuzee.app.dao.AccrediatedDetailDao;
import com.yuzee.app.dao.CourseDao;
import com.yuzee.app.dao.InstituteDao;
import com.yuzee.app.dao.InstituteDomesticRankingHistoryDao;
import com.yuzee.app.dao.InstituteWorldRankingHistoryDao;
import com.yuzee.app.dao.ScholarshipDao;
import com.yuzee.app.dao.ServiceDao;
import com.yuzee.app.dto.AccrediatedDetailDto;
import com.yuzee.app.dto.AdvanceSearchDto;
import com.yuzee.app.dto.CourseScholarshipAndFacultyCountDto;
import com.yuzee.app.dto.CourseSearchDto;
import com.yuzee.app.dto.FollowerCountDto;
import com.yuzee.app.dto.InstituteCampusDto;
import com.yuzee.app.dto.InstituteDomesticRankingHistoryDto;
import com.yuzee.app.dto.InstituteElasticSearchDTO;
import com.yuzee.app.dto.InstituteFacultyDto;
import com.yuzee.app.dto.InstituteFilterDto;
import com.yuzee.app.dto.InstituteGetRequestDto;
import com.yuzee.app.dto.InstituteRequestDto;
import com.yuzee.app.dto.InstituteResponseDto;
import com.yuzee.app.dto.InstituteSearchResultDto;
import com.yuzee.app.dto.InstituteTimingResponseDto;
import com.yuzee.app.dto.InstituteWorldRankingHistoryDto;
import com.yuzee.app.dto.LatLongDto;
import com.yuzee.app.dto.NearestInstituteDTO;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.PaginationUtilDto;
import com.yuzee.app.dto.StorageDto;
import com.yuzee.app.enumeration.EntitySubTypeEnum;
import com.yuzee.app.enumeration.EntityTypeEnum;
import com.yuzee.app.exception.ConstraintVoilationException;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.exception.NotFoundException;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.ConnectionHandler;
import com.yuzee.app.handler.ElasticHandler;
import com.yuzee.app.handler.IdentityHandler;
import com.yuzee.app.handler.ReviewHandler;
import com.yuzee.app.handler.StorageHandler;
import com.yuzee.app.repository.InstituteRepository;
import com.yuzee.app.util.CDNServerUtil;
import com.yuzee.app.util.CommonUtil;
import com.yuzee.app.util.DateUtil;
import com.yuzee.app.util.IConstant;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackFor = {ConstraintVoilationException.class,Exception.class})
public class InstituteProcessor {

	@Value("${s3.url}")
	private String s3URL;
	
	@Value("${min.radius}")
	private Integer minRadius;
	
	@Value("${max.radius}")
	private Integer maxRadius;
	
	@Autowired
	private InstituteDao dao;

	@Autowired
	private InstituteWorldRankingHistoryDao institudeWorldRankingHistoryDAO;

	@Autowired
	private InstituteDomesticRankingHistoryDao instituteDomesticRankingHistoryDAO;

	@Autowired
	private ServiceDao serviceDetailsDAO;

	@Autowired
	private StorageHandler storageHandler;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private ElasticHandler elasticHandler;

	@Autowired
	private AccrediatedDetailDao accrediatedDetailDao;
	
	@Autowired
	private InstituteRepository instituteRepository;
	
	@Autowired
	private InstituteTimingProcessor instituteTimingProcessor;
	
	@Autowired
	private ConnectionHandler connectionHandler;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ReviewHandler reviewHandler;
	
	@Autowired
	private IdentityHandler userHandler;
	
	@Autowired
	private ScholarshipDao scholarshipDao;

	public Institute get(final String id) {
		return dao.get(id);
	}

	
	public List<String> getTopInstituteIdByCountry(final String countryId/* , Long startIndex, Long pageSize */) {
		return dao.getTopInstituteByCountry(countryId/* , startIndex, pageSize */);
	}

	
	public List<String> getRandomInstituteIdByCountry(final List<String> countryIdList/* , Long startIndex, Long pageSize */) {
		return dao.getRandomInstituteByCountry(countryIdList/* , startIndex, pageSize */);
	}

	
	public List<InstituteSearchResultDto> getInstitueBySearchKey(final String searchKey) {
		return dao.getInstitueBySearchKey(searchKey);
	}

	
	public List<InstituteResponseDto> getAllInstitutesByFilter(final CourseSearchDto filterObj, final String sortByField, final String sortByType,
			final String searchKeyword, final Integer startIndex, final String cityId, final String instituteTypeId, final Boolean isActive,
			final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking) {
		return dao.getAllInstitutesByFilter(filterObj, sortByField, sortByType, searchKeyword, startIndex, cityId, instituteTypeId, isActive, updatedOn,
				fromWorldRanking, toWorldRanking);
	}

	
	public InstituteResponseDto getInstituteByID(final String instituteId) {
		return dao.getInstituteById(instituteId);
	}

	
	public List<InstituteResponseDto> getAllInstituteByID(final Collection<String> listInstituteId) throws ValidationException {
		log.debug("Inside getAllInstituteByID() method");
		List<InstituteResponseDto> instituteResponseDTOList = new ArrayList<>();
		log.info("Fetching institute from DB based on passed instituteIds");
		List<Institute> inistituteList = dao.getAllInstituteByID(listInstituteId);
		if(!CollectionUtils.isEmpty(inistituteList)) {
			log.info("Institute fetched from DB based on instituteId list, start iterating list to set value in DTO");
			inistituteList.stream().forEach(institute -> {
				InstituteResponseDto instituteResponseDTO = new InstituteResponseDto();
				log.info("Copying bean class to DTO class");
				BeanUtils.copyProperties(institute, instituteResponseDTO);
				if (institute.getCountryName() != null) {
					instituteResponseDTO.setCountryName(institute.getCountryName());
				}
				if (institute.getCityName() != null) {
					instituteResponseDTO.setCityName(institute.getCityName());
				}
				instituteResponseDTO.setWorldRanking(institute.getWorldRanking());
				instituteResponseDTO.setLocation(institute.getLatitude() + "," + institute.getLongitude());
				try {
					log.info("Invoking storage service to get images for instituteId = "+instituteResponseDTO.getId());
					List<StorageDto> storageDTOList = storageHandler.getStorages(instituteResponseDTO.getId(), EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.IMAGES);
					instituteResponseDTO.setStorageList(storageDTOList);
				} catch (NotFoundException | InvokeException e) {
					log.error("Error invoking Storage service having exception = "+e);
				}
				instituteResponseDTOList.add(instituteResponseDTO);
			});
		}
		return instituteResponseDTOList;
	}

	
	public List<InstituteResponseDto> getInstituteByCityName(final String cityName) {
		log.debug("Inside getInstitudeByCityId() method");
		List<InstituteResponseDto> instituteResponseDtos = new ArrayList<>();
		log.info("fetching institutes from DB having cityName = "+ cityName);
		List<Institute> institutes = instituteRepository.findByCityName(cityName);
		if(!CollectionUtils.isEmpty(institutes)) {
			log.info("Institutes fetched from DB, start iterating data to make response");
			institutes.stream().forEach(institute -> {
				InstituteResponseDto instituteResponseDto = new InstituteResponseDto();
				log.info("Converting bean class to DTO class using beanUtils");
				BeanUtils.copyProperties(institute, instituteResponseDto);
				instituteResponseDtos.add(instituteResponseDto);
			});
		}
		return instituteResponseDtos;
	}

	
	public List<InstituteResponseDto> getInstituteByListOfCityId(final String cityId) {
		log.debug("Inside getInstituteByListOfCityId() method");
		List<InstituteResponseDto> instituteResponse = new ArrayList<>();
		String[] citiesArray = cityId.split(",");
		String tempList = "";
		for (String id : citiesArray) {
			tempList = tempList + "," + "'" + id + "'";
		}
		log.info("Calling DAO layer to fetch institutes based on cityName");
		List<InstituteResponseDto> instituteResponseDtos = dao.getInstituteByListOfCityId(tempList.substring(1, tempList.length()));
		if(!CollectionUtils.isEmpty(instituteResponseDtos)) {
			log.info("Institutes fetched from DB, hence start iterating to make final response");
			instituteResponseDtos.stream().forEach(instituteResponseDto -> {
				log.info("fetching instituteTiming from DB for instituteId =" +instituteResponseDto.getId());
				InstituteTimingResponseDto instituteTimingResponseDto = instituteTimingProcessor.getInstituteTimeByInstituteId(instituteResponseDto.getId());
				instituteResponseDto.setInstituteTiming(instituteTimingResponseDto);
				instituteResponse.add(instituteResponseDto);
			});
		}
		return instituteResponse;
	}

	public void saveInstitute(final List<InstituteRequestDto> instituteRequests) throws Exception {
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
				instituteElasticSearchDto
						.setCountryName(institute.getCountryName() != null ? institute.getCountryName() : null);
				instituteElasticSearchDto.setCityName(institute.getCityName() != null ? institute.getCityName() : null);
				instituteElasticSearchDto.setInstituteTypeName(
						institute.getInstituteType() != null ? institute.getInstituteType() : null);
				instituteElasticSearchDto.setIntakes(instituteRequest.getIntakes());
				instituteElasticDtoList.add(instituteElasticSearchDto);
			}
			log.info("Calling elasticSearch Service to add new institutes on elastic index");
			elasticHandler.saveInsituteOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_INSTITUTE,
					EntityTypeEnum.INSTITUTE.name().toLowerCase(), instituteElasticDtoList, IConstant.ELASTIC_SEARCH);
		} catch (Exception exception) {
			log.error("Exception while saving institutes having exception ", exception.getMessage());
			throw exception;
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

	public void updateInstitute(final String userId, final String instituteId, final InstituteRequestDto instituteRequest) throws Exception {
		log.debug("Inside updateInstitute() method");
		try {
			List<InstituteElasticSearchDTO> instituteElasticDtoList = new ArrayList<>();
			log.info("fetching institute from DB having instituteId: {}", instituteId);
			Institute oldInstitute = dao.get(instituteId);
			Institute newInstitute = new Institute();
			InstituteElasticSearchDTO instituteElasticSearchDto = new InstituteElasticSearchDTO();
			log.info("Copying bean class to DTO class");
			BeanUtils.copyProperties(instituteRequest, newInstitute);
			if (!StringUtils.isEmpty(instituteRequest.getDomesticRanking())
					&& !instituteRequest.getDomesticRanking().equals(oldInstitute.getDomesticRanking())) {
				log.info("DomesticRanking is not null hence saving domesticRanking History");
				saveDomesticRankingHistory(newInstitute, oldInstitute);
			}
			if (!StringUtils.isEmpty(instituteRequest.getWorldRanking()) 
					&& !instituteRequest.getWorldRanking().equals(oldInstitute.getWorldRanking())) {
				log.info("WorldRanking is not null hence saving worldRanking History");
				saveWorldRankingHistory(newInstitute, oldInstitute);
			}
			log.info("Start updating institute for instituteId: {}", instituteId);
			Institute institute = saveInstitute(instituteRequest, instituteId);
			log.info("Copying DTO class to elasticSearch DTO");
			BeanUtils.copyProperties(instituteRequest, instituteElasticSearchDto);
			instituteElasticSearchDto
					.setCountryName(institute.getCountryName() != null ? institute.getCountryName() : null);
			instituteElasticSearchDto.setCityName(institute.getCityName() != null ? institute.getCityName() : null);
			instituteElasticSearchDto
					.setInstituteTypeName(institute.getInstituteType() != null ? institute.getInstituteType() : null);
			instituteElasticSearchDto.setIntakes(instituteRequest.getIntakes());
			instituteElasticSearchDto.setTagLine(instituteRequest.getTagLine());
			instituteElasticDtoList.add(instituteElasticSearchDto);
			
			// TODO: need to check user access before updating
			if (!ObjectUtils.isEmpty(instituteRequest.getProfilePermission())) {
				log.info("if profile permission is not empty then going to save/update userProfilePermission");
				userHandler.saveOrUpdateUserProfileDataPermission(userId, EntityTypeEnum.INSTITUTE.name(),
						institute.getId(), instituteRequest.getProfilePermission());
			}

			log.info("Calling elastic service to save instiutes on index");
			elasticHandler.updateInsituteOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_INSTITUTE,
					EntityTypeEnum.INSTITUTE.name().toLowerCase(), instituteElasticDtoList, IConstant.ELASTIC_SEARCH);
		} catch (Exception exception) {
			log.error("Exception while updating institute having exception ={}", exception.getMessage());
			throw exception;
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
			institute.setCreatedBy("API");
		}
		institute.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		institute.setUpdatedBy("API");
		if (!StringUtils.isEmpty(instituteRequest.getName())) {
			institute.setName(instituteRequest.getName());
		} else {
			log.error("InstituteName is required");
			throw new ValidationException("InstituteName is required");
		}
		institute.setDescription(instituteRequest.getDescription());
		if (!StringUtils.isEmpty(instituteRequest.getCountryName())) {
			institute.setCountryName(instituteRequest.getCountryName());
		} else {
			log.error("CountryName is required");
			throw new ValidationException("countryName is required");
		}
		if (!StringUtils.isEmpty(instituteRequest.getCityName())) {
			institute.setCityName(instituteRequest.getCityName());
		} else {
			log.error("CityName is required");
			throw new ValidationException("cityName is required");
		}

		if (!StringUtils.isEmpty(instituteRequest.getInstituteType())) {
			 institute.setInstituteType(instituteRequest.getInstituteType());
		} else {
			log.error("InstituteType is required like University, School, College etc");
			throw new ValidationException("InstituteType is required like University, School, College etc");
		}

		institute.setIsActive(true);
		institute.setDomesticRanking(instituteRequest.getDomesticRanking());
		institute.setWorldRanking(instituteRequest.getWorldRanking());
		institute.setPostalCode(instituteRequest.getPostalCode());
		institute.setWebsite(instituteRequest.getWebsite());
		if (!StringUtils.isEmpty(instituteRequest.getInstituteCategoryTypeId())) {
			institute.setInstituteCategoryType(getInstituteCategoryType(instituteRequest.getInstituteCategoryTypeId()));
		} else {
			log.error("instituteCategoryTypeId is required");
			throw new ValidationException("instituteCategoryTypeId is required");
		}
		institute.setState(instituteRequest.getStateName());
		institute.setAddress(instituteRequest.getAddress());
		institute.setEmail(instituteRequest.getEmail());
		institute.setPhoneNumber(instituteRequest.getPhoneNumber());
		institute.setLatitude(instituteRequest.getLatitude());
		institute.setLongitude(instituteRequest.getLongitude());
		institute.setCampusName(instituteRequest.getCampusName());
		institute.setEnrolmentLink(instituteRequest.getEnrolmentLink());
		institute.setTuitionFeesPaymentPlan(instituteRequest.getTuitionFessPaymentPlan());
		institute.setScholarshipFinancingAssistance(instituteRequest.getScholarshipFinancingAssistance());
		institute.setAvgCostOfLiving(instituteRequest.getAvgCostOfLiving());
		institute.setWhatsNo(instituteRequest.getWhatsNo());
		institute.setAboutInfo(instituteRequest.getAboutInfo());
		institute.setCourseStart(instituteRequest.getCourseStart());
		institute.setLink(instituteRequest.getLink());
		institute.setContact(instituteRequest.getContact());
		institute.setCurriculum(instituteRequest.getCurriculum());
		institute.setDomesticBoardingFee(instituteRequest.getDomesticBoardingFee());
		institute.setInternationalBoardingFee(instituteRequest.getInternationalBoardingFee());
		if (!StringUtils.isEmpty(instituteRequest.getTagLine())) {
			institute.setTagLine(instituteRequest.getTagLine());	
		}
		try {
			if (id != null) {
				log.info("if instituteId is not null then going to update institute for id = {}", id);
				dao.addUpdateInstitute(institute);
				// Adding data in Elastic DTO to save it on elasticSearch index
				log.info(
						"updation is done now adding data in elasticSearch DTO to add it on elastic indices for entityId = {}"
								, id);
				InstituteElasticSearchDTO instituteElasticDto = new InstituteElasticSearchDTO();
				List<InstituteElasticSearchDTO> instituteElasticDTOList = new ArrayList<>();
				BeanUtils.copyProperties(institute, instituteElasticDto);
				instituteElasticDTOList.add(instituteElasticDto);
			} else {
				log.info("institute is not present in DB hence adding new institute in DB");
				dao.addUpdateInstitute(institute);
			}
		} catch (DataIntegrityViolationException exception) {
			log.error("Institute already exists having \nname: {},\ncampus_name: {},\ncity_Name: {},\ncountry_name: {}",
					instituteRequest.getName(), instituteRequest.getCampusName(), instituteRequest.getCityName(),
					instituteRequest.getCountryName());
			throw new ConstraintVoilationException(
					"Institute already exists having name: " + instituteRequest.getName() + ", campus_name: "
							+ instituteRequest.getCampusName() + ", city_Name: " + instituteRequest.getCityName()
							+ ", country_name: " + instituteRequest.getCountryName());
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
		if(!CollectionUtils.isEmpty(instituteRequest.getInstituteTimings())) {
			log.info("instituteTimings is not null hence going to save institute timings in DB");
			instituteTimingProcessor.saveInstituteTiming(instituteRequest.getInstituteTimings(), institute);
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
			accreditation.stream().forEach(accreditedInstitute -> {
				AccrediatedDetail accreditedInstituteDetail = new AccrediatedDetail();
				accreditedInstituteDetail.setEntityId(institute.getId());
				accreditedInstituteDetail.setEntityType("INSTITUTE");
				accreditedInstituteDetail.setAccrediatedName(accreditedInstitute.getAccrediatedName());
				accreditedInstituteDetail.setAccrediatedWebsite(accreditedInstitute.getAccrediatedWebsite());
				accreditedInstituteDetail.setCreatedBy("API");
				accreditedInstituteDetail.setCreatedOn(new Date());
				log.info("Calling DAO layer to save accrediatedDetails in DB for institute ="+institute.getId());
				accrediatedDetailDao.addAccrediatedDetail(accreditedInstituteDetail);
			});
		}
	}

	private void saveInstituteService(final Institute institute, final List<String> offerService) {
		log.debug("Inside saveInstituteService() method");
		log.info("deleting existing instituteService from DB having instituteId = "+institute.getId());
		dao.deleteInstituteService(institute.getId());
		log.info("start iterating new offerServices coming in request for institute");
		offerService.stream().forEach(serviceId -> {
			log.info("fetching service from DB having by serviceId coming in request = "+serviceId);
			Optional<com.yuzee.app.bean.Service> service = serviceDetailsDAO.getServiceById(serviceId);
			com.yuzee.app.bean.InstituteService instituteServiceDetails = new com.yuzee.app.bean.InstituteService();
			instituteServiceDetails.setInstitute(institute);
			if (service.isPresent()) {
				instituteServiceDetails.setService(service.get());
			}
			instituteServiceDetails.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			instituteServiceDetails.setIsActive(true);
			instituteServiceDetails.setCreatedBy("AUTO");
			instituteServiceDetails.setUpdatedBy("AUTO");
			instituteServiceDetails.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
			log.info("Calling DAO layer to save institute service in DB");
			dao.saveInstituteserviceDetails(instituteServiceDetails);
		});
	}

	private InstituteCategoryType getInstituteCategoryType(final String instituteCategoryTypeId) {
		return dao.getInstituteCategoryType(instituteCategoryTypeId);
	}

	
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
			paginationResponseDto.setResponse(instituteGetRequestDtos);
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

	private InstituteGetRequestDto convertInstituteToInstituteGetRequestDto(final Institute institute) {
		log.debug("Inside getInstitute() method");
		InstituteGetRequestDto dto = new InstituteGetRequestDto();
		log.info("Converting bean class response into DTO class response");
		dto.setId(institute.getId());
		dto.setCityName(institute.getCityName());
		dto.setCountryName(institute.getCountryName());
		dto.setInstituteType(institute.getInstituteType());
		dto.setName(institute.getName());
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

	@Transactional
	public InstituteRequestDto getById(final String id) throws Exception {
		log.debug("Inside getById() method");
		log.info("Fetching institute from DB for instituteId = {}", id);
		Institute institute = dao.get(id);
		if (institute == null) {
			log.error("No institute found in DB for instituteId = {}", id);
			throw new ValidationException("Institute not found for id" + id);
		}
		log.info("Converting bean to request DTO class");
		InstituteRequestDto instituteRequestDto = CommonUtil.convertInstituteBeanToInstituteRequestDto(institute);
		log.info("fetching institute services from DB fro instituteID = {}", id);
		instituteRequestDto.setOfferService(getOfferServiceNames(id));
		instituteRequestDto.setOfferServiceName(getOfferServiceNames(id));
		log.info("fetching accrediation Details from DB fro instituteID = {}", id);
		instituteRequestDto.setAccreditationDetails(getAccreditationName(id));
		log.info("fetching institute intakes from DB fro instituteID = {}", id);
		instituteRequestDto.setIntakes(getIntakes(id));
		if (institute.getInstituteCategoryType() != null) {
			log.info("Adding institute category type in final Response");
			instituteRequestDto.setInstituteCategoryTypeId(institute.getInstituteCategoryType().getId());
		}
		InstituteTimingResponseDto instituteTimingResponseDto = instituteTimingProcessor
				.getInstituteTimeByInstituteId(id);
		if (!ObjectUtils.isEmpty(instituteTimingResponseDto)) {
			instituteRequestDto.setInstituteTimings(
					CommonUtil.convertInstituteTimingResponseDtoToInstituteRequestDto(instituteTimingResponseDto));
		}

		Map<String,String> profilePermissionsMap = userHandler.getUserProfileDataPermission(Arrays.asList(id));
		if (!CollectionUtils.isEmpty(profilePermissionsMap)) {
			instituteRequestDto.setProfilePermission(profilePermissionsMap.get(id));	
		}
		
		FollowerCountDto followerCountDto = connectionHandler.getFollowersCount(id);
		if (!ObjectUtils.isEmpty(followerCountDto)) {
			instituteRequestDto.setFollowersCount(followerCountDto.getConnection_number());
		}

		log.info("Calling review service to fetch user average review for instituteId");
		Map<String, Double> yuzeeReviewMap = reviewHandler.getAverageReview("INSTITUTE",
				Arrays.asList(instituteRequestDto.getId()));
		instituteRequestDto.setStars(yuzeeReviewMap.get(instituteRequestDto.getId()));
		return instituteRequestDto;
	}

	private List<String> getIntakes(@Valid final String id) {
		return dao.getIntakesById(id); 
	}
	
	private List<AccrediatedDetailDto> getAccreditationName(@Valid final String id) {
		log.debug("Inside getAccreditationName() method");
		List<AccrediatedDetailDto> accrediatedDetailDtos = new ArrayList<>();
		log.info("Fetching accrediation details from DB having entityId = "+id);
		List<AccrediatedDetail> accrediatedDetails = accrediatedDetailDao.getAccrediationDetailByEntityId(id);
		if(!CollectionUtils.isEmpty(accrediatedDetails)) {
			log.info("Acrediation details are fetched from DB, start iterating data to set value in DTO");
			accrediatedDetails.stream().forEach(accrediatedDetail -> {
				AccrediatedDetailDto accrediatedDetailDto = new AccrediatedDetailDto();
				accrediatedDetailDto.setId(accrediatedDetail.getId());
				accrediatedDetailDto.setEntityId(accrediatedDetail.getEntityId());
				accrediatedDetailDto.setEntityType(accrediatedDetail.getEntityType());
				accrediatedDetailDto.setAccrediatedName(accrediatedDetail.getAccrediatedName());
				accrediatedDetailDto.setAccrediatedWebsite(accrediatedDetail.getAccrediatedWebsite());
				accrediatedDetailDtos.add(accrediatedDetailDto);
			});
		}
		return accrediatedDetailDtos;
	}

	private List<String> getOfferServiceNames(final String id) {
		return serviceDetailsDAO.getServiceNameByInstituteId(id);
	}
	
	
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
					instituteGetRequestDtos.add(convertInstituteToInstituteGetRequestDto(institute));
				});
			}
		} catch (Exception exception) {
			log.error("Exception while searching Institutes having exception ="+exception);
		}
		return instituteGetRequestDtos;
	}

	
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
					instituteGetRequestDtos.add(convertInstituteToInstituteGetRequestDto(institute));
				});
				log.info("Setting values in pagination response DTO with institutes coming from DB");
				paginationInstituteResponseDto.setResponse(instituteGetRequestDtos);
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
			institutes.stream().forEach(institute -> {
				log.info("Start iterating institutes and set values in DTO for instituteId = " + institute.getId());
				InstituteGetRequestDto instituteGetRequestDto = new InstituteGetRequestDto();
				log.info("copying bean class to DTO and fetching institute videos based on countryName and instituteName");
				BeanUtils.copyProperties(institute, instituteGetRequestDto);
				instituteGetRequestDto.setInstituteYoutubes(getInstituteYoutube(institute.getCountryName(), institute.getName()));
				instituteGetRequestDtos.add(instituteGetRequestDto);
				
				log.info("Setting values in pagination response DTO with institutes coming from DB");
				paginationInstituteResponseDto.setResponse(instituteGetRequestDtos);
				paginationInstituteResponseDto.setHasNextPage(paginationUtilDto.isHasNextPage());
				paginationInstituteResponseDto.setHasPreviousPage(paginationUtilDto.isHasPreviousPage());
				paginationInstituteResponseDto.setTotalCount(totalCount);
				paginationInstituteResponseDto.setPageNumber(paginationUtilDto.getPageNumber());
				paginationInstituteResponseDto.setTotalPages(paginationUtilDto.getTotalPages());
			});
		} catch (Exception exception) {
			log.error("Exception while searching Institutes having exception = "+exception);
		}
		return paginationInstituteResponseDto;
	}

	public List<InstituteCategoryType> getAllCategories() {
		return dao.getAllCategories();
	}

	public void deleteInstitute(final String id) throws ValidationException {
		log.debug("Inside deleteInstitute() method");
		log.info("Fetching institute from DB for id = "+id);
		Institute institute = dao.get(id);
		if (institute != null) {
			log.info("Institute found making institute inActive");
			institute.setIsActive(false);
			institute.setIsDeleted(true);
			institute.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
			log.info("Calling DAO layer to update institute status from active to inactive");
			dao.addUpdateInstitute(institute);
		} else {
			log.error("Institute not found for id" + id);
			throw new ValidationException("Institute not found for id" + id);
		}
	}

	
	public List<Institute> ratingWiseInstituteListByCountry(final String countryName) {
		return dao.ratingWiseInstituteListByCountry(countryName);
	}

	
	public List<String> getInstituteIdsBasedOnGlobalRanking(final Long startIndex, final Long pageSize) {
		return dao.getInstituteIdsBasedOnGlobalRanking(startIndex, pageSize);
	}

	
	public List<String> getInstituteIdsFromCountry(final List<String> distinctCountryIds) {
		List<String> instituteIds = dao.getInstitudeByCountry(distinctCountryIds);
		return instituteIds;
	}

	
	public int getCountOfInstitute(final CourseSearchDto courseSearchDto, final String searchKeyword, final String cityId, final String instituteTypeId,
			final Boolean isActive, final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking) {
		return dao.getCountOfInstitute(courseSearchDto, searchKeyword, cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking, toWorldRanking);
	}

	public Integer getTotalCourseCountForInstitute(final String instituteId) {
		return courseDao.getTotalCourseCountForInstitute(instituteId);
	}

	
	public List<InstituteDomesticRankingHistoryDto> getHistoryOfDomesticRanking(final String instituteId) {
		log.debug("Inside getHistoryOfDomesticRanking() method");
		List<InstituteDomesticRankingHistoryDto> domesticRankingHistoryObj = new ArrayList<>();
		log.info("Calling DAO layer to fetch Domestic Ranking for instituteId = "+instituteId);
		List<InstituteDomesticRankingHistory> domesticRankingHistories = instituteDomesticRankingHistoryDAO.getHistoryOfDomesticRanking(instituteId);
		if(!CollectionUtils.isEmpty(domesticRankingHistories)) {
			log.info("Domestic Ranking history fetched from DB and start iterating to set values in DTO");
			domesticRankingHistories.stream().forEach(domesticRankingHistory -> {
				InstituteDomesticRankingHistoryDto domesticRankingHistoryDto = new InstituteDomesticRankingHistoryDto();
				domesticRankingHistoryDto.setDomesticRanking(domesticRankingHistory.getDomesticRanking());
				domesticRankingHistoryDto.setId(domesticRankingHistory.getId());
				domesticRankingHistoryDto.setInstituteName(domesticRankingHistory.getInstitute().getName());
				domesticRankingHistoryObj.add(domesticRankingHistoryDto);
			});
		}
		return domesticRankingHistoryObj;
	}

	
	public List<InstituteWorldRankingHistoryDto> getHistoryOfWorldRanking(final String instituteId) {
		log.debug("Inside getHistoryOfWorldRanking() method");
		List<InstituteWorldRankingHistoryDto> instituteWorldRankingHistoryResponse = new ArrayList<>();
		log.info("Calling DAO layer to fetch world ranking for instituteId ="+instituteId);
		List<InstituteWorldRankingHistory> instituteWorldRankingHistories = institudeWorldRankingHistoryDAO.getHistoryOfWorldRanking(instituteId);
		if(!CollectionUtils.isEmpty(instituteWorldRankingHistories)) {
			log.info("World Ranking history fetched from DB and start iterating to set values in DTO");
			instituteWorldRankingHistories.stream().forEach(instituteWorldRankingHistory -> {
				InstituteWorldRankingHistoryDto instituteWorldRankingHistoryDto = new InstituteWorldRankingHistoryDto();
				instituteWorldRankingHistoryDto.setId(instituteWorldRankingHistory.getId());
				instituteWorldRankingHistoryDto.setWorldRanking(instituteWorldRankingHistory.getWorldRanking());
				instituteWorldRankingHistoryDto.setInstituteName(instituteWorldRankingHistory.getInstitute().getName());
				instituteWorldRankingHistoryResponse.add(instituteWorldRankingHistoryDto);
			});
		}
		return instituteWorldRankingHistoryResponse;
	}

	
	public Map<String, Integer> getDomesticRanking(final List<String> courseIdList) {
		Map<String, Integer> courseDomesticRanking = dao.getDomesticRanking(courseIdList);
		return courseDomesticRanking;
	}

	
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
					List<StorageDto> storageDTOList = storageHandler.getStorages(nearestInstituteDTO.getId(), 
							EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.LOGO);
					nearestInstitute.setStorageList(storageDTOList);
					log.info("fetching instituteTiming from DB for instituteId = " +nearestInstituteDTO.getId());
					InstituteTimingResponseDto instituteTimingResponseDto = instituteTimingProcessor.getInstituteTimeByInstituteId(nearestInstituteDTO.getId());
					nearestInstitute.setInstituteTiming(instituteTimingResponseDto);
					nearestInstituteList.add(nearestInstitute);
				}
			}
		}
		Integer startIndex = PaginationUtil.getStartIndex(courseSearchDto.getPageNumber(), courseSearchDto.getMaxSizePerPage());
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(startIndex, courseSearchDto.getMaxSizePerPage(), totalCount);
		
		Boolean hasNextPage = false;
		if(initialRadius != maxRadius) {
			hasNextPage = true;
		}
		NearestInstituteDTO institutePaginationResponseDto = new NearestInstituteDTO(nearestInstituteList, totalCount, paginationUtilDto.getPageNumber(),
				paginationUtilDto.isHasPreviousPage(), hasNextPage, paginationUtilDto.getTotalPages());
		return institutePaginationResponseDto;
	}

	
	public List<InstituteResponseDto> getDistinctInstituteList(Integer startIndex, Integer pageSize, String instituteName) throws Exception {
		log.debug("Inside getDistinctInstituteList() method");
		List<InstituteResponseDto> instituteResponse = new ArrayList<>();
		log.info("Calling DAO layer to getDistinct institutes from DB based on pagination");
		List<InstituteResponseDto> instituteResponseDtos = dao.getInstitutesByInstituteName(startIndex, pageSize, instituteName);
		if(!CollectionUtils.isEmpty(instituteResponseDtos)) {
			
			List<String> instituteIds = instituteResponseDtos.stream().map(InstituteResponseDto::getId).collect(Collectors.toList());
			
			log.info("Calling review service to fetch user average review for instituteId");
			Map<String, Double> yuzeeReviewMap = reviewHandler.getAverageReview("INSTITUTE", instituteIds);
			
			Map<String, String> profilePermissionsMap = userHandler.getUserProfileDataPermission(instituteIds);

			log.info("Institutes are coming from DB start iterating and fetching instituteTiming from DB");
			for (InstituteResponseDto instituteResponseDto : instituteResponseDtos) {
				log.info("fetching instituteTiming from DB for instituteId =" + instituteResponseDto.getId());
				InstituteTimingResponseDto instituteTimingResponseDto = instituteTimingProcessor
						.getInstituteTimeByInstituteId(instituteResponseDto.getId());
				instituteResponseDto.setInstituteTiming(instituteTimingResponseDto);
				instituteResponseDto.setStars(yuzeeReviewMap.get(instituteResponseDto.getId()));
				if (!CollectionUtils.isEmpty(profilePermissionsMap)) {
					instituteResponseDto.setProfilePermission(profilePermissionsMap.get(instituteResponseDto.getId()));
				}
				instituteResponse.add(instituteResponseDto);
			}
		}
		return instituteResponse;
	}

	
	public int getDistinctInstituteCount(String instituteName) {
		return dao.getDistinctInstituteCountByName(instituteName);
	}

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
		log.info("Fetching total count of institute based on latitude and longitude from DB");
		Integer totalCount = dao.getTotalCountOfNearestInstitutes(centerLatAndLong.getLatitude(),centerLatAndLong.getLongitude(), radius);
		if(!CollectionUtils.isEmpty(nearestInstituteDTOs)) {
			log.info("institutes found, start iterating data into list");
			nearestInstituteDTOs.stream().forEach(nearestInstituteDTO -> {
				InstituteResponseDto nearestInstitute = new InstituteResponseDto();
				nearestInstitute.setDistance(Double.valueOf(radius));
				log.info("Copying bean class to DTO class");
				BeanUtils.copyProperties(nearestInstituteDTO, nearestInstitute);
				try {
					log.info("calling storage service to fetch logos for institute for instituteID "+nearestInstituteDTO.getId());
					List<StorageDto> storageDTOList = storageHandler.getStorages(nearestInstituteDTO.getId(), 
							EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.LOGO);
					nearestInstitute.setStorageList(storageDTOList);
				} catch (NotFoundException | InvokeException e) {
					log.error("Error while fetching logos from storage service"+e);
				}
				log.info("fetching instituteTiming from DB for instituteId =" +nearestInstituteDTO.getId());
				InstituteTimingResponseDto instituteTimingResponseDto = instituteTimingProcessor.getInstituteTimeByInstituteId(nearestInstituteDTO.getId());
				nearestInstitute.setInstituteTiming(instituteTimingResponseDto);
				nearestInstituteList.add(nearestInstitute);
			});
		} else {
			log.warn("No institutes found for latitude"+ centerLatAndLong.getLatitude() +"and longitude "+ centerLatAndLong.getLongitude() +
					" and radius is "+radius);
		}
		log.info("Calculating pagination based on pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(pageNumber, pageSize, totalCount);
		NearestInstituteDTO institutePaginationResponseDto = new NearestInstituteDTO(nearestInstituteList, totalCount, paginationUtilDto.getPageNumber(),
				paginationUtilDto.isHasPreviousPage(), paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return institutePaginationResponseDto;
	}
	
	public List<Institute> getAllInstitutes() {
		return instituteRepository.findAll();
	}
	
	public NearestInstituteDTO getInstituteByCountryName(String countryName, Integer pageNumber,Integer pageSize) throws NotFoundException {
		log.debug("Inside getInstituteByCountryName() method");
		CourseSearchDto courseSearchDto = new CourseSearchDto();
		courseSearchDto.setMaxSizePerPage(pageSize);
		log.info("fetching institutes from DB for countryName "+ countryName);
		List<InstituteResponseDto> nearestInstituteDTOs = new ArrayList<>();
		List<InstituteResponseDto> institutesFromDB = dao.getAllInstitutesByFilter(courseSearchDto, "countryName", 
					null, countryName, pageNumber, null, null, null, null, null, null);
		Integer totalCount = instituteRepository.getTotalCountOfInstituteByCountryName(countryName);
		if(!CollectionUtils.isEmpty(institutesFromDB)) {
			log.info("institutes found in DB for countryName "+ countryName + " so start iterating data");
			institutesFromDB.stream().forEach(institute -> {
				InstituteResponseDto nearestInstitute = new InstituteResponseDto();
				BeanUtils.copyProperties(institute, nearestInstitute);
				log.info("going to fetch institute logo from storage service having instituteID "+institute.getId());
				try {
					List<StorageDto> storageDTOList = storageHandler.getStorages(institute.getId(), 
							EntityTypeEnum.INSTITUTE,EntitySubTypeEnum.LOGO);
					nearestInstitute.setStorageList(storageDTOList);
				} catch (NotFoundException | InvokeException e) {
					log.error("Error while fetching logos from storage service"+e);
				}
				nearestInstituteDTOs.add(nearestInstitute);
			});
		}
		log.info("calculating pagination on the basis of pageNumber, pageSize and totalCount");
		PaginationUtilDto paginationUtilDto = PaginationUtil.calculatePagination(pageNumber, pageSize, totalCount);
		NearestInstituteDTO institutePaginationResponseDto = new NearestInstituteDTO(nearestInstituteDTOs, totalCount, paginationUtilDto.getPageNumber(),
				paginationUtilDto.isHasPreviousPage(), paginationUtilDto.isHasNextPage(), paginationUtilDto.getTotalPages());
		return institutePaginationResponseDto;
	}

	public List<InstituteCampusDto> getInstituteCampuses(String instituteId) throws NotFoundException {
		log.debug("inside processor.getInstitutCampuses method.");
		Institute institute = dao.get(instituteId);
		if (!ObjectUtils.isEmpty(institute)) {
			List<Institute> institutes = dao.getInstituteCampuses(instituteId,institute.getName());
			return institutes.stream().map(e->modelMapper.map(e, InstituteCampusDto.class)).collect(Collectors.toList());
		}else {
			log.error("Institute not found against id: {}", instituteId);
			throw new NotFoundException("Institute not found against id: " + instituteId);
		}
	}

	public List<InstituteFacultyDto> getInstituteFaculties(String instituteId) throws NotFoundException {
		log.debug("inside processor.getInstituteFaculties method.");
		if (!ObjectUtils.isEmpty(dao.get(instituteId))) {
			return dao.getInstituteFaculties(instituteId);
		}else {
			log.error("Institute not found against id: {}", instituteId);
			throw new NotFoundException("Institute not found against id: " + instituteId);
		}
	}

	public CourseScholarshipAndFacultyCountDto getInstituteCourseScholarshipAndFacultyCount(String instituteId)
			throws NotFoundException {
		CourseScholarshipAndFacultyCountDto dto = new CourseScholarshipAndFacultyCountDto();
		dto.setCourseCount(courseDao.getTotalCourseCountForInstitute(instituteId));
		dto.setFacultyCount(dao.getInstituteFaculties(instituteId).size());
		dto.setScholarshipCount(scholarshipDao.getCountByInstituteId(instituteId));
		return dto;
	}


	public List<InstituteResponseDto> getInstitutesByIdList(List<String> instituteIds) throws Exception {
		log.info("inside InstituteProcessor.getInstitutesByIdList");
		List<InstituteResponseDto> instituteResponseDtos = dao.findByIds(instituteIds);
		if (!CollectionUtils.isEmpty(instituteResponseDtos)) {

			instituteIds = instituteResponseDtos.stream().map(InstituteResponseDto::getId).collect(Collectors.toList());
			List<StorageDto> instituteLogos = storageHandler.getStorages(instituteIds, EntityTypeEnum.INSTITUTE,
					EntitySubTypeEnum.LOGO);

			log.info("Calling review service to fetch user average review for instituteId");
			Map<String, Double> yuzeeReviewMap = reviewHandler.getAverageReview("INSTITUTE", instituteIds);

			instituteResponseDtos.stream().forEach(instituteResponseDto -> {
				instituteResponseDto.setStars(yuzeeReviewMap.get(instituteResponseDto.getId()));
				Optional<StorageDto> logoStorage = instituteLogos.stream()
						.filter(e -> e.getEntityId().equals(instituteResponseDto.getId())).findFirst();
				instituteResponseDto.setLogoUrl(logoStorage.isPresent() ? logoStorage.get().getFileURL() : null);
			});
		}
		return instituteResponseDtos;
	}
}
