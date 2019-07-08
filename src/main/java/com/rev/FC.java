package com.rev;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FC
 */
public class FC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setAccessControlHeaders(response);
		
		
		StringBuffer requestURLSB = request.getRequestURL();
		System.out.println("RequestURL: " + requestURLSB);
		
		String requestURI = request.getRequestURI();
		System.out.println("RequestURI: " + requestURI);
		
		String contextPath = request.getContextPath();
		System.out.println("ContextPath: " + contextPath);
		
		String path = requestURI.substring(contextPath.length());
		System.out.println("Path: " + path);
		
		switch(path) {
		case "/login.do":
			System.out.println("Attempting to login..."); //searchServlet?
			Controller.login(request, response);
			break;
		case "/getEmployeeInfo.do":
			System.out.println("Getting user details...");
			Controller.getEmployeeDetails(request, response);
			break;
		case "/submitReimbursement.do":
			System.out.println("Submitting reimbursement...");
			Controller.submitReimbursement(request, response);
			break;
		case "/getReimbursements.do":
			System.out.println("Getting reimbursements...");
			Controller.getAllReimbursements(request, response);
			break;
		case "/registerEmployee.do":
			System.out.println("Registering new employee...");
			Controller.registerEmployee(request, response);
			break;
		case "/viewAllEmployee.do":
			System.out.println("Finding all employees...");
			Controller.viewAllEmployees(request, response);
			break;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void setAccessControlHeaders(HttpServletResponse resp) {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET");
	}

}
