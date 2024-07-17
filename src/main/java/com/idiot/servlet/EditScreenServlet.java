package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final String query="SELECT BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKLIST WHERE id=?";
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
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","root");){
			        PreparedStatement preparedStatement=con.prepareStatement(query);
			   preparedStatement.setInt(1, id);
			   ResultSet rs=preparedStatement.executeQuery();
			   rs.next();
			   pw.print("<form action='editurl?id="+ id+ "' method='post'>");
			   pw.println("<table  align='center'>");
			   pw.print("<tr>");
			   pw.print("<td>Book Name </td>");
			   pw.println("<td><input type ='text' name='bookName'  value='"+rs.getString(1)+"'></td>");
			   pw.print("</tr>");
			   pw.print("<tr>");
			   pw.print("<td>Book Edition </td>");
			   pw.println("<td><input type ='text' name='bookEdition'  value='"+rs.getString(2)+"'></td>");
			   pw.print("</tr>");
			   pw.print("<tr>");
			   pw.print("<td>Book Price </td>");
			   pw.println("<td><input type ='text' name='bookPrice'  value='"+rs.getFloat(3)+"'></td>");
			   pw.print("</tr>");
			   pw.print("<tr>");
			   pw.print("<td><input type='submit' value='Edit'></td>");
			   pw.print("<td><input type='reset' value='cancel'></td>");
			   pw.print("</tr>");
			   pw.println("</table>");
			   pw.println("</form>");
			      
		}catch(SQLException se){
			    se.printStackTrace();
			    pw.println("<h1>"+se.getMessage()+"</h2>");
		    }catch (Exception  e){
			    e.printStackTrace();
			    pw.println("<h1>"+e.getMessage()+"</h2>");
		    }
		pw.println("<a href='home.html'>Home</a>");

	}  
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doGet(req,res);
		}
}
