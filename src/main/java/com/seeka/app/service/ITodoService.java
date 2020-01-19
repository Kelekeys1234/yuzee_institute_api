package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.seeka.app.dto.TodoDto;
import com.seeka.app.dto.TodoFolder;

public interface ITodoService {

    public void save(TodoDto todoDto);

    public void update(TodoDto todoDto, BigInteger id);

    public TodoDto get(BigInteger id);

    public List<TodoDto> getByUserId(BigInteger userId);

    public List<TodoDto> getAll();

    public Map<String, Object> delete(@Valid BigInteger id);

    public List<TodoDto> getByTitle(String title);

    public List<TodoFolder> getTodoFolder();
}
