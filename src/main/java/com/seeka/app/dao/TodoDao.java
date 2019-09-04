package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seeka.app.bean.Todo;

@Repository
@Transactional
public class TodoDao implements ITodoDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(Todo todo) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(todo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(Todo todo) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(todo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Todo get(BigInteger id) {
        Todo todo = new Todo();
        try {
            Session session = sessionFactory.getCurrentSession();
            todo = session.get(Todo.class, id);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return todo;
    }

    @Override
    public List<Todo> getByUserId(BigInteger userId) {
        List<Todo> todos = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria crit = session.createCriteria(Todo.class);
            crit.add(Restrictions.eq("userId", userId));
            todos = crit.list();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return todos;
    }

    @Override
    public List<Todo> getAll() {
        List<Todo> todos = new ArrayList<>();
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria crit = session.createCriteria(Todo.class);
            todos = crit.list();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return todos;
    }

}
