package com.seeka.app.service;

import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.exception.ValidationException;

public interface IImageService {

	String uploadImage(MultipartFile file, BigInteger entityId, String entityType);

	String addCategoryImage(MultipartFile file, String category, String subCategory, BigInteger categoryId) throws ValidationException;

}
