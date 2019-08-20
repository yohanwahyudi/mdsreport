package com.vdi.batch.mds.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.vdi.model.TempValue;

public interface TempValueRepository extends CrudRepository<TempValue, Integer>{
	
	@Query(value="SELECT * FROM temp_value where name = :name", nativeQuery=true)
	public TempValue getTempValueByName(@Param("name") String name);
	
	@Query(value="SELECT * FROM temp_value", nativeQuery=true)
	public TempValue getTempValue();
	
	@Query(value="UPDATE temp_value SET value = :value WHERE name = :name", nativeQuery=true)
	public void updateTempValue(@Param("value") String value, @Param("name") String name);

}
