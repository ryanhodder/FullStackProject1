package com.rev.Services;

import java.util.Optional;

import com.rev.DAO.EmployeeDAO;
import com.rev.DAO.EmployeeOracle;
import com.rev.DAO.ReimbursementDAO;
import com.rev.DAO.ReimbursementOracle;
import com.rev.Models.Reimbursement;

public class ReimbursementService {

	private static ReimbursementService r;
	private static ReimbursementDAO rDao = ReimbursementOracle.getDAO();
	
	private ReimbursementService() {}
	public static ReimbursementService getService() {
		if(r == null) {
			r = new ReimbursementService();
		}
		return r;
	}
	
	public Optional<Reimbursement> createReimbursement(int amount){
		return rDao.createReimbursement(amount);
	}
}
