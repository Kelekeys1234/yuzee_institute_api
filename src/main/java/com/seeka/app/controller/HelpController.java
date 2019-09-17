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

import com.seeka.app.dto.HelpDto;
import com.seeka.app.service.IHelpService;

@RestController
@RequestMapping("/help")
public class HelpController {

    @Autowired
    private IHelpService helpService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> save(@Valid @RequestBody final HelpDto helpDto) throws Exception {
        return ResponseEntity.accepted().body(helpService.save(helpDto));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(helpService.get(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable BigInteger id, @RequestBody final HelpDto helpDto) {
        return ResponseEntity.accepted().body(helpService.update(helpDto, id));
    }
}
