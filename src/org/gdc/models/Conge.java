package org.gdc.models;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testProject.DBManager;

public class Conge {
	private String matricule;
	private Date beginDate;
	private Date endDate;
	private int duration;
	private String reason;
	private String type;
	private String state;
	private Date validDate;
	private String wording;
	
	public Conge() {
	}

	public Conge(String matricule, Date beginDate, Date endDate, int duration, String reason, String type,
			String state, Date validDate, String wording) {
		this.matricule = matricule;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.duration = duration;
		this.reason = reason;
		this.type = type;
		this.state = state;
		this.validDate = validDate;
		this.wording = wording;
	}

	public String getmatricule() {
		return matricule;
	}

	public void setmatricule(String matricule) {
		this.matricule = matricule;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public String getWording() {
		return wording;
	}

	public void setWording(String wording) {
		this.wording = wording;
	}

	@Override
	public String toString() {
		return "Conge [matricule=" + matricule + ", beginDate=" + beginDate + ", endDate=" + endDate + ", duration="
				+ duration + ", reason=" + reason + ", type=" + type + ", state=" + state + ", validDate=" + validDate
				+ ", wording=" + wording + "]";
	}
	
	public String getNameEmploye()
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String emp = null;
		
		try {
			conn = DBManager.getInstance().getConnection();
			if (conn != null) {
				stmt = conn.prepareStatement("SELECT * FROM Employe WHERE matricule = ?"); 
				stmt.setString(1, matricule);
				rs = stmt.executeQuery();
					rs.first();
					emp = rs.getString("pnom")+" "+rs.getString("nom");
				
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
	
}