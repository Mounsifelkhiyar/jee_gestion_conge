package org.gdc.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gdc.models.Employee;
import org.gdc.models.Conge;
import org.gdc.repositories.EmployeeRepo;
import org.gdc.repositories.EmployeeRepoImpl;
import org.gdc.repositories.CongeRepo;
import org.gdc.repositories.CongeRepoImpl;

/**
 * Servlet implementation class CongeEditController
 */
@WebServlet("/CongeEditController")
public class CongeEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, String> errors = new HashMap<String, String>();

	private CongeRepo CongeRepo = new CongeRepoImpl();
	private EmployeeRepo employeeRepo = new EmployeeRepoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CongeEditController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getnbjouv(Date d1, Date d2) 
	{
		 //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        cal1.setTime(d1);
	        cal2.setTime(d2);
	 
	        int numberOfDays = 0;
	        while (cal1.before(cal2)) {
	            if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))&&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) 
	            {
	                numberOfDays++;
	                cal1.add(Calendar.DATE,1);
	            }else 
	            {
	                cal1.add(Calendar.DATE,1);
	            }
	        }
	        System.out.println(numberOfDays);
			return numberOfDays;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			Employee emp = employeeRepo.getEmployee((String) request.getParameter("matricule"));
			request.setAttribute("emp", emp);

			Date beginDate = null;
			try {
				beginDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("beginDate"));
			} catch (ParseException e) {}
			Conge Conge = CongeRepo.getConge(request.getParameter("matricule"), beginDate);
			request.setAttribute("conge", Conge);
			session.setAttribute("conge", Conge);
				
			this.getServletContext().getRequestDispatcher("/ModificationConge.jsp").forward( request, response );
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee emp = employeeRepo.getEmployee((String) request.getParameter("selectemp"));
		request.setAttribute("emp", emp);
		
		
		Date beginDate = null, endDate = null;
		Conge oldConge = (Conge) session.getAttribute("conge");
		int duration = 0;
		try {
			beginDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("bday"));
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("eday"));
			//long diff = endDate.getTime() - beginDate.getTime();
			//duration = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			duration = getnbjouv(beginDate, endDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String reason = request.getParameter("motif");
		String type = request.getParameter("type");

		//check remaining Conge balance
		try {
			if(emp.getnbConges() - duration < 0) {
				throw new Exception( "Solde actuel ne vous permet pas de poser autant de nouveaux congés" );
			} else {
				Conge Conge = CongeRepo.getConge(emp.getmatricule(), oldConge.getBeginDate());
				Conge.setBeginDate(beginDate);
				Conge.setEndDate(endDate);
				Conge.setDuration(duration);
				Conge.setReason(reason);
				Conge.setType(type);
				CongeRepo.updateConge(Conge, oldConge.getBeginDate());
				session.setAttribute("conge", Conge);
		
			}
		} catch ( Exception e ) {
			errors.put("remainingBalance", e.getMessage());
			request.setAttribute("errors", errors);
			this.getServletContext().getRequestDispatcher("/ModificationConge.jsp").forward( request, response );
		} finally {
			this.getServletContext().getRequestDispatcher("/CongePersoController").forward( request, response );
		}
	}

}
