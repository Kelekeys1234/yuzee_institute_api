package com.seeka.app.service;

import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

import com.seeka.app.exception.ValidationException;

public interface IMediaService {

	String uploadImage(MultipartFile file, BigInteger entityId, String entityType, final String subCategory);

	String addCategoryImage(MultipartFile file, String category, String subCategory, BigInteger categoryId) throws ValidationException;

	String deleteCategoryImage(BigInteger id, String category, String subCategory, BigInteger categoryId) throws ValidationException;

}
