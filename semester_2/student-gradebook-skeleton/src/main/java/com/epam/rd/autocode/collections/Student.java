package com.epam.rd.autocode.collections;

/**
 * @author D.Kolesnikov
 */
public class Student {

	private String firstName;

	private String lastName;

	private String group;

	public Student(String firstName, String lastName, String group) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.group = group;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getGroup() {
		return group;
	}

	@Override
	public String toString() {
		return firstName + "_" + lastName + "_" + group;
	}

}
