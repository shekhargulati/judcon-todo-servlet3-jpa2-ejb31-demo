package com.todo.service;

import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.todo.domain.TodoList;

@Stateless
@Local
public class TodoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	public TodoList create(TodoList todoList){
		entityManager.persist(todoList);
		return todoList;
	}
	

	@Asynchronous
	public TodoList createAsync(TodoList todoList){
		try {
			System.out.println("Sleeping for 15 seconds");
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// ignore it
		}
		entityManager.persist(todoList);
		return todoList;
	}


	public TodoList find(Long id) {
		return entityManager.find(TodoList.class, id);
	}

}
