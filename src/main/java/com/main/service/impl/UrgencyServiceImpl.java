package com.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.main.domain.Urgency;
import com.main.mapper.UrgencyMapper;
import com.main.service.UrgencyService;

public class UrgencyServiceImpl implements UrgencyService {
	
	@Autowired
	UrgencyMapper urgencyMapper;

	@Override
	public List<Urgency> getUrgencyList() {
		return urgencyMapper.getUrgencyList();
	}

	@Override
	public int updateUrgency(String id, String a1, String a2, String a3, String a4) {
		return urgencyMapper.updateUrgency(id, a1, a2, a3, a4);
	}

}
