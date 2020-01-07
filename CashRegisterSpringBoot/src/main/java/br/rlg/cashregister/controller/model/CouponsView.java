package br.rlg.cashregister.controller.model;

public class CouponsView {

	private int quantity;
	private int moneyOff;
	private int threshold;
	
	public CouponsView(int quantity, int moneyOff, int threshold) {
		this.quantity = quantity;
		this.moneyOff = moneyOff;
		this.threshold = threshold;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getMoneyOff() {
		return moneyOff;
	}
	
	public void setMoneyOff(int moneyOff) {
		this.moneyOff = moneyOff;
	}
	
	public int getThreshold() {
		return threshold;
	}
	
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
