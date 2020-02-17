package com.seeka.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.bean.AccreditedInstituteDetail;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.FacultyLevel;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteDomesticRankingHistory;
import com.seeka.app.bean.InstituteIntake;
import com.seeka.app.bean.InstituteLevel;
import com.seeka.app.bean.InstituteVideos;
import com.seeka.app.bean.InstituteWorldRankingHistory;
import com.seeka.app.bean.Level;
import com.seeka.app.constant.Type;
import com.seeka.app.dao.IAccreditedInstituteDetailDao;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dao.IInstituteDomesticRankingHistoryDAO;
import com.seeka.app.dao.IInstituteTypeDAO;
import com.seeka.app.dao.IInstituteVideoDao;
import com.seeka.app.dao.IInstituteWorldRankingHistoryDAO;
import com.seeka.app.dao.ServiceDetailsDAO;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ElasticSearchDTO;
import com.seeka.app.dto.InstituteDetailsGetRequest;
import com.seeka.app.dto.InstituteElasticSearchDTO;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteGetRequestDto;
import com.seeka.app.dto.InstituteMedia;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.dto.PaginationUtilDto;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.enumeration.ImageCategory;
import com.seeka.app.enumeration.SeekaEntityType;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.util.CDNServerUtil;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.DateUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@Service
@Transactional
public class InstituteService implements IInstituteService {

	@Autowired
	private IInstituteDAO dao;

	@Autowired
	private ICountryDAO countryDAO;

	@Autowired
	private IInstituteWorldRankingHistoryDAO institudeWorldRankingHistoryDAO;

	@Autowired
	private IInstituteDomesticRankingHistoryDAO instituteDomesticRankingHistoryDAO;

	@Autowired
	private ICityDAO cityDAO;

	@Autowired
	private IInstituteTypeDAO instituteTypeDAO;

	@Autowired
	private IInstituteVideoDao instituteVideoDao;

	@Autowired
	private ServiceDetailsDAO serviceDetailsDAO;

	@Autowired
	private IAccreditedInstituteDetailDao accreditedInstituteDetailDao;

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

	@Autowired
	private IFacultyService iFacultyService;

	@Autowired
	private IFacultyLevelService iFacultyLevelService;

	@Autowired
	private LevelService levelService;

	@Autowired
	private IInstituteLevelService iInstituteLevelService;

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
			final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking, final String campusType) {
		return dao.getAllInstitutesByFilter(filterObj, sortByField, sortByType, searchKeyword, startIndex, cityId, instituteTypeId, isActive, updatedOn,
				fromWorldRanking, toWorldRanking, campusType);
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
			if (institute.getCountry() != null) {
				instituteResponseDTO.setCountryId(institute.getCountry().getId());
				instituteResponseDTO.setCountryName(institute.getCountry().getName());
			}
			if (institute.getCity() != null) {
				instituteResponseDTO.setCityId(institute.getCity().getId());
				instituteResponseDTO.setCityName(institute.getCity().getName());
			}
			instituteResponseDTO.setWorldRanking(institute.getWorldRanking());
			instituteResponseDTO.setLocation(institute.getLatitute() + "," + institute.getLongitude());
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(instituteResponseDTO.getId(), ImageCategory.INSTITUTE.toString(), null,
					"en");
			instituteResponseDTO.setStorageList(storageDTOList);
			instituteResponseDTOList.add(instituteResponseDTO);
		}
		return instituteResponseDTOList;
	}

	@Override
	public List<InstituteResponseDto> getInstitudeByCityId(final String cityId) {
		return dao.getInstitudeByCityId(cityId);
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
	public Map<String, Object> save(final List<InstituteRequestDto> instituteRequests) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<InstituteElasticSearchDTO> instituteElasticDtoList = new ArrayList<>();
			for (InstituteRequestDto instituteRequest : instituteRequests) {
				Institute institute = saveInstitute(instituteRequest, null);
				InstituteElasticSearchDTO instituteElasticSearchDto = new InstituteElasticSearchDTO();
				if (instituteRequest.getDomesticRanking() != null) {
					saveDomesticRankingHistory(institute, null);
				}
				if (instituteRequest.getWorldRanking() != null) {
					saveWorldRankingHistory(institute, null);
				}
				if (instituteRequest.getInstituteMedias() != null && !instituteRequest.getInstituteMedias().isEmpty()) {
					saveInstituteYoutubeVideos(instituteRequest.getInstituteMedias(), institute);
				}
				if (instituteRequest.getFacultyIds() != null && !instituteRequest.getFacultyIds().isEmpty()) {
					Map<String, String> facultyIdNameMap = saveFacultyLevel(institute, instituteRequest.getFacultyIds());
					List<String> facultyNames = new ArrayList<>(facultyIdNameMap.values());
					instituteElasticSearchDto.setFacultyNames(facultyNames);
				}
				if (instituteRequest.getLevelIds() != null && !instituteRequest.getLevelIds().isEmpty()) {
					Map<String, String> levelNameLevelCodeMap = saveInstituteLevel(institute, instituteRequest.getLevelIds());
					instituteElasticSearchDto.setLevelName(new ArrayList<>(levelNameLevelCodeMap.keySet()));
					instituteElasticSearchDto.setLevelCode(new ArrayList<>(levelNameLevelCodeMap.values()));
				}

				BeanUtils.copyProperties(institute, instituteElasticSearchDto);
				instituteElasticSearchDto.setCountryName(institute.getCountry() != null ? institute.getCountry().getName() : null);
				instituteElasticSearchDto.setCityName(institute.getCity() != null ? institute.getCity().getName() : null);
				instituteElasticSearchDto.setInstituteTypeName(institute.getInstituteType() != null ? institute.getInstituteType().getName() : null);
				instituteElasticSearchDto.setIntakes(instituteRequest.getIntakes());
				instituteElasticDtoList.add(instituteElasticSearchDto);
			}

			elasticSearchService.saveInsituteOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_INSTITUTE, SeekaEntityType.INSTITUTE.name().toLowerCase(),
					instituteElasticDtoList, IConstant.ELASTIC_SEARCH);

			response.put("message", "Institute saved successfully");
			response.put("status", HttpStatus.OK.value());
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	private void saveWorldRankingHistory(final Institute institute, final Institute oldInstitute) {
		InstituteWorldRankingHistory worldRanking = new InstituteWorldRankingHistory();
		worldRanking.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		worldRanking.setCreatedBy(institute.getCreatedBy());
		worldRanking.setWorldRanking(institute.getWorldRanking());
		if (oldInstitute != null) {
			worldRanking.setInstitute(oldInstitute);
		} else {
			worldRanking.setInstitute(institute);
		}
		institudeWorldRankingHistoryDAO.save(worldRanking);

	}

	private void saveDomesticRankingHistory(final Institute institute, final Institute oldInstitute) {
		InstituteDomesticRankingHistory domesticRanking = new InstituteDomesticRankingHistory();
		domesticRanking.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
		domesticRanking.setCreatedBy(institute.getCreatedBy());
		domesticRanking.setDomesticRanking(institute.getDomesticRanking());
		if (oldInstitute != null) {
			domesticRanking.setInstitute(oldInstitute);
		} else {
			domesticRanking.setInstitute(institute);
		}
		instituteDomesticRankingHistoryDAO.save(domesticRanking);
	}

	private Map<String, String> saveInstituteLevel(final Institute institute, final List<String> levelIds) {
		iInstituteLevelService.deleteInstituteLevel(institute.getId());
		Map<String, String> levelNameLevelCodeMap = new HashMap<>(); // contains Map<LevelName, LevelCode>

		for (String levelId : levelIds) {
			Level level = levelService.get(levelId);
			InstituteLevel instituteLevel = new InstituteLevel();
			instituteLevel.setLevel(level);
			instituteLevel.setCountry(institute.getCountry());
			instituteLevel.setCity(institute.getCity());
			instituteLevel.setInstitute(institute);
			instituteLevel.setCreatedBy("API");
			instituteLevel.setCreatedOn(new Date());
			instituteLevel.setUpdatedBy("API");
			instituteLevel.setUpdatedOn(new Date());
			instituteLevel.setIsActive(true);
			levelNameLevelCodeMap.put(level.getName(), level.getCode());
			iInstituteLevelService.save(instituteLevel);
		}
		return levelNameLevelCodeMap;
	}

	private Map<String, String> saveFacultyLevel(final Institute institute, final List<String> facultyIds) {
		iFacultyLevelService.deleteFacultyLevel(institute.getId());
		Map<String, String> facultyIdNameMap = new HashMap<>();
		for (String facultyId : facultyIds) {
			Faculty faculty = iFacultyService.get(facultyId);
			FacultyLevel facultyLevel = new FacultyLevel();
			facultyLevel.setFaculty(faculty);
			facultyLevel.setInstitute(institute);
			facultyLevel.setCreatedBy("API");
			facultyLevel.setCreatedOn(new Date());
			facultyLevel.setUpdatedBy("API");
			facultyLevel.setUpdatedOn(new Date());
			facultyLevel.setIsActive(true);
			facultyIdNameMap.put(facultyId, faculty.getName());
			iFacultyLevelService.save(facultyLevel);
		}
		return facultyIdNameMap;
	}

	@Override
	public Map<String, Object> update(final List<InstituteRequestDto> instituteRequests, @Valid final String id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<InstituteElasticSearchDTO> instituteElasticDtoList = new ArrayList<>();
			for (InstituteRequestDto instituteRequest : instituteRequests) {
				Institute oldInstitute = dao.get(id);
				Institute newInstitute = new Institute();
				InstituteElasticSearchDTO instituteElasticSearchDto = new InstituteElasticSearchDTO();
				BeanUtils.copyProperties(instituteRequest, newInstitute);
				if (instituteRequest.getDomesticRanking() != null && !instituteRequest.getDomesticRanking().equals(oldInstitute.getDomesticRanking())) {
					saveDomesticRankingHistory(newInstitute, oldInstitute);
				}
				if (instituteRequest.getWorldRanking() != null && !instituteRequest.getWorldRanking().equals(oldInstitute.getWorldRanking())) {
					saveWorldRankingHistory(newInstitute, oldInstitute);
				}
				Institute institute = saveInstitute(instituteRequest, id);
				if (instituteRequest.getInstituteMedias() != null && !instituteRequest.getInstituteMedias().isEmpty()) {
					saveInstituteYoutubeVideos(instituteRequest.getInstituteMedias(), institute);
				}
				if (instituteRequest.getFacultyIds() != null && !instituteRequest.getFacultyIds().isEmpty()) {
					Map<String, String> facultyIdNameMap = saveFacultyLevel(institute, instituteRequest.getFacultyIds());
					List<String> facultyNames = (List<String>) facultyIdNameMap.values();
					instituteElasticSearchDto.setFacultyNames(facultyNames);
				}
				if (instituteRequest.getLevelIds() != null && !instituteRequest.getLevelIds().isEmpty()) {
					Map<String, String> levelNameLevelCodeMap = saveInstituteLevel(institute, instituteRequest.getLevelIds());
					instituteElasticSearchDto.setLevelName(new ArrayList<>(levelNameLevelCodeMap.keySet()));
					instituteElasticSearchDto.setLevelCode(new ArrayList<>(levelNameLevelCodeMap.values()));
				}

				BeanUtils.copyProperties(instituteRequest, instituteElasticSearchDto);
				instituteElasticSearchDto.setCountryName(institute.getCountry() != null ? institute.getCountry().getName() : null);
				instituteElasticSearchDto.setCityName(institute.getCity() != null ? institute.getCity().getName() : null);
				instituteElasticSearchDto.setInstituteTypeName(institute.getInstituteType() != null ? institute.getInstituteType().getName() : null);
				instituteElasticSearchDto.setIntakes(instituteRequest.getIntakes());
				instituteElasticDtoList.add(instituteElasticSearchDto);
			}
			elasticSearchService.updateInsituteOnElasticSearch(IConstant.ELASTIC_SEARCH_INDEX_INSTITUTE, SeekaEntityType.INSTITUTE.name().toLowerCase(),
					instituteElasticDtoList, IConstant.ELASTIC_SEARCH);
			response.put("message", "Institute update successfully");
			response.put("status", HttpStatus.OK.value());
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		return response;
	}

	private void saveInstituteYoutubeVideos(final List<InstituteMedia> instituteMedias, final Institute institute) {
		for (InstituteMedia instituteMedia : instituteMedias) {
			InstituteVideos instituteVideo = new InstituteVideos();
			instituteVideo.setInstitute(institute);
			instituteVideo.setYouTubeChannel(instituteMedia.getYouTubeChannel());
			instituteVideo.setYouTubeId1(instituteMedia.getYouTubeId1());
			instituteVideo.setYouTubeId2(instituteMedia.getYouTubeId2());
			instituteVideo.setYouTubeId3(instituteMedia.getYouTubeId3());
			instituteVideo.setYouTubeId4(instituteMedia.getYouTubeId4());
			instituteVideo.setCreatedBy("API");
			instituteVideo.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			instituteVideo.setUpdatedBy("API");
			instituteVideo.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
			instituteVideoDao.save(instituteVideo);
		}
	}

	private List<InstituteMedia> getInstituteMedia(final String instituteId) {
		List<InstituteVideos> instituteVideos = instituteVideoDao.findByInstituteId(instituteId);
		List<InstituteMedia> instituteMedias = new ArrayList<>();
		for (InstituteVideos instituteVideos2 : instituteVideos) {
			InstituteMedia instituteMedia = new InstituteMedia();
			BeanUtils.copyProperties(instituteVideos2, instituteMedia);
			instituteMedias.add(instituteMedia);
		}
		return instituteMedias;
	}

	private Institute saveInstitute(@Valid final InstituteRequestDto instituteRequest, final String id) throws ValidationException {
		Institute institute = null;
		if (id != null) {
			institute = dao.get(id);
		} else {
			institute = new Institute();
			institute.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			institute.setCreatedBy(instituteRequest.getCreatedBy());
		}
		institute.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
		institute.setUpdatedBy(instituteRequest.getUpdatedBy());
		institute.setName(instituteRequest.getName());
		institute.setDescription(instituteRequest.getDescription());
		if (instituteRequest.getCountryId() != null) {
			institute.setCountry(countryDAO.get(instituteRequest.getCountryId()));
		} else {
			throw new ValidationException("countryId is required");
		}
		if (instituteRequest.getCountryId() != null) {
			institute.setCity(cityDAO.get(instituteRequest.getCityId()));
		} else {
			throw new ValidationException("cityId is required");
		}

		if (instituteRequest.getInstituteTypeId() != null) {
			institute.setInstituteType(instituteTypeDAO.get(instituteRequest.getInstituteTypeId()));
		} else {
			throw new ValidationException("instituteTypeId is required");
		}

		institute.setIsActive(true);
		institute.setDomesticRanking(instituteRequest.getDomesticRanking());
		institute.setWorldRanking(instituteRequest.getWorldRanking());
		institute.setWebsite(instituteRequest.getWebsite());
		if (instituteRequest.getInstituteCategoryTypeId() != null) {
			institute.setInstituteCategoryType(getInstituteCategoryType(instituteRequest.getInstituteCategoryTypeId()));
		} else {
			throw new ValidationException("instituteCategoryTypeId is required");
		}

		institute.setCampusType(instituteRequest.getCampusType());
		institute.setAddress(instituteRequest.getAddress());
		institute.setEmail(instituteRequest.getEmail());
		institute.setPhoneNumber(instituteRequest.getPhoneNumber());
		institute.setLatitute(instituteRequest.getLatitude());
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
			dao.update(institute);
			/**
			 * Add this institute in elastic search
			 */
			InstituteElasticSearchDTO instituteElasticDto = new InstituteElasticSearchDTO();
			List<InstituteElasticSearchDTO> instituteElasticDTOList = new ArrayList<>();
			BeanUtils.copyProperties(institute, instituteElasticDto);
			instituteElasticDTOList.add(instituteElasticDto);
		} else {
			dao.save(institute);
		}
		if (instituteRequest.getOfferService() != null && !instituteRequest.getOfferService().isEmpty()) {
			saveInstituteService(institute, instituteRequest.getOfferService());
		}
		if (instituteRequest.getAccreditation() != null && !instituteRequest.getAccreditation().isEmpty()) {
			saveAccreditedInstituteDetails(institute, instituteRequest.getAccreditation());
		}
		if (instituteRequest.getIntakes() != null && !instituteRequest.getIntakes().isEmpty()) {
			saveIntakesInstituteDetails(institute, instituteRequest.getIntakes());
		}
		return institute;
	}

	private void saveIntakesInstituteDetails(final Institute institute, final List<String> intakes) {
		dao.deleteInstituteIntakeById(institute.getId());
		for (String intakeId : intakes) {
			InstituteIntake instituteIntake = new InstituteIntake();
			instituteIntake.setEntityId(institute.getId());
			instituteIntake.setEntityType("INSTITUTE");
			instituteIntake.setIntake(intakeId);
			dao.saveInstituteIntake(instituteIntake);
		}
	}

	private void saveAccreditedInstituteDetails(final Institute institute, final List<String> accreditation) {
		accreditedInstituteDetailDao.deleteAccreditedInstitueDetailByEntityId(institute.getId());
		for (String accreditedInstituteDetail2 : accreditation) {
			AccreditedInstituteDetail accreditedInstituteDetail = new AccreditedInstituteDetail();
			accreditedInstituteDetail.setEntityId(institute.getId());
			accreditedInstituteDetail.setEntityType("INSTITUTE");
			accreditedInstituteDetail.setAccreditedInstituteId(accreditedInstituteDetail2);
			accreditedInstituteDetailDao.addAccreditedInstituteDetail(accreditedInstituteDetail);
		}
	}

	private void saveInstituteService(final Institute institute, final List<String> offerService) {
		dao.deleteInstituteService(institute.getId());
		for (String id : offerService) {
			com.seeka.app.bean.Service service = serviceDetailsDAO.getServiceById(id);
			com.seeka.app.bean.InstituteService instituteServiceDetails = new com.seeka.app.bean.InstituteService();
			instituteServiceDetails.setInstitute(institute);
			instituteServiceDetails.setService(service);
			instituteServiceDetails.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
			instituteServiceDetails.setIsActive(true);
			instituteServiceDetails.setCreatedBy("AUTO");
			instituteServiceDetails.setUpdatedBy("AUTO");
			instituteServiceDetails.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
			dao.saveInstituteserviceDetails(instituteServiceDetails);
		}
	}

	private InstituteCategoryType getInstituteCategoryType(final String instituteCategoryTypeId) {
		return dao.getInstituteCategoryType(instituteCategoryTypeId);
	}

	@Override
	public Map<String, Object> getAllInstitute(final Integer pageNumber, final Integer pageSize) {
		Map<String, Object> response = new HashMap<>();
		List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = dao.findTotalCount();
			int startIndex;
			if (pageNumber > 1) {
				startIndex = (pageNumber - 1) * pageSize + 1;
			} else {
				startIndex = pageNumber;
			}
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			List<Institute> institutes = dao.getAll(startIndex, pageSize);
			for (Institute institute : institutes) {
				instituteGetRequestDtos.add(getInstitute(institute));
			}
			if (institutes != null && !institutes.isEmpty()) {
				response.put("message", "Institute fetched successfully");
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("message", IConstant.INSTITUDE_NOT_FOUND);
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("data", instituteGetRequestDtos);
		response.put("totalCount", totalCount);
		response.put("pageNumber", paginationUtilDto.getPageNumber());
		response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		response.put("hasNextPage", paginationUtilDto.isHasNextPage());
		response.put("totalPages", paginationUtilDto.getTotalPages());
		return response;
	}

	private InstituteGetRequestDto getInstitute(final Institute institute) {
		InstituteGetRequestDto dto = new InstituteGetRequestDto();
		dto.setId(institute.getId());
		dto.setCity(institute.getCity());
		dto.setCountry(institute.getCountry());
		dto.setInstituteType(institute.getInstituteType());
		dto.setName(institute.getName());
		dto.setIsActive(institute.getIsActive());
		dto.setCreatedOn(institute.getCreatedOn());
		dto.setCreatedBy(institute.getCreatedBy());
		dto.setUpdatedOn(institute.getUpdatedOn());
		dto.setUpdatedBy(institute.getUpdatedBy());
		dto.setInstituteDetails(getInstituteDetails(institute.getId()));
		dto.setInstituteYoutubes(getInstituteYoutube(institute.getCountry(), institute.getName()));
		dto.setCourseCount(institute.getCourseCount());
		dto.setCampusType(institute.getCampusType());
		return dto;
	}

	private List<String> getInstituteYoutube(final Country country, final String instituteName) {
		List<String> images = new ArrayList<>();
		if (country != null && country.getName() != null) {
			for (int i = 1; i <= 20; i++) {
				images.add(CDNServerUtil.getInstituteImages(country.getName(), instituteName, i));
			}
		}
		return images;
	}

	private List<InstituteDetailsGetRequest> getInstituteDetails(final String id) {
		List<InstituteDetailsGetRequest> instituteDetailsGetRequests = new ArrayList<>();
		Institute dto = dao.get(id);
		InstituteDetailsGetRequest instituteDetail = new InstituteDetailsGetRequest();
		if (dto.getLatitute() != null) {
			instituteDetail.setLatitute(String.valueOf(dto.getLatitute()));
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
	}

	@Override
	public List<InstituteRequestDto> getById(final String id) throws ValidationException {
		InstituteRequestDto instituteRequestDto = null;
		List<InstituteRequestDto> instituteRequestDtos = new ArrayList<>();
		Institute institute = dao.get(id);
		if (institute == null) {
			throw new ValidationException("Institute not found for id" + id);
		}
		instituteRequestDto = CommonUtil.convertInstituteBeanToInstituteRequestDto(institute);
		instituteRequestDto.setOfferService(getOfferServices(id));
		instituteRequestDto.setAccreditation(getAccreditation(id));
		instituteRequestDto.setInstituteMedias(getInstituteMedia(id));
		instituteRequestDto.setIntakes(getIntakes(id));
		instituteRequestDto.setFacultyIds(getFacultyLevelData(id));
		instituteRequestDto.setLevelIds(getInstituteLevelData(id));
		if (institute.getInstituteCategoryType() != null) {
			instituteRequestDto.setInstituteCategoryTypeId(institute.getInstituteCategoryType().getId());
		}

		instituteRequestDtos.add(instituteRequestDto);
		if (institute != null && institute.getCampusType() != null && institute.getCampusType().equals("PRIMARY")) {
			if (institute.getCountry() != null && institute.getCity() != null && institute.getName() != null) {
				List<Institute> institutes = dao.getSecondayCampus(institute.getCountry().getId(), institute.getCity().getId(), institute.getName());
				for (Institute campus : institutes) {
					instituteRequestDto = CommonUtil.convertInstituteBeanToInstituteRequestDto(campus);
					instituteRequestDto.setOfferService(getOfferServices(campus.getId()));
					instituteRequestDto.setAccreditation(getAccreditation(campus.getId()));
					instituteRequestDto.setInstituteMedias(getInstituteMedia(id));
					instituteRequestDto.setIntakes(getIntakes(campus.getId()));
					instituteRequestDto.setFacultyIds(getFacultyLevelData(campus.getId()));
					instituteRequestDto.setLevelIds(getInstituteLevelData(campus.getId()));
					if (institute.getInstituteCategoryType() != null) {
						instituteRequestDto.setInstituteCategoryTypeId(institute.getInstituteCategoryType().getId());
					}
					instituteRequestDtos.add(instituteRequestDto);
				}
			}
		}
		return instituteRequestDtos;
	}

	private List<String> getFacultyLevelData(final String id) {
		List<FacultyLevel> facultyLevel = iFacultyLevelService.getAllFacultyLevelByInstituteId(id);
		if (facultyLevel != null && !facultyLevel.isEmpty()) {
			return facultyLevel.stream().map(i -> i.getFaculty().getId()).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	private List<String> getInstituteLevelData(final String id) {
		List<InstituteLevel> instituteLevel = iInstituteLevelService.getAllLevelByInstituteId(id);
		if (instituteLevel != null && !instituteLevel.isEmpty()) {
			return instituteLevel.stream().map(i -> i.getLevel().getId()).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	private List<String> getIntakes(@Valid final String id) {
		return dao.getIntakesById(id);
	}

	private List<String> getAccreditation(@Valid final String id) {
		return accreditedInstituteDetailDao.getAccreditation(id);
	}

	private List<String> getOfferServices(final String id) {
		return serviceDetailsDAO.getServicesById(id);
	}

	@Override
	public Map<String, Object> searchInstitute(@Valid final String searchText) {
		Map<String, Object> response = new HashMap<>();
		List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
		try {
			if (searchText != null && !searchText.isEmpty()) {
				String sqlQuery = "select inst.id, inst.name , inst.country_id , inst.city_id, inst.institute_type_id FROM institute inst where inst.is_active = 1  ";
				String countryId = searchText.split(",")[0];
				String name = searchText.split(",")[1];
				String cityId = searchText.split(",")[2];
				String worldRanking = searchText.split(",")[3];
				String typeId = searchText.split(",")[4];
				String postDate = searchText.split(",")[5];
				if (countryId != null && !countryId.isEmpty()) {
					sqlQuery += " and inst.country_id = " + Integer.valueOf(countryId);
				} else if (name != null && !name.isEmpty()) {
					sqlQuery += " and inst.name = '" + name + "'";
				} else if (cityId != null && !cityId.isEmpty()) {
					sqlQuery += " and inst.city_id = " + Integer.valueOf(cityId);
				} else if (worldRanking != null && !worldRanking.isEmpty()) {
					sqlQuery += " and inst.world_ranking = " + Integer.valueOf(worldRanking);
				} else if (typeId != null && !typeId.isEmpty()) {
					sqlQuery += " and inst.institute_type_id = " + Integer.valueOf(typeId);
				} else if (postDate != null && !postDate.isEmpty()) {

				}
				List<Institute> institutes = dao.searchInstitute(sqlQuery);
				for (Institute institute : institutes) {
					instituteGetRequestDtos.add(getInstitute(institute));
				}
			} else {
				response.put("message", IConstant.INSTITUDE_NOT_FOUND);
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("message", "Institute fetched successfully");
		response.put("status", HttpStatus.OK.value());
		response.put("institutes", instituteGetRequestDtos);
		return response;
	}

	@Override
	public Map<String, Object> instituteFilter(final InstituteFilterDto instituteFilterDto) {
		Map<String, Object> response = new HashMap<>();
		List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = dao.findTotalCountFilterInstitute(instituteFilterDto);
			int startIndex = (instituteFilterDto.getPageNumber() - 1) * instituteFilterDto.getMaxSizePerPage();
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, instituteFilterDto.getMaxSizePerPage(), totalCount);
			List<Institute> institutes = dao.instituteFilter(startIndex, instituteFilterDto.getMaxSizePerPage(), instituteFilterDto);
			for (Institute institute : institutes) {
				instituteGetRequestDtos.add(getInstitute(institute));
			}
			if (institutes != null && !institutes.isEmpty()) {
				response.put("message", "Institute fetched successfully");
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("message", IConstant.INSTITUDE_NOT_FOUND);
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("data", instituteGetRequestDtos);
		response.put("totalCount", totalCount);
		response.put("pageNumber", paginationUtilDto.getPageNumber());
		response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		response.put("hasNextPage", paginationUtilDto.isHasNextPage());
		response.put("totalPages", paginationUtilDto.getTotalPages());
		return response;
	}

	@Override
	public Map<String, Object> autoSearch(final Integer pageNumber, final Integer pageSize, final String searchKey) {
		Map<String, Object> response = new HashMap<>();
		List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
		int totalCount = 0;
		PaginationUtilDto paginationUtilDto = null;
		try {
			totalCount = dao.findTotalCountForInstituteAutosearch(searchKey);
			int startIndex = (pageNumber - 1) * pageSize;
			paginationUtilDto = PaginationUtil.calculatePagination(startIndex, pageSize, totalCount);
			List<Institute> institutes = dao.autoSearch(startIndex, pageSize, searchKey);
			for (Institute institute : institutes) {
				instituteGetRequestDtos.add(getInstitute(institute));
			}
			if (institutes != null && !institutes.isEmpty()) {
				response.put("message", "Institute fetched successfully");
				response.put("status", HttpStatus.OK.value());
			} else {
				response.put("message", IConstant.INSTITUDE_NOT_FOUND);
				response.put("status", HttpStatus.NOT_FOUND.value());
			}
		} catch (Exception exception) {
			response.put("message", exception.getCause());
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		response.put("data", instituteGetRequestDtos);
		response.put("totalCount", totalCount);
		response.put("pageNumber", paginationUtilDto.getPageNumber());
		response.put("hasPreviousPage", paginationUtilDto.isHasPreviousPage());
		response.put("hasNextPage", paginationUtilDto.isHasNextPage());
		response.put("totalPages", paginationUtilDto.getTotalPages());
		return response;
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
	public List<Institute> ratingWiseInstituteListByCountry(final Country country) {
		return dao.ratingWiseInstituteListByCountry(country);
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
			final Boolean isActive, final Date updatedOn, final Integer fromWorldRanking, final Integer toWorldRanking, final String campusType) {
		return dao.getCountOfInstitute(courseSearchDto, searchKeyword, cityId, instituteTypeId, isActive, updatedOn, fromWorldRanking, toWorldRanking,
				campusType);
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
	public InstituteDomesticRankingHistory getHistoryOfDomesticRanking(final String instituteId) {
		return instituteDomesticRankingHistoryDAO.getHistoryOfDomesticRanking(instituteId);
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
	public List<NearestInstituteDTO> getNearestInstituteList(final Integer pageNumber, final Integer pageSize, final Double latitude, final Double longitude)
			throws ValidationException {
		int startIndex = (pageNumber - 1) * pageSize;
		List<NearestInstituteDTO> nearestInstituteDTOs = dao.getNearestInstituteList(startIndex, pageSize, latitude, longitude);
		for (NearestInstituteDTO nearestInstituteDTO : nearestInstituteDTOs) {
			List<StorageDto> storageDTOList = iStorageService.getStorageInformation(nearestInstituteDTO.getInstituteId(), ImageCategory.INSTITUTE.toString(),
					Type.LOGO.name(), "en");
			nearestInstituteDTO.setInstituteLogoImages(storageDTOList);
		}
		return nearestInstituteDTOs;
	}
}
