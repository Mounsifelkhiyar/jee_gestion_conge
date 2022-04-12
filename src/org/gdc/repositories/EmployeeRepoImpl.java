package org.gdc.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.gdc.models.Employee;
import org.gdc.models.Conge;
import org.testProject.DBManager;


public class EmployeeRepoImpl implements EmployeeRepo {

	public void updateannee(int duree) {
		Connection conn = null;
		//PreparedStatement stmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null ) {
				
				stmt = conn.createStatement();
				//int solde_conge = rs.getInt("solde_conges");
				String sql="UPDATE Employe SET solde_conges = solde_conges +'"+duree+"'";
				stmt.executeUpdate(sql);
		       
				/*
				stmt = conn.prepareStatement("UPDATE Employe SET solde_conges = ? +'"+duree+"'");
				//stmt.setInt(1, allemploye.getnbConges());rs.getInt("solde_conges")
				stmt.setInt(1, 22);
				stmt.executeUpdate();*/
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
	}

	@Override
	public Employee getEmployee(String username) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Employee emp = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Employe WHERE matricule = ?"); 
				stmt.setString(1, username);
				rs = stmt.executeQuery();
				while (rs.next()) {
					emp = new Employee(rs.getString("matricule"), rs.getString("pnom"), rs.getString("nom"), rs.getString("adresse"), rs.getString("ville"), rs.getString("code_postal"), rs.getString("nom_Dep"), rs.getString("nom_fonction"), rs.getString("mail"), rs.getInt("solde_conges"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return emp;
	}

	@Override
	public void actualizeRemainingBalance(Employee emp, int newRemainingBalance) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("UPDATE Employe SET solde_conges = ? WHERE matricule = ?");
				stmt.setInt(1, newRemainingBalance);
				stmt.setString(2, emp.getmatricule());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}		
	}

	@Override
	public List<Employee> getEmployees() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Employee> listEmployees = new ArrayList<Employee>();
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM Employe");
				while (rs.next()) {
					listEmployees.add(new Employee(rs.getString("matricule"), rs.getString("pnom"), rs.getString("nom"), rs.getString("adresse"), rs.getString("ville"), rs.getString("code_postal"), rs.getString("nom_Dep"), rs.getString("nom_fonction"), rs.getString("mail"), rs.getInt("solde_conges")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return listEmployees;
	}

	@Override
	public void addEmployee(Employee newEmp) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("INSERT INTO Employe(matricule, pnom, nom, adresse, ville, code_postal, nom_Dep,nom_fonction, mail, solde_conges) VALUES(?,?,?,?,?,?,?,?,?,?)");
				stmt.setString(1, newEmp.getmatricule());
				stmt.setString(2, newEmp.getFname());
				stmt.setString(3, newEmp.getName());
				stmt.setString(4, newEmp.getAddress());
				stmt.setString(5, newEmp.getCity());
				stmt.setString(6, newEmp.getZipCode());
				stmt.setString(7, newEmp.getdepartements());
				stmt.setString(8, newEmp.getRole());
				stmt.setString(9, newEmp.getMail());
				stmt.setInt(10, newEmp.getnbConges());
				stmt.executeUpdate();
				
				stmt2 = conn.prepareStatement("INSERT INTO Authentification(matricule, password) VALUES(?,?)");
				stmt2.setString(1, newEmp.getmatricule());
				stmt2.setString(2, newEmp.getmatricule());
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}		
	}

	@Override
	public void updateEmployee(Employee employee, String login) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("UPDATE Employe SET matricule = ?, pnom = ?, nom = ?, adresse = ?, ville = ?, code_postal = ?, nom_Dep = ?, nom_fonction = ?, mail = ?, solde_conges = ? WHERE matricule = ?");
				stmt.setString(1, employee.getmatricule());
				stmt.setString(2, employee.getFname());
				stmt.setString(3, employee.getName());
				stmt.setString(4, employee.getAddress());
				stmt.setString(5, employee.getCity());
				stmt.setString(6, employee.getZipCode());
				stmt.setString(7, employee.getdepartements());
				stmt.setString(8, employee.getRole());
				stmt.setString(9, employee.getMail());
				stmt.setInt(10, employee.getnbConges());
				stmt.setString(11, login);
				stmt.executeUpdate();
				
				stmt2 = conn.prepareStatement("UPDATE Authentification SET matricule = ?, password = ? VALUES(?,?) WHERE matricule = ?");
				stmt2.setString(1, employee.getmatricule());
				stmt2.setString(2, employee.getmatricule());
				stmt2.setString(3, login);
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
	}

	@Override
	public Integer countEmployeesByTeam(String team) {
		int nbEmployees = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT count(*) as total FROM Employe WHERE nom_Dep = ?");
				stmt.setString(1, team);
				rs = stmt.executeQuery();
				while (rs.next()) {
					nbEmployees = rs.getInt("total");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return nbEmployees;
	}
	
	@Override
	public void deleteEmployee(Employee empToDelete) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt =  conn.prepareStatement("DELETE FROM Authentification WHERE matricule = ?");	 
				stmt.setString(1, empToDelete.getmatricule());
				stmt.executeUpdate();
				stmt2 = conn.prepareStatement("DELETE FROM Employe WHERE matricule = ?");
				stmt2.setString(1, empToDelete.getmatricule());
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
	}
}
