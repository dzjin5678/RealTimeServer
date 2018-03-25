package com.main.domain;

public class Price{
	
	private int contract_id;
	private String station;
	private double price;
	
	public void setContract_id(int contract_id){
		this.contract_id=contract_id;
	}
	public int getContract_id(){
		return contract_id;
	}
	public void setStation(String station){
		this.station=station;
	}
	public String getStation(){
		return station;
	}
	public void setPrice(double price){
		this.price=price;
	}
	public double getPrice(){
		return price;
	}
	@Override
	public String toString() {
		return "Price [contract_id=" + contract_id + ", station=" + station + ", price=" + price + "]";
	}
	
}

