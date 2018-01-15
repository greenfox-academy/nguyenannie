package com.greenfoxacademy.mysqlconnect.Repository;

import com.greenfoxacademy.mysqlconnect.Model.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToDoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByDoneFalse();
    List<Todo> findByDoneTrue();
    Todo findByTitle(String title);
}
