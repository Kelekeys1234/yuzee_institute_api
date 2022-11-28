package com.yuzee.app.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.app.bean.Service;

@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {
    public Service findByNameIgnoreCase(String name);
    public List<Service> findByNameIgnoreCaseIn(List<String> names);
}
