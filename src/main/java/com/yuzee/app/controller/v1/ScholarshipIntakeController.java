//package com.yuzee.app.controller.v1;
//
//import java.util.List;
//import java.util.Locale;
//
//import javax.validation.Valid;
//
//import org.apache.commons.lang3.EnumUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.yuzee.app.dto.ValidList;
//import com.yuzee.app.endpoint.ScholarshipIntakeInterface;
//import com.yuzee.app.enumeration.StudentCategory;
//import com.yuzee.app.processor.ScholarshipIntakeProcessor;
//import com.yuzee.common.lib.dto.institute.ScholarshipIntakeDto;
//import com.yuzee.common.lib.exception.ForbiddenException;
//import com.yuzee.common.lib.exception.NotFoundException;
//import com.yuzee.common.lib.exception.RuntimeValidationException;
//import com.yuzee.common.lib.exception.ValidationException;
//import com.yuzee.common.lib.handler.GenericResponseHandlers;
//import com.yuzee.common.lib.util.Utils;
//import com.yuzee.local.config.MessageTranslator;
//
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@Slf4j
//public class ScholarshipIntakeController implements ScholarshipIntakeInterface {
//
//	@Autowired
//	private ScholarshipIntakeProcessor scholarshipIntakeProcessor;
//	@Autowired
//	private MessageTranslator messageTranslator;
//	@Override
//	public ResponseEntity<?> saveUpdateScholarshipIntake(String userId, String scholarshipId,
//			@Valid ValidList<ScholarshipIntakeDto> scholarshipIntakeDtos)
//			throws ValidationException, NotFoundException {
//		log.info("inside ScholarshipIntakeController.saveUpdateScholarshipIntake");
//
//		scholarshipIntakeDtos.stream().forEach(e -> {
//			if (!EnumUtils.isValidEnumIgnoreCase(StudentCategory.class, e.getStudentCategory())) {
//				String studentCategories = Utils.getEnumNamesAsString(StudentCategory.class);
//				log.error(messageTranslator.toLocale("scolarship-intake.category", studentCategories,Locale.US));
//				throw new RuntimeValidationException(
//						messageTranslator.toLocale("scolarship-intake.category", studentCategories));
//			}
//		});
//		scholarshipIntakeProcessor.saveUpdateScholarshipIntakes(userId, scholarshipId, scholarshipIntakeDtos);
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("scholarship_intake.added"))
//				.setStatus(HttpStatus.OK).create();
//	}
//
//	@Override
//	public ResponseEntity<?> deleteByScholarshipIntakeIds(String userId, String scholarshipId,
//			List<String> scholarshipIntakeIds) throws ValidationException, NotFoundException, ForbiddenException {
//		log.info("inside ScholarshipIntakeController.deleteByScholarshipIntakeIds");
//		scholarshipIntakeProcessor.deleteByScholarshipIntakeIds(userId, scholarshipId, scholarshipIntakeIds);
//		return new GenericResponseHandlers.Builder().setMessage(messageTranslator.toLocale("scholarship_intake.deleted"))
//				.setStatus(HttpStatus.OK).create();
//	}
//}
