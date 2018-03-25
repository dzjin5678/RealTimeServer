package com.main.domain;

public class Station{
	private short id;
	private String station;
	public void setId(short id){
		this.id=id;
	}
	public short getId(){
		return id;
	}
	public void setStation(String station){
		this.station=station;
	}
	public String getStation(){
		return station;
	}
}

