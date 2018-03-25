package com.main.service;

import java.util.List;

import com.main.domain.Urgency;

public interface UrgencyService {
	
	/**
	 * 获取急迫度表
	 * @return
	 */
	public List<Urgency> getUrgencyList();
	
	/**
	 * 更新急迫度
	 * @param id
	 * @param a1
	 * @param a2
	 * @param a3
	 * @param a4
	 * @return
	 */
	public int updateUrgency(String id,String a1,String a2,String a3,String a4);

}
