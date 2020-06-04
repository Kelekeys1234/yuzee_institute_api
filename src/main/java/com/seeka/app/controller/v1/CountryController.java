package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.NearestCourseResponseDto;
import com.seeka.app.dto.NearestInstituteDTO;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.service.ICountryService;

@RestController("countryControllerV1")
@RequestMapping("/api/v1/country")
public class CountryController {

    @Autowired
    private ICountryService countryService;


   /* @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = null;
        try {
            countryList = countryService.getAllCountries();
            if (countryList != null && !countryList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COUNTRY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COUNTRY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCountry() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = null;
        try {
            countryList = countryService.getAllCountryName();
            if (countryList != null && !countryList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COUNTRY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COUNTRY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/states/cities", method = RequestMethod.GET)
    public ResponseEntity<?> getWithCities() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = null;
        try {
            countryList = countryService.getAllCountryWithCities();
            if (countryList != null && !countryList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COUNTRY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COUNTRY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable String id) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        Country countryObj = null;
        try {
            countryObj = countryService.get(id);
            if (countryObj != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COUNTRY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COUNTRY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", countryObj);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/institute", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUniversityCountries() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryDto> countryList = null;
        try {
            countryList = CountryUtil.getUnivCountryList();
            if (countryList != null && !countryList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.COUNTRY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.COUNTRY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", countryList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> saveCountry(@RequestBody CountryRequestDto countryRequestDto) throws Exception {
        return ResponseEntity.accepted().body(countryService.save(countryRequestDto));
    }

    @RequestMapping(value = "/discover", method = RequestMethod.GET)
    public ResponseEntity<?> getDiscoverCountry() {
        return ResponseEntity.accepted().body(countryService.getAllDiscoverCountry());
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

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCountryDetailsById(@PathVariable String id) {
        return ResponseEntity.accepted().body(countryService.getCountryDetailsById(id));
    }

    @RequestMapping(value = "/level/faculty", method = RequestMethod.GET)
    public ResponseEntity<?> getCountryLevelFaculty() {
        return ResponseEntity.accepted().body(countryService.getCountryLevelFaculty());
    }

    @RequestMapping(value = "/autoSearch/{searchKey}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> autoSearch(@PathVariable final String searchKey) throws Exception {
        return ResponseEntity.accepted().body(countryService.autoSearch(searchKey));
    }*/

    @RequestMapping(value = "/course", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCourseCountry() throws Exception {
        return ResponseEntity.accepted().body(countryService.getCourseCountry());
    }
    
	@GetMapping(value = "/course/pageNumber/{pageNumber}/pageSize/{pageSize}/{countryName}")
	public ResponseEntity<?> getCourseByCountryName(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable String countryName) throws NotFoundException {
		List<NearestCourseResponseDto> courseResponse = countryService.getCourseByCountryName(countryName, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(courseResponse)
				.setMessage("Courses displayed successfully").setStatus(HttpStatus.OK).create();
	}

	@GetMapping(value = "/institute/pageNumber/{pageNumber}/pageSize/{pageSize}/{countryName}")
	public ResponseEntity<?> getInstituteByCountryName(@PathVariable Integer pageNumber, @PathVariable Integer pageSize,
			@PathVariable String countryName) throws NotFoundException {
		List<NearestInstituteDTO> instituteResponse = countryService.getInstituteByCountryName(countryName, pageNumber, pageSize);
		return new GenericResponseHandlers.Builder().setData(instituteResponse)
				.setMessage("Institutes displayed successfully").setStatus(HttpStatus.OK).create();
	}
}