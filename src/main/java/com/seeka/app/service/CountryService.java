package com.seeka.app.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.Country;
import com.seeka.app.dao.ICountryDAO;
import com.seeka.app.dao.ICountryDetailsDAO;
import com.seeka.app.dao.ICountryImageDAO;
import com.seeka.app.dto.CountryDto;
import com.seeka.app.dto.CountryImageDto;
import com.seeka.app.dto.CountryRequestDto;
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
            response.put("status", IConstant.SUCCESS_CODE);
            response.put("message", IConstant.COUNTRY_SUCCESS_MESSAGE);
        } else {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", IConstant.FAIL);
        }
        return response;
    }
}
