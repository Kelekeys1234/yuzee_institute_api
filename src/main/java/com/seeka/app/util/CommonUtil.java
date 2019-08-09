package com.seeka.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.seeka.app.bean.City;
import com.seeka.app.bean.Country;
import com.seeka.app.bean.CountryDetails;
import com.seeka.app.bean.CountryImages;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.Scholarship;
import com.seeka.app.dto.CityDto;
import com.seeka.app.dto.CountryDetailsDto;
import com.seeka.app.dto.CountryImageDto;
import com.seeka.app.dto.CountryRequestDto;
import com.seeka.app.dto.ScholarshipDto;

public class CommonUtil {

    public static Country convertDTOToBean(CountryRequestDto countryRequestDto) {
        ObjectMapper mapper = new ObjectMapper();
        Country country = null;
        try {
            country = mapper.readValue(mapper.writeValueAsString(countryRequestDto), Country.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static CountryDetails convertCountryDetailsDTOToBean(CountryDetailsDto countryDetailsDto, Country country) {
        ObjectMapper mapper = new ObjectMapper();
        CountryDetails countryDetails = null;
        try {
            countryDetails = mapper.readValue(mapper.writeValueAsString(countryDetailsDto), CountryDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countryDetails;
    }

    public static CountryImages convertCountryImageDTOToBean(CountryImageDto dto, Country country) {
        ObjectMapper mapper = new ObjectMapper();
        CountryImages countryImages = null;
        try {
            countryImages = mapper.readValue(mapper.writeValueAsString(dto), CountryImages.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countryImages;
    }

    public static City convertCityDTOToBean(CityDto cityObj, Country country) {
        City city = new City();
        city.setCityImgCnt(cityObj.getCityImgCnt());
        city.setAvailableJobs(cityObj.getAvailableJobs());
        city.setCountry(country);
        city.setDescription(cityObj.getDescription());
        city.setName(cityObj.getName());
        city.setTripAdvisorLink(cityObj.getTripAdvisorLink());
        city.setCreatedOn(new Date());
        city.setUpdatedOn(new Date());
        return city;
    }

    public static Scholarship convertScholarshipDTOToBean(ScholarshipDto scholarshipObj, Country country, Institute institute, Level level) {
        Scholarship scholarship = new Scholarship();

        scholarship.setCountry(country);
        scholarship.setInstitute(institute);
        scholarship.setLevel(level);
        scholarship.setName(scholarshipObj.getName());
        scholarship.setAmount(scholarshipObj.getAmount());
        scholarship.setDescription(scholarshipObj.getDescription());
        scholarship.setStudent(scholarshipObj.getStudent());
        scholarship.setWebsite(scholarshipObj.getWebsite());
        scholarship.setCreatedOn(new Date());
        scholarship.setUpdatedOn(new Date());
        scholarship.setScholarshipTitle(scholarshipObj.getScholarshipTitle());
        scholarship.setOfferedBy(scholarshipObj.getOfferedBy());
        scholarship.setBenefits(scholarshipObj.getBenefits());
        scholarship.setRequirements(scholarshipObj.getRequirements());
        scholarship.setEligibility(scholarshipObj.getEligibility());
        scholarship.setIntake(scholarshipObj.getIntake());
        scholarship.setLanguage(scholarshipObj.getLanguage());
        scholarship.setValidity(scholarshipObj.getValidity());
        scholarship.setGender(scholarshipObj.getGender());
        scholarship.setApplicationDeadline(scholarshipObj.getApplicationDeadline());
        scholarship.setScholarshipAmount(scholarshipObj.getScholarshipAmount());
        scholarship.setNumberOfAvaliability(scholarshipObj.getNumberOfAvaliability());
        scholarship.setHeadquaters(scholarshipObj.getHeadquaters());
        scholarship.setEmail(scholarshipObj.getEmail());
        scholarship.setAddress(scholarshipObj.getAddress());
        return scholarship;
    }

    public static String getCurrencyDetails(String baseCurrency) {
        String currencyResponse = null;
        URL url = null;
        try {
            url = new URL(IConstant.CURRENCY_URL + "latest?access_key=" + IConstant.API_KEY + "&base=" + baseCurrency);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();
            currencyResponse = jsonobj.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return currencyResponse;
    }
}
