package com.arexis.mugen.model.strain.allele;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemote;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemoteHome;
import com.arexis.mugen.model.strain.strain.StrainRemote;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.species.gene.GeneRemoteHome;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.search.Keyword;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

public class StrainAlleleBean extends AbstractMugenBean implements EntityBean, StrainAlleleRemoteBusiness {
    private EntityContext context;
    
    private int id;
    private String mgiid;
    private String symbol;
    private String name;
    private String attributes;
    
    private int gene;
    private int strainid;
    
    private boolean dirty;
    
    private MutationTypeRemoteHome mutationTypeHome;
    private GeneRemoteHome geneHome;
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    public void setEntityContext(EntityContext aContext) {
        context = aContext;
        mutationTypeHome = (MutationTypeRemoteHome)locator.getHome(ServiceLocator.Services.MUTATION_TYPE);
        geneHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
    }
    
    public void ejbActivate() {}
    
    public void ejbPassivate() {}
    
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from strain_allele where id=?");
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("StrainAlleleBean#ejbRemove: Unable to delete strain allele. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select id,mgiid,name,symbol,gene,strainid, attributes " +
                    "from strain_allele where id=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                id = rs.getInt("id");
                mgiid = rs.getString("mgiid");
                name = rs.getString("name");
                symbol = rs.getString("symbol");
                gene = rs.getInt("gene");
                strainid = rs.getInt("strainid");
                attributes = rs.getString("attributes");
                dirty = false;
            } else
                throw new EJBException("StrainAlleleBean#ejbLoad: Error loading strain allele");
        } catch (Exception e) {
            throw new EJBException("StrainAlleleBean#ejbLoad: error loading strain allele. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void ejbStore() {
        if (dirty) {
            makeConnection();
            PreparedStatement ps = null;
            try
            {
                ps = conn.prepareStatement("update strain_allele set name=?,symbol=?,mgiid=?,gene=?, attributes=? where id=?");
                
                ps.setString(1, name);
                ps.setString(2, symbol);
                ps.setString(3, mgiid);
                if (gene!=0)
                    ps.setInt(4, gene);
                else
                    ps.setNull(4, java.sql.Types.INTEGER);
                ps.setString(5, attributes);
                ps.setInt(6, id);
                
                ps.execute();
            } catch (Exception e) {
                throw new EJBException("StrainAlleleBean#ejbStore: error storing strain allele. \n"+e.getMessage());
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
            ps = conn.prepareStatement("select id from strain_allele where id = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("StrainAlleleBean#ejbFindByPrimaryKey: Cannot find strain allele. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("StrainAlleleBean#ejbFindByPrimaryKey: Cannot find strain allele. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return aKey;
    }
    
    public Collection ejbFindByStrain(int strainid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select id from strain_allele where strainid = ? order by id");
            ps.setInt(1,strainid);

            
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("StrainAlleleBean#ejbFindByStrain: unable to find Strain Alleles. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        int id = 0;
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select id from strain_allele where "
                    +"(lower(symbol) like ? "+
                    "or lower(name) like ? "+
                    "or lower(mgiid) like ? )");
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            
            result = ps.executeQuery();
            
            
            while (result.next()) {
                id = result.getInt("id");
                if(alleleSecurityControl(id, caller))
                    arr.add(new Integer(id));
            }
            
        } catch (SQLException se) {
            throw new FinderException("StrainAlleleBean#ejbFindByKeyword: Cannot find allele by keyword\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Collection ejbFindByGene(int gaid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        int id = 0;
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select id from strain_allele where gene = ?");
            ps.setInt(1, gaid);
            result = ps.executeQuery();
            
            while (result.next()) {
                id = result.getInt("id");
                if(alleleSecurityControl(id, caller))
                    arr.add(new Integer(id));
            }
        } catch (SQLException se) {
            throw new FinderException("StrainAlleleBean#ejbFindByGene: unable to find Strain Alleles. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    //</editor-fold>
    
    //misc methods
    // <editor-fold defaultstate="collapsed">
    
    public boolean alleleSecurityControl(int id, MugenCaller caller) throws javax.ejb.FinderException {
        
        boolean check = false;
        int level = 0;
        
        if (caller.hasPrivilege("MODEL_ADM")) {
            level = 2;
        } else if (caller.hasPrivilege("MODEL_MUGEN")) {
            level = 1;
        }
        
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select level from model where strain in (select strainid from strain_allele where id = ?)");
            ps.setInt(1,id);
            result = ps.executeQuery();
            
            while (result.next()) {
                if(result.getInt("level")<=level)
                    check = true;
            }
        } catch (SQLException se) {
            throw new FinderException("StrainAlleleBean#alleleSecurityControl: Cannot get allele's security level \n"+se.getMessage());
        }
        return check;
    }
    
    //</editor-fold>
    
    //setter+getter methods
    // <editor-fold defaultstate="collapsed">
    
    public int getId() {
        return id;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
        dirty = true;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        dirty = true;
    }

    public String getMgiId() {
        return mgiid;
    }

    public void setMgiId(String imsrid) {
        this.mgiid = imsrid;
        dirty = true;
    }
    
    public Collection getMutationTypes() {
        try {
            return mutationTypeHome.findByStrainAllele(id, caller);
        } catch (Exception e) {
            throw new EJBException("StrainAlleleBean#getMutationType: Unable to get mutation types. \n"+e.getMessage());
        }
    }

    public GeneRemote getGene() {
        try {   
            if (gene!=0)
                return geneHome.findByPrimaryKey(new Integer(gene));
            else
                return null;
        } catch (Exception e) {
            throw new EJBException("StrainAlleleBean#getGene: Unable to get gene. \n"+e.getMessage());
        }
    }
    
    public void setGene(GeneRemote gene) {
        try {   
            this.gene = gene.getGaid();
            dirty = true;
        } catch (Exception e) {
            throw new EJBException("StrainAlleleBean#getGene: Unable to get gene. \n"+e.getMessage());
        }
    }
    
    public String getAttributes() {
        if(attributes==null || attributes.length()==0){
            attributes = "N/A";
        }
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
        dirty = true;
    }
    
    //</editor-fold>

    //relational methods
    // <editor-fold defaultstate="collapsed">
    
    public void addMutationType(MutationTypeRemote mutationType) throws ApplicationException {
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("insert into R_MUTATION_TYPE_STRAIN_ALLELE (mutationtype,strainallele) values (?,?) ");
            ps.setInt(1, mutationType.getMutantid());
            ps.setInt(2, id);
            
            ps.execute();
        } catch (Exception e) {
            throw new ApplicationException("StrainAlleleBean#addMutationType: Unable to add type to strain allele "+id,e);
        } finally {
            releaseConnection();
        }
    }
    
    public void removeMutationType(MutationTypeRemote mutationType) throws ApplicationException {
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("delete from R_MUTATION_TYPE_STRAIN_ALLELE where mutationtype=? and strainallele=?");
            ps.setInt(1, mutationType.getMutantid());
            ps.setInt(2, id);
            
            ps.execute();
        } catch (Exception e) {
            throw new ApplicationException("StrainAlleleBean#removeMutationType: Unable to remove type from strain allele "+id,e);
        } finally {
            releaseConnection();
        }
    }
    
    public void setGeneToNULL(int strainalleleid) throws ApplicationException {
        makeConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("update strain_allele set gene=? where id=?");
            ps.setObject(1,null);
            ps.setInt(2, strainalleleid);
            ps.execute();
            System.out.println("setGeneToNULL executed query with strainalleleid value --> "+strainalleleid);
        } catch (Exception e) {
            throw new ApplicationException("StrainAlleleBean#setGeneToNULL: Unable to set gene to NULL for this allele ",e);
        } finally {
            releaseConnection();
        }
    }
    //</editor-fold>
    
    //create+postcreate methods
    //<editor-fold defaultstate="collapsed">
    public Integer ejbCreate(int id, String symbol, String name, StrainRemote strain, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            
            this.caller = caller;
            
            this.id = id;
            this.name = name;
            this.symbol = symbol;
            
            pk = new Integer(id);
            
            PreparedStatement ps = conn.prepareStatement("insert into strain_allele (id, name, symbol, strainid) values (?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, symbol);
            ps.setInt(4, strain.getStrainid());
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("StrainAlleleBean#ejbCreate: Unable to create strain allele. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int id, String symbol, String name, StrainRemote strain, MugenCaller caller) throws javax.ejb.CreateException {}
    //</editor-fold>
    
}
