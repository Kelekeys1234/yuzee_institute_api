package com.seeka.app.controller.v1;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.service.IEducationAgentService;
import com.seeka.app.service.ISkillService;

@RestController("skillControllerV1")
@RequestMapping("/api/v1/skill")
@Transactional
public class SkillController {

    @Autowired
    private IEducationAgentService educationService;
    
    @Autowired
    private ISkillService iSkillService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllSkill() throws Exception {
        return ResponseEntity.accepted().body(educationService.getAllSkill());
    }
    
    @RequestMapping(value = "/autoSearch/{searchKey}/pageNumber/{pageNumber}/pageSize/{pageSize}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> autoSearch(@PathVariable final String searchKey, @PathVariable final Integer pageNumber, @PathVariable final Integer pageSize) throws Exception {
        return ResponseEntity.accepted().body(iSkillService.autoSearch(pageNumber, pageSize, searchKey));
    }
}
