package org.gdc.repositories;
import java.time.LocalDate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.gdc.models.Employee;
import org.gdc.models.Conge;
import org.testProject.DBManager;

public class CongeRepoImpl implements CongeRepo {
	
	public String gettypeConges(String type) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("select type_conges from conge where type_conges= ? ");
				stmt.setString(1, type);
				rs = stmt.executeQuery();
				
				while (rs.next()) {
					type= rs.getString("type_conges");
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return type;
	}
	
	/*
	public List<Conge> gettypeConges(String type) {
		List<Conge> listConges = new ArrayList<Conge>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Conge c,ref_type_conges tc where c.type_conges= tc.type_conges and c.type_conges= ? ");
				stmt.setString(1, type);
				rs = stmt.executeQuery();
				
				while (rs.next()) {
					listConges.add(new Conge(rs.getString("matricule"), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("duree"), rs.getString("motif"), rs.getString("type_conges"), rs.getString("etat"), rs.getDate("date_validation"), rs.getString("commentaire")));
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return listConges;
	}
	
	public List<Integer> getAllCongesduree() {
		List<Integer> alldureeconge = new ArrayList<Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT duree FROM Conge");
				rs = stmt.executeQuery();
				while (rs.next()) {
					 alldureeconge.add(rs.getInt("duree"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return alldureeconge;
	}
	*/
	
	public List<Conge> getAllConges() {
		List<Conge> listConges = new ArrayList<Conge>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Conge");
				rs = stmt.executeQuery();
				while (rs.next()) {
					listConges.add(new Conge(rs.getString("matricule"), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("duree"), rs.getString("motif"), rs.getString("type_conges"), rs.getString("etat"), rs.getDate("date_validation"), rs.getString("commentaire")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return listConges;
	}
	
	public List<Conge> getConges(Employee employee) {
		List<Conge> listConges = new ArrayList<Conge>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Conge where matricule=?");
				stmt.setString(1, employee.getmatricule());
				rs = stmt.executeQuery();
				while (rs.next()) {
					listConges.add(new Conge(employee.getmatricule(), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("duree"), rs.getString("motif"), rs.getString("type_conges"), rs.getString("etat"), rs.getDate("date_validation"), rs.getString("commentaire")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return listConges;
	}

	@Override
	public void addConge(Conge Conge) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("INSERT INTO Conge(matricule, date_debut, date_fin, duree, motif, type_conges, etat) VALUES(?,?,?,?,?,?,?)");
				stmt.setString(1, Conge.getmatricule());
				stmt.setDate(2, new java.sql.Date(Conge.getBeginDate().getTime()));
				stmt.setDate(3, new java.sql.Date(Conge.getEndDate().getTime()));
				stmt.setInt(4, Conge.getDuration());
				stmt.setString(5, Conge.getReason());
				stmt.setString(6, Conge.getType());
				stmt.setString(7, Conge.getState());
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
	
	public Conge rechcongematricule(String login)
	{
		Conge Conge = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Date now =new Date();
		DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");

		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Conge WHERE matricule = ?");
				stmt.setString(1, login);
				
				/*TimeZone timeZone = TimeZone.getDefault();
		        System.out.println("11. Default time zone is:");
		        System.out.println(timeZone);*/
		        
		        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
		        //Setting UTC time zone
		        TimeZone.setDefault(utcTimeZone);
		    
				//System.out.println(stmt.toString());
				rs = stmt.executeQuery();
				while (rs.next()) {
	
					Conge = new Conge(rs.getString("matricule"), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("duree"), rs.getString("motif"), rs.getString("type_conges"), rs.getString("etat"), rs.getDate("date_validation"), rs.getString("commentaire"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return Conge;
	}
	
	
	public Conge getConge(String login, Date beginDate) {
		Conge Conge = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Date now =new Date();
		DateFormat dateFormatYMD = new SimpleDateFormat("yyyy-MM-dd");

		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Conge WHERE matricule = ? and date_debut = ?");
				stmt.setString(1, login);
				
				/*TimeZone timeZone = TimeZone.getDefault();
		        System.out.println("11. Default time zone is:");
		        System.out.println(timeZone);*/
		        
		        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
		        //Setting UTC time zone
		        TimeZone.setDefault(utcTimeZone);
		        

				stmt.setDate(2, new java.sql.Date(beginDate.getTime()));
				//System.out.println(stmt.toString());
				rs = stmt.executeQuery();
				while (rs.next()) {
	
					Conge = new Conge(rs.getString("matricule"), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("duree"), rs.getString("motif"), rs.getString("type_conges"), rs.getString("etat"), rs.getDate("date_validation"), rs.getString("commentaire"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return Conge;
	}

	@Override
	public void deleteConge(Conge CongeToDelete) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("DELETE FROM Conge WHERE matricule = ? and date_debut = ?"); 
				stmt.setString(1, CongeToDelete.getmatricule());
				stmt.setDate(2, new java.sql.Date(CongeToDelete.getBeginDate().getTime()));
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
	public List<Conge> getCongesByStateAll( String state) {
		List<Conge> listConges = new ArrayList<Conge>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Conge WHERE  etat = ?");
				stmt.setString(1, state);
				rs = stmt.executeQuery();
				
				while (rs.next()) {
					listConges.add(new Conge(rs.getString("matricule"), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("duree"), rs.getString("motif"), rs.getString("type_conges"), rs.getString("etat"), rs.getDate("date_validation"), rs.getString("commentaire")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return listConges;
	}
	
	@Override
	public List<Conge> getCongesByState(Employee employee, String state) {
		List<Conge> listConges = new ArrayList<Conge>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Conge WHERE matricule != ? AND etat = ?");
				stmt.setString(1, employee.getmatricule());
				stmt.setString(2, state);
				rs = stmt.executeQuery();
				
				while (rs.next()) {
					listConges.add(new Conge(rs.getString("matricule"), rs.getDate("date_debut"), rs.getDate("date_fin"), rs.getInt("duree"), rs.getString("motif"), rs.getString("type_conges"), rs.getString("etat"), rs.getDate("date_validation"), rs.getString("commentaire")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return listConges;
	}

	@Override
	public void updateConge(Conge Conge, Date oldBeginDate) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("UPDATE Conge SET date_debut = ?, date_fin = ?, duree = ?, motif = ?, type_conges = ?, etat = ?, date_validation = ?, commentaire = ? WHERE matricule = ? AND date_debut = ?");
				stmt.setDate(1, new java.sql.Date(Conge.getBeginDate().getTime()));
				stmt.setDate(2, new java.sql.Date(Conge.getEndDate().getTime()));
				stmt.setInt(3, Conge.getDuration());
				stmt.setString(4, Conge.getReason());
				stmt.setString(5, Conge.getType());
				stmt.setString(6, Conge.getState());
				stmt.setObject(7, Conge.getValidDate() != null ? new java.sql.Date(Conge.getValidDate().getTime()) : null, java.sql.Types.DATE);
				stmt.setString(8, Conge.getWording());
				stmt.setString(9, Conge.getmatricule());
				stmt.setDate(10, new java.sql.Date(oldBeginDate.getTime()));
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
	public Integer countCongesByState(String state) {
		int nbConges = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT count(*) as total FROM Conge WHERE etat = ?");
				stmt.setString(1, state);
				rs = stmt.executeQuery();
				while (rs.next()) {
					nbConges = rs.getInt("total");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return nbConges;
	}

	@Override
	public Integer countCongesByMonth(int month) {
		int nbConges = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT count(*) as total FROM Conge WHERE MONTH(date_debut) = ?");
				stmt.setInt(1, month);
				rs = stmt.executeQuery();
				while (rs.next()) {
					nbConges = rs.getInt("total");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				DBManager.getInstance().cleanup(conn, stmt, rs);
			}
		}
		return nbConges;
	}
}
