package com.seeka.app.controller.v1;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.service.IEducationAgentService;

@RestController("skillControllerV1")
@RequestMapping("/v1/skill")
@Transactional
public class SkillController {

    @Autowired
    private IEducationAgentService educationService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAllSkill() throws Exception {
        return ResponseEntity.accepted().body(educationService.getAllSkill());
    }
}
