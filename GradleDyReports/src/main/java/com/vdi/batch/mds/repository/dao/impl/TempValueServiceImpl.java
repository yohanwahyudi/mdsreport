package com.vdi.batch.mds.repository.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.TempValueRepository;
import com.vdi.batch.mds.repository.dao.TempValueService;
import com.vdi.model.TempValue;

@Transactional
@Repository("tempValueDAO")
public class TempValueServiceImpl implements TempValueService{

	@Autowired
	private TempValueRepository repo;
	
	@Override
	public TempValue getTempValueByName(String name) {
		return repo.getTempValueByName(name);
	}
	
	@Override
	public TempValue getTempValue() {
		return repo.getTempValue();
	}

	@Override
	public void updateTempValue(String value, String name) {
		
		repo.updateTempValue(value, name);
	}
	
	@Override
	public void save(TempValue tempValue) {
		repo.save(tempValue);
	}

}
