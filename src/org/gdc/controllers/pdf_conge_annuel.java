package org.gdc.controllers;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gdc.models.Conge;
import org.gdc.models.Employee;
import org.gdc.repositories.CongeRepo;
import org.gdc.repositories.CongeRepoImpl;
import org.gdc.repositories.EmployeeRepo;
import org.gdc.repositories.EmployeeRepoImpl;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;


@WebServlet("/pdf_conge_annuel")
public class pdf_conge_annuel extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	private CongeRepo CongeRepo = new CongeRepoImpl();
	private EmployeeRepo employeeRepo = new EmployeeRepoImpl();
	
    public pdf_conge_annuel() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
    	List<Conge> listCongesValidated = CongeRepo.getCongesByStateAll("Valide");
        	request.setAttribute("listCongesValidated", listCongesValidated);
        	
        	String matricule=request.getParameter("matricule");
        	request.setAttribute("matricule", matricule);

        	
        	String duration=request.getParameter("duration");
        	request.setAttribute("duration", duration);

        	
        	String beginDate=request.getParameter("beginDate");
        	request.setAttribute("beginDate", beginDate);

        	
        	String endDate=request.getParameter("endDate");
        	request.setAttribute("endDate", endDate);

   
        Conge recherche_conge_mat =CongeRepo.rechcongematricule(matricule);
        request.setAttribute("recherche_conge_mat", recherche_conge_mat);
        
        Employee recherche_emp_mat= employeeRepo.getEmployee(matricule);
        request.setAttribute("recherche_conge_mat", recherche_emp_mat);

        int yearinst = Calendar.getInstance().get(Calendar.YEAR);
        
        try { 
        	
        } catch (Exception exception ) {
            // Nothing to do
        }
  
       
		 /**/String masterPath = request.getServletContext().getRealPath( "/WEB-INF/conge_annuel.pdf" );
	        response.setContentType( "application/pdf" );
	       
      	  
	        
	        try ( PdfReader reader = new PdfReader( masterPath );
	                PdfWriter writer = new PdfWriter( response.getOutputStream() );
	                PdfDocument document = new PdfDocument( reader, writer ) ) {
	              
	              PdfPage page = document.getPage( 1 );
	              PdfCanvas canvas = new PdfCanvas( page );
	              
	              canvas.beginText();
	                FontProgram fontProgram = FontProgramFactory.createFont();
	                PdfFont font = PdfFontFactory.createFont( fontProgram, "utf-8", true );
	                canvas.setFontAndSize( font, 12 );
	                
	                canvas.setTextMatrix( 320, 648 );
	                canvas.showText(""+recherche_emp_mat.getFname()+" "+recherche_emp_mat.getName());
	                
	                canvas.setTextMatrix( 375, 591 );
	                canvas.showText(""+recherche_emp_mat.getdepartements());
	                
	                canvas.setTextMatrix( 180, 563 );
	                canvas.showText(""+yearinst);
	                
	                canvas.setTextMatrix( 480, 480 );
	                canvas.showText(""+duration);
	                
	                canvas.setTextMatrix( 300, 480 );
	                canvas.showText(""+beginDate);
	                
	                canvas.setTextMatrix( 120, 480 );
	                canvas.showText(""+endDate);
	                
	                canvas.setTextMatrix( 380, 311 );
	                canvas.showText(""+22);
	                
	                canvas.setTextMatrix( 450, 283 );
	                canvas.showText(""+recherche_emp_mat.getnbConges());
	                
	                canvas.setTextMatrix( 180, 283 );
	                canvas.showText(""+yearinst);

	              canvas.endText();
	        }
	        
	         
	 }
}
