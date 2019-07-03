package com.seeka.app.controller;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Institute;
import com.seeka.app.bean.InstituteDetails;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Service;
import com.seeka.app.bean.UserInfo;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.InstituteSearchResultDto;
import com.seeka.app.dto.PaginationDto;
import com.seeka.app.jobs.CountryUtil;
import com.seeka.app.service.IInstituteDetailsService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.IInstituteServiceDetailsService;
import com.seeka.app.service.IInstituteTypeService;
import com.seeka.app.service.IServiceDetailsService;
import com.seeka.app.service.IUserService;
import com.seeka.app.util.CDNServerUtil;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.PaginationUtil;

@RestController
@RequestMapping("/institute")
public class InstituteController {

    @Autowired
    private IInstituteService instituteService;

    @Autowired
    private IInstituteDetailsService instituteDetailsService;

    @Autowired
    private IInstituteTypeService instituteTypeService;

    @Autowired
    private IServiceDetailsService serviceDetailsService;

    @Autowired
    private IInstituteServiceDetailsService instituteServiceDetailsService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/type", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveInstituteType(@Valid @RequestBody InstituteType instituteTypeObj) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>(3);
        instituteTypeService.save(instituteTypeObj);
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("instituteTypeObj", instituteTypeObj);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody Institute instituteObj) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>(3);
        instituteService.save(instituteObj);
        if (null != instituteObj.getInstituteDetailsObj()) {
            InstituteDetails instituteDetails = instituteObj.getInstituteDetailsObj();
            instituteDetails.setInstituteId(instituteObj.getId());
            instituteDetailsService.save(instituteDetails);
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("instituteObj", instituteObj);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@Valid @PathVariable BigInteger id) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        Institute instituteObj = instituteService.get(id);
        if (instituteObj == null) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Invalid institue.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }
        CountryDto country = CountryUtil.getCountryByCountryId(instituteObj.getCountry().getId());
        instituteObj.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(country.getName(), instituteObj.getName()));
        instituteObj.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(country.getName(), instituteObj.getName()));
        InstituteDetails instituteDetailsObj = instituteDetailsService.get(id);
        if (null != instituteDetailsObj) {
            instituteObj.setInstituteDetailsObj(instituteDetailsObj);
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("instituteObj", instituteObj);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> search(@Valid @RequestParam("searchkey") String searchkey) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<InstituteSearchResultDto> instituteList = instituteService.getInstitueBySearchKey(searchkey);
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("instituteList", instituteList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/service/{instituteTypeId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> saveService(@PathVariable BigInteger instituteTypeId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Service> list = new ArrayList<>();
        String createdBy = "AUTO";
        Date createdOn = new Date();

        Service serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Visa Work Benefits");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Visa Work Benefits");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Employment and career development");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Employment and career development");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Counselling – personal and academic");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Counselling – personal and academic");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Study Library Support");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Study Library Support");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Health services");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Health services");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Disability Support");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Disability Support");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Childcare Centre");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Childcare Centre");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Cultural inclusion/anti-racism programs");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Cultural inclusion/anti-racism programs");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Technology Services");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Technology Services");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Accommodation");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Accommodation");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Medical");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Medical");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Legal Services");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Legal Services");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Accounting Services");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Accounting Services");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Bus");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Bus");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Train");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Train");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Airport Pickup");
        /// serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Airport Pickup");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Swimming pool");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Swimming pool");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Sports Center");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Sports Center");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Sport Teams");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Sport Teams");
        list.add(serviceObj);

        serviceObj = new Service();
        serviceObj.setCreatedBy(createdBy);
        serviceObj.setCreatedOn(createdOn);
        serviceObj.setDescription("Housing Services");
        // serviceObj.setInstituteTypeId(instituteTypeId);
        serviceObj.setIsActive(true);
        serviceObj.setIsDeleted(false);
        serviceObj.setName("Housing Services");
        list.add(serviceObj);

        for (Service serviceDetails : list) {
            try {
                serviceDetailsService.save(serviceDetails);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("serviceList", list);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/service/get", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllInstituteService() throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Service> list = serviceDetailsService.getAll();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("serviceList", list);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}/service", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllServicesByInstitute(@Valid @PathVariable BigInteger id) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<String> serviceNames = instituteServiceDetailsService.getAllServices(id);
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("serviceList", serviceNames);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getInstitutesBySearchFilters(@RequestBody CourseSearchDto request) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();

        if (request.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        UserInfo user = userService.get(request.getUserId());

        List<BigInteger> countryIds = request.getCountryIds();
        if (null == countryIds || countryIds.isEmpty()) {
            countryIds = new ArrayList<>();
        }
        if (null != user.getPreferredCountryId()) {
            countryIds.add(user.getPreferredCountryId());
            request.setCountryIds(countryIds);
        }

        List<InstituteResponseDto> courseList = instituteService.getAllInstitutesByFilter(request);
        for (InstituteResponseDto obj : courseList) {
            try {
                obj.setInstituteImageUrl(CDNServerUtil.getInstituteLogoImage(obj.getCountryName(), obj.getInstituteName()));
                obj.setInstituteLogoUrl(CDNServerUtil.getInstituteMainImage(obj.getCountryName(), obj.getInstituteName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Integer maxCount = 0, totalCount = 0;
        if (null != courseList && !courseList.isEmpty()) {
            totalCount = courseList.get(0).getTotalCount();
            maxCount = courseList.size();
        }
        boolean showMore;
        if (request.getMaxSizePerPage() == maxCount) {
            showMore = true;
        } else {
            showMore = false;
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("paginationObj", new PaginationDto(totalCount, showMore));
        response.put("instituteList", courseList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/recommended", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllRecommendedInstitutes(@RequestBody CourseSearchDto request) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        ErrorDto errorDto = null;
        if (request.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Maximum course limit per is " + PaginationUtil.courseResultPageMaxSize);
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        if (null == request.getUserId()) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Invalid user data.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        UserInfo user = userService.get(request.getUserId());

        List<BigInteger> countryIds = request.getCountryIds();
        if (null == countryIds || countryIds.isEmpty()) {
            countryIds = new ArrayList<>();
        }
        if (null != user.getPreferredCountryId()) {
            countryIds.add(user.getPreferredCountryId());
            request.setCountryIds(countryIds);
        }

        List<InstituteResponseDto> courseList = instituteService.getAllInstitutesByFilter(request);
        for (InstituteResponseDto obj : courseList) {
            try {
                obj.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(obj.getCountryName(), obj.getInstituteName()));
                obj.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(obj.getCountryName(), obj.getInstituteName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Integer maxCount = 0, totalCount = 0;
        if (null != courseList && !courseList.isEmpty()) {
            totalCount = courseList.get(0).getTotalCount();
            maxCount = courseList.size();
        }
        boolean showMore;
        if (request.getMaxSizePerPage() == maxCount) {
            showMore = true;
        } else {
            showMore = false;
        }
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("paginationObj", new PaginationDto(totalCount, showMore));
        response.put("instituteList", courseList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/city/{cityId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getInstituteByCityId(@Valid @PathVariable BigInteger cityId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<InstituteResponseDto> institutes = instituteService.getInstitudeByCityId(cityId);
        if (institutes != null && !institutes.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.INSTITUDE_NOT_FOUND);
        }
        response.put("institutes", institutes);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/multiple/city/{cityId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getInstituteByListOfCityId(@Valid @PathVariable String cityId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<InstituteResponseDto> institutes = instituteService.getInstituteByListOfCityId(cityId);
        if (institutes != null && !institutes.isEmpty()) {
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", IConstant.INSTITUDE_NOT_FOUND);
        }
        response.put("institutes", institutes);
        return ResponseEntity.accepted().body(response);
    }
}
