package com.seeka.app.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.service.IMediaService;

@RestController
@RequestMapping("/media/upload")
public class MediaController {

	@Autowired
	private IMediaService iImageService;

	@PostMapping
	public ResponseEntity<Object> addCategoryImage(@RequestPart(value = "file") final MultipartFile file,
			@RequestParam(name = "category") final String category, @RequestParam(name = "subCategory", required = false) final String subCategory,
			@RequestParam(name = "categoryId") final BigInteger categoryId) throws ValidationException {
		String imageName = iImageService.addCategoryImage(file, category, subCategory, categoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(imageName).setMessage("file uploading successfully.").create();
	}

	@DeleteMapping
	public ResponseEntity<Object> deleteCategoryImage(@RequestParam(name = "id") final BigInteger id, @RequestParam(name = "category") final String category,
			@RequestParam(name = "subCategory", required = false) final String subCategory, @RequestParam(name = "categoryId") final BigInteger categoryId)
			throws ValidationException {
		String imageName = iImageService.deleteCategoryImage(id, category, subCategory, categoryId);
		return new GenericResponseHandlers.Builder().setStatus(HttpStatus.OK).setData(imageName).setMessage("file Deleting successfully.").create();
	}

}
