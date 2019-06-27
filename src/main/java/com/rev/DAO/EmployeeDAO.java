package com.rev.DAO;

import java.util.List;
import java.util.Optional;

import com.rev.Models.Employee;
import com.rev.Models.Reimbursement;

public interface EmployeeDAO {
	
	Optional<Employee> login(String username, String password);
	
	//the user will be logged in at this point and we should be able to get the details from the session right
	boolean logout();
	Optional<Employee> viewInfo(String username);
	Optional<List<Reimbursement>> viewPending(Employee e);
	Optional<List<Reimbursement>> viewPast(Employee e);
	boolean submitReimbursement(Reimbursement r);
	//not sure if best to pass a reimbursement here, or pass the ingredients of a Reimbursement
	//I guess probably best to pass a reimbursement
	//and then create that object with the elements that come from the page
}
