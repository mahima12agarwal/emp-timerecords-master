package com.accenture.leanarchri.timerecords.client.vo;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.data.couchbase.core.mapping.Document;


import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
* @author mahima.agarwal
*
*/

@Document
public class EmployeeAssignments {
	@Id
	public Integer id;
	@Field
	public Integer employeeId;
	@Field
	public Collection<Assignments> assignments;
	
	public EmployeeAssignments() {
	}
	
	public EmployeeAssignments(Integer id, Integer employeeId, Collection<Assignments> assignments) {
		this.id = id;
		this.employeeId = employeeId;
		this.assignments = assignments;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Collection<Assignments> getAssignments() {
		return assignments;
	}
	public void setAssignments(Collection<Assignments> assignments) {
		this.assignments = assignments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		return "EmployeeAttendance [ employeeId=" + employeeId + ", assignments="
				+ (assignments != null ? toString(assignments, maxLen) : null) + "]";
	}
	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}
}
