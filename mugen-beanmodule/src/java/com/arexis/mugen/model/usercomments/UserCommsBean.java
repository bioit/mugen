package com.arexis.mugen.model.usercomments;

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
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

public class UserCommsBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.model.usercomments.UserCommsRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int commid, userid;
    private String comm; 
    private java.sql.Date ts;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    private ChromosomeRemoteHome chromosomeHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setEntityContext(javax.ejb.EntityContext aContext) {
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
            ps = conn.prepareStatement("delete from comms where commid=?");
            ps.setInt(1, commid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("UserCommsBean#ejbRemove: Unable to delete user comment.\n"+e.getMessage());
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
            
            ps = conn.prepareStatement("select commid, userid, comm, ts from comms where commid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                commid = rs.getInt("commid");
                userid = rs.getInt("userid");
                comm = rs.getString("comm");
                ts = rs.getDate("ts");
                dirty = false;
            } else
                throw new EJBException("UserCommsBean#ejbLoad: Error loading user comment");
        } catch (Exception e) {
            throw new EJBException("UserCommsBean#ejbLoad: error loading user comment. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void ejbStore() {
        if (dirty)
        {
            makeConnection();
            PreparedStatement ps = null;
            try {
                
                ps = conn.prepareStatement("update comms set userid=?,comm=?,ts=? where commid=?");

                ps.setInt(1, userid);
                ps.setString(2, comm);
                ps.setDate(3, new Date(System.currentTimeMillis()));
                ps.setInt(4, commid);
               
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("UserCommsBean#ejbStore: error storing user comment. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    // <editor-fold defaultstate="collapsed">
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer key) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select commid from comms where commid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("UserCommsBean#ejbFindByPrimaryKey: Cannot find user comment. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("UserCommsBean#ejbFindByPrimaryKey: Cannot find user comment. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    public java.util.Collection ejbFindByModel(int eid) throws javax.ejb.FinderException {
        makeConnection();
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select g.commid from comms g, comms_model_r r where g.commid=r.commid and r.eid = ? order by g.ts");
            ps.setInt(1,eid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("commid")));
            }
        } catch (SQLException se) {
            throw new FinderException("UserCommsBean#ejbFindByModel: Cannot find user comment by model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    //</editor-fold>
    
    //setter+getter methods
    // <editor-fold defaultstate="collapsed">

    public int getCommid() {
        return commid;
    }

    public int getUserid(){
        return userid;
    }
    
    public String getUsername(){
        try {
            UserRemote usr = userHome.findByPrimaryKey(new Integer(userid));
            return usr.getName();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException("Could not get user name");
        }
    }
    
    public void setUserid(int userid){
        this.userid = userid;
        dirty = true;
    }
    
    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    public Date getTs() {
        return ts;
    }
    
    //</editor-fold>
    
    //relational methods
    //<editor-fold defaultstate="collapsed">
    public void connectModel(int eid) throws ApplicationException{
        try
        {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("insert into comms_model_r (commid,eid) values (?,?)");
            ps.setInt(1, commid);
            ps.setInt(2, eid);
            ps.execute();
            
        } catch (Exception e) {
            throw new ApplicationException("UserCommsBean#connectModel: Failed to connect user comment to model",e);
        } finally {
            releaseConnection();
        }
    }
    //</editor-fold>
    
    //create+postcreate methods
    // <editor-fold defaultstate="collapsed">

    public java.lang.Integer ejbCreate(int commid, int userid) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            
            this.commid = commid;
            this.userid = userid;
            this.comm = "";
            this.ts = new Date(System.currentTimeMillis());
            
            pk = new Integer(commid);
            
            PreparedStatement ps = conn.prepareStatement("insert into comms (commid,userid,comm,ts) values (?,?,?,?)");
            ps.setInt(1, commid);
            ps.setInt(2, userid);
            ps.setString(3, comm);
            ps.setDate(4, ts);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("GeneBean#ejbCreate: Unable to create Gene. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int commid, int userid) throws javax.ejb.CreateException {}
    
    //</editor-fold>
    
}
