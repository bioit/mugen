/*
 * SampleServlet.java
 *
 * Created on June 16, 2005, 9:29 AM
 */

package test.cactus;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author heto
 * @version
 */
public class SampleServlet extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet SampleServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet SampleServlet at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
    
    public void saveToSession(HttpServletRequest request)
    {
    	String testparam = request.getParameter("testparam");
    	request.getSession().setAttribute("testAttribute", testparam);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
