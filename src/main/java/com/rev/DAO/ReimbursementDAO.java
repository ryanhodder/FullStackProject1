package com.rev.DAO;

import java.util.Optional;

import com.rev.Models.Reimbursement;

public interface ReimbursementDAO {

	Optional<Reimbursement> createReimbursement(int amount);
	//will grab the employeeId from the employee who submits the reimbursement I think
	//because need to be logged in to submit Reimbursement
}
