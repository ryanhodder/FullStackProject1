package com.rev.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.rev.Models.Employee;
import com.rev.Models.Reimbursement;
import com.rev.Util.ConnectionUtil;

public class EmployeeOracle implements EmployeeDAO{

	private static EmployeeOracle e;
	
	private EmployeeOracle() {}
	
	public static EmployeeDAO getDAO() {
		if(e == null) {
			e = new EmployeeOracle();
		}
		return e;
	}
	
	public Optional<Employee> login(String username, String password) {

		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			String sql = "SELECT * FROM EMPLOYEE WHERE userName = ? AND e_password = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			Employee employee = new Employee();
			boolean rsempty = true;
			
			if(rs.next()) {
				rsempty = false;
				employee = new Employee(rs.getInt("employee_id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("e_password"));
				
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(employee);
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
	
	public Optional<Employee> viewInfo(String username) {
		
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			String sql = "SELECT * FROM EMPLOYEE WHERE userName = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			Employee employee = new Employee();
			boolean rsempty = true;
			
			if(rs.next()) {
				rsempty = false;
				employee = new Employee(rs.getInt("employee_id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("userName"), rs.getString("e_password"));
				
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(employee);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Internal error...");
			return Optional.empty();
		}
	}
			
	public Optional<List<Reimbursement>> viewPending(Employee e) {
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			Reimbursement r = new Reimbursement();
			
			String sql = "SELECT * FROM REIMBURSEMENT WHERE employee_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, e.getEmployeeId());
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
	
	public Optional<List<Reimbursement>> viewPast(Employee e) {
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return Optional.empty();
		}
		
		try {
			List<Reimbursement> rList = new ArrayList<Reimbursement>();
			Reimbursement r = new Reimbursement();
			
			String sql = "SELECT * FROM REIMBURSEMENT WHERE employee_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, e.getEmployeeId());
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
	
	public boolean submitReimbursement(Reimbursement r) {
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			return false;
		}
		
		try {
			String sql = "call addReim(?,?,?,?)";
			CallableStatement cs = c.prepareCall(sql);
			cs.setDouble(1, r.getAmount());
			cs.setInt(2, 1); //using 1 to represent true
			cs.setInt(3, r.getEmployeeId());
			cs.registerOutParameter(4, Types.INTEGER);
			boolean b = cs.execute();
			
			int rID = cs.getInt(4);
			
			if(b) {
				return true;
			}
			
			return false;
			
		}
		catch(SQLException se) {
			System.out.println("Error submitting reimbursement");
		}
		catch(Exception e) {
			System.out.println("Different error submitting reimbursement");
		}
		return false;
	}
}
