package com.yuzee.app.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yuzee.app.dto.FundingResponseDto;
import com.yuzee.app.dto.FundingWrapperDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EligibilityHandler {

	@Autowired
	private RestTemplate restTemplate;

	private static final String FUNDING_NAME_BY_ID = "/funding-name";

	private static final String INVOKE_EXCEPTION = "Error invoking eligibility service {}";

	private static final String INVALID_STATUS_CODE_EXCEPTION = "Error response recieved from eligibility service with error code {}";

	public List<FundingResponseDto> getFundingByFundingNameId(List<String> fundingNameIds) throws InvokeException {
		ResponseEntity<FundingWrapperDto> fundingNameAPIResponse = null;
		try {
			StringBuilder path = new StringBuilder();
			path.append(IConstant.ELIGIBILITY_CONNECTION_URL).append(FUNDING_NAME_BY_ID);

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(path.toString());
			fundingNameIds.stream().forEach(e -> uriBuilder.queryParam("funding_name_ids", e));

			fundingNameAPIResponse = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
					FundingWrapperDto.class);

			if (fundingNameAPIResponse.getStatusCode().value() != 200) {
				log.error(INVALID_STATUS_CODE_EXCEPTION, fundingNameAPIResponse.getStatusCode().value());
				throw new InvokeException("Error response recieved from eligibility service with error code "
						+ fundingNameAPIResponse.getStatusCode().value());
			}
		} catch (Exception e) {
			log.error(INVOKE_EXCEPTION, e.getMessage());
			if (e instanceof InvokeException) {
				throw e;
			} else {
				throw new InvokeException("Error invoking review service");
			}
		}
		return fundingNameAPIResponse.getBody().getData();
	}

}
