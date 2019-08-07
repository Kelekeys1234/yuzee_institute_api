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
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICountryDetailsDAO;
import com.seeka.app.dao.ICountryImageDAO;
import com.seeka.app.dto.CountryDetailsResponse;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CountryImageDto;
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
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Country details get successfully");
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        response.put("data", countryDetailsResponse);
        return response;
    }

    private List<String> getCountryImages(BigInteger id) {
        List<String> images = new ArrayList<>();
        Country country = countryDAO.get(id);
        if (country != null) {
            images.add(CDNServerUtil.getCountryImageUrl(country.getName()));
        }
        return images;
    }
}
