package com.seeka.app.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.bean.CountryDetails;
import com.seeka.app.bean.Faculty;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.YoutubeVideo;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICountryDetailsDAO;
import com.seeka.app.dao.ICountryImageDAO;
import com.seeka.app.dao.ICourseDAO;
import com.seeka.app.dao.IFacultyDAO;
import com.seeka.app.dao.ILevelDAO;
import com.seeka.app.dao.YoutubeVideoDAO;
import com.seeka.app.dto.CountryDetailsResponse;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CountryImageDto;
import com.seeka.app.dto.CountryLevelFacultyDto;
import com.seeka.app.dto.CountryRequestDto;
import com.seeka.app.dto.DiscoverCountryDto;
import com.seeka.app.util.CDNServerUtil;
import com.seeka.app.util.CommonUtil;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class CountryService implements ICountryService {

    @Autowired
    private ICountryDAO countryDAO;

    @Autowired
    private ICountryDetailsDAO countryDetailsDAO;

    @Autowired
    private ICountryImageDAO countryImageDAO;

    @Autowired
    private ICityService iCityService;

    @Autowired
    private YoutubeVideoDAO youtubeVideoDAO;

    @Autowired
    private ILevelDAO levelDAO;

    @Autowired
    private IFacultyDAO facultyDAO;

    @Autowired
    private ICourseDAO courseDAO;

    @Override
    public List<Country> getAll() {
        return countryDAO.getAll();
    }

    @Override
    public Country get(BigInteger id) {
        return countryDAO.get(id);
    }

    @Override
    public List<CountryDto> getAllUniversityCountries() {
        return countryDAO.getAllUniversityCountries();
    }

    @Override
    public List<CountryDto> searchInterestByCountry(String name) {
        return countryDAO.searchInterestByCountry(name);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return countryDAO.getAllCountries();
    }

    @Override
    public List<CountryDto> getAllCountryName() {
        return countryDAO.getAllCountryName();
    }

    @Override
    public List<CountryDto> getAllCountryWithCities() {
        List<CountryDto> countryList = countryDAO.getAllCountryName();
        List<CountryDto> resultCountryList = new ArrayList<>();
        for (CountryDto countryDto : countryList) {
            countryDto.setCityList(iCityService.getAllCitiesByCountry(countryDto.getId()));
            resultCountryList.add(countryDto);
        }
        return resultCountryList;
    }

    @Override
    public Map<String, Object> save(CountryRequestDto countryRequestDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        boolean status = true;
        try {
            Country country = countryDAO.save(CommonUtil.convertDTOToBean(countryRequestDto));
            if (countryRequestDto.getCountryDetailsDto() != null) {
                countryDetailsDAO.save(CommonUtil.convertCountryDetailsDTOToBean(countryRequestDto.getCountryDetailsDto(), country));
            }
            if (countryRequestDto.getCountryImageDtos() != null && !countryRequestDto.getCountryImageDtos().isEmpty()) {
                for (CountryImageDto dto : countryRequestDto.getCountryImageDtos()) {
                    countryImageDAO.save(CommonUtil.convertCountryImageDTOToBean(dto, country));
                }
            }
        } catch (Exception exception) {
            status = false;
            exception.printStackTrace();
        }
        if (status) {
            response.put("status", HttpStatus.OK.value());
            response.put("message", IConstant.COUNTRY_SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            ;
            response.put("message", IConstant.SQL_ERROR);
        }
        return response;
    }

    @Override
    public Map<String, Object> getAllDiscoverCountry() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<DiscoverCountryDto> discoverCountryDtos = new ArrayList<DiscoverCountryDto>();
        try {
            discoverCountryDtos = countryDAO.getDiscoverCountry();
            if (discoverCountryDtos != null && !discoverCountryDtos.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.DISCOVER_GET_COUNTRY_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.DISCOVER_COUNTRY_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", discoverCountryDtos);
        return response;
    }

    @Override
    public Map<String, Object> getCountryDetailsById(BigInteger id) {
        Map<String, Object> response = new HashMap<String, Object>();
        CountryDetailsResponse countryDetailsResponse = new CountryDetailsResponse();
        try {
            CountryDetails countryDetails = countryDetailsDAO.getDetailsByCountryId(id);
            countryDetailsResponse.setCountryId(id);
            countryDetailsResponse.setCountryDetails(countryDetails);
            countryDetailsResponse.setImages(getCountryImages(id));
            countryDetailsResponse.setYoutubeVideos(getYoutubeVideos(id));
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Country details get successfully");
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", countryDetailsResponse);
        return response;
    }

    private List<YoutubeVideo> getYoutubeVideos(BigInteger id) {
        return youtubeVideoDAO.getYoutubeVideoByCountryId(id, IConstant.COUNTRY_TYPE);
    }

    private List<String> getCountryImages(BigInteger id) {
        List<String> images = new ArrayList<>();
        Country country = countryDAO.get(id);
        if (country != null) {
            images.add(CDNServerUtil.getCountryImageUrl(country.getName()));
        }
        return images;
    }

    @Override
    public Map<String, Object> getCountryLevelFaculty() {
        Map<String, Object> response = new HashMap<String, Object>();
        List<CountryLevelFacultyDto> dtos = new ArrayList<>();
        try {
            List<Country> countries = countryDAO.getAll();
            for (Country country : countries) {
                CountryLevelFacultyDto dto = new CountryLevelFacultyDto();
                dto.setId(country.getId());
                dto.setName(country.getName());
                dto.setCountryCode(country.getCountryCode());
                dto.setLevels(getLevelByCountryId(country.getId()));
                dtos.add(dto);
            }
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Country details get successfully");
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", dtos);
        return response;
    }

    private List<Level> getLevelByCountryId(BigInteger id) {
        List<Level> levels = levelDAO.getLevelByCountryId(id);
        for (Level level : levels) {
            level.setFacultyList(getFacultiesByLevelId(level.getId(), id));
        }
        return levels;
    }

    private List<Faculty> getFacultiesByLevelId(BigInteger levelId, BigInteger countryId) {
        return facultyDAO.getFacultyByCountryIdAndLevelId(countryId, levelId);
    }

    @Override
    public Map<String, Object> autoSearch(String searchKey) {
        Map<String, Object> response = new HashMap<>();
        List<CountryDto> countries = new ArrayList<CountryDto>();
        try {
            countries = countryDAO.autoSearch(1, 50, searchKey);
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
	public Country getCountryBasedOnCitizenship(String citizenship) {
		return countryDAO.getCountryBasedOnCitizenship(citizenship);
	}
}
