package com.main.domain;

public class Record{
	
	private int id;
	private String contract;
	private int contract_id;
	private String station;
	private String superviser;
	private String worker;
	private double price;
	private String begin_datetime;
	private String end_datetime;
	private String state;
	private String problem;
	private String love;
	private String client;
	private String delivery_date;
	private String urgency;
	
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getLove() {
		return love;
	}
	public void setLove(String love) {
		if(love.equals("love")){
			this.love = "赞";
		}else{
			this.love = love;
		}
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
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
	public void setSuperviser(String superviser){
		this.superviser=superviser;
	}
	public String getSuperviser(){
		return superviser;
	}
	public void setWorker(String worker){
		this.worker=worker;
	}
	public String getWorker(){
		return worker;
	}
	public void setPrice(double price){
		this.price=price;
	}
	public double getPrice(){
		return price;
	}
	public void setBegin_datetime(String begin_datetime){
		//2018-03-08 15:42:18.0
		
		this.begin_datetime=begin_datetime.split(":")[0]+":"+begin_datetime.split(":")[1];
	}
	public String getBegin_datetime(){
		return begin_datetime;
	}
	public void setEnd_datetime(String end_datetime){
		//2018-03-08 15:42:18.0
		this.end_datetime=end_datetime.split(":")[0]+":"+end_datetime.split(":")[1];
	}
	public String getEnd_datetime(){
		return end_datetime;
	}
	public void setState(String state){
		this.state=state;
	}
	public String getState(){
		return state;
	}
	public void setProblem(String problem){
		this.problem=problem;
	}
	public String getProblem(){
		return problem;
	}
	@Override
	public String toString() {
		return "Record [id=" + id + ", contract=" + contract + ", contract_id=" + contract_id + ", station=" + station
				+ ", superviser=" + superviser + ", worker=" + worker + ", price=" + price + ", begin_datetime="
				+ begin_datetime + ", end_datetime=" + end_datetime + ", state=" + state + ", problem=" + problem
				+ ", love=" + love + "]";
	}
}

