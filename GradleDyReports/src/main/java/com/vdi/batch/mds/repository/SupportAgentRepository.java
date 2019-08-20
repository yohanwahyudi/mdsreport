package com.vdi.batch.mds.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vdi.model.Agent;

@Repository
public interface SupportAgentRepository extends CrudRepository<Agent, Long>{

}
