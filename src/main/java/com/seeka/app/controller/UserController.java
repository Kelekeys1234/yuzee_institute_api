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

   /* @RequestMapping(value = "/hoobies", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> userHobbies(@Valid @RequestBody UserHobbies userHobbies) throws Exception {
        return ResponseEntity.badRequest().body(hobbyService.addUserHobbies(userHobbies));
    }

    @RequestMapping(value = "{userId}/hobbies", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUserHobbies(@PathVariable BigInteger userId) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.getUserHobbies(userId));
    }*/

   /* @RequestMapping(value = "{userId}/hobbies/{hobbyId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteUserHobbies(@PathVariable BigInteger userId, @PathVariable BigInteger hobbyId) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.deleteUserHobbies(userId, hobbyId));
    }*/

   /* @RequestMapping(value = "/interest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> userInterest(@Valid @RequestBody UserInterest userInterest) throws Exception {
        return ResponseEntity.badRequest().body(hobbyService.addUserInterest(userInterest));
    }

    @RequestMapping(value = "{userId}/interests", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUserInterest(@PathVariable BigInteger userId) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.getUserInterest(userId));
    }*/

   /* @RequestMapping(value = "{userId}/interest/{interestId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteUserInterest(@PathVariable BigInteger userId, @PathVariable BigInteger interestId) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.deleteUserInterest(userId, interestId));
    }*/
    
    @RequestMapping(value = "/interest", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addCountryHobbies(@Valid @RequestBody UserCountryHobbiesDto userHountryHobbies) throws Exception {
        return ResponseEntity.badRequest().body(hobbyService.addCountryHobbies(userHountryHobbies));
    }
    
    @RequestMapping(value = "{userId}/interest", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUserHobbiesAndCountry(@PathVariable BigInteger userId) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.getUserHobbiesAndCountry(userId));
    }
    
    @RequestMapping(value = "{userId}/{hobbyId}/{countryName}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteInterest(@PathVariable BigInteger userId, @PathVariable BigInteger hobbyId,@PathVariable String countryName) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.deleteInterest(userId, hobbyId,countryName));
    }
}
