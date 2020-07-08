package com.seeka.app.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.seek.app.exception.FileStorageException;
import com.seeka.app.constant.Constant;
import com.seeka.app.dto.StorageDto;
import com.seeka.app.dto.StorageDtoWrapper;


@Service
public class StorageHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(StorageHandler.class);

	@Autowired
	private RestTemplate restTemplate;

	private static final String GET_CERTIFICATES_BY_ID = "/api/v1";
	private static final String DELETE_CERTIFICATES_BY_ID = "/api/v1/deleteStorage";

	public List<StorageDto> getCertificates(String entityId, String entityType) throws Exception {
		ResponseEntity<StorageDtoWrapper> listOfCertificatesResponse = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.STORAGE_BASE_PATH).append(GET_CERTIFICATES_BY_ID);
			// adding the query params to the URL
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString())
					.queryParam("type", entityType).queryParam("entityId", entityId)
					.queryParam("entityType", entityType);
			LOGGER.info("Calling storage service to fetch certificates for asset Id {}", entityId);
			listOfCertificatesResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
					StorageDtoWrapper.class);
			if (listOfCertificatesResponse.getStatusCode().value() != 200) {
				throw new FileStorageException("Error response recieved from storage service with error code "
						+ listOfCertificatesResponse.getStatusCode().value());
			}

		} catch (Exception e) {
			LOGGER.error("Error invoking storage service {}", e);
			if (e instanceof FileStorageException) {
				throw e;
			} else {
				throw new FileStorageException("Error invoking storage service");
			}
		}
		return listOfCertificatesResponse.getBody().getData();
	}

	public void deleteCertificates(String assetID) throws Exception {
		ResponseEntity<Void> response;
		try {
			StringBuilder path = new StringBuilder();
			path.append(Constant.STORAGE_BASE_PATH).append(DELETE_CERTIFICATES_BY_ID);
			// adding the query params to the URL
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString()).queryParam("entityId",
					assetID);
			LOGGER.info("Calling storage service to delete certificates for asset Id {}", assetID);
			response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.DELETE, null, Void.class);
			if (response.getStatusCode().value() != 200) {
				throw new FileStorageException("Error response recieved from storage service with error code "
						+ response.getStatusCode().value());
			}

		} catch (Exception e) {
			LOGGER.error("Error invoking storage service {}", e);
			if (e instanceof FileStorageException) {
				throw e;
			} else {
				throw new FileStorageException("Error invoking storage service");
			}
		}
	}

}
