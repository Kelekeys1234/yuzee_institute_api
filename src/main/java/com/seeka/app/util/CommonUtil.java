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
import com.seeka.app.bean.Course;
import com.seeka.app.bean.Institute;
import com.seeka.app.bean.Level;
import com.seeka.app.bean.Scholarship;
import com.seeka.app.bean.Todo;
import com.seeka.app.dto.CityDto;
import com.seeka.app.dto.CountryDetailsDto;
import com.seeka.app.dto.CountryImageDto;
import com.seeka.app.dto.CountryRequestDto;
import com.seeka.app.dto.CourseRequest;
import com.seeka.app.dto.InstituteCampusDto;
import com.seeka.app.dto.InstituteRequestDto;
import com.seeka.app.dto.ScholarshipDto;
import com.seeka.app.dto.TodoDto;

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

    public static InstituteRequestDto convertInstituteBeanToInstituteRequestDto(Institute institute) {
        InstituteRequestDto instituteRequestDto = new InstituteRequestDto();
        instituteRequestDto.setAverageCostFrom(institute.getAvgCostOfLiving());
        instituteRequestDto.setCityId(institute.getCity().getId());
        instituteRequestDto.setCountryId(institute.getCountry().getId());
        instituteRequestDto.setDescription(institute.getDescription());
        instituteRequestDto.setInstituteLogoUrl(institute.getInstituteLogoUrl());
        instituteRequestDto.setInstituteId(institute.getId());
        if (institute.getInstituteType() != null) {
            instituteRequestDto.setInstituteTypeId(institute.getInstituteType().getId());
        }
        if (institute.getLatitute() != null) {
            instituteRequestDto.setLatitute((institute.getLatitute()));
        }
        if (institute.getLongitude() != null) {
            instituteRequestDto.setLongitude((institute.getLongitude()));
        }
        instituteRequestDto.setAddress(institute.getAddress());
        instituteRequestDto.setPhoneNumber(institute.getPhoneNumber());
        instituteRequestDto.setOpeningHour(institute.getOpeningFrom());
        instituteRequestDto.setClosingHour(institute.getOpeningTo());
        instituteRequestDto.setTotalStudent(institute.getTotalStudent());
        instituteRequestDto.setWorldRanking(institute.getWorldRanking());
        instituteRequestDto.setInstituteName(institute.getName());
        instituteRequestDto.setCampusType(institute.getCampusType());
        instituteRequestDto.setCampusName(institute.getCampusName());
        instituteRequestDto.setEmail(institute.getEmail());
        return instituteRequestDto;
    }

    public static ScholarshipDto convertScholarshipBeanToScholarshipDto(Scholarship scholarship) {
        ScholarshipDto scholarshipDto = new ScholarshipDto();
        scholarshipDto.setAddress(scholarship.getAddress());
        scholarshipDto.setScholarshipAmount(scholarship.getAmount());
        scholarshipDto.setApplicationDeadline(scholarship.getApplicationDeadline());
        scholarshipDto.setBenefits(scholarship.getBenefits());
        scholarshipDto.setCountryId(scholarship.getCountry().getId());
        scholarshipDto.setDescription(scholarship.getDescription());
        scholarshipDto.setEligibility(scholarship.getEligibility());
        scholarshipDto.setEmail(scholarship.getEmail());
        scholarshipDto.setGender(scholarship.getGender());
        scholarshipDto.setHeadquaters(scholarship.getHeadquaters());
        scholarshipDto.setId(scholarship.getId());
        scholarshipDto.setInstituteId(scholarship.getInstitute().getId());
        scholarshipDto.setIntake(scholarship.getIntake());
        scholarshipDto.setIsActive(scholarship.getIsActive());
        scholarshipDto.setIsDeleted(scholarship.getIsDeleted());
        scholarshipDto.setLanguage(scholarship.getLanguage());
        scholarshipDto.setLevelId(scholarship.getLevel().getId());
        scholarshipDto.setScholarshipTitle(scholarship.getName());
        scholarshipDto.setNumberOfAvaliability(scholarship.getNumberOfAvaliability());
        scholarshipDto.setOfferedBy(scholarship.getOfferedBy());
        scholarshipDto.setRequirements(scholarship.getRequirements());
        scholarshipDto.setScholarshipAmount((scholarship.getAmount()));
        scholarshipDto.setScholarshipTitle(scholarship.getScholarshipTitle());
        scholarshipDto.setStudent(scholarship.getStudent());
        scholarshipDto.setValidity(scholarship.getValidity());
        scholarshipDto.setWebsite(scholarship.getWebsite());
        return scholarshipDto;
    }

    public static Scholarship convertScholarshipDTOToBean(ScholarshipDto scholarshipObj, Country country, Institute institute, Level level) {
        Scholarship scholarship = new Scholarship();
        scholarship.setCountry(country);
        scholarship.setInstitute(institute);
        scholarship.setLevel(level);
        scholarship.setName(scholarshipObj.getScholarshipTitle());
        scholarship.setAmount(scholarshipObj.getScholarshipAmount());
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
        scholarship.setAmount(scholarshipObj.getScholarshipAmount());
        scholarship.setNumberOfAvaliability(scholarshipObj.getNumberOfAvaliability());
        scholarship.setHeadquaters(scholarshipObj.getHeadquaters());
        scholarship.setEmail(scholarshipObj.getEmail());
        scholarship.setAddress(scholarshipObj.getAddress());
        scholarship.setIsActive(true);
        scholarship.setCoverage(scholarshipObj.getCoverage());
        scholarship.setType(scholarshipObj.getType());
        return scholarship;
    }

    public static CourseRequest convertCourseDtoToCourseRequest(Course course) {
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setCourseId(course.getId());
        courseRequest.setStars(course.getStars());
        courseRequest.setDescription(course.getDescription());
        courseRequest.setDuration(course.getDuration());
        courseRequest.setDurationTime(course.getDurationTime());
        courseRequest.setWorldRanking(course.getWorldRanking());
        // courseRequest.setCost(course.getCostRange());
        courseRequest.setName(course.getName());
        courseRequest.setCourseLink(course.getCourseLink());
        courseRequest.setIntake(course.getIntake());
        courseRequest.setLanguage(course.getCourseLang());
        if (course.getFaculty() != null) {
            courseRequest.setFacultyId(course.getFaculty().getId());
        }
        courseRequest.setDomasticFee(course.getDomesticFee());
        courseRequest.setInternationalFee(course.getInternationalFee());
        if (course.getCountry() != null) {
            courseRequest.setCountryId(course.getCountry().getId());
        }
        courseRequest.setGrades(course.getGrades());
        // courseRequest.setPartTime(course.getp);
        courseRequest.setContact(course.getContact());
        // courseRequest.setOpeningHourFrom(openingHourFrom);
        // courseRequest.setOpeningHourTo(openingHourTo);
        courseRequest.setCampusLocation(course.getCampusLocation());
        courseRequest.setWebsite(course.getWebsite());
        // courseRequest.setFullTime(course.g);
        courseRequest.setCourseCurrency(course.getCurrency());
        return courseRequest;
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

    public static InstituteCampusDto convertInstituteCampusToInstituteCampusDto(Institute campus) {
        InstituteCampusDto campusDto = new InstituteCampusDto();
        campusDto.setAddress(campus.getAddress());
        campusDto.setEmail(campus.getEmail());
        campusDto.setLatitute(campus.getLatitute());
        campusDto.setLongitute(campus.getLongitude());
        campusDto.setOpeningFrom(campus.getOpeningFrom());
        campusDto.setOpeningTo(campus.getOpeningTo());
        campusDto.setPhoneNumber(campus.getPhoneNumber());
        campusDto.setTotalStudent(campus.getTotalStudent());
        campusDto.setId(campus.getId());
        campusDto.setCampusType(campus.getCampusType());
        return campusDto;
    }

    public static Todo convertTodoDtoIntoTodo(TodoDto todoDto) {
        Todo todo = new Todo();
        todo.setDescription(todoDto.getDescription());
        todo.setTitle(todoDto.getTitle());
        todo.setUserId(todoDto.getUserId());
        todo.setStatus(todoDto.getStatus());
        todo.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        todo.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        todo.setFolderId(todoDto.getFolderId());
        todo.setCreatedBy(todoDto.getCreatedBy());
        todo.setUpdatedBy(todoDto.getUpdatedBy());
        return todo;
    }

    public static TodoDto convertTodoIntoTodoDto(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setDescription(todo.getDescription());
        todoDto.setTitle(todo.getTitle());
        todoDto.setUserId(todo.getUserId());
        todoDto.setStatus(todo.getStatus());
        todoDto.setFolderId(todo.getFolderId());
        todoDto.setCreatedBy(todo.getCreatedBy());
        todoDto.setUpdatedBy(todo.getUpdatedBy());
        return todoDto;
    }
}
