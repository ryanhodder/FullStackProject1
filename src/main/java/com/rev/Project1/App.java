package com.rev.Project1;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.rev.Models.Employee;
import com.rev.Models.Reimbursement;
import com.rev.Services.EmployeeService;

public class App 
{
	
	public static void main(String[] args) {
		//App.run();
	}
	
	
    public static void run() {
    	String testUser = "ryho";
		String testPass = "pass";
		
		try {
			Employee e = EmployeeService.getService().login(testUser, testPass).get();
			Reimbursement testR = new Reimbursement(5, 300.00, true, e.getEmployeeId());
			
			System.out.println("\n================================");
			System.out.println("Employee tests");
			System.out.println(e.getFirstName());
			System.out.println(e.getLastName());
			System.out.println(e.getEmployeeId());
			System.out.println(e.getUserName());
			
			Employee b = EmployeeService.getService().viewInfo("beho").get();
			System.out.println(b.getFirstName());
			
			List<Reimbursement> pendingList = EmployeeService.getService().viewPending(e).get();
			for(Reimbursement r: pendingList) {
				System.out.println(r);
			}
			
			List<Reimbursement> pastList = EmployeeService.getService().viewPast(e).get();
			for(Reimbursement r: pastList) {
				System.out.println(r);
			}
			
			boolean testSubmit = EmployeeService.getService().submitReimbursement(testR);
			System.out.println("Submit reimbursement: " + testSubmit);
			
		}
		catch(NoSuchElementException e) {
			System.out.println("No employee coming through");
		}
		catch(Exception e) {
			System.out.println("Error in main");
			System.out.println(e.getStackTrace());
		}
    }
}
