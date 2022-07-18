package com.yuzee.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.InstituteService;
import com.yuzee.common.lib.dto.CountDto;

@Repository
public interface InstituteServiceRepository extends MongoRepository<InstituteService, String> {

	@Query(value = "{'id' : ?0}")
	 List<InstituteService> findByInstituteId(String instituteId);

	 InstituteService findByInstituteIdAndServiceId(String instituteId, String serviceId);

	@Query(value = "{ 'id' : { $all: ?0 } }", fields = "{'count' : 1 , 'id' : 0}")
	 List<CountDto> countByInstituteIdsIn(List<String> instituteIds);

	//@Query("SELECT new com.yuzee.common.lib.dto.CountDto(s.institute.id, count(s)) from InstituteService s JOIN s.institute i WHERE i.id IN :instituteIds group by i.id")
}
