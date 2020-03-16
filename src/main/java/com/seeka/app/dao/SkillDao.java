package com.seeka.app.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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
	public List<SkillDto> getDistinctSkillsListByName(Integer startIndex, Integer pageSize, String skillName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Skill.class)
				.setProjection(Projections.projectionList().add(Projections.groupProperty("skillName").as("skillName"))
						.add(Projections.property("id").as("id")))
				.setResultTransformer(Transformers.aliasToBean(SkillDto.class));
		if (StringUtils.isNotEmpty(skillName)) {
			criteria.add(Restrictions.like("skillName", skillName, MatchMode.ANYWHERE));
		}
		criteria.setFirstResult(startIndex);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	@Override
	public int getDistinctSkillsCountByName(String skillName) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sqlQuery = new StringBuilder("select distinct s.skill_name as skillName from skill s");
		if (StringUtils.isNotEmpty(skillName)) {
			sqlQuery.append(" where skill_name like ('" + "%" + skillName.trim() + "%')");
		}
		Query query = session.createSQLQuery(sqlQuery.toString());
		List<Object[]> rows = query.list();
		return rows.size();
	}
}
