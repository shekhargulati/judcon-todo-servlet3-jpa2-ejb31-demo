package com.todo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class TodoList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private Date createdOn = new Date();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderColumn
	@JoinColumn(name = "todolist_id")
	private List<Todo> todos = new ArrayList<Todo>();

	@ElementCollection
	@CollectionTable(name = "Tags", joinColumns = @JoinColumn(name = "todolist_id"))
	@Column(name = "tag")
	private List<String> tags;

	public TodoList(String name, List<Todo> todos, List<String> tags) {
		super();
		this.name = name;
		this.todos = todos;
		this.tags = tags;
	}

	public TodoList() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public List<Todo> getTodos() {
		return todos;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "TodoList [id=" + id + ", name=" + name + ", tags=" + tags + "]";
	}

}
