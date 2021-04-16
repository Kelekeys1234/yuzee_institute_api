package com.yuzee.app.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yuzee.app.dto.NotificationTemplateDataDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.util.IConstant;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class NotificationHandler {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String PUSH_NOTIFICATION = "/notification";
	
	public void sendPushNotification(NotificationTemplateDataDto notificationDto) throws InvokeException{
		try {
			StringBuilder path = new StringBuilder();
			path.append(IConstant.NOTIFICATION_CONNECTION_URL).append(PUSH_NOTIFICATION);
			ResponseEntity<String> status = restTemplate.postForEntity(path.toString(), notificationDto, String.class);
			if(status.getStatusCodeValue() != 200) {
				throw new InvokeException("Error response recieved from notification service with error code " + status.getStatusCodeValue());
			}
		} catch (Exception e) {
			log.error("Error invoking notification service {}", e);
			throw new InvokeException("Error invoking notification service");
		}
	}
}
