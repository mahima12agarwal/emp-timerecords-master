/**
 * 
 */
package com.accenture.leanarchri.timerecords.repository;

import java.util.List;

import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;

import com.accenture.leanarchri.timerecords.domain.EmployeeTimerecords;


/**
 * @author j.venugopalan
 *
 */
public interface TimerecordsRepository extends CrudRepository<EmployeeTimerecords, String>{
	
	@View(designDocument = "byEmployeeId_design", viewName = "byEmployeeId")
	 List<EmployeeTimerecords> findByEmployeeId(Integer employeeId);
}
