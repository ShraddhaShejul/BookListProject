package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final String query="delete from BOOKLIST WHERE id=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw=res.getWriter();
		//set content type
		res.setContentType("text/html");
		
		//get the id of record
		int id=Integer.parseInt(req.getParameter("id"));
		//Load jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		//generate the connection
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","root");
			        PreparedStatement preparedStatement=con.prepareStatement(query);)
		{
			       preparedStatement.setInt(1, id);
			      int count= preparedStatement.executeUpdate();
			        if(count==1) {
			        	pw.println("<h2>Record is deleted successfully</h2>");
			        }else {
			        	pw.println("<h2>Record is not deleted Registered </h2>");
			        }
		}catch(SQLException se){
			    se.printStackTrace();
			    pw.println("<h1>"+se.getMessage()+"</h1>");
		    }catch (Exception  e){
			    e.printStackTrace();
			    pw.println("<h1>"+e.getMessage()+"</h1>");
		    }
		pw.println("<a href='home.html'>Home</a>");
		pw.print("<br>");
		pw.println("<a href='booklist'>Book List</a>");
	}  
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doGet(req,res);
		}
}
