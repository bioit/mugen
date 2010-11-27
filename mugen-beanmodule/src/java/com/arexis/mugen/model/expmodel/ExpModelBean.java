package com.arexis.mugen.model.expmodel;

import com.arexis.form.FormDataManager;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.PermissionDeniedException;
import com.arexis.mugen.model.functionalsignificance.FunctionalSignificanceRemoteHome;
import com.arexis.mugen.model.modelmanager.StrainAlleleDTO;
import com.arexis.mugen.species.gene.GeneRemoteHome;
import com.arexis.mugen.model.geneticmodification.GeneticModificationRemoteHome;
import com.arexis.mugen.model.reference.ReferenceRemote;
import com.arexis.mugen.model.reference.ReferenceRemoteHome;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemote;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemoteHome;

import com.arexis.mugen.model.strain.strain.StrainRemote;
import com.arexis.mugen.model.strain.strain.StrainRemoteHome;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemote;
import com.arexis.mugen.model.strain.allele.StrainAlleleRemoteHome;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemote;
import com.arexis.mugen.model.strain.mutationtype.MutationTypeRemoteHome;

import com.arexis.mugen.model.availability.AvailabilityRemote;
import com.arexis.mugen.model.availability.AvailabilityRemoteHome;
import com.arexis.mugen.model.modelmanager.AvailabilityDTO;

import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyPathRemote;

import com.arexis.mugen.model.geneticbackground.GeneticBackgroundRemote;
import com.arexis.mugen.model.geneticbackground.GeneticBackgroundRemoteHome;
import com.arexis.mugen.model.modelmanager.GeneticBackgroundDTO;

import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.resource.file.FileRemote;
import com.arexis.mugen.resource.file.FileRemoteHome;
import com.arexis.mugen.resource.resource.ResourceRemote;
import com.arexis.mugen.resource.resource.ResourceRemoteHome;
import com.arexis.mugen.samplingunit.expobj.ExpObj;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.search.Keyword;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.gene.GeneRemote;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;

public class ExpModelBean extends ExpObj implements javax.ejb.EntityBean, com.arexis.mugen.model.expmodel.ExpModelRemoteBusiness {
    
    //private params
    // <editor-fold defaultstate="collapsed">
    private javax.ejb.EntityContext context;
    private String researchApplicationText, availability, geneticBackground, groupName;
    
//    private static Logger logger = Logger.getLogger(ExpModelBean.class);
    
    private int level;
    
    private int desired_level;
    
    private int MutationDistinctionParameter=0;
    
    private int researchApplication, contact, genotyping, handling, strain;
    
    private GeneticModificationRemoteHome geneticModificationHome;
    
    private ResearchApplicationRemoteHome researchApplicationHome;
    
    private GeneticBackgroundRemoteHome genbackHome;
    
    private FileRemoteHome fileHome;
    private FunctionalSignificanceRemoteHome functionalSignificanceHome;
    private GeneRemoteHome geneHome;
    private ReferenceRemoteHome referenceHome;
    private StrainRemoteHome strainHome;
    private StrainAlleleRemoteHome alleleHome;
    private MutationTypeRemoteHome mutationHome;
    private UserRemoteHome _userHome;
    private ResourceRemoteHome resourceHome;
    private AvailabilityRemoteHome availabilityHome;
    //</editor-fold>
    
    //ejb methods
    // <editor-fold defaultstate="collapsed">
    
    public void setEntityContext(javax.ejb.EntityContext aContext) {
        context = aContext;
        geneticModificationHome = (GeneticModificationRemoteHome)locator.getHome(ServiceLocator.Services.GENETICMODIFICATION);
        researchApplicationHome = (ResearchApplicationRemoteHome)locator.getHome(ServiceLocator.Services.RESEARCHAPPLICATION);
        fileHome = (FileRemoteHome)locator.getHome(ServiceLocator.Services.FILE);
        functionalSignificanceHome = (FunctionalSignificanceRemoteHome)locator.getHome(ServiceLocator.Services.FUNCTIONALSIGNIFICANCE);
        geneHome = (GeneRemoteHome)locator.getHome(ServiceLocator.Services.GENE);
        referenceHome = (ReferenceRemoteHome)locator.getHome(ServiceLocator.Services.REFERENCE);
        strainHome = (StrainRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN);
        alleleHome = (StrainAlleleRemoteHome)locator.getHome(ServiceLocator.Services.STRAIN_ALLELE);
        mutationHome = (MutationTypeRemoteHome)locator.getHome(ServiceLocator.Services.MUTATION_TYPE);
        
        _userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        resourceHome = (ResourceRemoteHome)locator.getHome(ServiceLocator.Services.RESOURCE);
        availabilityHome = (AvailabilityRemoteHome)locator.getHome(ServiceLocator.Services.AVAILABILITY);
        genbackHome = (GeneticBackgroundRemoteHome)locator.getHome(ServiceLocator.Services.GENETIC_BACKGROUND);
    }
    
    public void ejbActivate() {
        
    }
    
    public void ejbPassivate() {
        
    }
    
    public void ejbRemove() {
        makeConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from model where eid=?");
            ps.setInt(1, eid);
            ps.execute();
            super.remove();
        } catch (Exception e) {
            throw new EJBException("ExpModelBean#ebjRemove: Unable to remove exp model. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void unsetEntityContext(){
        context = null;
    }
    
    public void ejbLoad() {
        makeConnection();
        Integer pk = (Integer)context.getPrimaryKey();
        PreparedStatement ps = null;
        try {
            
            /** Load common things */
            super.load(pk.intValue());
            
            
            ps = conn.prepareStatement("select eid,background,availability,contact, application, apptext, genotyping, handling, strain, level, desired_level " +
                    "from model where eid=?");
            ps.setInt(1, pk.intValue());
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                eid = rs.getInt("eid");
                geneticBackground = rs.getString("background");
                availability = rs.getString("availability");
                researchApplication = rs.getInt("application");
                researchApplicationText = rs.getString("appText");
                contact = rs.getInt("contact");
                genotyping = rs.getInt("genotyping");
                handling = rs.getInt("handling");
                strain = rs.getInt("strain");
                level = rs.getInt("level");
                desired_level = rs.getInt("desired_level");
                dirty = false;
            } else
                throw new EJBException("ExpModelBean#ejbLoad: Error loading ExpModel");
        } catch (Exception e) {
            logger.error("Failed to load model", e);
            throw new EJBException("ExpModelBean#ejbLoad: "+e.getMessage());
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
                super.store();
                
                ps = conn.prepareStatement("update model set background=?,availability=?,contact=?,application=?, appText=? " +
                        ", genotyping=?, handling=?, strain=?, level=?, desired_level=? where eid=?");

                int i=0;
                ps.setString(++i, geneticBackground);
                ps.setString(++i, availability);
                if (contact != 0)
                    ps.setInt(++i, contact); 
                else
                    ps.setNull(++i, java.sql.Types.INTEGER);
                if(researchApplication != 0)
                    ps.setInt(++i, researchApplication);
                else
                    ps.setNull(++i, java.sql.Types.INTEGER);
                ps.setString(++i, researchApplicationText);
                
                if(genotyping != 0)
                {
                    ps.setInt(++i, genotyping);
                }
                else
                {
                    ps.setNull(++i, java.sql.Types.INTEGER);                
                }
                
                if(handling != 0)
                    ps.setInt(++i, handling);
                else
                    ps.setNull(++i, java.sql.Types.INTEGER); 
                
                if (strain != 0)
                    ps.setInt(++i, strain);
                else
                    ps.setNull(++i, java.sql.Types.INTEGER);
                            

                ps.setInt(++i, level);
                
                ps.setInt(++i, desired_level);
                
                ps.setInt(++i, eid);
                
                int rows = ps.executeUpdate();
                if (rows!=1) {
                    throw new EJBException("ExpModelBean#ejbStore: Error saving ExpModel. Rows affected "+rows);
                }
            } catch (Exception e) {
                logger.debug("Failed to store model", e);
                throw new EJBException("ExpModelBean#ejbStore: Error saving ExpModel. \n"+e.getMessage());
            } finally {
                releaseConnection();
                dirty = false;
            }
        }
    }    
    
    // </editor-fold>
    
    //finder methods
    // <editor-fold defaultstate="collapsed">
    public java.lang.Integer ejbFindByPrimaryKey(java.lang.Integer eid) throws javax.ejb.FinderException {
        makeConnection();
        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            //String levelString = getModelLevelSql(caller);  // +levelString
            ps = conn.prepareStatement("select eid from model where eid = ? ");
            ps.setInt(1, eid.intValue());
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("ExpModelBean#ejbFindByPrimaryKey: Cannot find model");
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByPrimaryKey: Cannot find model "+eid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return eid;
    }
    
    public java.lang.Integer ejbFindByStrainID(int strainid) throws javax.ejb.FinderException {
        makeConnection();
        
        int eid=0;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = conn.prepareStatement("select eid from model where strain = ? ");
            ps.setInt(1, strainid);
            result = ps.executeQuery();
            
            if (!result.next()) {
                throw new ObjectNotFoundException("ExpModelBean#ejbFindByStrainID: Cannot find model");
            } else {
                eid = result.getInt("eid");
            }
            
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByStrainID: Cannot find model with strain id "+strainid+" \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return new Integer(eid);
    }
    
    public java.util.Collection ejbFindBySamplingUnit(int suid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            String levelString = getModelLevelSql(caller);
            ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and e.suid = ? "+levelString);
            ps.setInt(1, suid);
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindBySamplingUnit: Cannot find models\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByAllele(int allid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            String levelString = getModelLevelSql(caller);
            ps = conn.prepareStatement("select eid from model where strain in (select strainid from strain_allele where id = ?) "+levelString);
            ps.setInt(1, allid);
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByAllele: Cannot find models by allele\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByBackrossingListGeneration(MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select m.eid from model m, expobj e, users u, research_application r where m.eid = e.eid and m.contact=u.id and m.application=r.raid order by r.name, u.group_name");
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByBackrossingListGeneration: Cannot get models for backrossing list generation\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByModelsThatNeedDissUpdate(MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select eid from model where level != desired_level");
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbModelsThatNeedDissUpdate: Cannot find models that need dissemination level update.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByIMSRSubmission(int suid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            //String levelString = getModelLevelSql(caller);
            //get only public models that are not entered in the r_model_imsr table
            String levelString = "and level = 0";
            //ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid");
            ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and m.eid not in (select eid from r_model_imsr) "+levelString);
            //ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and e.suid = ? and m.eid not in (select eid from r_model_imsr) "+levelString);
            //ps.setInt(1, suid);
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByIMSRSubmission: Cannot find models for IMSR Submission\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByUniqueIMSRSubmission(MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            String levelString = "and level = 0";
            //ps = conn.prepareStatement("select m.eid from model m where m.eid not in (select eid from r_model_imsr) and strain not in (select strainid from strain where mgiid not like '0' and mgiid not like 'null' and mgiid not like '' and mgiid not like 'Null') "+levelString+" order by m.eid");
            ps = conn.prepareStatement("select m.eid from model m where strain not in (select strainid from strain where mgiid not like '0' and mgiid not like 'null' and mgiid not like '' and mgiid not like 'Null') and strain not in (select strainid from strain where designation like '') "+levelString+" order by m.eid");
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByUniqueIMSRSubmission: Cannot find unique models for IMSR Submission\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByGene(int gaid, MugenCaller caller) throws javax.ejb.FinderException {
//        setCaller(caller);
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            String levelString = getModelLevelSql(caller);
            ps = conn.prepareStatement("select m.eid from r_gene_model r, model m where r.eid = m.eid and gaid = ? "+levelString);
            ps.setInt(1, gaid);
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
//            se.printStackTrace();
            logger.error(getStackTrace(se));
            throw new FinderException("ExpModelBean#ejbFindByGene: Cannot find models by gene\n"+se.getMessage());
        } finally {
            releaseConnection();
        }        
        return models;
    }
    
    public java.util.Collection ejbFindByFormDataManagerForDissUpdate(FormDataManager fdm, com.arexis.mugen.MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        // Get Research application checkbox value (raid)
        int raid = 0;
        String tmp = fdm.getValue("raid");
        if (tmp!=null && !tmp.equals("") && !tmp.equals("*"))
            raid = new Integer(tmp).intValue();
        
        String partcipantname = "";
        tmp = fdm.getValue("participantname");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            partcipantname = tmp;
        
        String groupname ="";
        tmp = fdm.getValue("groupname");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            groupname = tmp;
        
        int mutationtypes = 0;
        tmp = fdm.getValue("mutationtypes");
        if (tmp!=null && !tmp.equals("") && !tmp.equals("*"))
            mutationtypes = new Integer(tmp).intValue();
        
        String orderby ="";
        tmp = fdm.getValue("ordertype");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            orderby = tmp;
        
        String disslevel ="";
        tmp = fdm.getValue("disslevel");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            disslevel = tmp;
        
        try {
            String sql = "select eid from model where level != desired_level";
            
            if (raid!=0)
            {
                sql += " and application = ? ";
            }
            
            if (!partcipantname.equals(""))
            {
                sql += " and eid in (select eid from model where contact in (select id from users where name like ?)) ";
            }
                        
            if (!groupname.equals(""))
            {
                sql += " and eid in (select eid from model where contact in (select id from users where group_name like ?)) ";
            }
            
            if (mutationtypes!=0)
            {
                sql += " and eid in (select eid from model where strain in (select strainid from strain where strainid in (select strainid from strain_allele where id in (select strainallele from r_mutation_type_strain_allele where mutationtype in (select id from mutation_type where id=?))))) ";
            }
            
            if(!disslevel.equals("")){
                if(disslevel.equals("Admin")){
                    sql += " and eid in (select eid from model where level=2) ";
                }
                
                if(disslevel.equals("Mugen")){
                    sql += " and eid in (select eid from model where level=1) ";
                }
                
                if(disslevel.equals("Public")){
                    sql += " and eid in (select eid from model where level=0) ";
                }
            }
            
            String levelString = getModelLevelSql(caller);
            sql += levelString;
            
            if (!orderby.equals(""))
            {
                if(orderby.equals("MMMDb ID")){
                    sql += " order by eid";
                }
                
                if(orderby.equals("DATE")){
                    sql += " order by ts";
                }
                
            }
                    
            ps = conn.prepareStatement(sql);
            
            int i = 1;
            
            if (raid!=0)
                ps.setInt(i++, raid);
            if (!partcipantname.equals(""))
                ps.setString(i++, partcipantname);
            if (!groupname.equals(""))
                ps.setString(i++, groupname);
            if (mutationtypes!=0)
                ps.setInt(i++, mutationtypes);
            
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByFormDataManagerForDissUpdate: Cannot find models\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public Collection ejbFindByResearchApplication(int raid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            String levelString = getModelLevelSql(caller);
            ps = conn.prepareStatement("select eid from model where application = ? "+levelString);
            ps.setInt(1, raid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByResearchApplication: Cannot find model \n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    public java.util.Collection ejbFindByKeyword(Keyword keyword, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        //validate("MODEL_R",caller);
        try {
            String levelString = getModelLevelSql(caller);
            ps = conn.prepareStatement("select m.eid, e.suid from model m, expobj e where m.eid=e.eid and "
                    +"(lower(background) like ? "+
                    "or lower(availability) like ? "+
                    "or lower(apptext) like ? "+
                    "or lower(identity) like ? "+
                    "or lower(alias) like ? "+
                    "or lower(comm) like ?" +
                    "or strain in (select strainid from strain where lower(designation) like ? )) " +levelString);
            
            String search = "%"+keyword.getKeyword()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setString(5, search);
            ps.setString(6, search);
            ps.setString(7, search);
            
            result = ps.executeQuery();
            
            
            while (result.next()) {
                try {
                    validateSU("MODEL_R", caller, result.getInt("suid"));
                    arr.add(new Integer(result.getInt("eid")));
                } catch (PermissionDeniedException pde) {}
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByKeyword: Cannot find model by keyword\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }

    public java.util.Collection ejbFindByFormDataManager(FormDataManager fdm, com.arexis.mugen.MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        // Get Functional Significance type value (fstid)
        int fstid = 0;
        String tmp = fdm.getValue("fstid");
        if (tmp!=null && !tmp.equals("") && !tmp.equals("*"))
            fstid = new Integer(tmp).intValue();
        
        // Get Research application checkbox value (raid)
        int raid = 0;
        tmp = fdm.getValue("raid");
        if (tmp!=null && !tmp.equals("") && !tmp.equals("*"))
            raid = new Integer(tmp).intValue();
        
        // Get gene checkbox value (gaid)
        int gaid = 0;
        tmp = fdm.getValue("gaid");
        if (tmp!=null && !tmp.equals("") && !tmp.equals("*"))
            gaid = new Integer(tmp).intValue();
        
        // Get the sampling unit value (suid)
        int suid = 0;
        tmp = fdm.getValue("suid");
        if (tmp!=null && !tmp.equals(""))
             suid = new Integer(tmp).intValue();
        else
            suid = caller.getSuid();
        
        String groupname ="";
        tmp = fdm.getValue("groupname");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            groupname = tmp;
        
        String partcipantname = "";
        tmp = fdm.getValue("participantname");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            partcipantname = tmp;
        
        int mutationtypes = 0;
        tmp = fdm.getValue("mutationtypes");
        if (tmp!=null && !tmp.equals("") && !tmp.equals("*"))
            mutationtypes = new Integer(tmp).intValue();
        
        int mpterm = 0;
        tmp = fdm.getValue("mpterm");
        if (tmp!=null && !tmp.equals("") && !tmp.equals("*"))
            mpterm = new Integer(tmp).intValue();
        
        String orderby ="";
        tmp = fdm.getValue("ordertype");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            orderby = tmp;
        
        String disslevel ="";
        tmp = fdm.getValue("disslevel");
        if (tmp!=null && !tmp.equals("") && tmp.compareTo("*")!=0)
            disslevel = tmp;
        
        try {
            String sql = "select m.eid from model m, expobj e where m.eid=e.eid and suid = ? ";
            
            if (fstid!=0)
            {
                sql += " and m.eid in (select eid from functional_significance where fstid=?) ";
            }
            
            if (raid!=0)
            {
                sql += " and application = ? ";
            }
            
            if (gaid!=0)
            {
                sql += " and m.eid in (select eid from r_gene_model where gaid=?) ";
            }
            
            if (!groupname.equals(""))
            {
                sql += " and m.eid in (select eid from model where contact in (select id from users where group_name like ?)) ";
            }
            
            if (!partcipantname.equals(""))
            {
                sql += " and m.eid in (select eid from model where contact in (select id from users where name like ?)) ";
            }
            
            if (mutationtypes!=0)
            {
                sql += " and m.eid in (select eid from model where strain in (select strainid from strain where strainid in (select strainid from strain_allele where id in (select strainallele from r_mutation_type_strain_allele where mutationtype in (select id from mutation_type where id=?))))) ";
            }
            
            if (mpterm!=0)
            {
                sql += " and m.eid in (select eid from pheno_model_r_ where mp like ?) ";
            }
            
            if(!disslevel.equals("")){
                if(disslevel.equals("Admin")){
                    sql += " and m.eid in (select eid from model where level=2) ";
                }
                
                if(disslevel.equals("Mugen")){
                    sql += " and m.eid in (select eid from model where level=1) ";
                }
                
                if(disslevel.equals("Public")){
                    sql += " and m.eid in (select eid from model where level=0) ";
                }
            }
            
            String levelString = getModelLevelSql(caller);
            sql += levelString;
            
            if (!orderby.equals(""))
            {
                if(orderby.equals("MMMDb ID")){
                    sql += " order by eid";
                }
                
                if(orderby.equals("LINE NAME")){
                    sql += " order by alias";
                }
                
                if(orderby.equals("DATE")){
                    sql += " order by e.ts desc";
                }
                
            }
                    
            ps = conn.prepareStatement(sql);
            
            int i = 1;
            ps.setInt(i++, suid);
            
            if (fstid!=0)
                ps.setInt(i++, fstid);
            if (raid!=0)
                ps.setInt(i++, raid);
            if (gaid!=0)
                ps.setInt(i++, gaid);
            if (!groupname.equals(""))
                ps.setString(i++, groupname);
            if (!partcipantname.equals(""))
                ps.setString(i++, partcipantname);
            if (mutationtypes!=0)
                ps.setInt(i++, mutationtypes);
            if (mpterm!=0)
                ps.setString(i++, "%>"+new Integer(mpterm).toString());
            
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindBySamplingUnit: Cannot find models\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public Collection ejbFindByMP(String potid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        potid = "%>"+potid;
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            String levelString = getModelLevelSql(caller);
            ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and m.eid in (select eid from pheno_model_r_ where mp like ?) "+levelString);
            ps.setString(1, potid);
            result = ps.executeQuery();
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByMP: Cannot find model(s) by mammalian phenotype.\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindByJAXID(MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            //ps = conn.prepareStatement("select eid from model where strain in (select strainid from strain where mgiid not like '0')");
            ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and m.strain in (select strainid from strain where mgiid not like '0')");
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByJAXID: Cannot find models that have JAX ID\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByEMMAID(MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and m.strain in (select strainid from strain where emmaid not like '0')");
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByEMMAID: Cannot find models that have JAX ID\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByJAXEMMAID(MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and m.strain in (select strainid from strain where emmaid not like '0'  and mgiid not like '0')");
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByJAXEMMAID: Cannot find models that have JAX AND EMMA ID\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    public java.util.Collection ejbFindByGenBackValue(int bid, MugenCaller caller) throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select eid from model where eid in " +
                    "(select distinct eid from genetic_back where dna_origin=? " +
                    "or targeted_back=? or host_back=? or backcrossing_strain=?)");
            ps.setInt(1, bid);
            ps.setInt(2, bid);
            ps.setInt(3, bid);
            ps.setInt(4, bid);
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
            
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByGenBackValue: Cannot find models that have JAX AND EMMA ID\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    //</editor-fold>
    
    //relational methods
    // <editor-fold defaultstate="collapsed">
    
    public Collection getGeneticModifications(){
        Collection arr = null;
        
        try {
            arr = geneticModificationHome.findByModel(eid);
        } catch(ObjectNotFoundException oe) {
            // Nothing..return empty list
            return new ArrayList();            
        } catch (FinderException fe) {
            // Nothing..return empty list
            return new ArrayList();
        } catch (RemoteException re) {
            throw new EJBException(re);
        }
        return arr;
    }
    
    public Collection getFunctionalSignificance(){
        Collection arr = null;
        try {
            arr = functionalSignificanceHome.findByModel(eid);
        } catch(ObjectNotFoundException oe) {
            // Nothing..return empty list
            return new ArrayList();          
        } catch (FinderException fe) {
            // Nothing..return empty list
            return new ArrayList();
        } catch (RemoteException re) {
            throw new EJBException(re);
        }
        return arr;
    }
    
    public Collection getGeneAffected(){
        Collection arr = null;
        try {
            arr = geneHome.findByModel(eid);
        } catch(ObjectNotFoundException oe) {
            // Nothing..return empty list
            return new ArrayList();             
        } catch (FinderException fe) {
            // Nothing..return empty list
            return new ArrayList();
        } catch (RemoteException re) {
            throw new EJBException(re);
        }
        return arr;
    }
    
    public void removeReference(ReferenceRemote reference) throws ApplicationException {
        makeConnection();
        try {
            
            PreparedStatement ps = conn.prepareStatement("delete from r_ref_model where refid = ? and eid = ?");
            ps.setInt(1, reference.getRefid());
            ps.setInt(2, eid);
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("ExpModelBean#removeReference: Unable to remove reference. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }

    public void addResource(ResourceRemote res) throws ApplicationException {
        makeConnection();
        try {
            
            PreparedStatement ps = conn.prepareStatement("insert into r_resource_model (resourceid,eid) values (?,?)");
            ps.setInt(1, res.getResourceId());
            ps.setInt(2, eid);
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("ExpModelBean#addResource: Unable to add resource. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
  
    public void addReference(ReferenceRemote ref) throws ApplicationException {
        makeConnection();
        try {
            
            PreparedStatement ps = conn.prepareStatement("insert into r_ref_model (refid,eid) values (?,?)");
            ps.setInt(1, ref.getRefid());
            ps.setInt(2, eid);
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("ExpModelBean#addReference: Unable to add reference. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public void addGene(GeneRemote gene) throws ApplicationException{
        try
        {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("insert into r_gene_model (gaid,eid) values (?,?)");
            ps.setInt(1, gene.getGaid());
            ps.setInt(2, eid);
            
            ps.execute();
        }
        catch (Exception e)
        {
            throw new ApplicationException("ExpModelBean#addGene: Failed to add gene to this model",e);
        }
        finally 
        {
            releaseConnection();
        }
    }
    
    public void removeGene(GeneRemote gene) throws ApplicationException{
        try
        {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("delete from r_gene_model where gaid=? and eid=?");
            ps.setInt(1, gene.getGaid());
            ps.setInt(2, eid);
            ps.execute();
        }
        catch (Exception e)
        {
            throw new ApplicationException("ExpModelBean#removeGene: Failed to remove gene from this model",e);
        }
        finally 
        {
            releaseConnection();
        }
    }
    
    public void unassignGeneFromStrainAlleles(int strainId, int geneId) throws ApplicationException{
        try
        {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("update strain_allele set gene=null where strainid=? and gene=?");
            ps.setInt(1, strainId);
            ps.setInt(2, geneId);
            ps.execute();
        }
        catch (Exception e)
        {
            throw new ApplicationException("ExpModelBean#unassignGeneFromStrainAlleles: Failed to unassign genes from alleles of this model",e);
        }
        finally 
        {
            releaseConnection();
        }
    }
    
    public void unassignStrainAllelesFromGene(int eid, int strainid) throws ApplicationException {
        try
        {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("delete from r_gene_model where eid=? and gaid not in (select gene from strain_allele where strainid=? and gene is not null)");
            ps.setInt(1, eid);
            ps.setInt(2, strainid);
            ps.execute();
        }
        catch (Exception e)
        {
            throw new ApplicationException("ExpModelBean#unassignStrainAllelesFromGene: Failed to unassign genes from allele gene reassignment",e);
        }
        finally 
        {
            releaseConnection();
        }
    }
    
    public int IMSRSubmit(int eid) throws ApplicationException{
        try {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("insert into r_model_imsr (eid,imsr,ts) values (?,?,?)");
            ps.setInt(1, eid);
            ps.setInt(2, 1);
            ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            ps.execute();
            return 1;
        } catch (Exception e) {
            throw new ApplicationException("ExpModelBean#IMSRSubmit: Failed to add this model to the r_model_imsr table",e);
        } finally {
            releaseConnection();
        }
    }
    
    public Collection getMPs() throws ApplicationException {
        makeConnection();
        Collection mps = new ArrayList();
        String mp = "";
        String[] mp_ = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select mp from pheno_model_r_ where eid = ? ");
            ps.setInt(1, eid);
            result = ps.executeQuery();
            
            while(result.next()) {
                mp = result.getString("mp");
                mp_ = mp.split(">");
                mps.add(mp_);
            }
        } catch (Exception e) {
            throw new ApplicationException("ExpModelBean#getMPs: method failed",e);
        }finally {
            releaseConnection();
        }
        
        return mps;
    }
    
    // </editor-fold>
    
    //setter+getter methods
    // <editor-fold defaultstate="collapsed">

    public String getModelLevelSql(MugenCaller clr){
        String levelString = "";
        if (clr.hasPrivilege("MODEL_ADM")) {
            levelString = " and level <= 2 ";
        } else if (clr.hasPrivilege("MODEL_MUGEN")) {
            levelString = " and level <= 1 ";
        } else if (clr.hasPrivilege("MODEL_PUB")) {
            levelString = " and level <= 0 ";
        }
        return levelString;
    }
    
    public String getResearchApplicationText() {
        return researchApplicationText;
    }

    public void setResearchApplicationText(String researchApplicationText) {
        this.researchApplicationText = researchApplicationText;
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;        
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;        
    }

    public String getGeneticBackground() {
        return geneticBackground;
    }

    public void setGeneticBackground(String geneticBackground) {
        this.geneticBackground = geneticBackground;
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;        
    }
    
    public Collection getGeneticBackgroundInfo() throws ApplicationException{
        Collection arr = null;
        try {
            arr = genbackHome.findByGeneticBackgroundModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = arr.iterator();
            
            while(i.hasNext()) {                
                dtos.add(new GeneticBackgroundDTO((GeneticBackgroundRemote)i.next()));
            }
            
            return dtos;
            
            } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get genetic background (backcrosses etc.) information for specific model.", e);
        }
    }

    public ResearchApplicationRemote getResearchApplication() {
        try
        {
            if(researchApplication > 0) {
                ResearchApplicationRemote ra = researchApplicationHome.findByPrimaryKey(new Integer(researchApplication));
                return ra;
            } else
                return null;
        }
        catch (Exception e)
        {
            throw new EJBException("ExpModelBean#getResearchApplication: Failed to get researchApplication");
        }
    }

    public void setResearchApplication(ResearchApplicationRemote ra) throws RemoteException {
        this.researchApplication = ra.getRaid();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;        
    }
    
    public UserRemote getContact() {
        try
        {
            UserRemote usr = _userHome.findByPrimaryKey(new Integer(contact));
            return usr;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public FileRemote getGenotypingFile() {
        try {
            FileRemote file = fileHome.findByPrimaryKey(new Integer(genotyping));
            return file;
        } catch (FinderException fe) {
            return null;
        } catch (Exception e) {
            throw new EJBException("Could not get genotyping file");
        }
    }

    public FileRemote getHandlingFile() {
        try {
            FileRemote file = fileHome.findByPrimaryKey(new Integer(handling));
            return file;
        } catch (FinderException fe) {
            return null;
        } catch (Exception e) {
            throw new EJBException("Could not get handling file");
        }
    }

    public void setGenotypingFile(int fileid) {
        genotyping = fileid;
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;  
    }

    public void setHandlingFile(int fileid) {
        handling = fileid;
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }

    public void setContact(UserRemote usr) throws RemoteException {
        this.contact = usr.getId();
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;
    }
    
    public void setCaller(MugenCaller caller) {
        this.caller = caller;
        dirty = true;
    }
    
    public Collection getResources() throws ApplicationException {
        try {
            return resourceHome.findByModel(eid, caller);
        } catch (Exception e) {
            throw new ApplicationException("Could not get resources");
        }
    }
    
    public Collection getAvailabilityForModel(int eid) throws ApplicationException {
        try {
            Collection availability = availabilityHome.findByModel(eid);
            Collection dtos = new ArrayList();
            Iterator i = availability.iterator();
            while(i.hasNext()) {                
                dtos.add(new AvailabilityDTO((AvailabilityRemote)i.next()));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get availability information for specific model.", e);
        }
    }

    public int getNumberOfPhenotypes() throws ApplicationException {
        makeConnection();
        try {
            
            PreparedStatement ps = conn.prepareStatement("select count(iid) as num from phenotypes where iid = ? and suid = ?");
            ps.setInt(1, eid);
            ps.setInt(2, suid);
            
            ResultSet result = ps.executeQuery();
            int num = 0;
            if(result.next())
                num = result.getInt("num");
            
            return num;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("ExpModelBean#getNumberOfPhenotypes Unable to count phenotypes. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    public Collection getReferences(){
        Collection arr = null;
        try {
            arr = referenceHome.findByModel(eid);
        } catch (FinderException fe) {
            throw new EJBException(fe);
        } catch (RemoteException re) {
            throw new EJBException(re);
        }
        return arr;
    }
    
    public void setStrain(StrainRemote strain){
        try
        {
            if (strain==null)
                this.strain = 0;
            else
                this.strain = strain.getStrainid();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public StrainRemote getStrain() throws ApplicationException {
        try
        {
            return strainHome.findByPrimaryKey(new Integer(strain));
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ApplicationException("ExpModelBean#getStrain: Failed to get strain from a model. ",ex);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        dirty = true;
    }
    
    public int getDesiredLevel(){
        return desired_level;
    }
    
    public void setDesiredLevel(int desired_level){
        this.desired_level = desired_level;
        dirty = true;
    }
    
    public String getMutationTypesForModel () throws ApplicationException {
        try{
            StrainRemote strain = getStrain();
            Collection strainAlleles = strain.getStrainAlleles();
            Iterator i = strainAlleles.iterator();
            String mutations = "";
            MutationDistinctionParameter = 0;
            int j=0;
            while (i.hasNext()) {
                StrainAlleleRemote sar = (StrainAlleleRemote)i.next();
                Collection mutationTypes = sar.getMutationTypes();
                Iterator k = mutationTypes.iterator();
                while (k.hasNext()) {
                    MutationTypeRemote m = (MutationTypeRemote)k.next();
                    
                    if (!mutations.contains(m.getName())){
                        if (j!=0)
                            mutations += ", ";
                        mutations += m.getName();
                        
                        //Mutation Distinction Factor
                        if (m.getName().compareTo("transgenic")==0){
                            MutationDistinctionParameter = 1;
                        }
                    }
                    j++;
                }//nested while
            }//while
            return mutations;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ApplicationException("ExpModelBean#getMutationTypesForModel: Failed to get mutation types for model. ",ex);
        }
    }
    
    public Collection getStrainAlleleInfo() throws ApplicationException {
        try {
            StrainRemote strain = getStrain();
            Collection strainAlleles = strain.getStrainAlleles();
            Collection dtos = new ArrayList();
            
            Iterator i = strainAlleles.iterator();
            
            while(i.hasNext()) {                
                dtos.add(new StrainAlleleDTO((StrainAlleleRemote)i.next(), "superscript"));
            }
            
            return dtos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to get strain allele information for specific model.", e);
        }
    }
    
    public int getMutationDistinctionParameter() throws ApplicationException {
        return MutationDistinctionParameter;
    }
    
    public void setUpdate() {
        ts = new java.sql.Date(System.currentTimeMillis());
        dirty = true;        
    }
    //</editor-fold>

    //create+postcreate methods
    // <editor-fold defaultstate="collapsed">
    public java.lang.Integer ejbCreate(int eid, java.lang.String identity, SamplingUnitRemote samplingUnit, MugenCaller caller) throws javax.ejb.CreateException {
        makeConnection();
        Integer pk = null;
        try {
            this.eid = eid;
            this.identity = identity;
            
            create(eid, identity, samplingUnit, caller);
            
            geneticBackground = "";
            availability = "";
            contact = 0;
            researchApplication = 0;
            researchApplicationText = "";
            status = "E";
            alias = "Alias";
            level = 1;
            ts = new java.sql.Date(System.currentTimeMillis());
            id = caller.getId();
            
            pk = new Integer(eid);
            
            PreparedStatement ps = conn.prepareStatement("insert into model (eid) values (?)");
            ps.setInt(1, eid);
            
            
            ps.execute();
            dirty = false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CreateException("ExpModelBean#ejbCreate: Unable to create model. \n"+e.getMessage());
        } finally {
            releaseConnection();
        }
        return pk;
    }

    public void ejbPostCreate(int eid, java.lang.String identity, SamplingUnitRemote samplingUnit, MugenCaller caller) throws javax.ejb.CreateException {
        //TODO implement ejbPostCreate
    }
    
    //</editor-fold>
    
    //phenotype functions
    //<editor-fold defaultstate="collapsed">
    public void addPhenoOntology(int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09) throws ApplicationException
    {
        try
        {
            makeConnection();
            PreparedStatement ps_vital = conn.prepareStatement("select eid from pheno_model_r where eid=? and mp01=? and mp02=? and mp03=? and mp04=? and mp05=? and mp06=? and mp07=? and mp08=? and mp09=?");
            ps_vital.setInt(1, eid);
            ps_vital.setInt(2, mp01);
            ps_vital.setInt(3, mp02);
            ps_vital.setInt(4, mp03);
            ps_vital.setInt(5, mp04);
            ps_vital.setInt(6, mp05);
            ps_vital.setInt(7, mp06);
            ps_vital.setInt(8, mp07);
            ps_vital.setInt(9, mp08);
            ps_vital.setInt(10, mp09);
            
            ResultSet rs = ps_vital.executeQuery();
            
            //only the selected combination is not already entered is the following query executed..
            if (!rs.next()) {
                PreparedStatement ps = conn.prepareStatement("insert into pheno_model_r (eid, mp01, mp02, mp03, mp04, mp05, mp06, mp07, mp08, mp09) values (?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, eid);
                ps.setInt(2, mp01);
                ps.setInt(3, mp02);
                ps.setInt(4, mp03);
                ps.setInt(5, mp04);
                ps.setInt(6, mp05);
                ps.setInt(7, mp06);
                ps.setInt(8, mp07);
                ps.setInt(9, mp08);
                ps.setInt(10, mp09);
            
                ps.execute();
            }
        }
        catch (Exception e)
        {
            throw new ApplicationException("ExpModelBean#addPhenoOntology: Failed to add phenotype ontology to this model",e);
        }
        finally 
        {
            releaseConnection();
        }
    }
    
    public void removePhenoOntology(int mp01, int mp02, int mp03, int mp04, int mp05, int mp06, int mp07, int mp08, int mp09) throws ApplicationException
    {
        try
        {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("delete from pheno_model_r where eid=? and mp01=? and mp02=? and mp03=? and mp04=?" +
                " and mp05=? and mp06=? and mp07=? and mp08=? and mp09=?");
            ps.setInt(1, eid);
            ps.setInt(2, mp01);
            ps.setInt(3, mp02);
            ps.setInt(4, mp03);
            ps.setInt(5, mp04);
            ps.setInt(6, mp05);
            ps.setInt(7, mp06);
            ps.setInt(8, mp07);
            ps.setInt(9, mp08);
            ps.setInt(10, mp09);
            ps.execute();
        }
        catch (Exception e)
        {
            throw new ApplicationException("ExpModelBean#removePhenoOntology: Failed to remove phenotype ontology from this model",e);
        }
        finally 
        {
            releaseConnection();
        }
    }
    //</editor-fold>
    
    //phenotype functions
    //<editor-fold defaultstate="collapsed">
    public void addPhenoOntologyPath(PhenotypeOntologyPathRemote pop) throws ApplicationException {
        try {
            makeConnection();
            PreparedStatement ps_vital = conn.prepareStatement("select eid from pheno_model_r_ where eid=? and mp=?");
            ps_vital.setInt(1, eid);
            ps_vital.setString(2, pop.getPath());
            ResultSet rs = ps_vital.executeQuery();
            
            //only the selected combination is not already entered is the following query executed..
            if (!rs.next()) {
                PreparedStatement ps = conn.prepareStatement("insert into pheno_model_r_ (eid, mp) values (?,?)");
                ps.setInt(1, eid);
                ps.setString(2, pop.getPath());
                ps.execute();
            }
        } catch (Exception e) {
            throw new ApplicationException("ExpModelBean#addPhenoOntologyPath: Failed to add phenotype ontology path to this model",e);
        } finally {
            releaseConnection();
        }
    }
    
    public void removePhenoOntologyPath(PhenotypeOntologyPathRemote pop) throws ApplicationException {
        try {
            makeConnection();
            PreparedStatement ps = conn.prepareStatement("delete from pheno_model_r_ where eid=? and mp=?");
            ps.setInt(1, eid);
            ps.setString(2, pop.getPath());
            ps.execute();
        } catch (Exception e) {
            throw new ApplicationException("ExpModelBean#removePhenoOntologyPath: Failed to remove phenotype ontology path from this model",e);
        } finally {
            releaseConnection();
        }
    }
    //</editor-fold>
    
    //web services methods
    //<editor-fold defaultstate="collapsed">
    
    //simple model finder
    public java.util.Collection ejbFindByWebServiceRequest() throws javax.ejb.FinderException {
        makeConnection();
        Collection models = new ArrayList();
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select m.eid from model m, expobj e where m.eid = e.eid and level <= 0 ");
            result = ps.executeQuery();
            
            while(result.next()) {
                models.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByWebServiceRequest: Cannot find models\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        
        return models;
    }
    
    //key model finder
    
    public java.util.Collection ejbFindByWebServiceKeywordRequest(String keyword) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select m.eid, e.suid from model m, expobj e where m.eid=e.eid and level <= 0 and "
                    +"(lower(background) like ? "+
                    "or lower(availability) like ? "+
                    "or lower(apptext) like ? "+
                    "or lower(identity) like ? "+
                    "or lower(alias) like ? "+
                    "or lower(comm) like ? " +
                    "or strain in (select strainid from strain where lower(designation) like ? ) " +
                    //model search based on gene
                    "or m.eid in (select eid from r_gene_model where gaid in (select distinct gaid from gene where lower(name) like ? or lower(comm) like ? or lower(mgiid) like ? or lower(genesymbol) like ? or lower(idensembl) like ?)) " +
                    //model search bansed on phenotype
                    "or m.eid in (select eid from getPaths(?) as (eid int4)) " +
                    //model search based on availability
                    "or m.eid in (select distinct eid from r_model_repositories_avgenback where " +
                    "rid in (select distinct rid from repositories where lower(reponame) like ?) or " +
                    "aid in (select distinct aid from available_genetic_back where lower(avbackname) like ?) or " +
                    "stateid in (select distinct id from strain_state where lower(name) like ?) or " +
                    "typeid in (select distinct id from strain_type where lower(name) like ?)) " +
                    //model search based on genetic background
                    "or m.eid in (select distinct eid from genetic_back where " +
                    "dna_origin in (select bid from genetic_back_values where lower(backname) like ?) " +
                    "or targeted_back in (select bid from genetic_back_values where lower(backname) like ?) " +
                    "or host_back in (select bid from genetic_back_values where lower(backname) like ?) " +
                    "or backcrossing_strain in (select bid from genetic_back_values where lower(backname) like ?) " +
                    "or lower(backcrosses) like ?) " +
                    
                    ")");
            
            String search = "%"+keyword.toLowerCase()+"%";
            
            ps.setString(1, search);
            ps.setString(2, search);
            ps.setString(3, search);
            ps.setString(4, search);
            ps.setString(5, search);
            ps.setString(6, search);
            ps.setString(7, search);
            ps.setString(8, search);
            ps.setString(9, search);
            ps.setString(10, search);
            ps.setString(11, search);
            ps.setString(12, search);
            ps.setString(13, search);
            ps.setString(14, search);
            ps.setString(15, search);
            ps.setString(16, search);
            ps.setString(17, search);
            ps.setString(18, search);
            ps.setString(19, search);
            ps.setString(20, search);
            ps.setString(21, search);
            ps.setString(22, search);
            
            result = ps.executeQuery();
            
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByWebServiceKeywordRequest: Cannot find model by keyword\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    public java.util.Collection ejbFindByWebServiceEnsemblRequest(String ensembl) throws javax.ejb.FinderException {
        makeConnection();
        Collection arr = new ArrayList();        
        PreparedStatement ps = null;
        ResultSet result = null;
        
        try {
            ps = conn.prepareStatement("select m.eid, e.suid from model m, expobj e where m.eid=e.eid and level <= 0 and "
                    + "m.eid in (select eid from r_gene_model where gaid in (select gaid from gene where lower(idensembl) like ?))");
            
            String search = "%"+ensembl.toLowerCase()+"%";
            
            ps.setString(1, search);
            
            result = ps.executeQuery();
            
            
            while (result.next()) {
                arr.add(new Integer(result.getInt("eid")));
            }
        } catch (SQLException se) {
            throw new FinderException("ExpModelBean#ejbFindByWebServiceEnsemblRequest: Cannot find model by ensembl gene id\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
        return arr;
    }
    
    //</editor-fold>
    
}
