package com.arexis.mugen.model.strain.strain;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.expmodel.ExpModelRemoteHome;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemoteHome;
import com.arexis.mugen.model.strain.state.StrainStateRemote;
import com.arexis.mugen.model.strain.state.StrainStateRemoteHome;
import com.arexis.mugen.model.strain.type.StrainTypeRemote;
import com.arexis.mugen.model.strain.type.StrainTypeRemoteHome;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.servicelocator.ServiceLocator;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the StrainBean enterprise bean.
 * Created Jul 5, 2006 9:38:30 AM
 * @author heto
 */
public class StrainBean extends AbstractMugenBean implements EntityBean, StrainRemoteBusiness
{
    private EntityContext context;
    
    private int strainid;
    private String designation;
    private String mgiid;
    private String emmaid;
    // Relational object
    private int pid;    // Project ID
    
    private boolean dirty;
    
    private ExpModelRemoteHome modelHome;
    private StrainTypeRemoteHome strainTypeHome;
    private StrainStateRemoteHome strainStateHome;
    private StrainAlleleRemoteHome strainAlleleHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
   
    public void setEntityContext(EntityContext aContext){
        context = aContext;
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        strainTypeHome = (StrainTypeRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_TYPE);
        strainStateHome = (StrainStateRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_STATE);
        strainAlleleHome = (StrainAlleleRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_ALLELE);
    }
    
    public void ejbActivate(){}
    
    public void ejbPassivate(){}
    
    public void ejbRemove(){
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from strain where strainid=?");
            ps.setInt(1, strainid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("StrainBean#ejbRemove: Unable to delete strain. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void unsetEntityContext(){
        context = null;
    }
    
    public void ejbLoad(){
        makeConnection();
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("select strainid,designation, mgiid, emmaid, pid " +
                    "from strain where strainid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                strainid = rs.getInt("strainid");
                designation = rs.getString("designation");
                mgiid = rs.getString("mgiid");
                emmaid = rs.getString("emmaid");
                pid = rs.getInt("pid");
                dirty = false;
            } else
                throw new EJBException("StrainBean#ejbLoad: Error loading strain");
        } catch (Exception e) {
            throw new EJBException("StrainBean#ejbLoad: error loading strain. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void ejbStore(){
        if (dirty)
        {
            makeConnection();
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement("update strain set designation=?, mgiid=?, emmaid=?, pid=? where strainid=?");

                ps.setString(1, designation);
                ps.setString(2, mgiid);
                ps.setString(3, emmaid);
                ps.setInt(4, pid);
                ps.setInt(5, strainid);                

                ps.execute();
            } catch (Exception e) {
                throw new EJBException("StrainBean#ejbStore: error storing strain. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    // <editor-fold defaultstate="collapsed">
    public Integer ejbFindByPrimaryKey(Integer aKey) throws FinderException{
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select strainid from strain where strainid = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("StrainBean#ejbFindByPrimaryKey: Cannot find strain [id="+strainid+"]. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("StrainBean#ejbFindByPrimaryKey: Cannot find strain [id="+strainid+"]. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return aKey;
    }
    
    public Collection ejbFindByProject(int pid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select strainid from strain where pid = ? order by strainid");
            ps.setInt(1,pid);

            
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("strainid")));
            }
        } catch (SQLException se) {
            throw new FinderException("StrainBean#ejbFindByProject: unable to find strains for project [id="+strainid+"]. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Collection ejbFindByFormDataManager(com.arexis.form.FormDataManager fdm, com.arexis.mugen.MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection strains = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        String otherid ="";
        String tmp = fdm.getValue("otherid");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            otherid = tmp;
        
        try {
            String sql = "select strainid from strain";
            
            if (!otherid.equals(""))
            {
                if(otherid.equals("EMMA")){
                    sql += " where emmaid not like '0'";
                }
                
                if(otherid.equals("MGI")){
                    sql += " where mgiid not like '0'";
                }
                
            }
            
            sql=sql+" order by designation";
                    
            ps = conn.prepareStatement(sql);
            
            result = ps.executeQuery();
            
            while(result.next()) {
                strains.add(new Integer(result.getInt("strainid")));
            }
        } catch (SQLException se) {
            throw new FinderException("StrainBean#ejbFindByFormDataManager: Cannot find strains\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return strains;
    }
    
    //</editor-fold>

    //setter+getter methods
    // <editor-fold defaultstate="collapsed">
    public int getStrainid(){
        return strainid;
    }

    public String getDesignation(){
        return designation;
    }

    public void setDesignation(String designation){
        System.out.println("setDesignation");
        this.designation = designation;
        dirty = true;
    }
    
    public int getExpModel(){
        int eid = 0;
        try{
            eid = modelHome.findByStrainID(strainid).getEid();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return eid;
    }
    
    public Collection getTypes(){
        Collection arr = null;
        try
        {
            arr = strainTypeHome.findByStrain(strainid,caller);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }
    
    public Collection getStates() {
        Collection arr = null;
        try
        {
            arr = strainStateHome.findByStrain(strainid,caller);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return arr;
    }
    
    public Collection getStrainAlleles() {
        Collection arr = new ArrayList();
        try
        {
            arr = strainAlleleHome.findByStrain(strainid, caller);
        }
        catch (Exception e)
        {
            throw new EJBException("StrainBean#getStrainAlleles: Unable to get strain alleles. \n"+e.getMessage());
        }
        return arr;
    }
    
    public String getMgiId(){
        return mgiid;
    }

    public void setMgiId(String mgiid){
        this.mgiid = mgiid;
        dirty = true;
    }
    
    public String getEmmaid(){
        return emmaid;
    }
    
    public void setEmmaid(String emmaid){
        this.emmaid = emmaid;
        dirty=true;
    }
    
    //</editor-fold>
    
    //relational methods
    // <editor-fold defaultstate="collapsed">
    
    public void addType(StrainTypeRemote type) throws RemoteException {
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("insert into r_strain_strain_type (strainid,typeid) values (?,?) ");
            ps.setInt(1, strainid);
            ps.setInt(2, type.getId());
            
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("StrainBean#addType: Unable to add type "+type.getId()+" to strain "+strainid);
        } finally {
            releaseConnection();
        }
    }
    
    public void removeType(StrainTypeRemote type) throws RemoteException {
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("delete from r_strain_strain_type where strainid=? and typeid=? ");
            ps.setInt(1, strainid);
            ps.setInt(2, type.getId());
            
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("StrainBean#removeType: Unable to remove type "+type.getId()+" from strain "+strainid);
        } finally {
            releaseConnection();
        }
    }
    
    public void addState(StrainStateRemote state) throws RemoteException {
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("insert into r_strain_strain_state (strainid,stateid) values (?,?) ");
            ps.setInt(1, strainid);
            ps.setInt(2, state.getId());
            
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("StrainBean#addState: Unable to add type "+state.getId()+" to strain "+strainid);
        } finally {
            releaseConnection();
        }
    }
    
    public void removeState(StrainStateRemote state) throws RemoteException {
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("delete from r_strain_strain_state where strainid=? and stateid=? ");
            ps.setInt(1, strainid);
            ps.setInt(2, state.getId());
            
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("StrainBean#removeState: Unable to remove state "+state.getId()+" from strain "+strainid);
        } finally {
            releaseConnection();
        }
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    // <editor-fold defaultstate="collapsed">
    public Integer ejbCreate(int strainid, String designation, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            
            this.caller = caller;
            
            this.strainid = strainid;
            this.designation = designation;
            this.pid = caller.getPid();
            
            pk = new Integer(strainid);
            
            PreparedStatement ps = conn.prepareStatement("insert into strain (strainid, designation, pid) values (?, ?, ?)");
            ps.setInt(1, strainid);
            ps.setString(2, designation);
            ps.setInt(3, pid);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("StrainBean#ejbCreate: Unable to create strain alleles. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int strainid, String designation, MugenCaller caller) throws javax.ejb.CreateException{}
    //</editor-fold>

    
}
