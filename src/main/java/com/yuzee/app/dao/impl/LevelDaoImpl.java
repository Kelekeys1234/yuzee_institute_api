package com.yuzee.app.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.app.bean.Level;
import com.yuzee.app.dao.LevelDao;
import com.yuzee.app.repository.LevelRepository;

@Component
@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
public class LevelDaoImpl implements LevelDao {

	@Autowired
	private LevelRepository levelRepository;

	@Override
	public void addUpdateLevel(Level level) {
		levelRepository.save(level);
	}

	@Override
	public Optional<Level> getLevel(UUID levelId) {
		return levelRepository.findById(levelId.toString());
	}

	@Override
	public List<Level> getAll() {
		return levelRepository.findAllByOrderBySequenceNoAsc();
	}

	// TODO No use of this method as its not used anywhere
	/*
	 * @Override public List<Level> getCourseTypeByCountryId(String countryID) {
	 * Session session = sessionFactory.getCurrentSession(); Query query = session.
	 * createSQLQuery("select distinct ct.id, ct.type_txt as courseType from course_type ct  inner join faculty f  on f.course_type_id = ct.id "
	 * +
	 * "inner join course c  on c.faculty_id = f.id inner join institute_course ic  on ic.course_id = c.id "
	 * + "where ic.country_id = :countryId") .setParameter("countryId", countryID);
	 * List<Object[]> rows = query.list(); List<Level> courseTypes = new
	 * ArrayList<>(); for (Object[] row : rows) { Level obj = new Level();
	 * obj.setId(row[0].toString()); obj.setName(row[1].toString());
	 * courseTypes.add(obj); } return courseTypes; }
	 */

	// TODO API using this method is depricated
	/*
	 * @Override public List<Level> getLevelByCountryId(String countryId) { Session
	 * session = sessionFactory.getCurrentSession(); Query query = session.
	 * createSQLQuery("select distinct le.id, le.name as name,le.code as levelkey from level le inner join institute_level il  on il.level_id = le.id "
	 * + "inner join country c on c.name = il.country_name " +
	 * "where il.country_name = :countryId").setParameter("countryId", countryId);
	 * List<Object[]> rows = query.list(); List<Level> level = new ArrayList<>();
	 * for (Object[] row : rows) { Level obj = new Level();
	 * obj.setId(row[0].toString()); obj.setName(row[1].toString());
	 * obj.setCode(row[2].toString()); level.add(obj); } return level; }
	 */

	// TODO API using this method is depricated
	/*
	 * @Override public List<Level> getAllLevelByCountry() { Session session =
	 * sessionFactory.getCurrentSession(); Query query = session.
	 * createSQLQuery("select distinct le.id, le.name as name,le.code as levelkey,il.country_name from level le  inner join institute_level il  on "
	 * + "il.level_id = le.id inner join country c  on c.name = il.country_name");
	 * List<Object[]> rows = query.list(); List<Level> level = new ArrayList<>();
	 * 
	 * for (Object[] row : rows) { Level obj = new Level();
	 * obj.setId(row[0].toString()); obj.setName(row[1].toString()); if (row[2] !=
	 * null) { obj.setCode(row[2].toString()); } //
	 * obj.setCountryId(row[3].toString()); level.add(obj); } return level; }
	 */

	// TODO API using this method is depricated
	/*
	 * @Override public List<LevelDto> getCountryLevel(String countryId) { Session
	 * session = sessionFactory.getCurrentSession(); Query query = session.
	 * createSQLQuery("select ll.id, ll.name as name,ll.code as levelkey from level ll inner join institute_level il on il.level_id = ll.id"
	 * +
	 * " inner join institute i on i.id = il.institute_id where i.country_name =:countryId"
	 * ) .setParameter("countryId", countryId); List<Object[]> rows = query.list();
	 * List<LevelDto> level = new ArrayList<>(); for (Object[] row : rows) {
	 * LevelDto obj = new LevelDto(); obj.setId(row[0].toString());
	 * obj.setName(row[1].toString()); obj.setCode(row[2].toString());
	 * level.add(obj); } return level; }
	 */

	// TODO API using this method is depricated
	/*
	 * @Override public List<String> getAllLevelNamesByInstituteId(final String
	 * instituteId) { List<String> list = new ArrayList<String>(); Session session =
	 * sessionFactory.getCurrentSession(); Query query = session.
	 * createSQLQuery("select l.id, l.`name` from institute_level il left join level l on l.id = il.level_id where il.institute_id ='"
	 * +instituteId+"'"); List<Object[]> rows = query.list(); for (Object[] row :
	 * rows) { list.add(new String(row[1].toString())); } return list; }
	 */

	@Override
	public List<Level> findByIdIn(List<String> ids) {
		return (List<Level>) levelRepository.findAllById(ids);
	}

	@Override
	public Level getLevelByLevelCode(String levelCode) {
		return levelRepository.findByCode(levelCode);
	}

	@Override
	@Transactional
	public Map<String, UUID> getAllLevelMap() {
		Map<String, UUID> levelMap = new HashMap<>();
		List<Level> levels = levelRepository.findAll();
		levels.stream().forEach(level -> {
			levelMap.put(level.getCode(), UUID.fromString(level.getId()));
		});
		return levelMap;
	}
}
