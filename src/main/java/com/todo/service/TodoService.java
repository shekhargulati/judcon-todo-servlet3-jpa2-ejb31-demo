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
import javax.persistence.criteria.Expression;
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
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TodoList> criteria = criteriaBuilder.createQuery(TodoList.class);
		Root<TodoList> root = criteria.from(TodoList.class);
		final Expression<List<String>>  expressionTags = root.get("tags");
		criteria.select(root).where(expressionTags.in(Arrays.asList(tags)));
		List<TodoList> todoLists = entityManager.createQuery(criteria).getResultList();
		for (TodoList todoList : todoLists) {
			initTags(todoList);
		}
		return todoLists;
	}
	
	private void initTags(TodoList todoList) {
		List<String> tags = todoList.getTags();
		System.out.println("Tags : " + tags);
	}

}
