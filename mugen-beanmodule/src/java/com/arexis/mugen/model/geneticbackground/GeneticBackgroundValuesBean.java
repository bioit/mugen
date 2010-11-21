package com.arexis.mugen.model.geneticbackground;

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

public class GeneticBackgroundValuesBean extends AbstractMugenBean implements EntityBean, GeneticBackgroundValuesRemoteBusiness {
    private EntityContext context;
    
    private int bid, pid;
    private String backname;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from genetic_back_values where bid=?");
            ps.setInt(1, getBid());
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GeneticBackgroundValuesBean#ejbRemove: Unable to delete Genetic Background Strain. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select bid, backname, pid " +
                    "from genetic_back_values where bid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bid = rs.getInt("bid");
                backname = rs.getString("backname");
                pid = rs.getInt("pid");
                dirty = false;
            } else
                throw new EJBException("GeneticBackgroundValuesBean#ejbLoad: Error loading Genetic Background Value Tuple");
        } catch (Exception e) {
            throw new EJBException("GeneticBackgroundValuesBean#ejbLoad: error loading Genetic Background Value. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void ejbStore() {
        if (dirty) {
            makeConnection();
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement("update genetic_back_values set backname=?, pid=? " +
                        "where bid=?");
                ps.setString(1, backname);
                ps.setInt(2, pid);
                ps.setInt(3, bid);
               
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("GeneticBackgroundValuesBean#ejbStore: error storing Genetic Background Values. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    // <editor-fold defaultstate="collapsed">
    
    public Integer ejbFindByPrimaryKey(Integer aKey) throws FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select bid from genetic_back_values where bid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GeneticBackgroundValuesBean#ejbFindByPrimaryKey: Cannot find Genetic Background value. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("GeneticBackgroundBeanValues#ejbFindByPrimaryKey: Cannot find Genetic Background value. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return aKey;
    }
    
    public java.util.Collection ejbFindByProject(int pid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select bid from genetic_back_values where pid = ? and backname not like '' order by backname");
            ps.setInt(1,pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("bid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneticBackgroundBeanValues#ejbFindByProject: Cannot find genetic backgrounds for project "+pid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    //</editor-fold>
    
    //setter+getter methods
    // <editor-fold defaultstate="collapsed">
    public int getBid() {
        return bid;
    }

    public String getBackname() {
        return backname;
    }

    public void setBackname(String backname) {
        this.backname = backname;
        dirty=true;
    }
    
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
        dirty=true;
    }

    public ProjectRemote getProject() throws ApplicationException {
        try {
            ProjectRemote prj = projectHome.findByPrimaryKey(new Integer(pid));
            return prj;
        } catch (Exception e) {
            throw new ApplicationException("Could not get project");
        }
    }
    
    public int getExpModelsCount() {
        int models = 0;
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select distinct count(eid) as num from genetic_back where " +
                        "dna_origin=? or targeted_back=? or host_back=? or backcrossing_strain=?");
            ps.setInt(1, bid);
            ps.setInt(2, bid);
            ps.setInt(3, bid);
            ps.setInt(4, bid);
            
            ResultSet result = ps.executeQuery();
            if(result.next())
                models = result.getInt("num");
            
        } catch (Exception e) {
            throw new EJBException("GeneticBackgroundValuesBean#ejbStore: error fetching models count for genetic backgound. \n"+e.getMessage());
        } finally {
            releaseConnection();
            dirty = false;
        }
        return models;
    }
    
    //</editor-fold>
    
    //create+post-create methods
    // <editor-fold defaultstate="collapsed">
    public Integer ejbCreate(int bid, String backname, ProjectRemote project) throws javax.ejb.CreateException {
        makeConnection();
        //pk=the Integer that the method will return.
        Integer pk = null;
        try {
            this.bid = bid;
            this.backname = backname;
            //this.pid = pid;
            this.pid = project.getPid();
            
            //pk is the eid=primary key for genetic_back table.
            pk = new Integer(bid);
            //prepare the SQL query of the create method.
            PreparedStatement ps = conn.prepareStatement("insert into genetic_back_values (bid,backname,pid) values (?,?,?)");
            //replace the questionmarks with the following data...
            ps.setInt(1, bid);
            ps.setString(2, backname);
            ps.setInt(3, pid);
            //execute the statement.
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("GeneticBackgroundBeanValues#ejbCreate: Unable to create Genetic Background Strain. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int bid, String backname, ProjectRemote project) throws javax.ejb.CreateException {}
    //</editor-fold>
    
}
