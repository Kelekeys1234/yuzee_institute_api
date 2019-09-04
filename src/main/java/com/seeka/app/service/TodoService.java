package com.seeka.app.service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.seeka.app.bean.Todo;
import com.seeka.app.dao.ITodoDao;
import com.seeka.app.dto.TodoDto;
import com.seeka.app.util.CommonUtil;

@Service
public class TodoService implements ITodoService {

    @Autowired
    private ITodoDao iTodoDao;

    @Override
    public void save(TodoDto todoDto) {
        Todo todo = CommonUtil.convertTodoDtoIntoTodo(todoDto);
        iTodoDao.save(todo);
    }

    @Override
    public void update(TodoDto todoDto, BigInteger id) {
        Todo todo = CommonUtil.convertTodoDtoIntoTodo(todoDto);
        todo.setId(id);
        iTodoDao.update(todo);
    }

    @Override
    public TodoDto get(BigInteger id) {
        Todo todo = iTodoDao.get(id);
        TodoDto todoDto = CommonUtil.convertTodoIntoTodoDto(todo);
        return todoDto;
    }

    @Override
    public List<TodoDto> getByUserId(BigInteger userId) {
        List<Todo> list = iTodoDao.getByUserId(userId);
        List<TodoDto> todoDtos = new LinkedList<>();
        for (Todo todo : list) {
            TodoDto todoDto = CommonUtil.convertTodoIntoTodoDto(todo);
            todoDtos.add(todoDto);
        }
        return todoDtos;
    }

    @Override
    public List<TodoDto> getAll() {
        List<Todo> list = iTodoDao.getAll();
        List<TodoDto> todoDtos = new LinkedList<>();
        for (Todo todo : list) {
            TodoDto todoDto = CommonUtil.convertTodoIntoTodoDto(todo);
            todoDtos.add(todoDto);
        }
        return todoDtos;
    }

}
