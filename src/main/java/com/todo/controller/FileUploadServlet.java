package com.todo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/upload")
@MultipartConfig(location = "/var/lib/openshift/65504c2d1c394a1f871e5084fa023d95/app-root/data")
public class FileUploadServlet extends HttpServlet {

	public static final String NAME = "helo";

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileUploadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		getServletContext().getRequestDispatcher("/WEB-INF/pages/form.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		Collection<Part> parts = request.getParts();

		out.write("<h2> Total parts : " + parts.size() + "</h2>");

		for (Part part : parts) {
			printPart(part, out);
			part.write(getFileName(part));
		}
	}

	private void printPart(Part part, PrintWriter pw) {
		StringBuffer sb = new StringBuffer();
		sb.append("<p>");
		sb.append("Name : " + part.getName());
		sb.append("<br>");
		sb.append("Content Type : " + part.getContentType());
		sb.append("<br>");
		sb.append("Size : " + part.getSize());
		sb.append("<br>");
		for (String header : part.getHeaderNames()) {
			sb.append(header + " : " + part.getHeader(header));
			sb.append("<br>");
		}
		sb.append("</p>");
		pw.write(sb.toString());

	}

	private String getFileName(Part part) {
		String partHeader = part.getHeader("content-disposition");

		for (String cd : partHeader.split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
}
