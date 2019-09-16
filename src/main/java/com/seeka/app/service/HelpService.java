package com.seeka.app.service;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.dao.IHelpDAO;
import com.seeka.app.dto.HelpDto;
import com.seeka.app.util.IConstant;

@Service
@Transactional
public class HelpService implements IHelpService {

    @Autowired
    private IHelpDAO helpDAO;

    @Override
    public Map<String, Object> save(@Valid HelpDto helpDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("status", HttpStatus.OK.value());
            response.put("message", IConstant.HELP_SUCCESS_MESSAGE);
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return response;
    }
}
