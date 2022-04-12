package org.gdc.models;

public class Employee {
	private String matricule;
	private String fname;
	private String name;
	private String address;
	private String city;
	private String zipCode;
	private String departements;
	private String role;
	private String mail;
	private int nbConges;

	public Employee() {
	}

	public Employee(String matricule, String fname, String name, String address, String city, String zipCode, String departements,
			String role, String mail, int nbConges) {
		this.matricule = matricule;
		this.fname = fname;
		this.name = name;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.departements = departements;
		this.role = role;
		this.mail = mail;
		this.nbConges = nbConges;
	}

	public String getmatricule() {
		return matricule;
	}

	public void setmatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getdepartements() {
		return departements;
	}

	public void setdepartements(String departements) {
		this.departements = departements;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public int getnbConges() {
		return nbConges;
	}

	public void setnbConges(int nbConges) {
		this.nbConges = nbConges;
	}

	@Override
	public String toString() {
		return "Employee [matricule=" + matricule + ", fname=" + fname + ", name=" + name + ", address=" + address + ", city="
				+ city + ", zipCode=" + zipCode + ", departements=" + departements + ", role=" + role + ", mail=" + mail + ", nbjourConge="
				+ nbConges + "]";
	}
}