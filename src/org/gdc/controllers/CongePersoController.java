package org.gdc.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * Servlet implementation class CongeManagerController
 */
@WebServlet("/CongePersoController")
public class CongePersoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, String> errors = new HashMap<String, String>();
	
	private CongeRepo CongeRepo = new CongeRepoImpl();
	private EmployeeRepo employeeRepo = new EmployeeRepoImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CongePersoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			Employee emp = employeeRepo.getEmployee((String) request.getParameter("matricule"));
			List<Conge> listConges = CongeRepo.getAllConges();
			request.setAttribute("listConges", listConges);
			
			int nbrconge=listConges.size();
			request.setAttribute("nbrconge", nbrconge);
			
			

			if (request.getParameter("action") != null) {
				switch (request.getParameter("action")) {
				case "delete":
					Date beginDate = null;
					try {
						beginDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("delBeginDate"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					/*System.out.println(" ///"+request.getParameter("matricule"));
					System.out.println(" ///"+beginDate);
					*/
					
					Conge CongeToDelete = CongeRepo.getConge(request.getParameter("matricule"), beginDate);
					//System.out.println(" ///"+CongeToDelete.toString());
					CongeRepo.deleteConge(CongeToDelete);
					
					break;
					
				}
				response.sendRedirect(request.getContextPath() + "/CongePersoController");
			} else {
				this.getServletContext().getRequestDispatcher("/GestionCongesPerso.jsp").forward( request, response );
			}
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
