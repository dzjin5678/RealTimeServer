package com.main.domain;

public class Contract{
	
	private int id;
	private String contract;
	private String client;
	private String current_state;
	private String delivery_date;
	private String urgency;
	private String tag;
	
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setContract(String contract){
		this.contract=contract;
	}
	public String getContract(){
		return contract;
	}
	public void setClient(String client){
		this.client=client;
	}
	public String getClient(){
		return client;
	}
	public void setCurrent_state(String current_state){
		this.current_state=current_state;
	}
	public String getCurrent_state(){
		return current_state;
	}
	public void setDelivery_date(String delivery_date){
		this.delivery_date=delivery_date;
	}
	public String getDelivery_date(){
		return delivery_date;
	}
	public void setUrgency(String urgency){
		this.urgency=urgency;
	}
	public String getUrgency(){
		return urgency;
	}
	public void setTag(String tag){
		this.tag=tag;
	}
	public String getTag(){
		return tag;
	}
	
	@Override
	public String toString() {
		return "Contract [id=" + id + ", contract=" + contract + ", client=" + client + ", current_state="
				+ current_state + ", delivery_date=" + delivery_date + ", urgency=" + urgency + ", tag=" + tag + "]";
	}
	
}

