package com.seeka.app.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seeka.app.bean.ErrorReport;
import com.seeka.app.bean.ErrorReportCategory;
import com.seeka.app.dao.IErrorReportDAO;
import com.seeka.app.dto.ErrorReportDto;
import com.seeka.app.util.DateUtil;

@Service
@Transactional
public class ErrorReportService implements IErrorReportService {

    @Autowired
    private IErrorReportDAO errorReportDAO;

    @Override
    public ResponseEntity<?> save(ErrorReportDto errorReportDto) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (errorReportDto != null) {
                ErrorReport errorReport = getErrorReport(errorReportDto);
                errorReportDAO.save(errorReport);
                response.put("message", "Error added successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Error can't be empty");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> update(ErrorReportDto errorReportDto, BigInteger id) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            if (errorReportDto != null && id.compareTo(new BigInteger("0")) == 1) {
                System.out.println("The Id is: " + id);
                ErrorReport errorReport = getErrorReport(errorReportDto);
                errorReport.setId(id);
                errorReportDAO.addErrorRepoerAudit(id);
                errorReportDAO.update(errorReport);
                response.put("message", "Error added successfully");
                response.put("status", HttpStatus.OK.value());
            } else if (id.compareTo(new BigInteger("0")) != 1) {
                response.put("message", "Error Bad Request");
                response.put("status", HttpStatus.BAD_REQUEST.value());
            } else {
                response.put("message", "Error can't be empty");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }

    private ErrorReport getErrorReport(ErrorReportDto errorReportDto) {
        ErrorReport errorReport = new ErrorReport();
        errorReport.setDescription(errorReportDto.getDescription());
        errorReport.setErrorReportCategory(errorReportDAO.getErrorCategory(errorReportDto.getErrorReportCategoryId()));
        errorReport.setUserId(errorReportDto.getUserId());
        errorReport.setCreatedBy(errorReportDto.getCreatedBy());
        errorReport.setCreatedOn(DateUtil.getUTCdatetimeAsDate());
        errorReport.setUpdatedBy(errorReportDto.getUpdatedBy());
        errorReport.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
        errorReport.setIsActive(true);
        errorReport.setCaseNumber(errorReportDto.getCaseNumber());
        errorReport.setStatus(errorReportDto.getStatus());
        errorReport.setCourseArticleId(errorReportDto.getCourseArticleId());
        if(errorReportDto.getDueDate()!=null && !errorReportDto.getDueDate().isEmpty()){
            errorReport.setDueDate(DateUtil.stringDateToDateYYYY_MM_DDFormat(errorReportDto.getDueDate()));
        }
        errorReport.setAssigneeUserId(errorReportDto.getAssigneeUserId());
        return errorReport;
    }

    @Override
    public ResponseEntity<?> getErrorReportByUserId(BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            List<ErrorReport> errorReports = errorReportDAO.getErrorReportByUserId(userId);
            if (errorReports != null && !errorReports.isEmpty()) {
                response.put("message", "Error fetched successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", errorReports);
            } else {
                response.put("message", "Error not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> getErrorReportById(BigInteger id) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            ErrorReport errorReports = errorReportDAO.getErrorReportById(id);
            if (errorReports != null) {
                response.put("message", "Error fetched successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", errorReports);
            } else {
                response.put("message", "Error not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> getAllErrorReport() {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            List<ErrorReport> errorReports = errorReportDAO.getAllErrorReport();
            if (errorReports != null && !errorReports.isEmpty()) {
                response.put("message", "Error fetched successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", errorReports);
            } else {
                response.put("message", "Error not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> deleteByUserId(@Valid BigInteger userId) {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            List<ErrorReport> errorReports = errorReportDAO.getErrorReportByUserId(userId);
            if (errorReports != null && !errorReports.isEmpty()) {
                for (ErrorReport errorReport : errorReports) {
                    errorReport.setUpdatedOn(DateUtil.getUTCdatetimeAsDate());
                    errorReport.setDeletedOn(DateUtil.getUTCdatetimeAsDate());
                    errorReport.setIsActive(false);
                    errorReportDAO.update(errorReport);
                }
                response.put("message", "Error deleted successfully");
                response.put("status", HttpStatus.OK.value());
            } else {
                response.put("message", "Error not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<?> getAllErrorCategory() {
        Map<String, Object> response = new HashMap<String, Object>();
        try {
            List<ErrorReportCategory> errorReportCategoriesrorReports = errorReportDAO.getAllErrorCategory();
            if (errorReportCategoriesrorReports != null && !errorReportCategoriesrorReports.isEmpty()) {
                response.put("message", "Error category fetched successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", errorReportCategoriesrorReports);
            } else {
                response.put("message", "Error category not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
            }
        } catch (Exception exception) {
            response.put("message", exception.getCause());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return ResponseEntity.ok().body(response);
    }

}
