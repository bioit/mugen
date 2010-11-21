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

/**
 * This is the bean class for the PhenotypeXrefBean enterprise bean.
 * Created 23 Ιαν 2007 1:19:55 μμ
 * @author zouberakis
 */
public class PhenotypeXrefBean extends AbstractMugenBean implements EntityBean, PhenotypeXrefRemoteBusiness {
    private EntityContext context;
    private int id;
    private String xref;
    
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
            ps = conn.prepareStatement("delete from pheno_xref where id=?");
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("PhenotypeXrefBean#ejbRemove: Unable to delete Phenotype Xref.\n"+e.getMessage());
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
            ps = conn.prepareStatement("select id, xref " +
                    "from pheno_xref where id=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                xref = rs.getString("xref");
                dirty = false;
            } else
                throw new EJBException("PhenotypeXrefBean#ejbLoad: Error loading Phenotype Xref");
        } catch (Exception e) {
            throw new EJBException("PhenotypeXrefBean#ejbLoad: error loading Phenotype Xref. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update pheno_xref set xref=?" +
                        "where id=?");

                ps.setString(1, xref);
                ps.setInt(2, id);
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("PhenotypeXrefBean#ejbStore: error storing Phenotype Xref. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select id from pheno_xref where id = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeXrefBean#ejbFindByPrimaryKey: Cannot find Phenotype Xref. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeXrefBean#ejbFindByPrimaryKey: Cannot find Phenotype Xref. \n"+se.getMessage());
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
            ps = conn.prepareStatement("select id from pheno_xref");
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeXrefBean#ejbFindByNoRestriction: Cannot find xrefs \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Integer ejbFindByXrefName(String xref) throws FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer key = null;
        try {
            ps = conn.prepareStatement("select id from pheno_xref where xref = ?");
            ps.setString(1,xref);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeXrefBean#ejbFindByXrefName: Cannot find Phenotype Xref by Xref name. No next in resultset");
            }
            
            key = new Integer(result.getInt("id"));
            
        } catch (SQLException se) {
            throw new FinderException("PhenotypeXrefBean#ejbFindByXrefName: Cannot find Phenotype Xref by Xref name. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    //<editor-fold>
    public Integer ejbCreate(int id, String xref) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.id = id;
            this.xref = xref;
            
            pk = new Integer(id);
            
            PreparedStatement ps = conn.prepareStatement("insert into pheno_xref (id, xref) values (?,?)");
            ps.setInt(1, id);
            ps.setString(2, xref);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("PhenotypeXrefBean#ejbCreate: Unable to create Phenotype Xref. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int id, String xref) throws javax.ejb.CreateException {}
    
    //</editor-fold>
    
    //setter+getter methods
    //<editor-fold>
    public int getId(){
        return id;
    }
    
    public String getXref(){
        return xref;
    }
    
    public void setXref(String xref){
        this.xref = xref;
        dirty = true;
    }
    
    //</editor-fold>
}
