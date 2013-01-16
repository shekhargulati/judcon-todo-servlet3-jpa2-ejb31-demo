package com.todo.service;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.todo.domain.TodoList;

@Stateless
public class TodoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public TodoList create(TodoList todoList){
		entityManager.persist(todoList);
		return todoList;
	}
	

	@Asynchronous
	public Future<TodoList> createAsync(TodoList todoList){
		try {
			System.out.println("Sleeping for 15 seconds");
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// ignore it
		}
		entityManager.persist(todoList);
		return new AsyncResult<TodoList>(todoList);
	}


	public TodoList find(Long id) {
		return entityManager.find(TodoList.class, id);
	}

}
