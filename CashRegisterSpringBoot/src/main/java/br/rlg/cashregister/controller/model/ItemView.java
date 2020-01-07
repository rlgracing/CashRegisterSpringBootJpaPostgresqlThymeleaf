package br.rlg.cashregister.controller.model;

import br.rlg.cashregister.util.UnitType;

public class ItemView {
	
	private int	id;
	private String description;
	private String price;
	private UnitType unitType;
	private double quantity;
	private String total;

	public ItemView(int id, String description, String price, UnitType unitType, double quantity, String total) {
		
		this.id = id;
		this.description = description;
		this.price = price;
		this.unitType = unitType;
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
	
	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return price;
	}
	
	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
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
		return "ItemView [id=" + id + ", description=" + description + ", price=" + price + ", unitType=" + unitType
				+ ", quantity=" + quantity + ", total=" + total + "]";
	}

}
