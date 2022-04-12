package org.gdc.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Servlet implementation class CongeEmpController
 */
@WebServlet("/CongeEmpController")
public class CongeEmpController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, String> errors = new HashMap<String, String>();
	
	private CongeRepo CongeRepo = new CongeRepoImpl();
	private EmployeeRepo employeeRepo = new EmployeeRepoImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CongeEmpController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			List<Conge> listCongesToValid = CongeRepo.getCongesByStateAll("En attente");
			List<Conge> listCongesValidated = CongeRepo.getCongesByStateAll("Valide");
			request.setAttribute("listCongesToValid", listCongesToValid);
			request.setAttribute("listCongesValidated", listCongesValidated);
			int nbrcongevalide=listCongesValidated.size();
			request.setAttribute("nbrcongevalide", nbrcongevalide);

			if (request.getParameter("action") != null) {
				switch (request.getParameter("action")) {
				case "accept":
					Date accBeginDate = null;
					try {
						accBeginDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("upBday"));

					} catch (ParseException e) {
						e.printStackTrace();
					} 
					Conge accConge = CongeRepo.getConge(request.getParameter("upmatricule"), accBeginDate);
					accConge.setState("Valide");
					accConge.setValidDate(new Date());
					accConge.setWording(request.getParameter("upWording"));
					CongeRepo.updateConge(accConge, accConge.getBeginDate());
					Employee updateEmp = employeeRepo.getEmployee(accConge.getmatricule());
					employeeRepo.actualizeRemainingBalance(updateEmp, updateEmp.getnbConges() - accConge.getDuration());
					response.sendRedirect(request.getContextPath() + "/CongeEmpController");
					break;

				case "decline":
					Date decBeginDate = null;
					try {
						decBeginDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("upBday"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Conge decConge = CongeRepo.getConge(request.getParameter("upmatricule"), decBeginDate);
					decConge.setState("Refuse");
					decConge.setValidDate(new Date());
					decConge.setWording(request.getParameter("upWording"));
					
					//check wording field filled
					try {
						if(request.getParameter("upWording").isEmpty()) {
							throw new Exception( "Le Commentaire est obligatoire en cas de refus de la demande" );
						} else {
							CongeRepo.updateConge(decConge, decConge.getBeginDate());
						}
					} catch ( Exception e ) {
						errors.put("wording", e.getMessage());
						request.setAttribute("errors", errors);
						this.getServletContext().getRequestDispatcher("/GestionCongesEmployes.jsp").forward( request, response );
					} finally {
						this.getServletContext().getRequestDispatcher("/CongeEmpController").forward( request, response );
					}
					break;
				}
			} else {
				this.getServletContext().getRequestDispatcher("/GestionCongesEmployes.jsp").forward( request, response );
			}
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
