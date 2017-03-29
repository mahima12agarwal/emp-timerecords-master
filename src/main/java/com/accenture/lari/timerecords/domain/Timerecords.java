package com.accenture.lari.timerecords.domain;
/**
 * 
 */

import java.util.Collection;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * @author j.venugopalan
 *
 */
@Document
public class Timerecords {
	
	@Id
	public Integer id;
	@Field
	public Integer employeeId;
	@Field
	public Collection<TimerecordLineItem> timerecord;
	public Timerecords(Integer id, Integer employeeId, Collection<TimerecordLineItem> timerecord) {
		this.id = id;
		this.employeeId = employeeId;
		this.timerecord = timerecord;
	}
	public Timerecords() {
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the employeeId
	 */
	public Integer getEmployeeId() {
		return employeeId;
	}
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * @return the timerecord
	 */
	public Collection<TimerecordLineItem> getTimerecord() {
		return timerecord;
	}
	/**
	 * @param timerecord the timerecord to set
	 */
	public void setTimerecord(Collection<TimerecordLineItem> timerecord) {
		this.timerecord = timerecord;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmployeeTimerecords [id=" + id + ", employeeId=" + employeeId + ", timerecord=" + timerecord + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((timerecord == null) ? 0 : timerecord.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Timerecords other = (Timerecords) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (timerecord == null) {
			if (other.timerecord != null)
				return false;
		} else if (!timerecord.equals(other.timerecord))
			return false;
		return true;
	}
	
	
	
}
