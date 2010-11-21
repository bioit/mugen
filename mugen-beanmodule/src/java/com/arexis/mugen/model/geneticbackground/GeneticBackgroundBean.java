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

public class GeneticBackgroundBean extends AbstractMugenBean implements EntityBean, GeneticBackgroundRemoteBusiness {
    private EntityContext context;
    
    private int gbid, eid, dna_origin, targeted_back, host_back, backcrossing_strain;//, backcrosses;
    private String backcrosses;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    private GeneticBackgroundValuesRemoteHome genbackValuesHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        modelHome = (ExpModelRemoteHome)locator.getHome(ServiceLocator.Services.EXPMODEL);
        projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
        genbackValuesHome = (GeneticBackgroundValuesRemoteHome)locator.getHome(ServiceLocator.Services.GENETIC_BACKGROUND_VALUES);
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from genetic_back where gbid=?");
            ps.setInt(1, gbid);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("GeneticBackgroundBean#ejbRemove: Unable to delete Genetic Background Info for model. "+eid+" \n"+e.getMessage());
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
            ps = conn.prepareStatement("select gbid, eid, dna_origin, targeted_back, host_back, backcrossing_strain, backcrosses " +
                    "from genetic_back where gbid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                gbid = rs.getInt("gbid");
                eid = rs.getInt("eid");
                dna_origin = rs.getInt("dna_origin");
                targeted_back = rs.getInt("targeted_back");
                host_back = rs.getInt("host_back");
                backcrossing_strain = rs.getInt("backcrossing_strain");
                backcrosses = rs.getString("backcrosses");
                dirty = false;
            } else
                throw new EJBException("GeneticBackgroundBean#ejbLoad: Error loading Genetic Background Info tuple");
        } catch (Exception e) {
            throw new EJBException("GeneticBackgroundBean#ejbLoad: error loading Genetic Background Info tuple for model. "+eid+" \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void ejbStore() {        
        if (dirty) {
            makeConnection();
            PreparedStatement ps = null;
            try {
                ps = conn.prepareStatement("update genetic_back set eid=?, dna_origin=?,targeted_back=?,host_back=?, backcrossing_strain=?, backcrosses=? " +
                        "where gbid=?");
                ps.setInt(1, eid);
                ps.setInt(2, dna_origin);
                ps.setInt(3, targeted_back);
                ps.setInt(4, host_back);
                ps.setInt(5, backcrossing_strain);
                ps.setString(6, backcrosses);
                ps.setInt(7, gbid);
               
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("GeneticBackgroundBean#ejbStore: error storing Genetin Background Information for model. "+eid+" \n"+e.getMessage());
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
            ps = conn.prepareStatement("select gbid from genetic_back where gbid = ?");
            ps.setInt(1, aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("GeneticBackgroundBean#ejbFindByPrimaryKey: Cannot find Genetic Background Information. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("GeneticBackgroundBean#ejbFindByPrimaryKey: Cannot find Genetic Background Information. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return aKey;
    }
    
    public Collection ejbFindByGeneticBackgroundModel(int eid) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select gbid from genetic_back where eid = ? order by eid");
            ps.setInt(1, eid);
            result = ps.executeQuery();
            while (result.next()) {
                //add the gbid of 'every tuple' to collection arr
                arr.add(new Integer(result.getInt("gbid")));
            }
        } catch (SQLException se) {
            throw new FinderException("GeneticBackgroundBean#ejbFindByProject: Cannot find genetic background information for model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    //</editor-fold>

    //setter+getter methods
    //<editor-fold defaultstate="collapsed">
    public int getGbid() {
        return gbid;
    }
    
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
        dirty = true;
    }

    public int getDna_origin() {
        return dna_origin;
    }

    public void setDna_origin(int dna_origin) {
        this.dna_origin = dna_origin;
        dirty = true;
    }

    public int getTargeted_back() {
        return targeted_back;
    }

    public void setTargeted_back(int targeted_back) {
        this.targeted_back = targeted_back;
        dirty = true;
    }

    public int getHost_back() {
        return host_back;
    }

    public void setHost_back(int host_back) {
        this.host_back = host_back;
        dirty = true;
    }

    public int getBackcrossing_strain() {
        return backcrossing_strain;
    }

    public void setBackcrossing_strain(int backcrossing_strain) {
        this.backcrossing_strain = backcrossing_strain;
        dirty = true;
    }

    public String getBackcrosses() {
        return backcrosses;
    }

    public void setBackcrosses(String backcrosses) {
        this.backcrosses = backcrosses;
        dirty = true;
    }
    
    public String getBackNameFromBackId(int backId){
        try {
            GeneticBackgroundValuesRemote genBackValues = genbackValuesHome.findByPrimaryKey(new Integer(backId));
            String backId_name = genBackValues.getBackname();
            return backId_name;
            
        } catch (Exception e) {
            throw new EJBException("Could not get background name");
        }
    }

    //</editor-fold>
    
    //create+postcreate methods.
    //<editor-fold defaultstate="collapsed">
    public Integer ejbCreate(int gbid, int eid, int dna_origin, int targeted_back, int host_back, int backcrossing_strain, String backcrosses) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.gbid = gbid;
            this.eid = eid;
            this.dna_origin = dna_origin;
            this.targeted_back = targeted_back;
            this.host_back = host_back;
            this.backcrossing_strain = backcrossing_strain;
            this.backcrosses = backcrosses;
            
            pk = new Integer(gbid);
            PreparedStatement ps = conn.prepareStatement("insert into genetic_back (gbid,eid,dna_origin,targeted_back,host_back,backcrossing_strain,backcrosses) values (?,?,?,?,?,?,?)");
            ps.setInt(1, gbid);
            ps.setInt(2, eid);
            ps.setInt(3, dna_origin);
            ps.setInt(4, targeted_back);
            ps.setInt(5, host_back);
            ps.setInt(6, backcrossing_strain);
            ps.setString(7, backcrosses);
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("GeneticBackgroundBean#ejbCreate: Unable to create Genetic Background Tuble for model."+eid+" \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int gbid,int eid, int dna_origin, int targeted_back, int host_back, int backcrossing_strain, String backcrosses) throws javax.ejb.CreateException {}
    //</editor-fold>
    
}
