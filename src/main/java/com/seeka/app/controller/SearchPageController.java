package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Level;
import com.seeka.app.bean.UserInfo;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CourseSearchDto;
import com.seeka.app.dto.CourseSearchFilterDto;
import com.seeka.app.dto.InstituteResponseDto;
import com.seeka.app.jobs.CountryLevelFacultyUtil;
import com.seeka.app.service.ICountryService;
import com.seeka.app.service.IFacultyService;
import com.seeka.app.service.IInstituteService;
import com.seeka.app.service.ILevelService;
import com.seeka.app.service.IUserService;
import com.seeka.app.util.CDNServerUtil;

@RestController
@RequestMapping("/search")
public class SearchPageController {

    @Autowired
    ICountryService countryService;

    @Autowired
    ILevelService levelService;

    @Autowired
    IFacultyService facultyService;

    @Autowired
    IUserService userService;

    @Autowired
    IInstituteService instituteService;

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCountries(@PathVariable("userId") BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        Date now = new Date();
        UserInfo user = userService.get(userId);
        CourseSearchDto courseSearchDto = new CourseSearchDto();
        List<BigInteger> countryIds = new ArrayList<>();
        if (null != user.getPreferredCountryId()) {
            countryIds.add(user.getPreferredCountryId());
            courseSearchDto.setCountryIds(countryIds);
        }
        CourseSearchFilterDto sortingObj = new CourseSearchFilterDto();
        sortingObj.setWorldRanking("DSC");
        courseSearchDto.setSortingObj(sortingObj);
        courseSearchDto.setPageNumber(1);
        courseSearchDto.setMaxSizePerPage(3);
        List<InstituteResponseDto> recommendedInstList = instituteService.getAllInstitutesByFilter(courseSearchDto);
        for (InstituteResponseDto obj : recommendedInstList) {
            try {
                obj.setInstituteLogoUrl(CDNServerUtil.getInstituteLogoImage(obj.getCountryName(), obj.getInstituteName()));
                obj.setInstituteImageUrl(CDNServerUtil.getInstituteMainImage(obj.getCountryName(), obj.getInstituteName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<CountryDto> countryList = CountryLevelFacultyUtil.getCountryList();
        List<Level> levelList = CountryLevelFacultyUtil.getLevelList();
        response.put("countryList", countryList);
        response.put("levelList", levelList);
        response.put("recommendedInstList", recommendedInstList);
        response.put("status", 1);
        response.put("message", "Success.!");
        long duration = new Date().getTime() - now.getTime();
        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        System.out.println("Total Computation in " + diffInSeconds);
        return ResponseEntity.accepted().body(response);
    }

}
