package org.gdc.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.gdc.models.Conge;
import org.gdc.repositories.CongeRepo;
import org.gdc.repositories.CongeRepoImpl;
import org.gdc.repositories.EmployeeRepo;
import org.gdc.repositories.EmployeeRepoImpl;
import org.testProject.DBManager;

import java.time.LocalDate;
/**
 * Servlet implementation class export_excel_conge_valide
 */
@WebServlet(urlPatterns="/export.xls")
public class export_excel_conge_valide extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public export_excel_conge_valide() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBManager.getInstance().getConnection();
			try(HSSFWorkbook workbook = new HSSFWorkbook();)
			{
				 	CellStyle headerCellStyle = workbook.createCellStyle();
	                HSSFFont font = workbook.createFont();
	                font.setBold( true );
	                headerCellStyle.setFont(font);  
	                headerCellStyle.setAlignment( HorizontalAlignment.CENTER );
	                
	                HSSFSheet articleSheet = workbook.createSheet( "Conge" );
	                HSSFRow row = articleSheet.createRow( 0 );
	                HSSFCell cell;
	                String Valide="Valide";
	                
	                row.setRowStyle( headerCellStyle );
	                cell = row.createCell( 0 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "matricule" );
	                
	                cell = row.createCell( 1 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "Nom_et_Prenom" );
	                
	                cell = row.createCell( 2 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "date_debut" );
	                
	                cell = row.createCell( 3 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "date_fin" );
	                
	                cell = row.createCell( 4 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "duree" );
	                
	                cell = row.createCell( 5 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "motif" );
	                
	                cell = row.createCell( 6 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "type_conges" );
	                
	                cell = row.createCell( 7 );
	                cell.setCellStyle( headerCellStyle);
	                cell.setCellValue( "etat" );
	                
	                int rowIndex = 1;
	                String strSql = "select c.matricule,e.nom,e.pnom,c.date_debut,c.date_fin,c.duree,c.motif,c.type_conges,c.etat from conge c,employe e where etat='"+Valide+"' and c.matricule=e.matricule ";
	                
	                try ( Statement statement = conn.createStatement() ) {
	                    try ( ResultSet resultSet = statement.executeQuery( strSql )) { 
	                        while ( resultSet.next()) {
	                            row = articleSheet.createRow( rowIndex++ );
	                            
	                            cell = row.createCell( 0 ); 
	                            cell.setCellValue( resultSet.getString( "matricule" ) );
	                            
	                            cell = row.createCell( 1 ); 
	                            cell.setCellValue( resultSet.getString("nom")+" "+resultSet.getString( "pnom" ) );
	                            
	                            
	                            
	                            cell = row.createCell(2,CellType.NUMERIC); 
	                             java.sql.Date date1= resultSet.getDate("date_debut");
	                             //System.out.println(date1);
	                            CellStyle cellStyle = workbook.createCellStyle();
	                            CreationHelper creationHelper = workbook.getCreationHelper();
	                            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
	                            //resultSet.getTimestamp("date_debut")
	                            cell.setCellValue(date1);
	                            cell.setCellStyle(cellStyle);
	                            
	                            cell = row.createCell( 3 ); 
	                            java.sql.Date date2= resultSet.getDate("date_fin");
	                            cellStyle = workbook.createCellStyle();
	                            creationHelper = workbook.getCreationHelper();
	                            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
	                            //resultSet.getTimestamp("date_fin")
	                            cell.setCellValue(date2);
	                            cell.setCellStyle(cellStyle);
	                            
	                            cell = row.createCell( 4, CellType.NUMERIC );
	                            cell.setCellValue( resultSet.getInt("duree") );
	                            
	                            cell = row.createCell( 5);
	                            cell.setCellValue( resultSet.getString("motif") );
	                            
	                            cell = row.createCell( 6);
	                            cell.setCellValue( resultSet.getString("type_conges") );
	                            
	                            cell = row.createCell( 7);
	                            cell.setCellValue( resultSet.getString("etat") );
	                            

	                        }
	                    }
	                }

	                response.setContentType( "application/vnd.ms-excel" );
	                	try ( OutputStream out = response.getOutputStream() ) {
	                			workbook.write( out );
	                
			}
			
			
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	

}
