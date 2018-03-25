package com.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.domain.Record;
import com.main.mapper.DispatchMapper;
import com.main.service.DispatchService;

@Service
public class DispatchServiceImpl implements DispatchService{

	@Autowired
	DispatchMapper dispatchMapper;
	
	@Override
	public List<Record> queryDispatchListByContractId(String contract_id) {
		return dispatchMapper.queryDispatchListByContractId(contract_id);
	}

	@Override
	public int insertDispatch(String contract_id, String station, String superviser, String worker, String price,
			String state) {
		return dispatchMapper.insertDispatch(contract_id, station, superviser, worker, price, state);
	}

	@Override
	public int updateDispatch(String id, String station, String superviser, String worker, String price) {
		return dispatchMapper.updateDispatch(id, station, superviser, worker, price);
	}

	@Override
	public int deleteDispatchs(String id) {
		return dispatchMapper.deleteDispatchs(id);
	}

	@Override
	public List<Record> queryDispatchByContractIdStationWorker(String contract_id, String station, String worker) {
		return dispatchMapper.queryDispatchByContractIdStationWorker(contract_id, station, worker);
	}

	@Override
	public List<Record> queryDispatchListByContractOrderByEndDatetime(String contract) {
		return dispatchMapper.queryDispatchListByContractOrderByEndDatetime(contract);
	}

	@Override
	public int updateDispatchLove(String id) {
		return dispatchMapper.updateDispatchLove(id);
	}

	@Override
	public List<Record> queryCurrentRecordListByStation(String station) {
		return dispatchMapper.queryCurrentRecordListByStation(station);
	}

	@Override
	public List<Record> queryLastRecordListByStation(String station, String begin_datetime, String end_datetime) {
		return dispatchMapper.queryLastRecordListByStation(station, begin_datetime, end_datetime);
	}

	@Override
	public List<Record> queryDispatchListPerStation(String contract, String station) {
		return dispatchMapper.queryDispatchListPerStation(contract, station);
	}
	
	
	
}
