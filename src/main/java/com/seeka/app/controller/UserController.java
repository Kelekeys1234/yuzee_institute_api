package com.seeka.app.controller;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.UserCountryHobbiesDto;
import com.seeka.app.service.IHobbyService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IHobbyService hobbyService;

    @RequestMapping(value = "/interest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addCountryHobbies(@Valid @RequestBody UserCountryHobbiesDto userHountryHobbies) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.addCountryHobbies(userHountryHobbies));
    }

    @RequestMapping(value = "{userId}/interest", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUserHobbiesAndCountry(@PathVariable BigInteger userId) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.getUserHobbiesAndCountry(userId));
    }

    @RequestMapping(value = "{userId}/{hobbyId}/{countryName}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteInterest(@PathVariable BigInteger userId, @PathVariable BigInteger hobbyId, @PathVariable String countryName) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.deleteInterest(userId, hobbyId, countryName));
    }
}
