package com.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.domain.Contract;
import com.main.mapper.ContractMapper;
import com.main.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService{
	
	@Autowired
	ContractMapper contractMapper;

	@Override
	public Contract getContractByContract(String contract) {
		return contractMapper.getContractByContract(contract);
	}
	
	@Override
	public Contract getContractByContractId(String id) {
		return contractMapper.getContractByContractId(id);
	}

	@Override
	public int updateContractCurrentState(String contract, String current_state) {
		return contractMapper.updateContractCurrentState(contract, current_state);
	}
	
	@Override
	public int updateContract(Contract contract) {
		return contractMapper.updateContract(
				String.valueOf(contract.getId()), 
				contract.getContract(), 
				contract.getClient(), 
				contract.getDelivery_date(), 
				contract.getTag());
	}

	@Override
	public List<Contract> queryContractList() {
		return contractMapper.queryContractList();
	}
	
	@Override
	public List<Contract> queryContractListByCondition(String current_state, String begin_datetime, String end_datetime) {
		return contractMapper.queryContractListByCondition(
				current_state, 
				begin_datetime, 
				end_datetime);
	}

	@Override
	public int addContract(Contract contract) {
		return contractMapper.addContract(
				contract.getContract(), 
				contract.getClient(),
				contract.getDelivery_date());
	}

	@Override
	public int deleteById(String Id) {
		return contractMapper.deleteById(Id);
	}

}
