package com.seeka.app.dao;

import java.math.BigInteger;
import java.util.List;
import com.seeka.app.bean.Todo;

public interface ITodoDao {

    void save(Todo todo);

    void update(Todo todo);

    Todo get(BigInteger id);

    List<Todo> getByUserId(BigInteger userId);

    List<Todo> getAll();
}
