package org.gdc.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gdc.models.Employee;
import org.gdc.repositories.CongeRepo;
import org.gdc.repositories.CongeRepoImpl;
import org.gdc.repositories.EmployeeRepo;
import org.gdc.repositories.EmployeeRepoImpl;

/**
 * Servlet implementation class initialiser_solde
 */
@WebServlet("/Initialiser_solde")
public class Initialiser_solde extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CongeRepo CongeRepo = new CongeRepoImpl();
	private EmployeeRepo employeeRepo = new EmployeeRepoImpl();
	
    public Initialiser_solde() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	HttpSession session = request.getSession();
		Employee emp = employeeRepo.getEmployee((String) request.getParameter("matricule"));
		request.setAttribute("emp", emp);
		
		int dur=22;		 
		if (request.getParameter("load") == "in") {
			employeeRepo.updateannee(dur);
		 }
			//System.out.println(request.getParameter("action"));
			this.getServletContext().getRequestDispatcher("/Initialiser_solde.jsp").forward( request, response );	
		 }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
