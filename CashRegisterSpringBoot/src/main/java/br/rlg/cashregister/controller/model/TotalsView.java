package br.rlg.cashregister.controller.model;

public class TotalsView {

	private double subTotal;
	private double totalDiscount;
	private double total;
	
	public double getSubTotal() {
		return subTotal;
	}
	
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	public double getTotalDiscount() {
		return totalDiscount;
	}
	
	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
}
