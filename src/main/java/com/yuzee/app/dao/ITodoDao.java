package com.yuzee.app.dao;

import java.util.List;

import com.yuzee.app.bean.Todo;
import com.yuzee.app.dto.TodoFolder;

public interface ITodoDao {

    void save(Todo todo);

    void update(Todo todo);

    Todo get(String id);

    List<Todo> getByUserId(String userId);

    List<Todo> getAll();

    List<Todo> getByTitle(String title);

    List<TodoFolder> getTodoFolder();
}
