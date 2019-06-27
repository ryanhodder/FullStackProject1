package com.rev.DAO;

import java.util.Optional;
import java.util.List;

import com.rev.Models.*;

public interface ManagerDAO {

	Optional<Manager> login(String username, String password);
	boolean logout();
	Optional<Employee> addUser(String firstName, String lastName, String username, String password);
	//will fail if there is already a username the same
	Optional<List<Reimbursement>> viewPending();
	Optional<List<Reimbursement>> viewPast();
	Optional<List<Reimbursement>> searchEmployee(String firstName, String lastName); //will make it so that the names cant be null
	//I guess in the Oracle I can give default values for the fname and lname if dont want to add one then
	Optional<List<Employee>> viewAllEmployee();
	
}
