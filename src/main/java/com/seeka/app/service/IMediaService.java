package com.seeka.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {

	String uploadImage(MultipartFile file, String entityId, String entityType, final String type);

}
