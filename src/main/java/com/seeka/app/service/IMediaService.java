package com.seeka.app.service;

import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {

	String uploadImage(MultipartFile file, BigInteger entityId, String entityType, final String type);

}
