package com.vdi.batch.mds.repository.dao;

import com.vdi.model.TempValue;

public interface TempValueService {
	
	public TempValue getTempValueByName(String name);
	public TempValue getTempValue();
	public void save(TempValue tempValue);
	public void updateTempValue(String value, String name);
	
}
