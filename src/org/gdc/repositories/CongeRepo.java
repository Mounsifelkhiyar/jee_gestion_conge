package org.gdc.repositories;

import java.util.Date;
import java.util.List;

import org.gdc.models.Employee;
import org.gdc.models.Conge;

public interface CongeRepo {
	String gettypeConges(String type);
	/*List<Conge> gettypeConges(String type);
	List<Integer> getAllCongesduree();*/
	List<Conge> getAllConges();
	List<Conge> getConges(Employee employee);
	void addConge(Conge Conge);
	Conge rechcongematricule(String login);
	Conge getConge(String login, Date beginDate);
	void deleteConge(Conge CongeToDelete);
	List<Conge> getCongesByStateAll(String state);
	List<Conge> getCongesByState(Employee employee, String state);
	void updateConge(Conge Conge, Date oldBeginDate);
	Integer countCongesByState(String state);
	Integer countCongesByMonth(int month);
}
