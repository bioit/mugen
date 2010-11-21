package com.arexis.arxframe;
import com.arexis.util.Timer;
import java.io.*;


import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

public class Help extends HttpServlet {
    
    private static Logger logger = Logger.getLogger(Menu.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // Set to expire far in the past.
        response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        response.setHeader("Content-Type", "text/html; charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        
        RequestDispatcher forwarder = request.getRequestDispatcher("/views/help/knownbugs.jsp");
        forwarder.forward(request, response);
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
