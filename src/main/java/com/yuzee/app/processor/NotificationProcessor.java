package com.yuzee.app.processor;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuzee.app.bean.Course;
import com.yuzee.app.dto.ContentTemplateDataDto;
import com.yuzee.app.dto.EmailAddressDto;
import com.yuzee.app.dto.NotificationMetaDataDto;
import com.yuzee.app.dto.NotificationTemplateDataDto;
import com.yuzee.app.dto.ViewTransactionDto;
import com.yuzee.app.exception.InvokeException;
import com.yuzee.app.handler.NotificationHandler;

import lombok.extern.slf4j.Slf4j;

@Service("notificationProcessor")
@Slf4j
public class NotificationProcessor {
	
	@Autowired
	private NotificationHandler notificationHandler;
	
	public void notifyCourseChanged(Course course, List<ViewTransactionDto> favoriteTransactions,String notificationTemplateType) {
		log.info("Inside method NotificationProcessor.notifyUserMentioned");	
		if(!CollectionUtils.isEmpty(favoriteTransactions)) {
			favoriteTransactions.stream().forEach(transaction -> {
				try {
					log.info("Send notification if user liked a post");
					NotificationTemplateDataDto notificationDto = new NotificationTemplateDataDto();
					notificationDto.setNotificationTemplateType(notificationTemplateType);
					if(!ObjectUtils.isEmpty(transaction.getUser())) {
						notificationDto.setUserId(transaction.getUser().getUserId());
						
						notificationDto.setEmailDetails(new EmailAddressDto(transaction.getUser().getFirstName(),transaction.getUser().getEmail(),null,null));
						
						NotificationMetaDataDto notificationMetaDataDto = new NotificationMetaDataDto();
						notificationMetaDataDto.setTargetEntityId(course.getId());
								
						notificationMetaDataDto.setContentTemplateData(Arrays.asList(new ContentTemplateDataDto(course.getId(),course.getName(),"COURSE")));
						
						notificationDto.setNotificationMetadata(notificationMetaDataDto);
						notificationDto.setSenderUserId(course.getUpdatedBy());
						log.info("Calling notification service to send push notification");
						notificationHandler.sendPushNotification(notificationDto);						
					}
				} catch (InvokeException se) {
					log.error("Unable to send notifications to mentioned users ",se);
				}
			});
		}
	}	
}
