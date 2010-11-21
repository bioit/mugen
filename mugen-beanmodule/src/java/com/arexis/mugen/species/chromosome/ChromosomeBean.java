package com.arexis.mugen.species.chromosome;

import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.*;

/**
 * This is the bean class for the ChromosomeBean enterprise bean.
 * Created Jun 13, 2005 10:05:10 AM
 * @author lami
 * @todo Additional findBy's() required? Database relations?
 */
public class ChromosomeBean extends AbstractMugenBean implements javax.ejb.EntityBean, com.arexis.mugen.species.chromosome.ChromosomeRemoteBusiness {
    private javax.ejb.EntityContext context;
    private int cid, sid;
    private String abbr;
    private String name;
    private String comm;
    private boolean dirty;
    
    private SpeciesRemoteHome speciesHome;
    private UserRemoteHome userHome;    
    private ChromosomeRemoteHome chromosomeHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise beans, Web services)
    // TODO Add business methods
    // TODO Add create methods
    /**
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);        
        speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
        chromosomeHome = (ChromosomeRemoteHome)locator.getHome(ServiceLocator.Services.CHROMOSOME);
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbActivate()
     */
    public void ejbActivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbPassivate()
     */
    public void ejbPassivate() {
        
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbRemove()
     */
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("delete from chromosomes where cid = ?");
            ps.setInt(1, cid);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            
            throw new EJBException("ChromosomeBean#ejbRemove: Internal error. Failed to delete chromsome\n(" +
                    e.getMessage() + ")");
        } finally {
            releaseConnection();
        }     
    }
    
    /**
     * @see javax.ejb.EntityBean#unsetEntityContext()
     */
    public void unsetEntityContext() {
        context = null;
    }
    
    /**
     * @see javax.ejb.EntityBean#ejbLoad()
     */
    public void ejbLoad() {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        Integer pk = (Integer)context.getPrimaryKey();
        try {
            ps = conn.prepareStatement("select name,abbr,comm,sid from chromosomes where cid = ?");
            ps.setInt(1,pk.intValue());
            
            result = ps.executeQuery();
            
            if (result.next()) {
                cid = pk.intValue();
                sid = result.getInt("sid");
                name = result.getString("name");
                abbr = result.getString("abbr");
                comm = result.getString("comm");
                dirty = false;
            } else
                throw new EJBException("ChromosomeBean#ejbLoad: Failed to load bean (no resultset?)");
        } catch (SQLException se) {
            throw new EJBException("ChromosomeBean#ejbLoad: Failed to load bean", se);
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
            try {
                PreparedStatement ps = null;
                ps = conn.prepareStatement("update Chromosomes set name = ?, abbr = ?, comm = ?, sid = ? where cid = ?");

                ps.setString(1, name);
                ps.setString(2, abbr);
                ps.setString(3, comm); 
                ps.setInt(4, sid);
                ps.setInt(5, cid);

                ps.execute();
            } catch (Exception e) {
                e.printStackTrace();
                throw new EJBException("ChromosomeBean#ejbStore: Error updating Chromosome ["+cid+"]");
            } finally {
                releaseConnection();
                dirty = false;
            }        
        }
    }
    
    // </editor-fold>
   
    /**
     * See EJB 2.0 and EJB 2.1 section 12.2.5
     * 
     * Locates a chromosome by its primary key.
     * @return an Integer primary key
     * @param key The chromosome key
     * @throws FinderException If no chromosome is found
     * @throws RemoteException If a Bean error occurs
     */
    public java.lang.Integer ejbFindByPrimaryKey(Integer key) throws javax.ejb.FinderException, java.rmi.RemoteException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select cid from chromosomes where cid = ?");
            ps.setInt(1,key.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("ChromosomeBean#ejbFindByPrimaryKey: Cannot find chromosome");
            }
        } catch (SQLException se) {
            throw new EJBException("ChromosomeBean#ejbFindByPrimaryKey: SQL exception thrown", se);
        } finally {
            releaseConnection();
        }                
        
        return key;     
    }
    
    /**
     * Locates a chromosome by its name and species id
     * @param name The chromosome name
     * @param sid The species id
     * @return The chromosome id if a matching chromosome was found
     * @throws FinderException If no match could be found
     */
    public java.lang.Integer ejbFindBySpeciesAndName(java.lang.String name, int sid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select cid from chromosomes where sid = ? and name = ?");
            ps.setString(2, name);
            ps.setInt(1,sid);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("ChromosomeBean#ejbFindBySpeciesAndName: Cannot find chromosome by name and species id");
            }
            else
            {
                cid = result.getInt("cid");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new FinderException("ChromosomeBean#ejbFindBySpeciesAndName: Cannot find chromosome by name and species id");
        } finally {
            releaseConnection();
        }

        return new Integer(cid);
    }    
    
    /**
     * Create method for the Chromosome bean.
     * @param cid a chromosome identifier
     * @param name a chromosome name
     * @param comm a comment
     * @return the chromosome id
     * @param sid The species id for the chromosome
     * @throws CreateException If there was a failure to create the chromosome
     * @throws RemoteException If there was an error when creating the chromosome
     */
    public java.lang.Integer ejbCreate(int cid, String name, String abbr, String comm, int sid) throws javax.ejb.CreateException, java.rmi.RemoteException {
        this.cid = cid;
        this.name = name;
        this.comm = comm;
        this.sid = sid;
        makeConnection();
        
        try {    
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into Chromosomes (cid,name,comm,sid) values (?,?,?,?)");
            ps.setInt(1, cid);
            ps.setString(2, name);
            ps.setString(3, comm);
            ps.setInt(4, sid);
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {                           
            throw new CreateException("ChromosomeBean#ejbCreate: Unable to create chromosome: "+e.getMessage());
        } finally {
            releaseConnection();    
        }

        return new Integer(cid);        
    }
    
    /**
     * 
     * @param cid 
     * @param name 
     * @param comm 
     * @param sid 
     * @throws javax.ejb.CreateException 
     * @throws java.rmi.RemoteException 
     */
    public void ejbPostCreate(int cid, String name, String abbr, String comm, int sid) throws javax.ejb.CreateException, java.rmi.RemoteException{
    }
   
    /**
     * Method for getting the chromosome name
     * @return chromosome name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Method for setting the chromosome name
     * @param name Sets the chromosome name
     */
    public void setName(java.lang.String name) {
        this.name = name;
        dirty = true;
    }
    
    /* 
     * Get the abbreviation of the chromosome
     */
    public String getAbbr() {
        return abbr;
    }

    /* 
     * Set the abbreviation of the chromosome
     */
    public void setAbbr(String abbr) {
        this.abbr = abbr;
        dirty = true;
    }
    
   /**
    * Method for getting the chromosome id.
    * @return the chromosome id (cid)
    */
    public int getCid(){
        return cid;
    }
    
    /**
     * Method for getting the chromosome comment
     * @return the comment
     */
    public String getComm(){
        return comm;
    }

    /**
     * Returns the species for this chromosome
     * @return The species
     */
    public SpeciesRemote getSpecies() {
        SpeciesRemote s = null;
        try {
                        
            s = speciesHome.findByPrimaryKey(new Integer(sid));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return s;
    }
    
    /**
     * Method for setting the chromosome comment
     * @param comm Sets the comment for the chromosome
     */
    public void setComm(java.lang.String comm){
        this.comm = comm;
        dirty = true;
    }

    public Collection ejbFindBySpecies(int sid, MugenCaller caller) throws javax.ejb.FinderException
    {
        Collection chromosomes = new ArrayList();

        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select cid from chromosomes where sid = ? order by cid");
            ps.setInt(1, sid);
            result = ps.executeQuery();
            
            while (result.next()) {
                chromosomes.add(new Integer(result.getInt("cid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ChromosomeBean#ejbFindChromosomes: unable to find chromosomes. \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
      
        return chromosomes;
    }
}
