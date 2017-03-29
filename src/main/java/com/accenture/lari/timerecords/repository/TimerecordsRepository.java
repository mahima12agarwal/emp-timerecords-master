/**
 * 
 */
package com.accenture.lari.timerecords.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;

import com.accenture.lari.timerecords.domain.Timerecords;


/**
 * @author j.venugopalan
 *
 */
public interface TimerecordsRepository extends CrudRepository<Timerecords, String>{
	
	@View(designDocument = "byEmployeeId_design", viewName = "byEmployeeId")
	 List<Timerecords> findByEmployeeId(Integer employeeId);
}
