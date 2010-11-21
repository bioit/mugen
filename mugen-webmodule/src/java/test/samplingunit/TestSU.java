/*
 * TestModel.java
 *
 * Created on December 14, 2005, 2:09 PM
 */

package test.samplingunit;
import com.arexis.arxframe.advanced.AdvancedWorkflowManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemote;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemoteHome;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemote;
import com.arexis.mugen.model.functionalsignificancetype.FunctionalSignificanceTypeRemoteHome;
import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.species.gene.GeneRemoteHome;
import com.arexis.mugen.model.modelmanager.ModelManagerRemote;
import com.arexis.mugen.model.reference.ReferenceRemote;
import com.arexis.mugen.model.reference.ReferenceRemoteHome;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemote;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemoteHome;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.samplingunit.samplingunitmanager.IndividualDTO;
import com.arexis.mugen.samplingunit.samplingunitmanager.SamplingUnitManagerRemote;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.*;
import javax.servlet.http.*;
import test.SqlHelper;

/**
 *
 * @author heto
 * @version
 */
public class TestSU extends HttpServlet {
    
    
    ServiceLocator locator;
    
    SamplingUnitRemoteHome suHome;
    ExpModelRemoteHome modelHome;
    ResearchApplicationRemoteHome raHome;
    FileRemoteHome fileHome;
    ModelManagerRemote modelManager;
    GeneRemoteHome gaHome; 
    ReferenceRemoteHome referenceHome;
    FunctionalSignificanceTypeRemoteHome fstHome;
    SpeciesRemoteHome speciesHome;
    
    IndividualRemoteHome indHome;
    SamplingUnitManagerRemote suManager;
    
    MugenCaller caller;
    
    public void lookup()
    {
        locator = ServiceLocator.getInstance();
        modelManager = (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        suHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
        gaHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
        fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
        referenceHome = (ReferenceRemoteHome)locator.getHome(ServiceLocator.Services.REFERENCE);
        fstHome = (FunctionalSignificanceTypeRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCETYPE);
        speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
        indHome = (IndividualRemoteHome)locator.getHome(ServiceLocator.Services.INDIVIDUAL);
        suManager = (SamplingUnitManagerRemote)locator.getManager(ServiceLocator.Services.SAMPLINGUNITMANAGER);
    }
    
    public void init() throws ServletException
    {
        lookup();
        super.init();
    }
    
    public MugenCaller getCaller()
    {
        if (caller==null)
        {
            caller = new MugenCaller();
            caller.setId(1001);
            caller.setPid(99);
            caller.setName("Tobias Terstad");
            caller.updatePrivileges();
        }
        return caller;
    }
    
    public void logException(Exception e, PrintWriter out)
    {
        out.println("<pre>");
        e.printStackTrace(out);
        out.println("</pre>");
    }
    
    public void testWorkflow(HttpServletRequest req, ServletContext ctx)
    {
        
        AdvancedWorkflowManager wfm = new AdvancedWorkflowManager();
        
        try
        {
            
            
            //wfm.getNextPage2(req, ctx);
            
            
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
        
    }
    
    
    public void insertLoop(PrintWriter out)
    {
        out.println("<pre>--- insertType ---");
        try
        {
            SpeciesRemote spc = speciesHome.findByPrimaryKey(new Integer(1004));
            
            
            SamplingUnitRemote su = suHome.create(new Integer(50), "Large SamplingUnit", "Test", spc, getCaller());
            
            for (int i=0;i<50000;i++)
            {
                String name = "Test"+i;
                modelHome.create(100000+i, name, su, getCaller());
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace(out);
        }
        
        out.println("--- insertType ---</pre>");
    }
    
//    public void insertType(PrintWriter out)
//    {
//        out.println("<pre>--- insertType ---");
//        try
//        {
//            
//            fstHome.create(1001, "RNA Level", 99, getCaller());
//            fstHome.create(1002, "DNA Level", 99, getCaller());
//            fstHome.create(1003, "Proteine Level", 99, getCaller());
//            fstHome.create(1004, "Functional Assays", 99, getCaller());
//            
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace(out);
//        }
//        
//        out.println("--- insertType ---</pre>");
//    }
    
    public void testReference(PrintWriter out)
    {
        out.println("<pre>--- testReference ---");
       
        
        ReferenceRemote ref = null;
        
        try
        {
            int pid = 99;
            
            referenceHome.create(4000, pid, "ref1", null, getCaller());
            
            ref = referenceHome.findByPrimaryKey(new Integer(4000));
            
            
            SamplingUnitRemote su = suHome.findByPrimaryKey(new Integer(99));
            
            ExpModelRemote model = modelHome.create(4000, "model", su, getCaller());
            
            
            model.addReference(ref);
            
            Collection refs = model.getReferences();
            Iterator i = refs.iterator();
            while (i.hasNext())
            {
                ReferenceRemote r = (ReferenceRemote)i.next();
                out.println("RefName="+r.getName());
                
                r.remove();
            }
            
            
            
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                
                
                SqlHelper sql = new SqlHelper("model", "eid", "1001", "10.0.1.30", "mugen2");
                
                sql.delete("eid", "4000");
               
                
                
                
                SqlHelper sql2 = new SqlHelper("expobj", "eid", "1001", "10.0.1.30", "mugen2");
                
                sql2.delete("eid", "4000");
                
                
                SqlHelper sql3 = new SqlHelper("reference", "refid", "1001", "10.0.1.30", "mugen2");
                
                sql3.delete("refid", "4000");
                
                
                
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testReference ---</pre>");
        
    }
    
    public void testModelGenotyping(PrintWriter out)
    {
        out.println("<pre>--- testModelGenotyping ---");
       
        SamplingUnitRemote su;
        SamplingUnitRemoteHome suHome;
        
        ExpModelRemoteHome modelHome;
        ExpModelRemote model = null;
        
        ResearchApplicationRemoteHome raHome;
        ResearchApplicationRemote ra;
        
        FileRemoteHome fileHome;
        FileRemote file;
        
        ModelManagerRemote modelManager;
        
        try
        {
            
            modelManager = (ModelManagerRemote)locator.getManager(ServiceLocator.Services.MODELMANAGER);
            
            
            
            modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
            model = modelHome.findByPrimaryKey(new Integer(1001));
            
            suHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
            su = suHome.findByPrimaryKey(new Integer(99));
            
            GeneRemoteHome gaHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
            
            
            ProjectRemoteHome pHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
            ProjectRemote prj = pHome.findByPrimaryKey(new Integer(getCaller().getPid()));
            
            GeneRemote ga = gaHome.create(4000, "ga", null, null,null,null,null,null,null,prj, getCaller());
            
            
            
            Collection fss = model.getFunctionalSignificance();
            out.println("FunctionalSignificance Size="+fss.size());
            
            Collection gas = model.getGeneAffected();
            out.println("GeneAffected size = "+gas.size());
            
            ga.remove();
            
            
            try
            {
                modelHome.create(4000, "modelid- M5", su, getCaller());
            }
            catch (Exception ignore)
            {}
            model = modelHome.findByPrimaryKey(new Integer(4000));
            
            /** 
             * Test file on model
             */
            int pid = 99;
            fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
            Collection files = fileHome.findByProject(pid);
            Iterator ifiles = files.iterator();
            
            FileRemote newFile = null;
            try
            {
                fileHome.create(new Integer(4000), "testfile.txt", "empty", "plain/text", "", getCaller());
            }
            catch (Exception ignore)
            {}
            newFile = fileHome.findByPrimaryKey(new Integer(4000));
            
            model.setGenotypingFile(newFile.getFileId());
            
            
            modelManager.removeGenotypingFile(model.getEid(), getCaller());
            
            
            
            
            
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                
                
                SqlHelper sql = new SqlHelper("model", "eid", "1001", "10.0.1.30", "mugen2");
                
                sql.delete("eid", "4000");
                sql.delete("eid", "4001");
                
                
                
                SqlHelper sql2 = new SqlHelper("expobj", "eid", "1001", "10.0.1.30", "mugen2");
                
                sql2.delete("eid", "4000");
                sql2.delete("eid", "4001");
                
                SqlHelper sql3 = new SqlHelper("research_application", "raid", "1001", "10.0.1.30", "mugen2");
                
                sql3.delete("raid", "4000");
                sql3.delete("raid", "4001");
                
                
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testModelGenotyping ---</pre>");
        
    }
    
    public void testModel(PrintWriter out)
    {
        out.println("<pre>--- testModel ---");
       
        SamplingUnitRemote su;
        SamplingUnitRemoteHome suHome;
        
        ExpModelRemoteHome modelHome;
        ExpModelRemote model = null;
        
        ResearchApplicationRemoteHome raHome;
        ResearchApplicationRemote ra;
        
        FileRemoteHome fileHome;
        FileRemote file;
        
        try
        {
            
            modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
            model = modelHome.findByPrimaryKey(new Integer(1001));
            
            suHome = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
            su = suHome.findByPrimaryKey(new Integer(99));
            
            modelHome.create(4000, "modelid- M5", su, getCaller());
            modelHome.create(4001, "modelid- M6", su, getCaller());
            
            
            
            
            
            
            
            
            
            model = modelHome.findByPrimaryKey(new Integer(4000));
            
            model.setCaller(getCaller());
            
            model.setComm("comment");
            out.println("comm="+model.getComm());
            
            
            /** 
             * Test file on model
             */
            int pid = 99;
            fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
            Collection files = fileHome.findByProject(pid);
            Iterator ifiles = files.iterator();
            
            FileRemote newFile = fileHome.create(new Integer(4000), "testfile.txt", "empty", "plain/text", "", getCaller());
            
            if (ifiles.hasNext())
            {
                file = (FileRemote)ifiles.next();
                model.setGenotypingFile(file.getFileId());
                
                out.println("Genotyping file set fileid="+file.getFileId());
                
                FileRemote file2 = model.getGenotypingFile();
                out.println("file2Name="+file2.getName());
                
                model.setGenotypingFile(0);
                out.println("Genotyping file removed fileid=0");
                
                
                model.setGenotypingFile(newFile.getFileId());
                out.println("Genotyping file added fileid="+newFile.getFileId());
                
                model.setGenotypingFile(0);
                out.println("Genotyping file removed fileid=0");
                
                newFile.remove();
                out.println("File removed from table.");
                
                
            }
                
            
            
            raHome = (ResearchApplicationRemoteHome)locator.getHome(ServiceLocator.Services.RESEARCHAPPLICATION);
            
            ra = raHome.create("klkk", "comment", 99, 4000, getCaller());
            raHome.create("type2", "comment", 99, 4001, getCaller());

            model.setResearchApplication(ra);
            
            ra = null;
            
            
            
            
            
            
            ra = model.getResearchApplication();
            
            if (ra!=null)
            {
                out.println("raName="+ra.getName());
            }
            
            
            
            
            
            
            
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                
                
                SqlHelper sql = new SqlHelper("model", "eid", "1001", "10.0.1.30", "mugen2");
                
                sql.delete("eid", "4000");
                sql.delete("eid", "4001");
                
                
                
                SqlHelper sql2 = new SqlHelper("expobj", "eid", "1001", "10.0.1.30", "mugen2");
                
                sql2.delete("eid", "4000");
                sql2.delete("eid", "4001");
                
                SqlHelper sql3 = new SqlHelper("research_application", "raid", "1001", "10.0.1.30", "mugen2");
                
                sql3.delete("raid", "4000");
                sql3.delete("raid", "4001");
                
                
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testModel ---</pre>");
        
    }
    
    public void testGeneAffected(PrintWriter out)
    {
        out.println("<pre>--- testGeneAffected ---");
        GeneRemoteHome home;
        GeneRemote ga = null;
        
        ExpModelRemoteHome modelHome;
        ExpModelRemote model = null;
        
        try
        {
            home = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
            modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
            model = modelHome.findByPrimaryKey(new Integer(1001));
            
            ProjectRemoteHome pHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
            ProjectRemote prj = pHome.findByPrimaryKey(new Integer(getCaller().getPid()));
            
            ga = home.create(1001, "name", null, null,null,null,null,null, null, prj, getCaller());
            ga.setComm("comment");
            
            home.create(1002, "name22", null, null,null,null,null,null, null, prj, getCaller());
            home.create(1003, "name333", null, null,null,null,null,null, null, prj, getCaller());
            
            out.println(ga.getComm()+"+"+ga.getName());
            out.println("userid="+ga.getUser().getId());
            
            
            
            out.println("findByModel");
            Collection arr = home.findByModel(model.getEid());
            Iterator i = arr.iterator();
            while (i.hasNext())
            {
                ga = (GeneRemote)i.next();
                out.println("name="+ga.getName());
            }
            
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                
                
                SqlHelper sql = new SqlHelper("gene_affected", "gaid", "1001", "10.0.1.30", "mugen2");
                
                sql.delete("gaid", "1001");
                sql.delete("gaid", "1002");
                sql.delete("gaid", "1003");
                
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testGeneAffected ---</pre>");
        
    }
    
    public void testFunctionalSignificance(PrintWriter out)
    {
        out.println("<pre>--- testFunctionalSignificance ---");
        
        FunctionalSignificanceRemoteHome fsHome;
        FunctionalSignificanceRemote fs;
        
        ExpModelRemoteHome modelHome;
        ExpModelRemote model = null;
        
        try
        {
            int pid = 99;
            
            
            modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
            model = modelHome.findByPrimaryKey(new Integer(1001));
            
            
            
            fsHome = (FunctionalSignificanceRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCE);
            fsHome.create(1001, "cat1", null, model, getCaller(), null);
            fsHome.create(1002, "cat2", null, model, getCaller(), null);
            fsHome.create(1003, "cat3", null, model, getCaller(), null);
            
            fs = fsHome.findByPrimaryKey(new Integer(1002));
            fs.setName("new name");
            
            out.println("Name="+fs.getName());
            
            
            Collection arr = fsHome.findByModel(model.getEid());
            Iterator i = arr.iterator();
            
            while (i.hasNext())
            {
                fs = (FunctionalSignificanceRemote)i.next();
                
                out.println(fs.getName());
            }
            
            
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                SqlHelper sql = new SqlHelper("functional_significance", "fsid", "1001", "10.0.1.30", "mugen2");
                
                sql.delete("fsid", "1001");
                sql.delete("fsid", "1002");
                sql.delete("fsid", "1003");
                
                
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testFunctionalSignificance ---</pre>");
        
    }
    
    public void testFunctionalSignificanceType(PrintWriter out)
    {
        out.println("<pre>--- testFunctionalSignificanceType ---");
        
        FunctionalSignificanceTypeRemoteHome fstHome;
        FunctionalSignificanceTypeRemote fst;
        
        ExpModelRemoteHome modelHome;
        ExpModelRemote model = null;
        
        try
        {
            int pid = 99;
            
            
            modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
            model = modelHome.findByPrimaryKey(new Integer(1001));
            
            
            
            fstHome = (FunctionalSignificanceTypeRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCETYPE);
            fstHome.create(1001, "cat1", pid, getCaller());
            fstHome.create(1002, "cat2", pid, getCaller());
            fstHome.create(1003, "cat3", pid, getCaller());
            
            fst = fstHome.findByPrimaryKey(new Integer(1002));
            fst.setName("new name");
            
            out.println("Name="+fst.getName());
            
            
            Collection arr = fstHome.findByProject(pid);
            Iterator i = arr.iterator();
            
            while (i.hasNext())
            {
                fst = (FunctionalSignificanceTypeRemote)i.next();
                
                out.println(fst.getName());
            }
            
            
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                SqlHelper sql = new SqlHelper("functional_significance_type", "fstid", "1001", "10.0.1.30", "mugen2");
                
                sql.delete("fstid", "1001");
                sql.delete("fstid", "1002");
                sql.delete("fstid", "1003");
                
                
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testFunctionalSignificanceType ---</pre>");
        
    }
    
    public void testGeneEffectedFindByModel(PrintWriter out)
    {
        out.println("<pre>--- testGeneEffectedFindByModel ---");
        
        GeneRemoteHome gaHome;
        GeneRemote ga;
        
        ExpModelRemoteHome modelHome;
        ExpModelRemote model = null;
        
        try
        {
            int pid = 99;
            
            
            modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
            model = modelHome.findByPrimaryKey(new Integer(1001));
            
            ProjectRemoteHome pHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
            ProjectRemote prj = pHome.findByPrimaryKey(new Integer(getCaller().getPid()));
            
            
            gaHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
            gaHome.create(1001, "ga1", null, null,null,null,null,null, null, prj, getCaller());
            gaHome.create(1002, "ga2", null, null,null,null,null,null, null, prj, getCaller());
            gaHome.create(1003, "ga3", null, null,null,null,null,null, null, prj, getCaller());
            
            ga = gaHome.findByPrimaryKey(new Integer(1003));
            
            out.println("Name="+ga.getName());
            
            
            
            Collection arr = gaHome.findByModel(model.getEid());
            Iterator i = arr.iterator();
            
            while (i.hasNext())
            {
                ga = (GeneRemote)i.next();
                
                out.println(ga.getName());
            }
            
            
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                SqlHelper sql = new SqlHelper("gene_affected", "gaid", "1001", "10.0.1.30", "mugen2");
                
                sql.delete("gaid", "1001");
                sql.delete("gaid", "1002");
                sql.delete("gaid", "1003");
                
                
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testGeneEffectedFindByModel ---</pre>");
        
    }
    
    public void testGRP(PrintWriter out)
    {
        out.println("<pre>--- testGRP ---");
        
        try
        {
            int iid = 52322923;
            String identity = "i-test-3";
            
            for (int i=0;i<100;i++)
            {    
                SamplingUnitRemote su = suHome.findByPrimaryKey(new Integer(1158));
                
                int tmp = suManager.createGrouping("123", "test", getCaller(), su.getSuid());
                
              
                suManager.removeGrouping(tmp, getCaller());
            } 
        }
        catch (Exception e)
        {
            logException(e, out);
        }
        finally
        {
            try
            {
                
//                SqlHelper sql = new SqlHelper("groupings", "gsid", "52322923", "127.0.0.1", "agdb2");
//                sql.delete("iid", "52322923");
//                
//                SqlHelper sql2 = new SqlHelper("expobj", "eid", "52322923", "127.0.0.1", "agdb2");
//                sql2.delete("eid", "52322923");
                
            }
            catch (Exception e)
            {
                logException(e, out);
            }
            
        }
        
        out.println("--- testGRP ---</pre>");
    }
   
    
    
    
   
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
        
        try
        {
            //testGRP(out);
            
            
            //testWorkflow(request, getServletContext());
            
            //testStrain(out);
            
            
            //insertType(out);
            //insertLoop(out);
            
            /*
            testReference(out);
            testModelGenotyping(out);
           
            
            testModel(out);
            testGeneEffectedFindByModel(out); 
            testFunctionalSignificanceType(out);
            testFunctionalSignificance(out);
            testGeneAffected(out);
            
            */
        }
        catch (Exception  e)
        {
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        
        
             
        /* TODO output your page here
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet TestModel</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet TestModel at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
         */
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
