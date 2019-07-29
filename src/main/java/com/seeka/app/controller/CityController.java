package com.seeka.app.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.City;
import com.seeka.app.dto.CityDto;
import com.seeka.app.dto.UpdateCityDto;
import com.seeka.app.jobs.CountryUtil;
import com.seeka.app.service.ICityService;
import com.seeka.app.util.IConstant;
import com.seeka.app.util.NumbeoWebServiceClient;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private ICityService cityService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAll() throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> cityList = null;
        try {
            cityList = cityService.getAll();
            if (cityList != null && !cityList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.CITY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.CITY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", cityList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/country/{countryId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCitiesByCountry(@PathVariable BigInteger countryId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> cityList = null;
        try {
            cityList = cityService.getAllCitiesByCountry(countryId);
            if (cityList != null && !cityList.isEmpty()) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.CITY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.CITY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", cityList);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable BigInteger id) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        City cityObj = null;
        try {
            cityObj = cityService.get(id);
            if (cityObj != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", IConstant.CITY_GET_SUCCESS);
            } else {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", IConstant.CITY_GET_NOT_FOUND);
            }
        } catch (Exception exception) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", exception.getCause());
        }
        response.put("data", cityObj);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> saveCity(@RequestBody CityDto city) throws Exception {
        return ResponseEntity.accepted().body(cityService.save(city));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable BigInteger id, @RequestBody UpdateCityDto city) throws Exception {
        return ResponseEntity.accepted().body(cityService.update(id, city));
    }

    @RequestMapping(value = "/update/pricing", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> updateCityPricing() throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> list = cityService.getAll();
        for (City city : list) {
            try {
                NumbeoWebServiceClient.getCityPricing(city.getName(), city.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}/pricing", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCityPricingByCityID(@PathVariable BigInteger id) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        File file = new File(NumbeoWebServiceClient.fileDirectory + id + ".json");
        City city = cityService.get(id);
        if (null != file && file.exists()) {
            // Already file there on the directory.
        } else {
            NumbeoWebServiceClient.getCityPricing(city.getName(), city.getId());
            file = new File(NumbeoWebServiceClient.fileDirectory + id + ".json");
        }
        JSONObject jsonObject = new JSONObject();
        if (null != file && file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            String responseStr = "";
            while ((st = br.readLine()) != null) {
                responseStr += st;
                System.out.println(responseStr);
            }
            br.close();
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(responseStr);
        }
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Success");
        response.put("cityName", city.getName() + ", " + CountryUtil.getCountryByCountryId(city.getCountry().getId()).getName());
        response.put("livingCost", jsonObject);
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/multiple/country/{countryId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllMultipleCitiesByCountry(@PathVariable String countryId) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        List<City> cityList = cityService.getAllMultipleCitiesByCountry(countryId);
        response.put("status", HttpStatus.OK.value());
        response.put("message", "City get successfully");
        response.put("data", cityList);
        return ResponseEntity.accepted().body(response);
    }
}
