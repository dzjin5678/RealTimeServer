package com.main.domain;

   /**
    * user 实体类
    *  dzjin
    */ 


public class User{
	private int id;
	private String user;
	private String password;
	private String phone;
	private String mac;
	private String receive;
	private String open;
	private String price;
	private String dispatch;
	private String worker;
	private String admin;
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setUser(String user){
		this.user=user;
	}
	public String getUser(){
		return user;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	public String getPhone(){
		return phone;
	}
	public void setMac(String mac){
		this.mac=mac;
	}
	public String getMac(){
		return mac;
	}
	public void setReceive(String receive){
		this.receive=receive;
	}
	public String getReceive(){
		return receive;
	}
	public void setOpen(String open){
		this.open=open;
	}
	public String getOpen(){
		return open;
	}
	public void setPrice(String price){
		this.price=price;
	}
	public String getPrice(){
		return price;
	}
	public void setDispatch(String dispatch){
		this.dispatch=dispatch;
	}
	public String getDispatch(){
		return dispatch;
	}
	public void setWorker(String worker){
		this.worker=worker;
	}
	public String getWorker(){
		return worker;
	}
	public void setAdmin(String admin){
		this.admin=admin;
	}
	public String getAdmin(){
		return admin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", user=" + user + ", password=" + password + ", phone=" + phone + ", mac=" + mac
				+ ", receive=" + receive + ", open=" + open + ", price=" + price + ", dispatch=" + dispatch
				+ ", worker=" + worker + ", admin=" + admin + "]";
	}
}

