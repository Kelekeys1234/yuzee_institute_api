package com.seeka.app.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.seeka.app.controller.handler.GenericResponseHandlers;
import com.seeka.app.dto.EducationAgentDto;
import com.seeka.app.dto.EducationAgentPartnershipsDto;
import com.seeka.app.dto.PaginationResponseDto;
import com.seeka.app.endpoint.EducationAgentInterface;
import com.seeka.app.exception.NotFoundException;
import com.seeka.app.processor.EducationAgentProcessor;

@RestController("educationAgentControllerV1")
public class EducationAgentController implements EducationAgentInterface {

    @Autowired
    private EducationAgentProcessor educationAgentProcessor;

    @Override
    public ResponseEntity<?> saveEducationAgent(final EducationAgentDto educationAgentDto) {
        educationAgentProcessor.saveEducationAgent(educationAgentDto);
        return new GenericResponseHandlers.Builder().setMessage("Education Agent Added successfully")
				.setStatus(HttpStatus.OK).create();
    }

    @Override
    public ResponseEntity<?> updateEducationAgent(String id, final EducationAgentDto educationAgentDto) {
        educationAgentProcessor.updateEducationAgent(educationAgentDto, id);
        return new GenericResponseHandlers.Builder().setMessage("Education Agent Updated successfully")
				.setStatus(HttpStatus.OK).create();
    }

    @Override
    public ResponseEntity<?> getAllEducationAgent(final Integer pageNumber, final Integer pageSize) throws Exception {
    	PaginationResponseDto paginationResponseDto = educationAgentProcessor.getAllEducationAgent(pageNumber, pageSize);
        return new GenericResponseHandlers.Builder().setMessage("Education Agent fetched successfully").setData(paginationResponseDto)
				.setStatus(HttpStatus.OK).create();
    }

    @Override
    public ResponseEntity<?> getEducationAgent(final String id) throws Exception {
    	EducationAgentDto agentDto = educationAgentProcessor.getEducationAgent(id);
        return new GenericResponseHandlers.Builder().setMessage("Education Agent fetched successfully").setData(agentDto)
				.setStatus(HttpStatus.OK).create();
    }

    @Override
    public ResponseEntity<?> deleteEducationAgent(String id) throws NotFoundException {
    	educationAgentProcessor.deleteEducationAgent(id);
        return new GenericResponseHandlers.Builder().setMessage("Education Agent Updated successfully").setStatus(HttpStatus.OK).create();
    }

    @Override
    public ResponseEntity<?> saveEducationAgentPartnership(final List<EducationAgentPartnershipsDto> agentPartnershipsDto) throws Exception {
        educationAgentProcessor.savePartnership(agentPartnershipsDto);
        return new GenericResponseHandlers.Builder().setMessage("Education Agent Partnership Saved successfully").setStatus(HttpStatus.OK).create();
    }
}
