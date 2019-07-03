package com.rev.Services;

import java.util.List;
import java.util.Optional;

import com.rev.DAO.ManagerDAO;
import com.rev.DAO.ManagerOracle;
import com.rev.Models.Employee;
import com.rev.Models.Manager;
import com.rev.Models.Reimbursement;

public class ManagerService {

	private static ManagerService m;
	private static ManagerDAO mDao = ManagerOracle.getDAO();
	
	private ManagerService() {}
	public static ManagerService getService() {
		if(m == null) {
			m = new ManagerService();
		}
		return m;
	}
	
	public Optional<Manager> login(String username, String password){
		return mDao.login(username, password);
	}
	
	public Optional<Employee> addUser(String firstName, String lastName, String username, String password){
		return mDao.addUser(firstName, lastName, username, password);
	}
	
	Optional<List<Reimbursement>> viewPending(){
		return mDao.viewPending();
	}
	
	Optional<List<Reimbursement>> viewPast(){
		return mDao.viewPast();
	}
	
	Optional<List<Reimbursement>> searchEmployee(int employeeID){
		return mDao.searchEmployee(employeeID);
	}
	
	Optional<List<Employee>> viewAllEmployee(){
		return mDao.viewAllEmployee();
	}
}
