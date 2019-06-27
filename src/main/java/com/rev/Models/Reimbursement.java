package com.rev.Models;

import java.io.Serializable;
//import java.time.LocalDate;

public class Reimbursement implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4316408912533226186L;
	private int reimbursementId;
	private double amount;
	// private LocalDate date;
	private boolean pending;
	private int employeeId;

	public Reimbursement() {
		super();
	}

	/**
	 * Is set that it starts out as pending when created This should be set to false
	 * when it is resolved
	 * 
	 * @param reimbursementId
	 * @param amount
	 */
	public Reimbursement(int reimbursementId, double amount, boolean pending, int employeeId) {
		super();
		this.reimbursementId = reimbursementId;
		this.amount = amount;
		this.pending = pending;
		this.employeeId = employeeId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getReimbursementId() {
		return reimbursementId;
	}

	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isPending() {
		return pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbursementId=" + reimbursementId + ", amount=" + amount + ", pending=" + pending
				+ ", employeeId=" + employeeId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + employeeId;
		result = prime * result + (pending ? 1231 : 1237);
		result = prime * result + reimbursementId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (pending != other.pending)
			return false;
		if (reimbursementId != other.reimbursementId)
			return false;
		return true;
	}
}
