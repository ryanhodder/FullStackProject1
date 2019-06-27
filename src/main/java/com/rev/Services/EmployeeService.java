package com.rev.Services;

import java.util.List;
import java.util.Optional;

import com.rev.DAO.EmployeeDAO;
import com.rev.DAO.EmployeeOracle;
import com.rev.Models.Employee;
import com.rev.Models.Reimbursement;

public class EmployeeService {

	private static EmployeeService e;
	private static EmployeeDAO eDao = EmployeeOracle.getDAO();
	
	private EmployeeService() {}
	public static EmployeeService getService() {
		if(e == null) {
			e = new EmployeeService();
		}
		return e;
	}
	
	public Optional<Employee> login(String username, String password){
		return eDao.login(username, password);
	}
	
	public Optional<Employee> viewInfo(String username){
		return eDao.viewInfo(username);
	}
	
	public Optional<List<Reimbursement>> viewPending(Employee e){
		return eDao.viewPending(e);
	}
	
	public Optional<List<Reimbursement>> viewPast(Employee e){
		return eDao.viewPast(e);
	}
}
