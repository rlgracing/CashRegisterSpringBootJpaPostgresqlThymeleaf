package br.rlg.cashregister.controller.model;

public class DiscountItemView {
	
	private int	id;
	private String description;
	private int discountPercentage;
	private double quantity;
	private String total;

	public DiscountItemView(int id, String description, int discountPercentage, double quantity, String total) {
		
		this.id = id;
		this.description = description;
		this.discountPercentage = discountPercentage;
		this.quantity = quantity;
		this.total = total;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "DiscountItemView [id=" + id + ", description=" + description + ", discountPercentage="
				+ discountPercentage + ", quantity=" + quantity + ", total=" + total + "]";
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

}
