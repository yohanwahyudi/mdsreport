package com.vdi.batch.mds.repository.dao;

import java.util.Collection;

public interface StagingUReqYTDDAOService {
	
	public void add(Object obj);
	public <T> void addAll(Collection<T> col);
	public void deleteEntity() ;

}
