package org.gdc.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * Servlet implementation class CongeCreateController
 */
@WebServlet("/CongeCreateController")
public class CongeCreateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, String> errors = new HashMap<String, String>();

	private CongeRepo CongeRepo = new CongeRepoImpl();
	private EmployeeRepo employeeRepo = new EmployeeRepoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CongeCreateController() {
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
		List<Employee> listEmployees = employeeRepo.getEmployees(); 
		request.setAttribute("listEmployees", listEmployees);
		this.getServletContext().getRequestDispatcher("/DemandeConge.jsp").forward( request, response );		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee emp = employeeRepo.getEmployee((String) request.getParameter("selectemp"));
		request.setAttribute("emp", emp);
		
	
		Date beginDate = null, endDate = null;
		int duration = 0;
		int dureeweek=0;
		try {
			beginDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("bday"));
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("eday"));
			duration=Integer.parseInt(request.getParameter("duree"));
			dureeweek = getnbjouv(beginDate, endDate);
			//duration = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			
		} 
		catch (ParseException e) {}
		String motif = request.getParameter("motif");
		String type = request.getParameter("type");

		//check remaining Conge balance
		try {
			if(emp.getnbConges() - duration < 0) {
				throw new Exception( "solde de l'employe actuel ne vous permet pas de poser de nouveaux congés" );
			} else {
				Conge Conge = new Conge(emp.getmatricule(), beginDate, endDate, duration, motif, type, "En attente", null, null);
				CongeRepo.addConge(Conge);
			}
		} catch ( Exception e ) {
			errors.put("remainingBalance", e.getMessage());
			request.setAttribute("errors", errors);
			this.getServletContext().getRequestDispatcher("/DemandeConge.jsp").forward( request, response );
		} finally {
			this.getServletContext().getRequestDispatcher("/CongePersoController").forward( request, response );
		}
	}

}
