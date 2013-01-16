package com.todo.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.todo.domain.TodoList;

@Stateless
public class TodoService {

	@PersistenceContext
	private EntityManager entityManager;

	public TodoList create(TodoList todoList) {
		entityManager.persist(todoList);
		return todoList;
	}

	@Asynchronous
	public Future<TodoList> createAsync(TodoList todoList) {
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
		TodoList todoList = entityManager.find(TodoList.class, id);
		initTags(todoList);
		return todoList;
	}

	public List<TodoList> findByTag(String... tags){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

	    CriteriaQuery<TodoList> query = builder.createQuery(TodoList.class);
	    Root<TodoList> todoList = query.from(TodoList.class);
	    query.select(todoList).where(
	            todoList.get("tags").in(Arrays.asList(tags)));
	    return entityManager.createQuery(query).getResultList();
	}
	
	private void initTags(TodoList todoList) {
		List<String> tags = todoList.getTags();
		System.out.println("Tags : " + tags);
	}

}
