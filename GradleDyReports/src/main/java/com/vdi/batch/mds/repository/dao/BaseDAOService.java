package com.vdi.batch.mds.repository.dao;

import java.util.Collection;

public interface BaseDAOService {
	
	public void add(Object obj);
	public <T> void addAll(Collection<T> col);
	public void deleteEntity();
	public void merge(Object obj);
}
