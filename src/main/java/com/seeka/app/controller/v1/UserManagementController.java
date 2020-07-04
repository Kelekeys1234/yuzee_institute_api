package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EducationSystemResponse;
import com.seeka.app.dto.UserAchivements;
import com.seeka.app.dto.UserDto;
import com.seeka.app.dto.UserManagement;
import com.seeka.app.exception.ValidationException;
import com.seeka.app.processor.EducationSystemProcessor;
import com.seeka.app.service.UsersService;

@RestController("userManagementControllerV1")
@RequestMapping("/api/v1/user")
public class UserManagementController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private EducationSystemProcessor educationSystemService;

	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUserManagementData(@PathVariable final String userId) throws ValidationException {
		UserManagement userManagement = new UserManagement();
		UserDto userDto = usersService.getUserById(userId);
		userManagement.setUserDto(userDto);
		List<UserAchivements> userAchivements = usersService.getUserAchivementsByUserId(userId);
		userManagement.setUserAchivementsList(userAchivements);
		EducationSystemResponse educationSystemResponse = educationSystemService.getEducationSystemsDetailByUserId(userId);
		userManagement.setEducationSystemResponse(educationSystemResponse);
		return new GenericResponseHandlers.Builder().setData(userManagement).setMessage("User management data disply successfully").setStatus(HttpStatus.OK)
				.create();
	}

}
