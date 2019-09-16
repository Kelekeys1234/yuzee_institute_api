package com.seeka.app.service;

import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.dto.HelpDto;

public interface IHelpService {

    public Map<String, Object> save(@Valid HelpDto helpDto);

}
