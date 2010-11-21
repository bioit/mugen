package com.arexis.arxframe;
import com.arexis.util.Timer;
import java.io.*;


import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

public class Welcome extends HttpServlet {
    
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
        
        request.getSession().setAttribute("RQ", "Welcome");
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>welcome</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"test.css\" />");
        out.println("<script defer language=\"JavaScript\" src=\"javascripts/framebreak.js\"></script>");
        out.println("</head>");
        out.println("<body class=\"content\">");
        out.println("<h1>Welcome</h1>");
        
        Caller caller = (Caller)request.getSession().getAttribute("caller");
        if(caller==null){
            out.println("<p class=\"toolbar\">&nbsp;</p>");
            out.println("<br><table>");
            out.println("<tr><td>");
            out.println("&nbsp;&nbsp;your browser's session seems unstable<br>");
            out.println("&nbsp;&nbsp;please recover session by clicking <a target=\"_top\" href=\"Controller?workflow=recover\"><b>here</b></a>");
            out.println("</td></tr>");
            out.println("</table>");
        }else if (caller.getUsr().compareTo("public")==0){
            out.println("<p class=\"toolbar\">&nbsp;</p>");
            out.println("<br><table>");
            out.println("<tr><td class=\"welcome\">");
            out.println("&nbsp;&nbsp;Welcome to the MUGEN Mouse Database (MMdb) a fully searchable database of murine models of immune processes and immunological diseases.");
            out.println("The MMdb is being developed within the context of the <a class=\"welcome\" target=\"_blank\" href=\"http://www.mugen-noe.org\" title=\"Visit MUGEN NoE\">MUGEN network of Excellence</a>, a consortium of 21 leading research institutes and universities, and currently holds all mutant mouse models that were developed within the consortium.");
            out.println("Its primary aim is to enable information exchange between participating institutions on mouse strain characteristics and availability.");
            out.println("More importantly, it aims to create a mouse-centric international forum on modelling of immunological diseases and pave the way to systems biology of the mouse by correlating various genotypic and phenotypic characteristics.");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;The basic categorization of models is based on three major research application categories:<br>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"welcome\" href=\"Controller?workflow=ViewModels&_raid=1\" title=\"View All Public Models of Human Disease\">- Model of Human Disease</a>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"welcome\" href=\"Controller?workflow=ViewModels&_raid=5\" title=\"View All Public Models of Immune Processes\">- Model of Immune Processes</a>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"welcome\" href=\"Controller?workflow=ViewModels&_raid=9001\" title=\"View All Public Transgenic Tools\">- Transgenic Tool</a>");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;Mutant strains carry detailed information on affected gene(s), mutant alleles and genetic background (DNA origin, targeted, host and backcrossing background).");
            out.println("Each gene/transgene index also includes IDs and direct links to <a class=\"welcome\" target=\"_blank\" href=\"http://www.ebi.ac.uk/ensembl/\" title=\"Visit Ensembl\">Ensembl</a> (EBI’s genome browser), <a class=\"welcome\" target=\"_blank\" href=\"http://www.ebi.ac.uk/arrayexpress/\" title=\"Visit ArrayExpress\">ArrayExpress</a> (providing expression profiles), <a class=\"welcome\" target=\"_blank\" href=\"http://www.eurexpress.org/ee/\" title=\"Visit Eurexpress II\">Eurexpress II</a> (for embryonic expression patterns) and <a class=\"welcome\" target=\"_blank\" href=\"http://www.ncbi.nlm.nih.gov/sites/entrez\" title=\"Visit NCBI\">NCBI’s Entrez Gene</a> database.");
            out.println("Phenotypic description is standardized and hierarchically structured, based on MGI’s mammalian phenotypic ontology terms, but also includes relevant images and references. Since version 2.1.0 MMdb is also utilizing PATO. ");
            out.println("Availability (in the form of live mice, cryopreserved embryos or sperm, as well as ES cells) is clearly indicated, along with handling and genotyping details (in the form of documents or hyperlinks) and all relevant contact information (including <a class=\"welcome\" target=\"_blank\" href=\"http://www.emmanet.org\" title=\"Visit EMMA\">EMMA</a> and <a class=\"welcome\" target=\"_blank\" href=\"http://www.jax.org\" title=\"Visit JAX\">JAX</a> hyperlinks where available).");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;The MMdb also serves as a case example of database integration and interoperability, in the context of <a class=\"welcome\" target=\"_blank\" href=\"http://www.casimir.org.uk\" title=\"Visit CASIMIR\">CASIMIR</a>, a co-ordination Action funded by the European Commission, aiming to gather together information and make recommendations on the integration of databases and resources throughout Europe that contain genetic and phenotypic data of relevance to the mouse as a model organism.");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;For a collection of other online related resources please click<a class=\"welcome\" href=\"views/resources/resources_.jsp\" title=\"View Resources\">&nbsp;here</a> or visit the<a class=\"welcome\" target=\"_blank\" href=\"http://www.fleming.gr/mrb\" title=\"visit MRB\">&nbsp;MRB|Mouse Resource Browser</a> project.");
            out.println("</td></tr>");
            out.println("</table>");
        }else{
            out.println("<p class=\"toolbar\">"+caller.getName()+"</p>");
            out.println("<br><table>");
            out.println("<tr><td class=\"welcome\">");
            out.println("&nbsp;&nbsp;Welcome to the MUGEN Mouse Database (MMdb) a fully searchable database of murine models of immune processes and immunological diseases.");
            out.println("The MMdb is being developed within the context of the <a class=\"welcome\" target=\"_blank\" href=\"http://www.mugen-noe.org\" title=\"Visit MUGEN NoE\">MUGEN network of Excellence</a>, a consortium of 21 leading research institutes and universities, and currently holds all mutant mouse models that were developed within the consortium.");
            out.println("Its primary aim is to enable information exchange between participating institutions on mouse strain characteristics and availability.");
            out.println("More importantly, it aims to create a mouse-centric international forum on modelling of immunological diseases and pave the way to systems biology of the mouse by correlating various genotypic and phenotypic characteristics.");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;The basic categorization of models is based on three major research application categories:<br>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"welcome\" href=\"Controller?workflow=ViewModels&_raid=1\" title=\"View All Public Models of Human Disease\">- Model of Human Disease</a>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"welcome\" href=\"Controller?workflow=ViewModels&_raid=5\" title=\"View All Public Models of Immune Processes\">- Model of Immune Processes</a>");
            out.println("&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"welcome\" href=\"Controller?workflow=ViewModels&_raid=9001\" title=\"View All Public Transgenic Tools\">- Transgenic Tool</a>");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;Mutant strains carry detailed information on affected gene(s), mutant alleles and genetic background (DNA origin, targeted, host and backcrossing background).");
            out.println("Each gene/transgene index also includes IDs and direct links to <a class=\"welcome\" target=\"_blank\" href=\"http://www.ebi.ac.uk/ensembl/\" title=\"Visit Ensembl\">Ensembl</a> (EBI’s genome browser), <a class=\"welcome\" target=\"_blank\" href=\"http://www.ebi.ac.uk/arrayexpress/\" title=\"Visit ArrayExpress\">ArrayExpress</a> (providing expression profiles), <a class=\"welcome\" target=\"_blank\" href=\"http://www.eurexpress.org/ee/\" title=\"Visit Eurexpress II\">Eurexpress II</a> (for embryonic expression patterns) and <a class=\"welcome\" target=\"_blank\" href=\"http://www.ncbi.nlm.nih.gov/sites/entrez\" title=\"Visit NCBI\">NCBI’s Entrez Gene</a> database.");
            out.println("Phenotypic description is standardized and hierarchically structured, based on MGI’s mammalian phenotypic ontology terms, but also includes relevant images and references. Since version 2.1.0 MMdb is also utilizing PATO. ");
            out.println("Availability (in the form of live mice, cryopreserved embryos or sperm, as well as ES cells) is clearly indicated, along with handling and genotyping details (in the form of documents or hyperlinks) and all relevant contact information (including <a class=\"welcome\" target=\"_blank\" href=\"http://www.emmanet.org\" title=\"Visit EMMA\">EMMA</a> and <a class=\"welcome\" target=\"_blank\" href=\"http://www.jax.org\" title=\"Visit JAX\">JAX</a> hyperlinks where available).");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;The MMdb also serves as a case example of database integration and interoperability, in the context of <a class=\"welcome\" target=\"_blank\" href=\"http://www.casimir.org.uk\" title=\"Visit CASIMIR\">CASIMIR</a>, a co-ordination Action funded by the European Commission, aiming to gather together information and make recommendations on the integration of databases and resources throughout Europe that contain genetic and phenotypic data of relevance to the mouse as a model organism.");
            out.println("<br>");
            out.println("<br>");
            out.println("&nbsp;&nbsp;For a collection of other online related resources please click<a class=\"welcome\" href=\"views/resources/resources_.jsp\" title=\"View Resources\">&nbsp;here</a> or visit the<a class=\"welcome\" target=\"_blank\" href=\"http://www.fleming.gr/mrb\" title=\"visit MRB\">&nbsp;MRB|Mouse Resource Browser</a> project.");
            out.println("</td></tr>");
            out.println("</table>");
        }
        
        out.println("</body>");
        out.println("</html>");
        out.close();
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
