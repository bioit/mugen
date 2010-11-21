package com.arexis.mugen.model.strain.mutationtype;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.AbstractMugenBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the MutationTypeBean enterprise bean.
 * Created Jul 5, 2006 11:34:25 AM
 * @author heto
 */
public class MutationTypeBean extends AbstractMugenBean implements EntityBean, MutationTypeRemoteBusiness
{
    private EntityContext context;
    
    private int id;
    private String abbreviation;
    private String name;
    
    private boolean dirty;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(EntityContext aContext)
    {
        context = aContext;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbActivate()
     */
    public void ejbActivate()
    {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbPassivate()
     */
    public void ejbPassivate()
    {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbRemove()
     */
    public void ejbRemove()
    {
        makeConnection();
        PreparedStatement ps = null;
        try
        {
            ps = conn.prepareStatement("delete from mutation_type where id=?");
            ps.setInt(1, id);
            ps.execute();
        }
        catch (Exception e)
        {
            throw new EJBException("MutationTypeBean#ejbRemove: Unable to delete mutation type. \n"+e.getMessage());
        }
        finally
        {
            releaseConnection();
        }
    }
    
    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext()
    {
        context = null;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad()
    {
        makeConnection();
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try
        {
            ps = conn.prepareStatement("select id,abbreviation,name " +
                    "from mutation_type where id=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next())
            {
                id = rs.getInt("id");
                abbreviation = rs.getString("abbreviation");
                name = rs.getString("name");
                dirty = false;
            }
            else
                throw new EJBException("MutationTypeBean#ejbLoad: Error loading mutation type");
        }
        catch (Exception e)
        {
            throw new EJBException("MutationTypeBean#ejbLoad: error loading mutation type. \n"+e.getMessage());
        }
        finally
        {
            releaseConnection();
        }
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbStore()
     */
    public void ejbStore()
    {
        if (dirty)
        {
            makeConnection();
            PreparedStatement ps = null;
            try
            {
                ps = conn.prepareStatement("update mutation_type set name=?,abbreviation=? where id=?");
                
                ps.setString(1, name);
                ps.setString(2, abbreviation);
                
                ps.setInt(3, id);
                
                ps.execute();
            }
            catch (Exception e)
            {
                throw new EJBException("MutationTypeBean#ejbStore: error storing mutation type. \n"+e.getMessage());
            }
            finally
            {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    //finder methods
    //<editor-fold>
    
    public Integer ejbFindByPrimaryKey(Integer aKey) throws FinderException
    {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try
        {
            ps = conn.prepareStatement("select id from mutation_type where id = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next())
            {
                throw new ObjectNotFoundException("MutationTypeBean#ejbFindByPrimaryKey: Cannot find mutation type. No next in resultset");
            }
        }
        catch (SQLException se)
        {
            throw new FinderException("MutationTypeBean#ejbFindByPrimaryKey: Cannot find mutation type. \n"+se.getMessage());
        }
        finally
        {
            releaseConnection();
        }
        return aKey;
    }
    
    public Collection ejbFindByProject(int pid, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select id from mutation_type where pid = ? order by id");
            ps.setInt(1,pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("MutationTypeBean#ejbFindByProject: unable to find mutation types for project. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    public Collection ejbFindByStrainAllele(int id, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            
            ps = conn.prepareStatement("select mutationtype from R_MUTATION_TYPE_STRAIN_ALLELE where strainallele = ? order by mutationtype");
            ps.setInt(1,id);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("mutationtype")));
            }
        } catch (SQLException se) {
            throw new FinderException("MutationTypeBean#ejbFindByStrainAllele: unable to find mutation types for strain allele. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public Collection ejbFindByStrainAlleleUnassignment(int strainalleleid, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select id from mutation_type where id not in (select mutationtype from r_mutation_type_strain_allele where strainallele=?)");
            ps.setInt(1,strainalleleid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("MutationTypeBean#ejbFindByStrainAlleleUnassignment: unable to find mutation types for this strainallele. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    //</editor-fold>

    //setter+getter methods
    //<editor-fold>
    /**
     * 
     * @deprecated Use getId() instead.
     */
    public int getMutantid()
    {
        return id;
    }
    
    public int getId()
    {
        return id;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
        dirty = true;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        dirty = true;
    }
    
    //</editor-fold>
    
}
