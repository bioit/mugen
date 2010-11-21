package com.arexis.mugen.model.strain.type;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.AbstractMugenBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the StrainTypeBean enterprise bean.
 * Created Jul 5, 2006 9:50:41 AM
 * @author heto
 */
public class StrainTypeBean extends AbstractMugenBean implements EntityBean, StrainTypeRemoteBusiness
{
    private EntityContext context;
    
    private int id, pid;
    private String name;
    private String abbreviation;
    
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
        try {
            ps = conn.prepareStatement("delete from strain_type where id=?");
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e) {
            throw new EJBException("StrainTypeBean#ejbRemove: Unable to delete strain type. \n"+e.getMessage());
        } finally {
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
        try {
            ps = conn.prepareStatement("select id,name,abbreviation, pid " +
                    "from strain_type where id=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                abbreviation = rs.getString("abbreviation");
                pid = rs.getInt("pid");
                dirty = false;
            } else
                throw new EJBException("StrainTypeBean#ejbLoad: Error loading strain type");
        } catch (Exception e) {
            throw new EJBException("StrainTypeBean#ejbLoad: error loading strain type. \n"+e.getMessage());
        } finally {
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
            try {
                ps = conn.prepareStatement("update strain_type set name=?,abbreviation=? where id=?");

                ps.setString(1, name);
                ps.setString(2, abbreviation);
                ps.setInt(3, id);

                ps.execute();
            } catch (Exception e) {
                throw new EJBException("StrainTypeBean#ejbStore: error storing strain type. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }
    
    // </editor-fold>
    
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     */
    public Integer ejbFindByPrimaryKey(Integer aKey) throws FinderException
    {
        makeConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select id from strain_type where id = ?");
            ps.setInt(1,aKey.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("StrainTypeBean#ejbFindByPrimaryKey: Cannot find strain type. No next in resultset");
            }
        } catch (SQLException se) {
            throw new FinderException("StrainTypeBean#ejbFindByPrimaryKey: Cannot find strain type. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return aKey;
    }

    public int getId()
    {
        return id;
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

    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
        dirty = true;
    }
    
    public void setCaller(MugenCaller caller)
    {
        this.caller = caller;
    }

    public Integer ejbCreate(int id, String name, String abbreviation, MugenCaller caller) throws javax.ejb.CreateException
    {
        makeConnection();
        Integer pk = null;
        try {
            
            this.caller = caller;
            
            this.id = id;
            this.name = name;
            this.abbreviation = abbreviation;
            
            pk = new Integer(id);
            
            PreparedStatement ps = conn.prepareStatement("insert into strain_type (id, name, abbreviation, pid) values (?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, abbreviation);
            ps.setInt(4, caller.getPid());
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("StrainTypeBean#ejbCreate: Unable to create strain type. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int id, String name, String abbreviation, MugenCaller caller) throws javax.ejb.CreateException
    {
        //TODO implement ejbPostCreate
    }

    public Collection ejbFindByProject(int pid, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select id from strain_type where pid = ? order by id");
            ps.setInt(1,pid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("StrainTypeBean#ejbFindByProject: unable to find strain types for project. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    public Collection ejbFindByStrain(int strainid, MugenCaller caller) throws javax.ejb.FinderException
    {
        makeConnection();
        this.caller = caller;
        Collection arr = new ArrayList();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {

            ps = conn.prepareStatement("select id from strain_type st, r_strain_strain_type r where st.id = r.typeid and r.strainid = ? order by id");
            ps.setInt(1,strainid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("id")));
            }
        } catch (SQLException se) {
            throw new FinderException("StrainTypeBean#ejbFindByStrain: unable to find strain types for strain. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
}
