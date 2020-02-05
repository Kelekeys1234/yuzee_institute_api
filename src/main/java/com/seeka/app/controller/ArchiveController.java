package com.seeka.app.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.constant.ArchiveEntityType;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IEnrollmentService;
import com.seeka.app.service.IErrorReportService;
import com.seeka.app.service.IHelpService;

@RestController
@RequestMapping("/archive")
public class ArchiveController {

	@Autowired
	private IEnrollmentService iEnrollmentService;

	@Autowired
	private IHelpService iHelpService;

	@Autowired
	private IErrorReportService iErrorReportService;

	@PostMapping("/entityType/{entityType}/entityId/{entityId}")
	public ResponseEntity<Object> addArchive(@PathVariable final String entityType, @PathVariable final String entityId,
			@RequestParam final boolean isArchive) throws ValidationException {
		if (entityType.equals(ArchiveEntityType.ENROLLMENT.name())) {
			iEnrollmentService.archiveEnrollment(entityId, isArchive);
		} else if (entityType.equals(ArchiveEntityType.ERROR_REPORT.name())) {
			iErrorReportService.archiveErrorReport(entityId, isArchive);
		} else if (entityType.equals(ArchiveEntityType.HELP_SUPPORT.name())) {
			iHelpService.archiveHelpSupport(entityId, isArchive);
		}
		return new GenericResponseHandlers.Builder().setMessage("Archive successfully").setStatus(HttpStatus.OK).create();

	}

}
