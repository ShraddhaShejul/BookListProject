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
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final String query="insert into booklist(BookName,BookEdition,BookPrice)values(?,?,?)";
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	//get PrintWriter
	PrintWriter pw=res.getWriter();
	//set content type
	res.setContentType("text/html");
	//GET the book info
	
	String bookName=req.getParameter("bookname");
	String bookEdition=req.getParameter("bookedition");
	float bookPrice=Float.parseFloat(req.getParameter("bookprice"));
	//Load jdbc driver
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException cnf) {
		cnf.printStackTrace();
	}
	//generate the connection
	try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","root");){
		        PreparedStatement preparedStatement=con.prepareStatement(query);
		        preparedStatement.setString(1, bookName);
		        preparedStatement.setString(2, bookEdition);
		        preparedStatement.setFloat(3, bookPrice);
		        int count= preparedStatement.executeUpdate();
		        if(count==1) {
		        	pw.println("<h2>Record is Registered successfully</h2>");
		        }else {
		        	pw.println("<h2>Record is not Registered </h2>");
		        }
	}catch(SQLException se){
		    se.printStackTrace();
		    pw.println("<h1>"+se.getMessage()+"</h2>");
	    }catch (Exception  e){
		    e.printStackTrace();
		    pw.println("<h1>"+e.getMessage()+"</h2>");
	    }
	pw.println("<a href='home.html'>Home</a>");
	pw.print("<br>");
	pw.println("<a href='booklist'>BookList</a>");
}  
@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}
