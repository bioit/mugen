package com.arexis.arxframe;
import com.arexis.util.Timer;
import java.io.*;


import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

public class Logit extends HttpServlet {
    
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

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>logit</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"test.css\" />");
        out.println("</head>");
        out.println("<body class=\"logit\">");
        out.println("<div id=\"centered2\">");
        
        Caller caller = (Caller)request.getSession().getAttribute("caller");
        if (caller==null || caller.getUsr().compareTo("public")==0){
            out.println("<form action=\"Controller?workflow=begin\" name=\"login\" method=\"post\" target=\"_top\">");
            out.println("<table id=\"logit\">");
            out.println("<tr><td class=\"logit\">user:</td></tr>");
            out.println("<tr><td><input type=\"text\" name=\"usr\" maxlength=\"19\" size=\"19\" class=\"logit\"></td></tr>");
            out.println("<tr><td class=\"logit\">pass:</td></tr>");
            out.println("<tr><td><input type=\"password\" name=\"pwd\" maxlength=\"19\" size=\"19\" class=\"logit\"></td></tr>");
            out.println("<tr><td><input name=\"submit\" type=\"image\" src=\"images/icons/login.png\" align=\"left\"></td></tr>");
            out.println("</table>");
            out.println("</form>");
        }else{
            out.println("<form action=\"Controller?workflow=logout\" name=\"login\" method=\"post\" target=\"_top\">");
            out.println("<table id=\"logit\">");
            out.println("<tr><td class=\"logit\">user: "+caller.getUsr());
            //out.println("<input type=\"hidden\" name=\"usr\" value=\"public\">");
            //out.println("<input type=\"hidden\" name=\"pwd\" value=\"notknown\">");
            out.println("</tr></td>");
            out.println("<tr><td><input name=\"submit\" type=\"image\" src=\"images/icons/logout.png\" align=\"left\"></td></tr>");
            out.println("</table>");
            out.println("</form>");
        }
        
            
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
         
        out.close();
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
