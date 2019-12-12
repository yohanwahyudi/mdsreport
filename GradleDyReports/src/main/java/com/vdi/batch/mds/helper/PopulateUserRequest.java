package com.vdi.batch.mds.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vdi.batch.mds.repository.dao.StagingUserRequestDAOService;
import com.vdi.batch.mds.service.ItopMDSDataLoaderService;
import com.vdi.model.staging.StagingUserRequest;

@Component
public class PopulateUserRequest {

	@Autowired
	@Qualifier("stagingUserRequestDAO")
	private StagingUserRequestDAOService urdao;

	@Autowired
	@Qualifier("userRequestDataLoaderService")
	private ItopMDSDataLoaderService load;

	private List<StagingUserRequest> stagingList;

	public void addToStaging() {
		stagingList = new ArrayList<StagingUserRequest>();
		stagingList = load.getStagingAllByURL();
		
//		stagingList = castList(StagingUserRequest.class, load.getStagingAllByURL());

		if (stagingList != null && stagingList.size() > 0) {
			urdao.addAll(stagingList);
		}
	}
	
	public <T> List<T> castList(Class<? extends T> clazz, Collection<?> c){
		List<T> r = new ArrayList<T>(c.size());
		for(Object o : c) {
			r.add(clazz.cast(o));
		}
		
		return r;
	}

	public void populate() {

		// delete and reset sequence for staging table
		urdao.deleteEntity();

		// add new incident list to staging
		addToStaging();

	}
	
	public int getDataLength() {
		
		int size=0;
		try {
			size = stagingList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return size;
	}

}
