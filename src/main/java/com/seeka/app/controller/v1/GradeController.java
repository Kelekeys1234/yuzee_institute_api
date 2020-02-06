package com.seeka.app.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.dto.GradeDto;
import com.seeka.app.service.IEducationSystemService;

@RestController("gradeControllerV1")
@RequestMapping("/v1/grade")
public class GradeController {

    @Autowired
    private IEducationSystemService educationSystemService;

    @RequestMapping(value = "/calculate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> calculate(@Valid @RequestBody final GradeDto gradeDto) throws Exception {
        return educationSystemService.calculate(gradeDto);
    }
}
