package com.seeka.app.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.bean.QuestionCategroy;
import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IQuestionCategroyService;

@RestController("questionCategroyControllerV1")
@RequestMapping("/v1/question/category")
public class QuestionCategroyController {

	@Autowired
	private IQuestionCategroyService iQuestionCategroy;

	@PostMapping
	public ResponseEntity<?> addQuestionCategory(@Valid @RequestBody final QuestionCategroy questionCategroy, final BindingResult bindingResult)
			throws ValidationException {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			throw new ValidationException(fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(",")));
		}

		QuestionCategroy questionCategory = iQuestionCategroy.addQuestionCategory(questionCategroy);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(questionCategory).setMessage("Created question Category successfully")
				.create();
	}

	@GetMapping
	public ResponseEntity<?> getQuestionCategoryList() {
		List<QuestionCategroy> questionCategory = iQuestionCategroy.getQuestionCategoryList();
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(questionCategory).setMessage("Get question Category list successfully")
				.create();
	}

	@DeleteMapping("/{questionCategoryId}")
	public ResponseEntity<?> deleteQuestionCategory(@PathVariable final String questionCategoryId) throws ValidationException {
		iQuestionCategroy.deleteQuestionCategory(questionCategoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setMessage("Deleted question Category successfully").create();
	}
}
