package com.seeka.app.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HelpDAO implements IHelpDAO {

    @Autowired
    private SessionFactory sessionFactory;

}
