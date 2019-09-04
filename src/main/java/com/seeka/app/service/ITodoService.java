package com.seeka.app.service;

import java.math.BigInteger;
import java.util.List;
import com.seeka.app.dto.TodoDto;

public interface ITodoService {
    
    public void save(TodoDto todoDto);
    public void update(TodoDto todoDto, BigInteger id);
    public TodoDto get(BigInteger id);
    public List<TodoDto> getByUserId(BigInteger userId);
    public List<TodoDto> getAll();
}
