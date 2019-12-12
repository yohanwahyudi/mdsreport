package com.vdi.batch.mds.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.StagingUReqYTDDAOService;
import com.vdi.batch.mds.repository.dao.UserRequestYTDDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.model.staging.StagingUserRequestYTD;

@Component
public class PopulateUserRequestYTD {
	
	@Autowired
	@Qualifier("stagingUReqYTDDAO")
	private StagingUReqYTDDAOService staging;
	
	@Autowired
	@Qualifier("userRequestDataLoaderYTDService")
	private ItopMDSDataLoaderService loader;

	@Autowired
	private UserRequestYTDDAOService urDao;
	
	private List<StagingUserRequestYTD> stagingList;
	
	private void addToStaging(){
		
		stagingList = new ArrayList<StagingUserRequestYTD>();
//		stagingList = loader.getStagingAllByURL();
		
		stagingList = castList(StagingUserRequestYTD.class, loader.getStagingAllByURL());

		if (stagingList != null && stagingList.size() > 0) {
			staging.addAll(stagingList);
		}
		
	}
	
	private <T> List<T> castList(Class<? extends T> clazz, Collection<?> c){
		List<T> r = new ArrayList<T>(c.size());
		for(Object o : c) {
			r.add(clazz.cast(o));
		}
		
		return r;
	}
	
	private void deleteStaging() {
		staging.deleteEntity();
	}
	
	private void syncMaster() {
		urDao.updateUserRequestYTDTable();
		urDao.insertToTable();
		urDao.syncDelete();
	}
	
	public void populate() {
		deleteStaging();
		addToStaging();
		syncMaster();
		deleteStaging();
	}
	
}
