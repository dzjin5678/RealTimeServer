package com.main.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.main.domain.Price;

public interface PriceService {
	
	/**
	 * 插入工价
	 * @param contract_id
	 * @param station
	 * @param price
	 * @return
	 */
	public int insertPrice(String contract_id,String station,String price);
	
	/**
	 * 查询工价
	 * @param contract_id
	 * @return
	 */
	public List<Price> queryPriceList(String contract_id);
	
	/**
	 * 查询非空工价
	 * @param contract_id
	 * @return
	 */
	public List<Price> queryNotNullPriceList(String contract_id);
	
	/**
	 * 修改工价
	 * @param contract_id
	 * @param station
	 * @param price
	 * @return
	 */
	public int updatePrice(String contract_id,String station,String price);
	
	public double queryPrice(String contract , String station);

}
