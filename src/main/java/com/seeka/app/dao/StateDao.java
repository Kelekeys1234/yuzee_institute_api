package com.seeka.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.State;

@Repository
@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class StateDao implements IStateDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(final State obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
	}

	@Override
	public List<State> getAll() {
		List<State> states = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("select s.id, s.name as name from state s ORDER BY s.name");
		List<Object[]> rows = query.list();
		State obj = null;
		for (Object[] row : rows) {
			obj = new State();
			obj.setId(row[0].toString());
			if (row[1] != null) {
				obj.setName(row[1].toString());
			}
			states.add(obj);
		}
		return states;
	}

	@Override
	public List<State> getStateByCountryId(final String countryId) {
		List<State> states = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"select s.id, s.name as name from state s where s.country_id = '" + countryId + "' ORDER BY s.name");
		List<Object[]> rows = query.list();
		State obj = null;
		for (Object[] row : rows) {
			obj = new State();
			obj.setId(row[0].toString());
			if (row[1] != null) {
				obj.setName(row[1].toString());
			}
			states.add(obj);
		}
		return states;
	}

	@Override
	public State get(final String id) {
		Session session = sessionFactory.getCurrentSession();
		State state = session.get(State.class, id);
		return state;
	}

	@Override
	public void update(final State obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
	}
}
