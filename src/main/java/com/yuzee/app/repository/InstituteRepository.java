package com.yuzee.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Institute;
import com.yuzee.app.dto.InstituteFacultyDto;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, String> {

	public Page<Institute> findByCountryName(String countryName, @PageableDefault Pageable pageable);

	@Query("SELECT COUNT(*) FROM Institute i where i.countryName = :countryName")
	public Integer getTotalCountOfInstituteByCountryName(String countryName);

	public List<Institute> findByCityName(String cityName);

	public List<Institute> findByIdNotAndName(String id, String name);

	@Query("SELECT new com.yuzee.app.dto.InstituteFacultyDto(f.id, f.name, count(*) as courseCount) "
			+ "from Course c "
			+ "join c.faculty f  "
			+ "where  c.institute.id = :instituteId "
			+ "group by c.faculty "
			+ "order by f.name")
	public List<InstituteFacultyDto> findFacultyWithCourseCountById(String instituteId);
}
