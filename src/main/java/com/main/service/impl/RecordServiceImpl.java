package com.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.domain.Record;
import com.main.mapper.RecordMapper;
import com.main.service.RecordService;

@Service
public class RecordServiceImpl implements RecordService {
	
	@Autowired
	RecordMapper recordMapper;
	

	@Override
	public List<Record> getRecordList(String worker) {
		return recordMapper.getRecordList(worker);
	}

	@Override
	public List<Record> getOneMonthRecordList(String worker,String year,String month) {
		return recordMapper.getOneMonthRecordList(worker,month,month);
	}

	@Override
	public int updateRecordBegin(String contract_id, String station, String worker, String begin_datetime,String state) {
		return recordMapper.updateRecordBegin(contract_id, station, worker, begin_datetime, state);
	}

	@Override
	public int updateRecordEnd(String worker, String station, String contract_id, String end_datetime, String state,String problem) {
		return recordMapper.updateRecordEnd(worker, station, contract_id, end_datetime, state, problem);
	}

}
