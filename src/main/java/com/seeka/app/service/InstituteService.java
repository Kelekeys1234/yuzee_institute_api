package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteCampus;
import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.bean.InstituteVideos;
import com.seeka.app.dao.ICityDAO;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.IInstituteDAO;
import com.seeka.app.dao.IInstituteDetailsDAO;
import com.seeka.app.dao.IInstituteTypeDAO;
import com.seeka.app.dao.IInstituteVideoDao;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.InstituteCampusDto;
import com.seeka.app.dto.InstituteDetailsGetRequest;
import com.seeka.app.dto.InstituteGetRequestDto;
import com.seeka.app.dto.InstituteMedia;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.PaginationUtilDto;
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
    private ICityDAO cityDAO;

    @Autowired
    private IInstituteTypeDAO instituteTypeDAO;

    @Autowired
    private IInstituteDetailsDAO instituteDetailsDAO;

    @Autowired
    private IInstituteVideoDao instituteVideoDao;

    @Override
    public void save(Institute institute) {
        Date today = new Date();
        institute.setCreatedOn(today);
        institute.setUpdatedOn(today);
        dao.save(institute);
    }

    @Override
    public void update(Institute institute) {
        Date today = new Date();
        institute.setUpdatedOn(today);
        dao.update(institute);
    }

    @Override
    public Institute get(BigInteger id) {
        return dao.get(id);
    }

    @Override
    public List<Institute> getAllInstituteByCountry(BigInteger countryId) {
        return dao.getAllInstituteByCountry(countryId);
    }

    @Override
    public List<Institute> getAll() {
        return dao.getAll();
    }

    @Override
    public List<InstituteSearchResultDto> getInstitueBySearchKey(String searchKey) {
        return dao.getInstitueBySearchKey(searchKey);
    }

    @Override
    public List<InstituteResponseDto> getAllInstitutesByFilter(CourseSearchDto filterObj) {
        return dao.getAllInstitutesByFilter(filterObj);
    }

    @Override
    public InstituteResponseDto getInstituteByID(BigInteger instituteId) {
        return dao.getInstituteByID(instituteId);
    }

    @Override
    public List<InstituteResponseDto> getInstitudeByCityId(BigInteger cityId) {
        return dao.getInstitudeByCityId(cityId);
    }

    @Override
    public List<InstituteResponseDto> getInstituteByListOfCityId(String cityId) {
        String[] citiesArray = cityId.split(",");
        String tempList = "";
        for (String id : citiesArray) {
            tempList = tempList + "," + "'" + new BigInteger(id) + "'";
        }
        return dao.getInstituteByListOfCityId(tempList.substring(1, tempList.length()));
    }

    @Override
    public Map<String, Object> save(@Valid InstituteRequestDto instituteRequest) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            Institute institute = saveInstitute(instituteRequest);
            saveInstituteCampus(getCampusDto(instituteRequest), institute, 1);
            for (InstituteCampusDto campusDto : instituteRequest.getInstituteCampus()) {
                saveInstituteCampus(campusDto, institute, 2);
            }
            if (instituteRequest.getInstituteMedias() != null && !instituteRequest.getInstituteMedias().isEmpty()) {
                saveInstituteYoutubeVideos(instituteRequest.getInstituteMedias(), institute);
            }
            response.put("message", "Institute saved successfully");
            response.put("status", HttpStatus.OK.value());
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }

    private void saveInstituteCampus(InstituteCampusDto campusDto, Institute institute, int campusType) {
        InstituteCampus campus = new InstituteCampus();
        campus.setInstitute(institute);
        campus.setCampusType(campusType);
        campus.setAddress(campusDto.getAddress());
        campus.setEmail(campusDto.getEmail());
        campus.setPhoneNumber(campusDto.getPhoneNumber());
        campus.setLatitute(campusDto.getLatitute());
        campus.setLongitute(campusDto.getLongitute());
        campus.setTotalStudent(campusDto.getTotalStudent());
        campus.setOpeningFrom(campusDto.getOpeningFrom());
        campus.setOpeningTo(campusDto.getOpeningTo());
        campus.setOfferService(campusDto.getOfferService());
        dao.save(campus);
    }

    private InstituteCampusDto getCampusDto(InstituteRequestDto institute) {
        InstituteCampusDto dto = new InstituteCampusDto();
        dto.setAddress(institute.getAddress());
        dto.setEmail(institute.getEmail());
        if (institute.getLatitute() != null) {
            dto.setLatitute(Double.parseDouble(institute.getLatitute()));
        }
        if (institute.getLongitude() != null) {
            dto.setLongitute(Double.parseDouble(institute.getLongitude()));
        }
        dto.setTotalStudent(institute.getTotalStudent());
        dto.setPhoneNumber(institute.getPhoneNumber());
        dto.setOpeningFrom(institute.getClosingHour());
        dto.setOpeningTo(institute.getOpeningHour());
        return dto;
    }

    private void saveInstituteYoutubeVideos(List<InstituteMedia> instituteMedias, Institute institute) {
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

    private void saveInstituteDetails(@Valid InstituteRequestDto instituteRequest, Institute institute) {
        InstituteDetails instituteDetail = new InstituteDetails();
        instituteDetail.setLatitute(instituteRequest.getLatitute());
        instituteDetail.setLongitude(instituteRequest.getLongitude());
        instituteDetail.setTotalStudent(instituteRequest.getTotalStudent());
        instituteDetail.setWorldRanking(instituteRequest.getWorldRanking());
        instituteDetail.setAccreditation(instituteRequest.getAccreditation());
        instituteDetail.setAverageCostFrom(instituteRequest.getAverageCostFrom());
        instituteDetail.setAverageCostTo(instituteRequest.getAverageCostTo());
        instituteDetail.setEnrolment(instituteRequest.getEnrolment());
        instituteDetail.setTuitionFessPaymentPlan(instituteRequest.getTuitionFessPaymentPlan());
        instituteDetail.setScholarshipFinancingAssistance(instituteRequest.getScholarshipFinancingAssistance());
        instituteDetail.setOpeningHour(instituteRequest.getOpeningHour());
        instituteDetail.setClosingHour(instituteRequest.getClosingHour());
        instituteDetail.setEmail(instituteRequest.getEmail());
        instituteDetail.setPhoneNumber(instituteRequest.getPhoneNumber());
        instituteDetail.setWebsite(instituteRequest.getWebsite());
        instituteDetail.setAddress(instituteRequest.getAddress());
        instituteDetailsDAO.save(instituteDetail);
    }

    private Institute saveInstitute(@Valid InstituteRequestDto instituteRequest) {
        Institute institute = new Institute();
        institute.setAccreditation(instituteRequest.getAccreditation());
        institute.setName(instituteRequest.getInstituteName());
        institute.setDescription(instituteRequest.getDescription());
        institute.setCountry(countryDAO.get(instituteRequest.getCountryId()));
        institute.setCity(cityDAO.get(instituteRequest.getCityId()));
        institute.setInstituteType(instituteTypeDAO.get(instituteRequest.getInstituteTypeId()));
        institute.setIsActive(true);
        institute.setCreatedBy("API");
        institute.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        institute.setUpdatedBy("API");
        institute.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        institute.setWorldRanking(instituteRequest.getWorldRanking());
        institute.setWebsite(instituteRequest.getWebsite());
        dao.save(institute);
        return institute;
    }

    
    @Override
    public Map<String, Object> getAllInstitute(Integer pageNumber, Integer pageSize) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<InstituteGetRequestDto> instituteGetRequestDtos = new ArrayList<>();
        int totalCount = 0;
        PaginationUtilDto paginationUtilDto = null;
        try {
            totalCount = dao.findTotalCount();
            int startIndex;
            if (pageNumber > 1) {
                startIndex = ((pageNumber - 1) * pageSize) + 1;
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

    private InstituteGetRequestDto getInstitute(Institute institute) {
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
        return dto;
    }

    private List<String> getInstituteYoutube(Country country, String instituteName) {
        List<String> images = new ArrayList<>();
        if (country != null && country.getName() != null) {
            for (int i = 1; i <= 20; i++) {
                images.add(CDNServerUtil.getInstituteImages(country.getName(), instituteName, i));
            }
        }
        return images;
    }

    private List<InstituteDetailsGetRequest> getInstituteDetails(BigInteger id) {
        List<InstituteDetailsGetRequest> instituteDetailsGetRequests = new ArrayList<>();
        Institute dto = dao.get(id);
        InstituteDetailsGetRequest instituteDetail = new InstituteDetailsGetRequest();
        instituteDetail.setLatitute(dto.getLatitute());
        instituteDetail.setLongitude(dto.getLongitude());
        instituteDetail.setTotalStudent(dto.getTotalStudent());
        instituteDetail.setWorldRanking(dto.getWorldRanking());
        instituteDetail.setAccreditation(dto.getAccreditation());
        instituteDetail.setEmail(dto.getEmail());
        instituteDetail.setPhoneNumber(dto.getPhoneNumber());
        instituteDetail.setWebsite(dto.getWebsite());
        instituteDetail.setAddress(dto.getAddress());
        instituteDetail.setAvgCostOfLiving(dto.getAvgCostOfLiving());
        instituteDetailsGetRequests.add(instituteDetail);
        return instituteDetailsGetRequests;
    }

    @Override
    public Map<String, Object> getById(@Valid BigInteger id) {
        Map<String, Object> response = new HashMap<String, Object>();
        InstituteGetRequestDto dto = null;
        InstituteRequestDto instituteRequestDto = null;
        List<InstituteCampusDto> instituteCampusDtoList = new ArrayList<>();
        try {
            Institute institute = dao.get(id);
            List<InstituteCampus> instituteCampusList = dao.getInstituteCampusByInstituteId(id);
            for(InstituteCampus campus : instituteCampusList){
                if(campus.getCampusType() == 2){
                    InstituteCampusDto campusDto = CommonUtil.convertInstituteCampusToInstituteCampusDto(campus); 
                    instituteCampusDtoList.add(campusDto);
                }else{
                    instituteRequestDto = CommonUtil.convertInstituteBeanToInstituteRequestDto(institute, instituteCampusList.get(0));
                }
            }
            if (institute == null) {
                response.put("message", "Institute not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            } else {
                dto = getInstitute(institute);
                response.put("message", "Institute fetched successfully");
                response.put("status", HttpStatus.OK.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
        response.put("instituteCampusList", instituteCampusDtoList);
        response.put("data", instituteRequestDto);
        return response;
    }

    @Override
    public Map<String, Object> searchInstitute(@Valid String searchText) {
        Map<String, Object> response = new HashMap<String, Object>();
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
    public Map<String, Object> update(InstituteRequestDto instituteRequest, @Valid BigInteger id) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            Institute institute = updateInstitute(instituteRequest, id);
            dao.delete(institute);
            saveInstituteCampus(getCampusDto(instituteRequest), institute, 1);
            for (InstituteCampusDto campusDto : instituteRequest.getInstituteCampus()) {
                saveInstituteCampus(campusDto, institute, 2);
            }
            if (instituteRequest.getInstituteMedias() != null && !instituteRequest.getInstituteMedias().isEmpty()) {
                saveInstituteYoutubeVideos(instituteRequest.getInstituteMedias(), institute);
            }
            response.put("message", "Institute update successfully");
            response.put("status", HttpStatus.OK.value());
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
    
    private Institute updateInstitute(@Valid InstituteRequestDto instituteRequest, BigInteger id) {
        Institute institute = new Institute();
        institute.setId(id);
        institute.setAccreditation(instituteRequest.getAccreditation());
        institute.setName(instituteRequest.getInstituteName());
        institute.setDescription(instituteRequest.getDescription());
        institute.setCountry(countryDAO.get(instituteRequest.getCountryId()));
        institute.setCity(cityDAO.get(instituteRequest.getCityId()));
        institute.setInstituteType(instituteTypeDAO.get(instituteRequest.getInstituteTypeId()));
        institute.setIsActive(true);
        institute.setCreatedBy("API");
        institute.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        institute.setUpdatedBy("API");
        institute.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        institute.setWorldRanking(instituteRequest.getWorldRanking());
        institute.setWebsite(instituteRequest.getWebsite());
        dao.update(institute);
        return institute;
    }
    
}
