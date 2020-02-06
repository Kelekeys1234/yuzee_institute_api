package com.seeka.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.service.IHobbyService;

@RestController("hobbiesControllerV1")
@RequestMapping("/api/v1/hobbies")
public class HobbiesController {

    @Autowired
    private IHobbyService hobbyService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getHobbies() throws Exception {
        return ResponseEntity.accepted().body(hobbyService.getHobbies());
    }

    @RequestMapping(value = "/search/{searchText}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> searchHobbies(@PathVariable String searchText) throws Exception {
        return ResponseEntity.accepted().body(hobbyService.searchHobbies(searchText));
    }
}
