package com.arexis.mugen.model.repositories;

import javax.ejb.*;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.project.ProjectRemote;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

/**
 * This is the bean class for the RepositoriesBean enterprise bean.
 * Created 20 Éïõë 2006 11:24:17 ðì
 * @author zouberakis
 */
public class RepositoriesBean extends AbstractMugenBean implements EntityBean, RepositoriesRemoteBusiness {
    private EntityContext context;
    
    private int rid, pid;
    private String reponame, repourl;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    
    //ejb infrastructure methods
    // <editor-fold>
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
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
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from repositories where rid=?");
            ps.setInt(1, rid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("RepositoriesBean#ejbRemove: Unable to delete Repository.\n"+e.getMessage());
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
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select rid, reponame, repourl, pid " +
                    "from repositories where rid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rid = rs.getInt("rid");
                reponame = rs.getString("reponame");
                repourl = rs.getString("repourl");
                pid = rs.getInt("pid");
                dirty = false;
            } else
                throw new EJBException("RepositoriesBean#ejbLoad: Error loading Repository");
        } catch (Exception e) {
            throw new EJBException("RepositoriesBean#ejbLoad: error loading Repository. \n"+e.getMessage());
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
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement("update repositories set reponame=?, repourl=? " +
                        "where rid=?");

                ps.setString(1, reponame);
                ps.setString(2, repourl);
                ps.setInt(3, rid);
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("RepositoriesBean#ejbStore: error storing Repository. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    //<editor-fold>
    public Integer ejbFindByPrimaryKey(Integer key) throws FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select rid from repositories where rid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("RepositoriesBean#ejbFindByPrimaryKey: Cannot find Repository. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("RepositoriesBean#ejbFindByPrimaryKey: Cannot find Repository. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    public Collection ejbFindByProject(int pid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select rid from repositories where pid = ? order by reponame");
            ps.setInt(1,pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("rid")));
            }
        } catch (SQLException se) {
            throw new FinderException("RepositoriesBean#ejbFindByProject: Cannot find repositories by project "+pid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    //</editor-fold>
    
    //get+set methods.
    //<editor-fold>

    public int getRid() {
        return rid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
        dirty = true;
    }

    public String getReponame() {
        return reponame;
    }

    public void setReponame(String reponame) {
        this.reponame = reponame;
        dirty = true;
    }
    
    public String getRepourl() {
        return repourl;
    }

    public void setRepourl(String repourl) {
        this.repourl = repourl;
        dirty = true;
    }
    
    public ProjectRemote getProject() throws ApplicationException {
        try
        {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid));
            return prj;
        }
        catch (Exception e)
        {
            throw new ApplicationException("Could not get project");
        }
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    //<editor-fold>
    public Integer ejbCreate(int rid, String reponame, String repourl, ProjectRemote project) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.rid = rid;
            this.reponame = reponame;
            this.pid = project.getPid();
            
            pk = new Integer(rid);
            
            PreparedStatement ps = conn.prepareStatement("insert into repositories (rid, reponame, repourl, pid) values (?,?,?,?)");
            ps.setInt(1, rid);
            ps.setString(2, reponame);
            ps.setString(3, repourl);
            ps.setInt(4, pid);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("RepositoriesBean#ejbCreate: Unable to create Repository. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int rid, String reponame, String repourl, ProjectRemote project) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    //</editor-fold>

    
    
    
}
