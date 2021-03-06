/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.barbershop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 
 */
@WebServlet(name = "Registration", urlPatterns = {"/Registration"})
public class Registration extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        String DB_URL = "jdbc:mysql://localhost/barbershop";
        
        String USER = "root";
        String PASS = "";
        
        Connection conn = null;
        Statement stmt = null;
        
       try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      
      String firstName = (String) request.getParameter("fname");
      String lastName = (String) request.getParameter("lname");
      String gender = (String) request.getParameter("gender");
      int isMale;
      if (gender.equals("male")) { isMale = 1; }else {isMale = 0;}
      String phone_num = (String) request.getParameter("phone_num");
      //TODO Check if email already exists
      String email = (String) request.getParameter("email");
      //TODO Hash the password
      String password = (String) request.getParameter("password");

      System.out.println("Insert into user_table(firstName, lastName, isMale, phone, email, password) values (" + firstName + ", " + 
                lastName + ", " + isMale + ", " + phone_num + ", " + email 
                 + ", " + password + ");");
      
     String sql = "Insert into user_table(firstName, lastName, isMale, phone, email, password) values ('" + firstName + "', '" + 
                lastName + "', " + isMale + ", '" + phone_num + "', '" + email 
                 + "', '" + password + "');";
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement(); 
      
      stmt.executeUpdate(sql);
      
      request.setAttribute("user", firstName + " " + lastName);
      request.getRequestDispatcher("/home.jsp").forward(request, response);
      
     
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
