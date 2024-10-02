package com.rd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/save")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String mobile = request.getParameter("mobile");
		String dob = request.getParameter("dob");
		String address = request.getParameter("address");
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String pass = "Dinesh@123";
		
		PrintWriter out = response.getWriter();
		
		
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,user,pass);
			
			String cquery = "select count(*) from userss where name = ?";
			PreparedStatement cps = conn.prepareStatement(cquery);
			cps.setString(1, name);
			ResultSet rss = cps.executeQuery();
			int count = 0;
			if(rss.next()) {
				count = rss.getInt(1);
			}
			if(count ==0) {
				
				String query = "insert into userss values(?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, name);
				ps.setString(2, password);
				ps.setString(3, mobile);
				ps.setString(4, dob);
				ps.setString(5, address);
				
				ps.executeUpdate();
				out.print("<h1>Registered successfully</h1>");
				
				RequestDispatcher rr = request.getRequestDispatcher("Login.html");
				rr.include(request, response);
			}else {
				out.print("<h1>User already Exists</h1>");
				RequestDispatcher rr = request.getRequestDispatcher("Register.html");
				rr.include(request, response);
			}
			}
			catch(Exception e) {
				System.out.println(e);
				
		}
	}
}
