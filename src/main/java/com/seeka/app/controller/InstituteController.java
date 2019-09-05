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
import com.seeka.app.bean.InstituteCategoryType;
import com.seeka.app.bean.InstituteType;
import com.seeka.app.bean.Service;
import com.seeka.app.bean.UserInfo;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.dto.InstituteFilterDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.dto.PaginationDto;
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
    private IInstituteTypeService instituteTypeService;

    @Autowired
    private IServiceDetailsService serviceDetailsService;

    @Autowired
    private IInstituteServiceDetailsService instituteServiceDetailsService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/type", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveInstituteType(@Valid @RequestBody final InstituteType instituteTypeObj) throws Exception {
        Map<String, Object> response = new HashMap<>(3);
        instituteTypeService.save(instituteTypeObj);
        response.put("message", "Institute type saved successfully");
        response.put("status", HttpStatus.OK.value());
        response.put("data", instituteTypeObj);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/service/{instituteTypeId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> saveService(@PathVariable final BigInteger instituteTypeId) throws Exception {
        Map<String, Object> response = new HashMap<>();
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
        // / serviceObj.setInstituteTypeId(instituteTypeId);
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
        Map<String, Object> response = new HashMap<>();
        List<Service> list = serviceDetailsService.getAll();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("serviceList", list);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}/service", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllServicesByInstitute(@Valid @PathVariable final BigInteger id) throws Exception {
        Map<String, Object> response = new HashMap<>();
        List<String> serviceNames = instituteServiceDetailsService.getAllServices(id);
        if (serviceNames != null && !serviceNames.isEmpty()) {
            response.put("message", "Service fetched successfully");
            response.put("status", HttpStatus.OK.value());
        } else {
            response.put("message", "Service not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
        response.put("data", serviceNames);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> instituteSearch(@RequestBody final CourseSearchDto request) throws Exception {
        return getInstitutesBySearchFilters(request);
    }

    @RequestMapping(value = "/search/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> instituteSearch(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize,
                    @RequestParam(required = false) final List<BigInteger> countryIds, @RequestParam(required = false) final List<BigInteger> facultyIds,
                    @RequestParam(required = false) final List<BigInteger> levelIds) throws Exception {
        CourseSearchDto courseSearchDto = new CourseSearchDto();
        courseSearchDto.setCountryIds(countryIds);
        courseSearchDto.setFacultyIds(facultyIds);
        courseSearchDto.setLevelIds(levelIds);
        courseSearchDto.setPageNumber(pageNumber);
        courseSearchDto.setMaxSizePerPage(pageSize);
        return getInstitutesBySearchFilters(courseSearchDto);
    }

    private ResponseEntity<?> getInstitutesBySearchFilters(final CourseSearchDto request) {
        Map<String, Object> response = new HashMap<>();
        if (request.getPageNumber() > PaginationUtil.courseResultPageMaxSize) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Maximum institute limit per is " + PaginationUtil.courseResultPageMaxSize);
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }

        // UserInfo user = userService.get(request.getUserId());

        List<BigInteger> countryIds = request.getCountryIds();
        if (null == countryIds || countryIds.isEmpty()) {
            countryIds = new ArrayList<>();
        }
        // if (null != user.getPreferredCountryId()) {
        // countryIds.add(user.getPreferredCountryId());
        // request.setCountryIds(countryIds);
        // }

        List<InstituteResponseDto> instituteList = instituteService.getAllInstitutesByFilter(request);
        for (InstituteResponseDto obj : instituteList) {
            try {
                obj.setInstituteImageUrl(CDNServerUtil.getInstituteLogoImage(obj.getCountryName(), obj.getInstituteName()));
                obj.setInstituteLogoUrl(CDNServerUtil.getInstituteMainImage(obj.getCountryName(), obj.getInstituteName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Integer maxCount = 0, totalCount = 0;
        if (null != instituteList && !instituteList.isEmpty()) {
            totalCount = instituteList.get(0).getTotalCount();
            maxCount = instituteList.size();
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
        response.put("instituteList", instituteList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/recommended", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllRecommendedInstitutes(@RequestBody final CourseSearchDto request) throws Exception {
        Map<String, Object> response = new HashMap<>();
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
    public ResponseEntity<?> getInstituteByCityId(@Valid @PathVariable final BigInteger cityId) throws Exception {
        Map<String, Object> response = new HashMap<>();
        List<InstituteResponseDto> institutes = instituteService.getInstitudeByCityId(cityId);
        if (institutes != null && !institutes.isEmpty()) {
            response.put("message", "Institute fetched successfully");
            response.put("status", HttpStatus.OK.value());
        } else {
            response.put("message", IConstant.INSTITUDE_NOT_FOUND);
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
        response.put("data", institutes);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/multiple/city/{cityId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getInstituteByListOfCityId(@Valid @PathVariable final String cityId) throws Exception {
        Map<String, Object> response = new HashMap<>();
        List<InstituteResponseDto> institutes = instituteService.getInstituteByListOfCityId(cityId);
        if (institutes != null && !institutes.isEmpty()) {
            response.put("message", "Institute fetched successfully");
            response.put("status", HttpStatus.OK.value());
        } else {
            response.put("message", IConstant.INSTITUDE_NOT_FOUND);
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
        response.put("institutes", institutes);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody final List<InstituteRequestDto> institutes) throws Exception {
        return ResponseEntity.accepted().body(instituteService.save(institutes));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> update(@Valid @PathVariable final BigInteger id, @RequestBody final List<InstituteRequestDto> institute) throws Exception {
        return ResponseEntity.accepted().body(instituteService.update(institute, id));
    }

    @RequestMapping(value = "/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllInstitute(@PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
        return ResponseEntity.accepted().body(instituteService.getAllInstitute(pageNumber, pageSize));
    }

    @RequestMapping(value = "/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllInstitute(@PathVariable final String searchKey, @PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
        return ResponseEntity.accepted().body(instituteService.autoSearch(pageNumber, pageSize, searchKey));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@Valid @PathVariable final BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(instituteService.getById(id));
    }

    @RequestMapping(value = "search/{searchText}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> searchInstitute(@Valid @PathVariable final String searchText) throws Exception {
        return ResponseEntity.accepted().body(instituteService.searchInstitute(searchText));
    }

    @RequestMapping(value = "all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllInstitute() throws Exception {
        Map<String, Object> response = new HashMap<>();
        List<Institute> institutes = instituteService.getAll();
        if (institutes != null && !institutes.isEmpty()) {
            response.put("message", "Institute fetched successfully");
            response.put("status", HttpStatus.OK.value());
        } else {
            response.put("message", IConstant.INSTITUDE_NOT_FOUND);
            response.put("status", HttpStatus.NOT_FOUND.value());
        }
        response.put("institutes", institutes);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> instituteFilter(@RequestBody final InstituteFilterDto instituteFilterDto) throws Exception {
        return ResponseEntity.ok().body(instituteService.instituteFilter(instituteFilterDto));
    }

    @RequestMapping(value = "/allCategoryType", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getAllCategoryType() throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            List<InstituteCategoryType> categoryTypes = instituteService.getAllCategories();
            if (categoryTypes != null && !categoryTypes.isEmpty()) {
                response.put("message", "CategoryType fetched successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", categoryTypes);
            } else {
                response.put("message", "Error CategoryType fetched");
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("data", "Error");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.put("message", "Error CategoryType fetched");
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("data", "Error");
        }
        return ResponseEntity.ok().body(response);
    }

}
