package com.arexis.mugen.model.researchapplication;

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
import javax.ejb.*;

public class ResearchApplicationBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.model.researchapplication.ResearchApplicationRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int raid, pid, id;
    private String name;
    private String comm;
    private java.sql.Date ts;
    
    private boolean dirty;
    
    private UserRemoteHome userHome;    
    private ProjectRemoteHome projectHome; 
    private ExpModelRemoteHome modelHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);         
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from research_application where raid=?");
            ps.setInt(1, raid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("ResearchApplicationBean#ejbRemove: Unable to delete research application. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void unsetEntityContext() {
        context = null;
    }
    
    public void ejbLoad() {
        makeConnection();
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select raid,name,comm, pid, ts, id " +
                    "from research_application where raid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                raid = rs.getInt("raid");
                name = rs.getString("name");
                comm = rs.getString("comm");
                pid = rs.getInt("pid");
                ts = rs.getDate("ts");
                id = rs.getInt("id");
                dirty = false;
            } else
                throw new EJBException("ResearchApplicationBean#ejbLoad: Error loading ResearchApplication");
        } catch (Exception e) {
            throw new EJBException("ResearchApplicationBean#ejbLoad: error loading ResearchApplicationBean. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void ejbStore() {
        if (dirty) {
            makeConnection();
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement("update research_application set name=?,comm=?,pid=?,id=?,ts=?" +
                        "where raid=?");

                ps.setString(1, name);
                ps.setString(2, comm);
                ps.setInt(3, pid);
                ps.setInt(4, caller.getId());
                ps.setDate(5, new Date(System.currentTimeMillis()));
                
                ps.setInt(6, raid);

                ps.execute();
            } catch (Exception e) {
                throw new EJBException("ResearchApplicationBean#ejbStore: error storing research application. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    //<editor-fold defaultstate="collapsed">
    
    public java.lang.Integer ejbFindByPrimaryKey(Integer key) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select raid from research_application where raid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("ResearchApplicationBean#ejbFindByPrimaryKey: Cannot find ResearchApplication. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("ResearchApplicationBean#ejbFindByPrimaryKey: Cannot find ResearchApplication. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    public java.util.Collection ejbFindByProject(int pid) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        Collection apps = new ArrayList();
        try {
            ps = conn.prepareStatement("select raid from research_application where pid = ? order by name");
            ps.setInt(1, pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                apps.add(new Integer(result.getInt("raid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ResearchApplicationBean#ejbFindByProject: Cannot find ResearchApplication. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return apps;
    }
    
    public Collection ejbFindByName(String name) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        Collection apps = new ArrayList();
        try {
            ps = conn.prepareStatement("select raid from research_application where lower(name) like lower(?)");
            ps.setString(1, name);
            result = ps.executeQuery();
            
            while (result.next()) {
                apps.add(new Integer(result.getInt("raid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ResearchApplicationBean#ejbFindByName: Cannot find ResearchApplication. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }        
        return apps;
    }

    public Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        int key = 0;
        Collection arr = new ArrayList();
        try {
            ps = conn.prepareStatement("select raid from research_application where lower(name) like ? or lower(comm) like ?");
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("raid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ResearchApplicationBean#ejbFindByKeyword: Cannot find research application by keyword. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    //</editor-fold>

    //setter+getter methods
    //<editor-fold defaultstate="collapsed">
    
    public int getRaid() {
        return raid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;
    }

    public String getComm() {
        return comm;
    }
    
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
        dirty = true;
    }  
    
    public MugenCaller getCaller() {
        return caller;
    }
    
    public void setComm(String comm) {
        this.comm = comm;
        id = caller.getId();
        ts = new java.sql.Date(System.currentTimeMillis());         
        dirty = true;
    }

    public UserRemote getUser() {
        try {
            return userHome.findByPrimaryKey(new Integer(id));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public java.sql.Date getTs() {
        return ts;
    }
    
    public ProjectRemote getProject() {
        try {
            return projectHome.findByPrimaryKey(new Integer(pid));
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Collection getModels() throws ApplicationException {
        try {
            Collection models = modelHome.findByResearchApplication(raid, caller);
            return models;
        } catch (Exception e) {
            throw new ApplicationException("failed to get models", e);
        }
    }
    
    //</editor-fold>

    //create+postcreate methods
    //<editor-fold defaultstate="collapsed">
    
    public java.lang.Integer ejbCreate(java.lang.String name, java.lang.String comm, int pid, int raid, MugenCaller caller) throws javax.ejb.CreateException {
        try {
            this.caller = caller;
            this.name = name;
            this.comm = comm;
            this.pid = pid;
            this.raid = raid;
            
            ts = new Date(System.currentTimeMillis());
            
            makeConnection();
                        
            String sql = "insert into research_application (raid, name, comm, id, ts, pid) values (?,?,?,?,?,?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, raid);
            stmt.setString(2, name);
            stmt.setString(3, comm);
            stmt.setInt(4, caller.getId());
            stmt.setDate(5, ts);
            stmt.setInt(6, pid);
            
            stmt.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("Unable to insert research application");
        } finally {
            releaseConnection();
        }
        return new Integer(raid);
    }

    public void ejbPostCreate(java.lang.String name, java.lang.String comm, int pid, int raid, MugenCaller caller) throws javax.ejb.CreateException {}
    
    //</editor-fold>

}
