package com.rev.DAO;

import java.util.Optional;
import java.util.List;

import com.rev.Models.*;

public interface ManagerDAO {

	Optional<Manager> login(String username, String password);
	boolean logout();
	Optional<Employee> addUser(String firstName, String lastName, String username, String password);
	Optional<List<Reimbursement>> viewPending();
	Optional<List<Reimbursement>> viewPast();
	Optional<List<Reimbursement>> searchEmployee(int employeeID);
	Optional<List<Employee>> viewAllEmployee();
	//Approve/Deny Reimbursement??
}
