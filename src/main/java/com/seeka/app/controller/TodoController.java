package com.seeka.app.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.seeka.app.dto.TodoDto;
import com.seeka.app.service.ITodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    public ITodoService iTodoService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> saveTodo(@Valid @RequestBody final TodoDto todoDto) throws Exception {
        Map<String, Object> response = new HashMap<>(3);
        try {
            iTodoService.save(todoDto);
            response.put("message", "Todo type saved successfully");
            response.put("status", HttpStatus.OK.value());
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", "Error to save todo");
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("data", "Error");
        }
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> get(@PathVariable BigInteger id) throws Exception {
        Map<String, Object> response = new HashMap<>(3);
        try {
            TodoDto todoDto = iTodoService.get(id);
            if (todoDto != null) {
                response.put("message", "Todo fetch successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", todoDto);
            } else {
                response.put("message", "Todo not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("data", todoDto);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", "Error to fetching todo");
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("data", "Error");
        }
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateTodo(@PathVariable BigInteger id, @Valid @RequestBody TodoDto todoDto) throws Exception {
        Map<String, Object> response = new HashMap<>(3);
        try {
            iTodoService.update(todoDto, id);
            response.put("message", "Todo type update successfully");
            response.put("status", HttpStatus.OK.value());
            response.put("data", todoDto);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", "Error to update todo");
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("data", "Error");
        }
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getTodoByUserId(@PathVariable BigInteger userId) throws Exception {
        Map<String, Object> response = new HashMap<>(3);
        try {
            List<TodoDto> todoDto = iTodoService.getByUserId(userId);
            if (!todoDto.isEmpty() && todoDto != null) {
                response.put("message", "Todo fetch successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", todoDto);
            } else {
                response.put("message", "Todo not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("data", todoDto);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", "Error to fetching todo");
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("data", "Error");
        }
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getAll() throws Exception {
        Map<String, Object> response = new HashMap<>(3);
        try {
            List<TodoDto> todoDto = iTodoService.getAll();
            if (!todoDto.isEmpty() && todoDto != null) {
                response.put("message", "Todo fetch successfully");
                response.put("status", HttpStatus.OK.value());
                response.put("data", todoDto);
            } else {
                response.put("message", "Todo not found");
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("data", todoDto);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            response.put("message", "Error to fetching todo");
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("data", "Error");
        }
        return ResponseEntity.accepted().body(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> delete(@Valid @PathVariable final BigInteger id) throws Exception {
        return ResponseEntity.accepted().body(iTodoService.delete(id));
    }

}
