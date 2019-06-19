package com.seeka.app.controller;

import java.math.BigInteger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.CountryEnglishEligibility;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CountryRequestDto;
import com.seeka.app.dto.ErrorDto;
import com.seeka.app.enumeration.EnglishType;
import com.seeka.app.jobs.CountryUtil;
import com.seeka.app.service.ICountryEnglishEligibilityService;
import com.seeka.app.service.ICountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    ICountryService countryService;

    @Autowired
    ICountryEnglishEligibilityService countryEnglishEligibilityService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = countryService.getAllCountries();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("list", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCountry() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = countryService.getAllCountryName();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("list", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/country/cities", method = RequestMethod.GET)
    public ResponseEntity<?> getWithCities() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = CountryUtil.getCountryList();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("list", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable BigInteger id) throws Exception {
        ErrorDto errorDto = null;
        Map<String, Object> response = new HashMap<String, Object>();
        System.out.println("id : " + id);
        Country countryObj = countryService.get(id);
        // image url, media, international health cover data isn't there in above API
        if (null == countryObj) {
            errorDto = new ErrorDto();
            errorDto.setCode("400");
            errorDto.setMessage("Country Not Found.!");
            response.put("status", 0);
            response.put("error", errorDto);
            return ResponseEntity.badRequest().body(response);
        }
        response.put("status", 1);
        response.put("message", "Success");
        response.put("countryObj", countryObj);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/country/institute", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUniversityCountries() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = CountryUtil.getUnivCountryList();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("countryList", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> saveCountry(@RequestBody CountryRequestDto countryRequestDto) throws Exception {
        return ResponseEntity.accepted().body(countryService.save(countryRequestDto));
    }

    @RequestMapping(value = "/discover", method = RequestMethod.GET)
    public ResponseEntity<?> getDiscoverPage() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = countryService.getAllCountries();
        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("countryList", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/add/english/sample", method = RequestMethod.GET)
    public ResponseEntity<?> addEnglishEligibility() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<Country> countryList = countryService.getAll();
        Date now = new Date();
        CountryEnglishEligibility eligibility = null;
        for (Country country : countryList) {

            eligibility = new CountryEnglishEligibility();
            eligibility.setCountry(country);
            eligibility.setCreatedBy("AUTO");
            eligibility.setCreatedOn(now);
            // eligibility.setId(BigInteger.randomBigInteger());
            eligibility.setIsActive(true);
            eligibility.setListening(4.5);
            eligibility.setOverall(4.5);
            eligibility.setReading(4.00);
            eligibility.setSpeaking(5.00);
            eligibility.setEnglishType(EnglishType.TOEFL.toString());
            eligibility.setWriting(3.25);
            countryEnglishEligibilityService.save(eligibility);

            eligibility = new CountryEnglishEligibility();
            eligibility.setCountry(country);
            eligibility.setCreatedBy("AUTO");
            eligibility.setCreatedOn(now);
            // eligibility.setId(BigInteger.randomBigInteger());
            eligibility.setIsActive(true);
            eligibility.setListening(4.5);
            eligibility.setOverall(4.5);
            eligibility.setReading(4.00);
            eligibility.setSpeaking(5.00);
            eligibility.setEnglishType(EnglishType.IELTS.toString());
            eligibility.setWriting(3.25);
            countryEnglishEligibilityService.save(eligibility);

        }

        response.put("status", 1);
        response.put("message", "Success.!");
        response.put("list", countryList);
        return ResponseEntity.accepted().body(response);
    }
}