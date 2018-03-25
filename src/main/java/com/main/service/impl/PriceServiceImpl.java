package com.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.domain.Price;
import com.main.mapper.PriceMapper;
import com.main.service.PriceService;

@Service
public class PriceServiceImpl implements PriceService{
	
	@Autowired
	PriceMapper priceMapper;

	@Override
	public int insertPrice(String contract_id, String station, String price) {
		return priceMapper.insertPrice(contract_id, station, price);
	}

	@Override
	public List<Price> queryPriceList(String contract_id) {
		return priceMapper.queryPriceList(contract_id);
	}

	@Override
	public int updatePrice(String contract_id, String station, String price) {
		return priceMapper.updatePrice(contract_id, station, price);
	}

	@Override
	public List<Price> queryNotNullPriceList(String contract_id) {
		return priceMapper.queryNotNullPriceList(contract_id);
	}

	@Override
	public double queryPrice(String contract, String station) {
		return priceMapper.queryPrice(contract, station);
	}

}
