package com.vdi.batch.mds.repository.dao.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vdi.batch.mds.repository.UserRequestYTDRepository;
import com.vdi.batch.mds.repository.dao.UserRequestYTDDAOService;

@Transactional
@Repository
public class UserRequestYTDDAOImpl implements UserRequestYTDDAOService{

	@Autowired
	private UserRequestYTDRepository urRepo;
	
	@Override
	public void insertToTable() {
		
		urRepo.insertToTable();
		
	}

	@Override
	public void updateUserRequestYTDTable() {
		
		urRepo.updateUserRequestYTDTable();
		
	}

	@Override
	public void syncDelete() {
		
		urRepo.syncDelete();
		
	}

	
	
}
