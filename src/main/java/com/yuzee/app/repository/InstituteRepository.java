package com.yuzee.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.yuzee.app.bean.*;
import com.yuzee.app.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends MongoRepository<Institute, UUID> {

	 Page<Institute> findByCountryName(String countryName, @PageableDefault Pageable pageable);

	@Query(value = "{'countryName' : ?0}" , count = true)
	 Integer getTotalCountOfInstituteByCountryName(String countryName);

	 List<Institute> findByCityName(String cityName);

	// ******** Example********//
	//@Query("{'type':?0,'$or':[{ $where: '?1 == null' },{'key':?1},{ $where: '?2 == null' },{'username':?2}]}")
	//    List<Doc> findByType(String type, String key, String username, Pageable pageable);
	//@Query("{'$or':[ {'type':?0}, {'name':?1} ]}")

	//Previous code//
//	@Query("SELECT i from Institute i where i.id != :id and i.name = :name and (i.isDeleted is null or i.isDeleted = false  )")
    @Query(value = " {'id' : ?0 , 'name' : ?1}, '$or' : [ {'isDeleted' : null, 'isDeleted' : false} ] ")
	 List<Institute> findByIdNotAndNameAndIsDeletedFalse(String id, String name);
//Sort sort = new Sort(Sort.Direction.DESC, "date")
//    @Query(value = " {'id' : ?0 }, '$group' : [{'facultyName'}]")
//	@Query("SELECT new com.yuzee.app.dto.InstituteFacultyDto(f.id, f.name, count(*) as courseCount) "
//			+ "from Course c "
//			+ "join c.faculty f  "
//			+ "where  c.institute.id = :instituteId "
//			+ "group by c.faculty "
//			+ "order by f.name")
    @Query(value = " {'id' : ?0 }, '$group' : [{'facultyName'}]")
	 List<InstituteFacultyDto> findFacultyWithCourseCountById(String instituteId, Sort sort);

	@Query("SELECT new com.yuzee.app.dto.InstituteResponseDto(i.id,i.name, i.worldRanking, i.cityName, i.countryName,i.state, "
			+ "i.website, i.aboutInfo, i.latitude, i.longitude, i.phoneNumber,i.whatsNo, "
			+ "(select count(c.id) from Course c where c.institute.id = i.id ), i.email, i.address, i.domesticRanking, "
			+ "i.tagLine, i.createdOn) from Institute i  where i.id in :instituteIds")
	 List<InstituteResponseDto> findByIdIn(List<String> instituteIds);


	 List<Institute> findByReadableIdIn(List<String> readableIds);

	 Institute findByReadableId(String readableId);

	 @Query(value = "{ '_id' : {'$in' : ?0 } }")
	 List<Institute> findAllById(List<String> instituteIds);

   // Institute getbyId(UUID id);

    @Query(value = "{'id' : ?0}")
    List<InstituteDomesticRankingHistory> getDomesticHistoryRankingByInstituteId(String instituteId);

	@Query(value = " {'id' : ?0}")
	List<InstituteWorldRankingHistory> getHistoryOfWorldRankingByInstituteId(String instituteId);

	@Query(value = " {'name' : ?0}")
	List<Institute> getAllByInstituteName(String instituteName);

	@Query(value = " {'id' : ?0}")
	List<InstituteFacility> getFacultiesById(String instituteId);

	@Query(value = " {'id' : ?0}")
    List<InstituteEnglishRequirements> findInstituteRequirementsById(UUID id);

	Optional<Institute> getInstituteEnglishRequirementsById(UUID fromString);

	@Query(value = " {'countryName' : ?0}")
    List<InstituteType> findAllInstituteByCountryName(String countryName);

	@Query(value = "{'id' : ?0}" , fields = "{'instituteIntakes'}")
    List<String> findInstituteIntakeById(String id);

	@Query(value = "{ $or : [ {" +
			"'countryName' : {$regex : /^?0*/ , $options : 'i'}" +
			", 'cityName' : {$regex : /^?0*/, $options : 'i'}" +
			", 'worldRanking' : {$regex : /^?0*/, $options : 'i'}" +
			", 'instituteType' : {$regex : /^?0*/, $options : 'i'}" +
			", 'name' : {$regex : /^?0*/, $options : 'i'} } " +
			"] }")
	List<Institute> getBySearchText(String searchText);

	@Query(value = " {'id' : ?0}")
    List<InstituteFacility> findAllFacilityById(String instituteId);

	@Query(value = "{ 'id' : ?1, 'facilityId' : ?0 }")
	void deleteFacilityByIdAndInstituteId(String instituteFacilityId, String instituteId);

    void save(List<Institute> institutes);


}
//, fields = "{instituteTypeName : 0}, {instituteId : 1}, {type : 2}, {description : 3}, {countryName : 4}"
