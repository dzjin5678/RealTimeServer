package com.main.service;

import java.util.List;

import com.main.domain.Contract;

public interface ContractService {
	
	/**
	 * 根据合同号获取工单信息
	 * @param contract
	 * @return
	 */
	public Contract getContractByContract(String contract);
	
	/**
	 * 根据ID获取工单信息
	 * @param id
	 * @return
	 */
	public Contract getContractByContractId(String id);
	
	/**
	 * 更新工单当前工位
	 * @param contract
	 * @param current_state
	 * @return
	 */
	public int updateContractCurrentState(String contract , String current_state);
	
	/**
	 * 更新工单
	 * @param contract
	 * @return
	 */
	public int updateContract(Contract contract);
	
	/**
	 * 查询所有工单
	 * @return
	 */
	public List<Contract> queryContractList();
	
	/**
	 * 根据当前状态以及起止时间查询工单
	 * @return
	 */
	public List<Contract> queryContractListByCondition(String current_state, String begin_datetime, String end_datetime);
	
	/**
	 * 添加工单
	 * @param contract
	 * @return
	 */
	public int addContract(Contract contract);
	
	/**
	 * 根据ID删除工单
	 * @param id
	 * @return
	 */
	public int deleteById(String id);
	

}
