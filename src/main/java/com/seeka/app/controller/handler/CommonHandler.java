package com.seeka.app.controller.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import com.seeka.app.dto.CommonDtoWrapper;
import com.seeka.app.dto.StudentVisaDto;
import com.seeka.app.dto.StudentVisaWrapperDto;
import com.seeka.app.dto.YouTubeVideoDto;
import com.seeka.app.exception.CommonInvokeException;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.util.IConstant;

@Service
public class CommonHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonHandler.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String GET_YOUTUBE_VIDEO_BY_INSTITUTE_NAME = "/youtube/video/pageNumber/{pageNumber}/pageSize/{pageSize}?courseName={courseName}"
					+ "	&instituteName={instituteName}&countryName={countryName}&cityName={cityName}";
	private static final String GET_STUDENT_VISA_BY_COUNTRY_NAME = "/student/visa?countryName={countryName}";
	
	public List<YouTubeVideoDto> getYoutubeDataforCourse(String instituteName, String countryName, String cityName,
				String courseName, Integer pageNumber, Integer pageSize) throws Exception {
		ResponseEntity<CommonDtoWrapper> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("courseName", courseName);
			params.put("instituteName", instituteName);
			params.put("countryName", countryName);
			params.put("cityName", cityName);
			params.put("pageNumber", pageNumber.toString());
			params.put("pageSize", pageSize.toString());
			
			StringBuilder path = new StringBuilder();
			path.append(IConstant.COMMON_CONNECTION_URL).append(GET_YOUTUBE_VIDEO_BY_INSTITUTE_NAME);
			responseEntity = restTemplate.exchange(path.toString(), HttpMethod.GET, null, CommonDtoWrapper.class, params);
			if(responseEntity.getStatusCode().value() != 200) {
				throw new CommonInvokeException ("Error response recieved from common service with error code " 
						+ responseEntity.getStatusCode().value());
			}
			
			if(ObjectUtils.isEmpty(responseEntity.getBody())) {
				throw new NotFoundException("No youTube videos found for given name " + courseName);
			}
		} catch(Exception e) {
			LOGGER.error("Error invoking common service {}", e);
			if (e instanceof CommonInvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new CommonInvokeException("Error invoking common service");
			}	
		}
		return responseEntity.getBody().getData().getYouTubeVideosList();
	}
	
	public StudentVisaDto getStudentVisaDetailsByCountryName(String countryName) throws CommonInvokeException {
		ResponseEntity<StudentVisaWrapperDto> responseEntity = null;
		Map<String, String> params = new HashMap<String, String>();
		try {
			params.put("countryName", countryName);
			StringBuilder path = new StringBuilder();
			path.append(IConstant.COMMON_CONNECTION_URL).append(GET_STUDENT_VISA_BY_COUNTRY_NAME);
			responseEntity = restTemplate.exchange(path.toString(), HttpMethod.GET, null, StudentVisaWrapperDto.class, params);
			if(responseEntity.getStatusCode().value() != 200) {
				throw new CommonInvokeException ("Error response recieved from common service with error code " 
						+ responseEntity.getStatusCode().value());
			}
		} catch (Exception e) {
			LOGGER.error("Error invoking common service {}", e);
			if (e instanceof CommonInvokeException || e instanceof NotFoundException) {
				throw e;
			} else {
				throw new CommonInvokeException("Error invoking common service");
			}	
		}
		return responseEntity.getBody().getData();
	}
}
