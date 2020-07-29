package com.yuzee.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.constant.ArchiveEntityType;
import com.yuzee.app.exception.ValidationException;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.HelpProcessor;
import com.yuzee.app.service.IErrorReportService;

@RestController("archiveControllerV1")
@RequestMapping("/api/v1/archive")
public class ArchiveController {

//	@Autowired
//	private IEnrollmentService iEnrollmentService;

	@Autowired
	private HelpProcessor helpProcessor;

	@Autowired
	private IErrorReportService iErrorReportService;

	@PostMapping("/entityType/{entityType}/entityId/{entityId}")
	public ResponseEntity<Object> addArchive(@PathVariable final String entityType, @PathVariable final String entityId,
			@RequestParam final boolean isArchive) throws ValidationException {
		if (entityType.equals(ArchiveEntityType.ENROLLMENT.name())) {
			// TODO call enrollment API to make it archive
			// iEnrollmentService.archiveEnrollment(entityId, isArchive);
		} else if (entityType.equals(ArchiveEntityType.ERROR_REPORT.name())) {
			iErrorReportService.archiveErrorReport(entityId, isArchive);
		} else if (entityType.equals(ArchiveEntityType.HELP_SUPPORT.name())) {
			helpProcessor.archiveHelpSupport(entityId, isArchive);
		}
		return new GenericResponseHandlers.Builder().setMessage("Archive successfully").setStatus(HttpStatus.OK).create();

	}

}
