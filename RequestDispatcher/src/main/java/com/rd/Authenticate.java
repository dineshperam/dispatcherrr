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
import javax.servlet.http.HttpSession;

@WebServlet("/check")
public class Authenticate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String pass = "Dinesh@123";
		
		try {
			
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url,user,pass);
		String query = "select count(*) from user where name = ? and password = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, name);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		
		int count=0;
		if(rs.next()) {
			count = rs.getInt(1);
		}
		
		if(count != 0) {
			RequestDispatcher pt = request.getRequestDispatcher("success");
			pt.forward(request, response);
		}else {
			PrintWriter out = response.getWriter();
			out.print("<h1> Login Credentials Validated </h1>");
			RequestDispatcher pt = request.getRequestDispatcher("fail");
			pt.include(request, response);
		}
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
}
