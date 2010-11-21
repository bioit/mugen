package com.arexis.mugen.simplelog;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.project.AbstractMugenBean;
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
 * This is the bean class for the SimpleLogBean enterprise bean.
 * Created Dec 22, 2005 10:42:25 AM
 * @author heto
 */
public class SimpleLogBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.simplelog.SimpleLogRemoteBusiness {
    private javax.ejb.EntityContext context;
    
    private int logid;
    private java.sql.Timestamp ts;
    private String txt;
    private String mgnaction, mgnuser, remoteadd, remotehost;
     
    private boolean dirty;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {}
    
    public void unsetEntityContext() {
        context = null;
    }
    
    public void ejbLoad() {
        makeConnection();
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select logid,ts,txt,mgnaction,mgnuser,remoteadd,remotehost " +
                    "from simplelog where logid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logid = rs.getInt("logid");
                ts = rs.getTimestamp("ts");
                txt = rs.getString("txt");
                mgnaction = rs.getString("mgnaction");
                mgnuser = rs.getString("mgnuser");
                remoteadd = rs.getString("remoteadd");
                remotehost = rs.getString("remotehost");
                dirty = false;
            } else
                throw new EJBException("SimpleLogBean#ejbLoad: Error loading log");
        } catch (Exception e) {
            throw new EJBException("SimpleLogBean#ejbLoad: error loading log. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update simplelog set txt=?,ts=?,mgnaction=?,mgnuser=?,remoteadd=?,remotehost=? where logid=?");

                ps.setString(1, txt);
                ps.setTimestamp(2, ts);                       
                ps.setString(3, mgnaction);
                ps.setString(4, mgnuser);
                ps.setString(5,remoteadd);
                ps.setString(6, remotehost);
                ps.setInt(7, logid);

                ps.execute();
            } catch (Exception e) {
                throw new EJBException("SimpleLogBean#ejbStore: error storing log. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    // <editor-fold defaultstate="collapsed">
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer aKey) throws javax.ejb.FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select logid from simplelog where logid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("SimpleLogBean#ejbFindByPrimaryKey: Cannot find log. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("SimpleLogBean#ejbFindByPrimaryKey: Cannot find log. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return aKey;
    }
    
    public java.util.Collection ejbFindByFormDataManager(FormDataManager fdm, com.arexis.mugen.MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection simplelogs = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        String user = "";
        String tmp = fdm.getValue("user");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            user = tmp;
        
        String action = "";
        tmp = fdm.getValue("mgnaction");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            action = tmp;
        
        try {
            String sql = "select logid from simplelog where remotehost is not null";
            
            if (!user.equals(""))
            {
                sql += " and mgnuser like ?";
            }
            
            if (!action.equals(""))
            {
                sql += " and mgnaction like ?";
            }
            
            sql += " order by ts desc";
                    
            ps = conn.prepareStatement(sql);
            
            int i = 1;
            
            if (!user.equals(""))
                ps.setString(i++, user);
            if (!action.equals(""))
                ps.setString(i++, action);
            
            result = ps.executeQuery();
            
            while(result.next()) {
                simplelogs.add(new Integer(result.getInt("logid")));
            }
        } catch (SQLException se) {
            throw new FinderException("SimpleLogBean#ejbFindByFormDataManager: Cannot find simplelogs by FDM.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return simplelogs;
    }
    
    public java.util.Collection ejbFindByNoRestriction() throws javax.ejb.FinderException {
        makeConnection();
        Collection simplelogs = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        
        try {
            String sql = "select logid from simplelog";
                    
            ps = conn.prepareStatement(sql);
            result = ps.executeQuery();
            
            while(result.next()) {
                simplelogs.add(new Integer(result.getInt("logid")));
            }
            
        } catch (SQLException se) {
            throw new FinderException("SimpleLogBean#ejbFindByNoRestriction: Cannot find simplelogs without restriction.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return simplelogs;
    }
    //</editor-fold>
    
    //setter+getter methods
    // <editor-fold defaultstate="collapsed">
    public int getLogid() {
        return logid;
    }

    public java.sql.Timestamp getTs() {
        return ts;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
    
    public String getMgnaction(){
        return mgnaction;
    }
    
    public void setMgnaction(String mgnaction){
        this.mgnaction = mgnaction;
    }
    
    public String getMgnuser(){
        return mgnuser;
    }
    
    public void setMgnuser(String mgnuser){
        this.mgnuser = mgnuser;
    }
    
    public String getRemoteadd(){
        return remoteadd;
    }
    
    public void setRemoteadd(String remoteadd){
        this.remoteadd = remoteadd;
    }
    
    public String getRemotehost(){
        return remotehost;
    }
    
    public void setRemotehost(String remotehost){
        this.remotehost = remotehost;
    }
    //</editor-fold>
    
    //create+postcreate methods
    // <editor-fold defaultstate="collapsed">
    public java.lang.Integer ejbCreate(java.lang.String txt, java.lang.String mgnaction, java.lang.String mgnuser, java.lang.String remoteadd, java.lang.String remotehost) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            
            PreparedStatement ps = conn.prepareStatement("select nextval('simplelog_seq') as logid");
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                logid=rs.getInt("logid");
            }
            
            
            
            
            this.txt = txt;
            
            ts = new java.sql.Timestamp(System.currentTimeMillis());
            
            pk = new Integer(logid);
            
            PreparedStatement ps2 = conn.prepareStatement("insert into simplelog (logid,txt,ts,mgnaction,mgnuser,remoteadd,remotehost) values (?,?,?,?,?,?,?)");
            ps2.setInt(1, logid);
            ps2.setString(2, txt);
            ps2.setTimestamp(3, ts);
            ps2.setString(4, mgnaction);
            ps2.setString(5, mgnuser);
            ps2.setString(6, remoteadd);
            ps2.setString(7, remotehost);
            
            ps2.execute();
            
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("SimpleLogBean#ejbCreate: Unable to create log. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(java.lang.String txt, java.lang.String mgnaction, java.lang.String mgnuser, java.lang.String remoteadd, java.lang.String remotehost) throws javax.ejb.CreateException {}
    //</editor-fold>
    
}
