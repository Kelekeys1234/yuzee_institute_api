package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Skill;
import com.seeka.app.dto.SkillDto;

@Repository
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
public class SkillDao implements ISkillDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SkillDto> autoSearch(Integer pageNumber, Integer pageSize, final String searchKey) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "select distinct s.id, s.skill_name as name from skill s where s.skill_name like '%"
				+ searchKey.trim() + "%'";
		sqlQuery = sqlQuery + " LIMIT " + pageNumber + " ," + pageSize;
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SkillDto> skills = new ArrayList<>();
		SkillDto obj = null;
		for (Object[] row : rows) {
			obj = new SkillDto();
			obj.setId(row[0].toString());
			obj.setSkillName(row[1].toString());
			skills.add(obj);
		}
		return skills;
	}

	@Override
	public Integer getAllSkillNamesCount(final String searchString) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Skill.class, "skill");
		if (searchString != null) {
			criteria.add(Restrictions.ilike("skill.skillName", searchString, MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.property("skill.skillName"));
		List<String> skillNameList = criteria.list();
		Set<String> skillNameSet = skillNameList == null ? null : new HashSet<>(skillNameList);
		return skillNameSet != null ? skillNameSet.size() : 0;
	}
}
