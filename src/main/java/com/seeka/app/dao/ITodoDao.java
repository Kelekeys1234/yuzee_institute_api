package com.seeka.app.dao;

import java.util.List;

import com.seeka.app.bean.Todo;
import com.seeka.app.dto.TodoFolder;

public interface ITodoDao {

    void save(Todo todo);

    void update(Todo todo);

    Todo get(String id);

    List<Todo> getByUserId(String userId);

    List<Todo> getAll();

    List<Todo> getByTitle(String title);

    List<TodoFolder> getTodoFolder();
}
