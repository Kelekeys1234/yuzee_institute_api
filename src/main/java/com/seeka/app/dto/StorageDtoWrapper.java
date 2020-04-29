package com.seeka.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class StorageDtoWrapper {
 
	private String message;

	private String status;
	
    private List<StorageDto> data; 
}
