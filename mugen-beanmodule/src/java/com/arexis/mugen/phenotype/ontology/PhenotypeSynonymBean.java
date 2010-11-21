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

public class PhenotypeSynonymBean extends AbstractMugenBean implements EntityBean, PhenotypeSynonymRemoteBusiness {
    private EntityContext context;
    private int id;
    private String synonym;
    
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
            ps = conn.prepareStatement("delete from pheno_synonym where id=?");
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("PhenotypeSynonymBean#ejbRemove: Unable to delete Phenotype Synonym.\n"+e.getMessage());
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
            ps = conn.prepareStatement("select id, synonym " +
                    "from pheno_synonym where id=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                synonym = rs.getString("synonym");
                dirty = false;
            } else
                throw new EJBException("PhenotypeSynonymBean#ejbLoad: Error loading Phenotype Synonym");
        } catch (Exception e) {
            throw new EJBException("PhenotypeSynonymBean#ejbLoad: error loading Phenotype Synonym. \n"+e.getMessage());
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
                ps = conn.prepareStatement("update pheno_synonym set synonym=?" +
                        "where id=?");

                ps.setString(1, synonym);
                ps.setInt(2, id);
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("PhenotypeSynonymBean#ejbStore: error storing Phenotype Synonym. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select id from pheno_synonym where id = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeSynonymBean#ejbFindByPrimaryKey: Cannot find Phenotype Synonym. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeSynonymBean#ejbFindByPrimaryKey: Cannot find Phenotype Synonym. \n"+se.getMessage());
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
            ps = conn.prepareStatement("select id from pheno_synonym");
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeSynonymBean#ejbFindByNoRestriction: Cannot find synonym \n"+se.getMessage());
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
            ps = conn.prepareStatement("select id from pheno_synonym where id in (select pheno_synonym from pheno_synonym_r where pheno_id = ?)");
            ps.setInt(1, pheno_id);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("PhenotypeSynonymBean#ejbFindByPhenoId: Cannot find pheno synonyms by pheno id \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Integer ejbFindBySynonym(String synonym) throws FinderException {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer key = null;
        try {
            ps = conn.prepareStatement("select id from pheno_synonym where synonym = ?");
            ps.setString(1,synonym);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("PhenotypeSynonymBean#ejbFindBySynonym: Cannot find Phenotype Synonym. No next in resultset");
            }
            
            key = new Integer(result.getInt("id"));
            
        } catch (SQLException se) {
            throw new FinderException("PhenotypeSynonymBean#ejbFindBySynonym: Cannot find Phenotype Synonym. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return key;
    }
    
    //</editor-fold>
    
    //create+postcreate methods
    //<editor-fold>
    public Integer ejbCreate(int id, String synonym) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.id = id;
            this.synonym = synonym;
            
            pk = new Integer(id);
            
            PreparedStatement ps = conn.prepareStatement("insert into pheno_synonym (id, synonym) values (?,?)");
            ps.setInt(1, id);
            ps.setString(2, synonym);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("PhenotypeSynonymBean#ejbCreate: Unable to create Phenotype Synonym. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int id, String synonym) throws javax.ejb.CreateException {}
    
    //</editor-fold>
    
    //setter+getter methods
    //<editor-fold>
    public int getId(){
        return id;
    }
    
    public String getSynonym(){
        return synonym;
    }
    
    public void setSynonym(String synonym){
        this.synonym = synonym;
        dirty = true;
    }
    //</editor-fold>
}
