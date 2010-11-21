package com.arexis.alab.imp.importjob;

import com.arexis.alab.imp.JobReport;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;
import org.apache.log4j.Logger;

/**
 * This is the bean class for the ImportJobBean enterprise bean.
 * Created Apr 25, 2006 8:40:41 AM
 * @author heto
 */
public class ImportJobBean extends AbstractMugenBean implements EntityBean, ImportJobRemoteBusiness {
    private EntityContext context;
   
    private static Logger logger = Logger.getLogger(ImportJobBean.class);
    
    private int jobId;
    private int userId;
    private int fileId;
    private int pid;
    private String report;
    
    private ImportAttributes attrs;
    
    private boolean dirty;
    
    private int progress;
    private String status;
    private int items;
    
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbActivate()
     */
    public void ejbActivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbPassivate()
     */
    public void ejbPassivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbRemove()
     */
    public void ejbRemove() throws RemoveException{
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("delete from importjob where jobid=?");
            ps.setInt(1, jobId);
            
            int res = ps.executeUpdate();
            
            if (res!=1) {
                throw new RemoveException("ImportJobBean#ejbRemove: Cannot remove ImportJob");
            }
        } catch (SQLException se) {
            
            throw new RemoveException("ImportJobBean#ejbRemove: SQL Exception");
        } finally {
            releaseConnection();   
        }
    }
    
    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext() {
        context = null;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad() {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer pk = (Integer)context.getPrimaryKey();
        try {
            ps = conn.prepareStatement("select jobid,userid,fileid,pid,attrs,report, status, items, progress from importjob where jobid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                
                jobId = pk.intValue();                
                userId = result.getInt("userid");
                fileId = result.getInt("fileid");
                pid = result.getInt("pid");
                attrs = new ImportAttributes(result.getString("attrs"));
                report = result.getString("report");
                status = result.getString("status");
                items = result.getInt("items");
                progress = result.getInt("progress");
                
            } else
                throw new EJBException("ImportJobBean#ejbLoad: Failed to load import job");
        } catch (SQLException se) {
            throw new EJBException("ImportJobBean#ejbLoad: SQL Exception", se);
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore() {
        if (dirty)
        {    
            makeConnection();
            try {
                PreparedStatement ps = null;
                ps = conn.prepareStatement("update importjob set " +
                        " userid = ?, fileid = ?, pid = ?, attrs=?, report=?, status=?, items=?, progress=? where jobid = ?");

                ps.setInt(1, userId);
                ps.setInt(2, fileId);
                ps.setInt(3, pid);
                ps.setString(4, attrs.getData());
                ps.setString(5, report);
                ps.setString(6, status);
                ps.setInt(7, items);
                ps.setInt(8, progress);
                
                ps.setInt(9, jobId);
                
                ps.execute();
                dirty = false;
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("ImportJobBean#ejbStore: Error updating ImportJob [jobId="+jobId+"]");
            } finally {
                releaseConnection();
                
            }
        }
    }
    
    // </editor-fold>
    
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     */
    public Integer ejbFindByPrimaryKey(Integer aKey) throws FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select jobid from importjob where jobid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("ImportJobBean#ejbFindByPrimaryKey: Cannot find ImportJob");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new EJBException("ImportJobBean#ejbFindByPrimaryKey: SQL Exception", se);
        } finally {
            releaseConnection();
        }                   
        
        return aKey;  
    }
    
    /**
     * Get the id of the job
     * @return the id of the import job.
     */
    public int getJobId()
    {
        return jobId;
    }
    
    /**
     * Set the report of an import. The report contains text in multiple lines.
     * @param report The report to store in the database. Multiline of text.
     */
    public void setReportString(String report)
    {
        this.report = report;
        dirty = true;
    }
    
    /**
     * Set the report of an import. The report contains text in multiple lines.
     * @param rep The report object to store in the database
     */
    public void setReport(JobReport rep)
    {
        report = rep.toString();
        dirty = true;
    }
    
    
    
    /**
     * Get the report of an import. The report contains text in multiple lines.
     * @return the report in multiline of text.
     */
    public String getReportString()
    {
        return report;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status=status;
        progress=0;
        dirty = true;
    }
    
    public int getProgress()
    {
        return progress;
    }
    
    public void setProgress(int progress)
    {
        this.progress=progress;
        dirty = true;
    }
    
    /**
     * Get the user that has created this import job.
     * @return the UserRemote object.
     */
    public UserRemote getUser()
    {
        UserRemote user = null;
        try
        {
            user = lookupUserBean().findByPrimaryKey(new Integer(userId));
        }
        catch (Exception e)
        {}
        return user;
    }
    
    /**
     * Get the file connected to the import job
     * @return a FileRemote object with the file data.
     */
    public FileRemote getFile()
    {
        FileRemote file = null;
        try
        {
            file = lookupFileBean().findByPrimaryKey(new Integer(fileId));
        }
        catch (Exception e)
        {}
        return file;
    }
    
    /**
     * Get the project the import is connected to.
     * @return the ProjectRemote object.
     */
    public ProjectRemote getProject()
    {
        ProjectRemote project = null;
        try
        {
            project = lookupProjectBean().findByPrimaryKey(new Integer(pid));
        }
        catch (Exception e)
        {}
        return project;
    }
    
    /**
     * Set all attributes the import needs. This is collected by the ImportAction
     * and forwarded to importers that may need this information.
     * @param attrs is an ImportAttributes object with all parameters for import.
     */
    public void setAttributes(ImportAttributes attrs)
    {
        this.attrs = attrs;
        dirty = true;
    }
    
    /**
     * Get the attributes for import
     * @return an ImportAttributes object with import attributes.
     */
    public ImportAttributes getAttributes()
    {
        return attrs;
    }
    
    public void setItems(int items)
    {
        this.items = items;
        dirty=true;
    }
    
    public int getItems()
    {
        return items;
    }
    
    private com.arexis.mugen.project.user.UserRemoteHome lookupUserBean() {
        try {
            return (com.arexis.mugen.project.user.UserRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/UserBean",com.arexis.mugen.project.user.UserRemoteHome.class);
        } catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.resource.file.FileRemoteHome lookupFileBean() {
        try {
            return (com.arexis.mugen.resource.file.FileRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/FileBean",com.arexis.mugen.resource.file.FileRemoteHome.class);
        } catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    private com.arexis.mugen.project.project.ProjectRemoteHome lookupProjectBean() {
        try {
            return (com.arexis.mugen.project.project.ProjectRemoteHome) com.arexis.alab.imp.ServiceLocator.getInstance().getRemoteHome("java:comp/env/ejb/ProjectBean",com.arexis.mugen.project.project.ProjectRemoteHome.class);
        } catch(javax.naming.NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE,"exception caught" ,ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * Get all jobs for a user in a project.
     * @param pid the project id
     * @param userid is the users id.
     * @throws javax.ejb.FinderException if some errors occurs.
     * @return a Collection
     */
    public Collection ejbFindByUserAndProject(int pid, int userid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        Integer id = null;
        ArrayList ids = new ArrayList();
        try
        {
            ps = conn.prepareStatement("select jobid from importjob where pid = ? and userid = ?");
            ps.setInt(1, pid);
            ps.setInt(2, userid);
            result = ps.executeQuery();
            
            while (result.next())
            {
                id = new Integer(result.getInt("jobid"));
                ids.add(id);
            }
        }
        catch (SQLException se)
        {
            throw new FinderException("ImportJobBean#ejbFindByUser: Cannot find ImportJob \n"+se.getMessage());
        }
        finally
        {
            releaseConnection();
        }
        return ids;
    }

    /**
     * Create a new ImportJob.
     * @param jobid The id of the ImportJob
     * @param userid The users id creating this job.
     * @param fileid The file id that should be imported
     * @param pid The project id to import to.
     * @return the primary key
     * @throws javax.ejb.CreateException is thrown if the create failed.
     */
    public Integer ejbCreate(int jobid, int userid, int fileid, int pid) throws javax.ejb.CreateException {
        
        makeConnection();
        
        this.jobId = jobid;
        this.userId = userid;
        this.fileId = fileid;
        this.pid = pid;
        
        String sql = "insert into importjob (jobid,userid,fileid,pid) values (?,?,?,?) ";
        
        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, jobId);
            stmt.setInt(2, userId);
            stmt.setInt(3, fileId);
            stmt.setInt(4, pid);
            
            stmt.executeUpdate();
        }
        catch (Exception e)
        {
            throw new CreateException("ImportJobBean#ejbCreate: unable to create ImportJob.\n"+e.getMessage());
        }
        finally
        {
            releaseConnection();
        }
        return new Integer(jobId);
    }

    public void ejbPostCreate(int jobid, int userid, int fileid, int pid) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
}
