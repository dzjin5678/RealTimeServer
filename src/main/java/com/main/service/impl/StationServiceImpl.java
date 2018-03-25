package com.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.domain.Station;
import com.main.mapper.StationMapper;
import com.main.service.StationService;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	StationMapper stationMapper;
	
	@Override
	public int insertStation(String id,String station) {
		return stationMapper.insertStation(id,station);
	}

	@Override
	public List<Station> getStationByStation(String station) {
		return stationMapper.getStationByStation(station);
	}

	@Override
	public List<Station> queryStationList() {
		return stationMapper.queryStationList();
	}

	@Override
	public int getStationNumber() {
		return stationMapper.getStationNumber();
	}

	@Override
	public int deleteStation(String id) {
		return stationMapper.deleteStation(id);
	}

	@Override
	public int updateStation(String id, String station) {
		return stationMapper.updateStation(id, station);
	}

}
