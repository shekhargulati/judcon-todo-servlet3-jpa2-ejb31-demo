package com.todo.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todo.domain.Todo;
import com.todo.domain.TodoList;
import com.todo.service.TodoService;

/**
 * Servlet implementation class TodoServlet
 */
@WebServlet("/todo")
public class TodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private TodoService todoService;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String todoListId = request.getParameter("id");
		System.out.println("TodoList Id : " + todoListId);
		
		if (todoListId != null) {
			TodoList todoList = todoService.find(Long.valueOf(todoListId));
			response.getWriter().println(todoList.toString());
			return;
		} 
		response.getWriter().println("Please specify either id or tags");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TodoList todoList = new TodoList("JUDCon India TodoList",
				Arrays.asList(new Todo("Give session 1"), new Todo(
						"Give session 2")), Arrays.asList("cloud", "judcon"));
		TodoList createdTodolisList = todoService.create(todoList);

		response.getWriter().println(
				"Created TodoList with Id : " + createdTodolisList.getId());

	}

}
