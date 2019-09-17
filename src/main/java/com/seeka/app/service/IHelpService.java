package com.seeka.app.service;

import java.math.BigInteger;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.dto.HelpDto;

public interface IHelpService {

    public Map<String, Object> save(@Valid HelpDto helpDto);

    public Map<String, Object> get(BigInteger id);

    public Map<String, Object> update(HelpDto helpDto, BigInteger id);

}
