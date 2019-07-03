package com.rev.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rev.Models.Employee;
import com.rev.Models.Manager;
import com.rev.Models.Reimbursement;
import com.rev.Util.ConnectionUtil;

public class ManagerOracle implements ManagerDAO{

	private static ManagerOracle m;
	
	private ManagerOracle() {}
	
	public static ManagerDAO getDAO() {
		if(m == null) {
			m = new ManagerOracle();
		}
		return m;
	}
	
	public Optional<Manager> login(String username, String password) {
		Connection c = ConnectionUtil.getConnection();
		
		//no connection
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			String sql = "SELECT * FROM MANAGER WHERE userName = ? AND m_password = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			Manager manager = new Manager();
			boolean rsempty = true;
			
			if(rs.next()) {
				rsempty = false;
				manager = new Manager(rs.getInt("manager_id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("m_password"));
				
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(manager);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Internal error...");
			return Optional.empty();
		}
	}

	public boolean logout() {
		// TODO Auto-generated method stub
		return false;
	}

	public Optional<Employee> addUser(String firstName, String lastName, String username, String password) {
		Connection c = ConnectionUtil.getConnection();
			
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			Employee e = new Employee();
			
			String sql = "call addEmployee(?,?,?,?,?)";
			CallableStatement cs = c.prepareCall(sql);
			cs.setString(1, firstName);
			cs.setString(2, lastName);
			cs.setString(3, username);
			cs.setString(4, password);
			cs.registerOutParameter(5, Types.INTEGER);
			boolean b = cs.execute();
			
			
			if(b) {
				int eID = cs.getInt(5);
				e = new Employee(eID, firstName, lastName, username, password);
				return Optional.of(e);
			}
			else {
				return Optional.empty();
			}
		}
		catch(Exception ex) {
			System.out.println("Error creating new employee");
			return Optional.empty();
		}
	}

	public Optional<List<Reimbursement>> viewPending() {
		Connection c = ConnectionUtil.getConnection();

		if(c == null) {
			return Optional.empty();
		}
		
		try {
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			Reimbursement r = new Reimbursement();
			
			String sql = "SELECT * FROM REIMBURSEMENT";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			boolean rsempty = true;
			
			while(rs.next()) {
				rsempty = false;
				
				//using int of 1 to represent that the reimbursement is still pending
				//can go ahead and set it to equal true since the Model takes a boolean
				if(rs.getInt("pending") == 1) {
					r = new Reimbursement(rs.getInt("reim_id"), rs.getDouble("amount"), true, rs.getInt("employee_id"));
					rList.add(r);
				}
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(rList);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			System.out.println("Internal error...");
			return Optional.empty();
		}
	}

	public Optional<List<Reimbursement>> viewPast() {
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			Reimbursement r = new Reimbursement();
			
			String sql = "SELECT * FROM REIMBURSEMENT";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			boolean rsempty = true;
			
			while(rs.next()) {
				rsempty = false;
				
				//using int of 1 to represent that the reimbursement is still pending
				//can go ahead and set it to equal true since the Model takes a boolean
				if(rs.getInt("pending") == 0) {
					r = new Reimbursement(rs.getInt("reim_id"), rs.getDouble("amount"), false, rs.getInt("employee_id"));
					rList.add(r);
				}
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(rList);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			System.out.println("Internal error...");
			return Optional.empty();
		}
	}

	public Optional<List<Reimbursement>> searchEmployee(int employeeID) {
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			Reimbursement r = new Reimbursement();
			
			String sql = "SELECT * FROM REIMBURSEMENT WHERE employee_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, employeeID);
			ResultSet rs = ps.executeQuery();
			
			boolean rsempty = true;
			
			while(rs.next()) {
				rsempty = false;
				
				if(rs.getInt("pending") == 1) {
					r = new Reimbursement(rs.getInt("reim_id"), rs.getDouble("amount"), true, rs.getInt("employee_id"));
					rList.add(r);
				}
				else {
					r = new Reimbursement(rs.getInt("reim_id"), rs.getDouble("amount"), false, rs.getInt("employee_id"));
					rList.add(r);
				}
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(rList);

		}
		catch(Exception ex) {
			System.out.println("Error finding reimbursements for employee");
			return Optional.empty();
		}
	}

	public Optional<List<Employee>> viewAllEmployee() {
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			List<Employee> eList = new ArrayList<Employee>();
			Employee e = new Employee();
			
			String sql = "SELECT * FROM EMPLOYEE";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			boolean rsempty = true;
			
			while(rs.next()) {
				rsempty = false;
				
				e = new Employee(rs.getInt("employee_id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("e_password"));
				eList.add(e);
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(eList);
			
		}
		catch(Exception ex) {
			System.out.println("Error retrieving list of employees");
			return Optional.empty();
		}
		
	}

}
