package com.rev.DAO;

import java.util.Optional;

import com.rev.Models.Reimbursement;

public class ReimbursementOracle implements ReimbursementDAO{
	
	private static ReimbursementOracle r;
	private ReimbursementOracle() {}
	
	public static ReimbursementDAO getDAO() {
		if(r == null) {
			r = new ReimbursementOracle();
		}
		return r;
	}

	public Optional<Reimbursement> createReimbursement(int amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
