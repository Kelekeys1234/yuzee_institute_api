package com.yuzee.app.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.yuzee.app.bean.Service;
import com.yuzee.app.dto.PaginationResponseDto;
import com.yuzee.app.dto.ServiceDto;
import com.yuzee.app.endpoint.ServiceInterface;
import com.yuzee.app.handler.GenericResponseHandlers;
import com.yuzee.app.processor.ServiceProcessor;
import com.yuzee.app.util.PaginationUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("serviceControllerV1")
public class ServiceController implements ServiceInterface {

	@Autowired
	private ServiceProcessor serviceProcessor;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<?> getAllServices(final Integer pageNumber, final Integer pageSize) throws Exception {
		log.debug("inside getAllServices(final Integer pageNumber, final Integer pageSize) method");

		Page<Service> servicesPage = serviceProcessor.getAllServices(pageNumber, pageSize);
		List<ServiceDto> serviceDtos = servicesPage.getContent().stream().map(e -> modelMapper.map(e, ServiceDto.class))
				.collect(Collectors.toList());

		PaginationResponseDto paginationResponseDto = PaginationUtil.calculatePaginationAndPrepareResponse(
				PaginationUtil.getStartIndex(pageNumber, pageSize), pageSize,
				((Long) servicesPage.getTotalElements()).intValue(), serviceDtos);

		return new GenericResponseHandlers.Builder().setData(paginationResponseDto)
				.setMessage("Services fetched successfully").setStatus(HttpStatus.OK).create();
	}
}
