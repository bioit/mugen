/*
 * TestServletSU.java
 *
 * Created on June 8, 2005, 3:27 PM
 */

package test;

import com.arexis.mugen.samplingunit.group.GroupRemote;
import com.arexis.mugen.samplingunit.grouping.GroupingRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemote;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.EJBException;
import javax.ejb.FinderException;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author heto
 * @version
 */
public class TestServletSU extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet TestServletSU</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet TestServletSU at " + request.getContextPath () + "</h1>");
        
        out.println("<pre>");
        
        try
        {
            SamplingUnitRemoteHome sh = lookupSamplingUnitBean1();
            SamplingUnitRemote s = sh.findByPrimaryKey(new Integer(1034),null);
            
            out.println("SamplingUnit: " +s.getName());
            
            SpeciesRemote species = s.getSpecies();
            //species.setComm("1123");
            out.println("Species="+species.getName());
           
            
            Collection g = s.getGroupings();
            Iterator itr = g.iterator();
            while (itr.hasNext())
            {
                GroupingRemote gr = (GroupingRemote)itr.next();
                
                Collection groups = gr.getGroups();
                Iterator g_itr = groups.iterator();
                while (g_itr.hasNext())
                {
                    GroupRemote group = (GroupRemote)g_itr.next();
                    out.println(gr.getGsid()+"\t"+gr.getName()+"\t"+group.getName());
                    
                    Collection individuals = group.getIndividuals();
                    Iterator ind_itr = individuals.iterator();
                    while (ind_itr.hasNext())
                    {
                        IndividualRemote ind = (IndividualRemote)ind_itr.next();
                        out.println(ind.getId()+":"+ind.getIdentity());
                    }
                }
                
                
                
                
                
            }
            
            out.println("--------------------------------");
            
            
            
            IndividualRemoteHome indHome = lookupIndividualBean();
            
            Collection inds_1034 = indHome.findBySamplingUnit(1086);
            itr = inds_1034.iterator();
            while (itr.hasNext())
            {
                IndividualRemote ind = (IndividualRemote)itr.next();
                out.println(ind.getIid()+":"+ind.getIdentity());
                
                out.println("FatherId="+ind.getFatherId());
                if (ind.getFatherId()!=0)
                {
                    IndividualRemote father = (IndividualRemote)indHome.findByPrimaryKey(new Integer(ind.getFatherId()));
                    out.println("Father="+father.getIdentity());
                }
            }
            
            
            
            //IndividualRemote ind = indHome.findByPrimaryKey(new Integer(50959));
            //out.println("Ind: "+ind.getIdentity()+"\t"+ind.getSex());
            
        }
        catch (FinderException e)
        {
            e.printStackTrace(out);
        }
        catch (EJBException e)
        {
            e.printStackTrace(out);
        }
        /*
        catch (ObjectNotFoundException e)
        {
            e.printStackTrace(out);
        }*/
        /*
        out.println("</pre>");
        
        out.println("</body>");
        out.println("</html>");
        
        
        out.close();
    }*/
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    /*protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }*/
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    /*protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }*/
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

    private com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome lookupSamplingUnitBean1() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/SamplingUnitBean");
            com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome rv = (com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.samplingunit.individual.IndividualRemoteHome lookupIndividualBean() {
        try {
            javax.naming.Context c = new javax.naming.InitialContext();
            Object remote = c.lookup("java:comp/env/ejb/IndividualBean");
            com.arexis.mugen.samplingunit.individual.IndividualRemoteHome rv = (com.arexis.mugen.samplingunit.individual.IndividualRemoteHome) javax.rmi.PortableRemoteObject.narrow(remote, com.arexis.mugen.samplingunit.individual.IndividualRemoteHome.class);
            return rv;
        }
        catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }
}
