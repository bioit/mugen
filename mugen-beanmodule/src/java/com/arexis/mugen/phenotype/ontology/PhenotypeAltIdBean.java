package com.arexis.mugen.phenotype.ontology;

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

public class PhenotypeAltIdBean extends AbstractMugenBean implements EntityBean, PhenotypeAltIdRemoteBusiness {
    private EntityContext context;
    private int id;
    private String alt_id;
    
    private boolean dirty;
    private UserRemoteHome userHome;
    private ExpModelRemoteHome modelHome;
    private ProjectRemoteHome projectHome;
    
    //ejb infrastructure methods
    // <editor-fold>
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
            ps = conn.prepareStatement("delete from pheno_alt_id where id=?");
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("PhenotypeAltIdBean#ejbRemove: Unable to delete Phenotype Alt Id.\n"+e.getMessage());
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
            ps = conn.prepareStatement("select id, alt_id " +
                    "from pheno_alt_id where id=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                alt_id = rs.getString("alt_id");
                dirty = false;
            } else
                throw new EJBException("PhenotypeAltIdBean#ejbLoad: Error loading Phenotype Alt Id");
        } catch (Exception e) {
            throw new EJBException("PhenotypeAltIdBean#ejbLoad: error loading Phenotype Alt Id. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update pheno_alt_id set alt_id=?" +
                        "where id=?");

                ps.setString(1, alt_id);
                ps.setInt(2, id);
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("PhenotypeAltIdBean#ejbStore: error storing Phenotype Alt Id. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select id from pheno_alt_id where id = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeAltIdBean#ejbFindByPrimaryKey: Cannot find Phenotype Alt Id. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeAltIdBean#ejbFindByPrimaryKey: Cannot find Phenotype Alt Id. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    public Collection ejbFindByNoRestriction() throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select id from pheno_alt_id");
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeAltIdBean#ejbFindByNoRestriction: Cannot find alt ids \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Collection ejbFindByPhenoId(int pheno_id) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select id from pheno_alt_id where id in (select pheno_alt_id from pheno_alt_id_r where pheno_id = ?)");
            ps.setInt(1, pheno_id);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeAltIdBean#ejbFindByNoPhenoId: Cannot find alt ids \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Integer ejbFindByAltId(String alt_id) throws FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer key = null;
        try {
            ps = conn.prepareStatement("select id from pheno_alt_id where alt_id = ?");
            ps.setString(1,alt_id);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeAltIdBean#ejbFindByAltId: Cannot find Phenotype Alt ID. No next in resultset");
            }
            
            key = new Integer(result.getInt("id"));
            
        } catch (SQLException se) {
            throw new FinderException("PhenotypeAltIdBean#ejbFindByAltId: Cannot find Phenotype Alt ID. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    //<editor-fold>
    public Integer ejbCreate(int id, String alt_id) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.id = id;
            this.alt_id = alt_id;
            
            pk = new Integer(id);
            
            PreparedStatement ps = conn.prepareStatement("insert into pheno_alt_id (id, alt_id) values (?,?)");
            ps.setInt(1, id);
            ps.setString(2, alt_id);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("PhenotypeAltIdBean#ejbCreate: Unable to create Phenotype Alt Id. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int id, String alt_id) throws javax.ejb.CreateException {}
    
    //</editor-fold>
    
    //setter+getter methods
    //<editor-fold>
    public int getId(){
        return id;
    }
    
    public String getAltId(){
        return alt_id;
    }
    
    public void setAltId(String alt_id){
        this.alt_id = alt_id;
        dirty = true;
    }
    
    //</editor-fold>
}
