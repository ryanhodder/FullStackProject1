package com.rev;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rev.Models.Employee;
import com.rev.Models.Manager;
import com.rev.Models.Reimbursement;
import com.rev.Services.EmployeeService;
import com.rev.Services.ManagerService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Controller {

	public static void login(HttpServletRequest request, HttpServletResponse response) throws IOException{

		try {
			PrintWriter out = response.getWriter();
			
			String user = request.getParameter("username");
			String pass = request.getParameter("password");
			System.out.println(user);
			System.out.println(pass);
			
			//currently works that it will check if there is a manager existing with the given creds
			//if not will check for employee
			//means if manager/employee share a username that employee will not be accessible
			Optional<Manager> optM = ManagerService.getService().login(user, pass);
			if(optM.isPresent()) {
				Manager m = optM.get();
				
				JsonObject j = new JsonObject();
				j.addProperty("id", m.getManagerId());
				j.addProperty("firstname", m.getFirstName());
				j.addProperty("lastname", m.getLastName());
				j.addProperty("username", m.getUserName());
				j.addProperty("password", m.getPassword());
				j.addProperty("isManager", true);
				
				out.write(j.toString());
			}
			else {
				Optional<Employee> optE = EmployeeService.getService().login(user, pass);
				if(optE.isPresent()) {
					Employee e = optE.get();
					
					JsonObject j = new JsonObject();
					j.addProperty("id", e.getEmployeeId());
					j.addProperty("firstname", e.getFirstName());
					j.addProperty("lastname", e.getLastName());
					j.addProperty("username", e.getUserName());
					j.addProperty("password", e.getPassword());
					j.addProperty("isManager", false);
					
					out.write(j.toString());
				}
				else {
					out.write("null");
				}			
				out.flush();
			}
		}
		catch(Exception ex) {
			System.out.println("Unable to find user with those login credentials");
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static void getEmployeeDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String user = request.getParameter("username");
		
		Employee e = EmployeeService.getService().viewInfo(user).get();
		
		JsonObject j = new JsonObject();
		j.addProperty("id", e.getEmployeeId());
		j.addProperty("firstname", e.getFirstName());
		j.addProperty("lastname", e.getLastName());
		j.addProperty("username", e.getUserName());
		j.addProperty("password", e.getPassword());
		
		PrintWriter out = response.getWriter();
		out.write(j.toString());
		out.flush();
	}
	
	public static void submitReimbursement(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sAmount = request.getParameter("amount");
		String userid = request.getParameter("userID");
		try {
			double a = Double.parseDouble(sAmount);
			int u = Integer.parseInt(userid);
			Reimbursement r = new Reimbursement(1, a, true, u);
			boolean b = EmployeeService.getService().submitReimbursement(r);
		}
		catch(NumberFormatException nfe) {
			System.out.println("Error processing amount");
		}
		catch(Exception e) {
			System.out.println("Error processing reimbursement");
		}
	}
	
	public static void getAllReimbursements(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String user = request.getParameter("username");
		
		try {
			Employee e = EmployeeService.getService().viewInfo(user).get();
			JsonObject j = new JsonObject();
			JsonArray jArray = new JsonArray();
			
			Optional<List<Reimbursement>> rOptPend = EmployeeService.getService().viewPending(e);
			if(rOptPend.isPresent()) {
				List<Reimbursement> rList = rOptPend.get();
				
				for(Reimbursement r: rList) {
					j = new JsonObject();
					j.addProperty("reimbursementID", r.getReimbursementId());
					j.addProperty("amount", r.getAmount());
					j.addProperty("pending", true);
					j.addProperty("employeeID", r.getEmployeeId());
					
					jArray.add(j);
				}
			}
			
			Optional<List<Reimbursement>> rOptPast = EmployeeService.getService().viewPast(e);
			if(rOptPast.isPresent()) {
				List<Reimbursement> rList = rOptPast.get();
				
				for(Reimbursement r: rList) {
					j = new JsonObject();
					j.addProperty("reimbursementID", r.getReimbursementId());
					j.addProperty("amount", r.getAmount());
					j.addProperty("pending", false);
					j.addProperty("employeeID", r.getEmployeeId());
					
					jArray.add(j);
				}
			}
			
			PrintWriter out = response.getWriter();
			out.write(jArray.toString());
			out.flush();
		}
		catch(Exception ex) {
			System.out.println("Error getting users reimbursements");
		}
	}
	
	//viewAllReimbursements - Manager
	//viewAllPast - Manager
	//viewAllPending - Manager
	
	
	public static void registerEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		
		Optional<Employee> optE = ManagerService.getService().addUser(fname, lname, user, pass);
		if(optE.isPresent()) {
			Employee e = optE.get();
			
			JsonObject j = new JsonObject();
			j.addProperty("id", e.getEmployeeId());
			j.addProperty("firstname", e.getFirstName());
			j.addProperty("lastname", e.getLastName());
			j.addProperty("username", e.getUserName());
			j.addProperty("password", e.getPassword());
			
			out.write(j.toString());
		}
		else {
			out.write("null");
		}
		out.flush();
	}
	
	public static void viewAllEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		JsonArray jArray = new JsonArray();
		JsonObject j = new JsonObject();
		
		try {
			Optional<List<Employee>> eOptList = ManagerService.getService().viewAllEmployee();
			if(eOptList.isPresent()) {
				List<Employee> eList = eOptList.get();
				
				for(Employee e: eList) {
					j = new JsonObject();
					j.addProperty("id", e.getEmployeeId());
					j.addProperty("firstname", e.getFirstName());
					j.addProperty("lastname", e.getLastName());
					j.addProperty("username", e.getUserName());
					j.addProperty("password", e.getPassword());
				}
				
				out.write(jArray.toString());
			}
			else {
				out.write("null");
			}
			
			out.flush();
		}
		catch(Exception e) {
			System.out.println("Error fetching employees");
		}
	}
}
