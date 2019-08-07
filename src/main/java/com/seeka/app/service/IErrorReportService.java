package com.seeka.app.service;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.seeka.app.dto.ErrorReportDto;

public interface IErrorReportService {

    public ResponseEntity<?> save(ErrorReportDto errorReport);

    public ResponseEntity<?> getErrorReportByUserId(BigInteger userId);

    public ResponseEntity<?> getAllErrorReport();

    public ResponseEntity<?> deleteByUserId(@Valid BigInteger userId);

    public ResponseEntity<?> getAllErrorCategory();

}
