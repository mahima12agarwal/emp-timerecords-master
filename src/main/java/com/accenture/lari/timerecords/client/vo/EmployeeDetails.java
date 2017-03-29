/**
 * 
 */
package com.accenture.lari.timerecords.client.vo;


/**
 * @author j.chandra.mali
 *
 */

public class EmployeeDetails{

	    public long id;
		public String firstName;
		public String lastName;
		public long age;
	    public String address;
	    public String email;
	    public long employeeId;

	    public EmployeeDetails() {}

	   

	    public EmployeeDetails(String firstName, String lastName, long age, String address, String email, long employeeId) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.address = address;
			this.email = email;
			this.employeeId = employeeId;
		}



		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}



		/**
		 * @param id the id to set
		 */
		public void setId(long id) {
			this.id = id;
		}



		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}



		/**
		 * @param firstName the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}



		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}



		/**
		 * @param lastName the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}



		/**
		 * @return the age
		 */
		public long getAge() {
			return age;
		}



		/**
		 * @param age the age to set
		 */
		public void setAge(long age) {
			this.age = age;
		}



		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}



		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}



		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}



		/**
		 * @param email the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}



		/**
		 * @return the employeeId
		 */
		public long getEmployeeId() {
			return employeeId;
		}



		/**
		 * @param employeeId the employeeId to set
		 */
		public void setEmployeeId(long employeeId) {
			this.employeeId = employeeId;
		}



		@Override
	    public String toString() {
	        return String.format(
	                "Employee[id=%d, firstName='%s', lastName='%s', age=%d, address='%s', email='%s', employeeId=%d]",
	                id, firstName, lastName, age, address, email, employeeId);
	    }

}