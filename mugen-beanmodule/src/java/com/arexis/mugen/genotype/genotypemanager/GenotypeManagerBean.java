package com.arexis.mugen.genotype.genotypemanager;


import com.arexis.arxframe.PageManager;
import com.arexis.form.FormDataManager;
import com.arexis.mugen.exceptions.ApplicationException;
import com.arexis.mugen.exceptions.ExceptionLogUtil;
import com.arexis.mugen.exceptions.SamplingUnitNotFoundException;
import com.arexis.mugen.genotype.allele.AlleleRemote;
import com.arexis.mugen.genotype.allele.AlleleRemoteHome;
import com.arexis.mugen.genotype.genotype.GenotypePk;
import com.arexis.mugen.genotype.genotype.GenotypeRemote;
import com.arexis.mugen.genotype.genotype.GenotypeRemoteHome;
import com.arexis.mugen.genotype.marker.MarkerRemote;
import com.arexis.mugen.genotype.marker.MarkerRemoteHome;
import com.arexis.mugen.genotype.markerset.MarkerSetRemote;
import com.arexis.mugen.genotype.markerset.MarkerSetRemoteHome;
import com.arexis.mugen.genotype.uallele.UAlleleRemote;
import com.arexis.mugen.genotype.uallele.UAlleleRemoteHome;
import com.arexis.mugen.genotype.umarker.UMarkerRemote;
import com.arexis.mugen.genotype.umarker.UMarkerRemoteHome;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemote;
import com.arexis.mugen.genotype.umarkerset.UMarkerSetRemoteHome;
import com.arexis.mugen.MugenCaller;
import com.arexis.mugen.exceptions.ChromosomeNotFoundException;
import com.arexis.mugen.exceptions.IllegalValueException;
import com.arexis.mugen.exceptions.MarkerNotFoundException;
import com.arexis.mugen.project.AbstractMugenBean;
import com.arexis.mugen.project.ParamDataObject;
import com.arexis.mugen.project.project.ProjectRemoteHome;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.project.user.UserRemoteHome;
import com.arexis.mugen.samplingunit.individual.IndividualRemote;
import com.arexis.mugen.samplingunit.individual.IndividualRemoteHome;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemote;
import com.arexis.mugen.samplingunit.samplingunit.SamplingUnitRemoteHome;
import com.arexis.mugen.samplingunit.samplingunitmanager.GroupDTO;
import com.arexis.mugen.servicelocator.ServiceLocator;
import com.arexis.mugen.species.chromosome.ChromosomeRemote;
import com.arexis.mugen.species.chromosome.ChromosomeRemoteHome;
import com.arexis.mugen.species.species.SpeciesRemote;
import com.arexis.mugen.species.species.SpeciesRemoteHome;
import com.arexis.util.graph.Graph;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import org.apache.log4j.Logger;



/**
 * This is the bean class for the GenotypeManagerBean enterprise bean.
 * Created Aug 24, 2005 3:09:46 PM
 * 
 * This class handles most of the business logic for genotype related data
 * @author lami
 */
public class GenotypeManagerBean extends AbstractMugenBean implements javax.ejb.SessionBean, com.arexis.mugen.genotype.genotypemanager.GenotypeManagerRemoteBusiness {
    private javax.ejb.SessionContext context;
    
    private static Logger logger = Logger.getLogger(GenotypeManagerBean.class);
    
    private static UAlleleRemoteHome uarh;
    private static UMarkerRemoteHome umrh;
    private static UMarkerSetRemoteHome umsrh;
    private static GenotypeRemoteHome grh;
    private static MarkerRemoteHome mrh;
    private static MarkerSetRemoteHome msrh;
    private static IndividualRemoteHome irh;
    private static SamplingUnitRemoteHome surh;
    private static AlleleRemoteHome arh;
    
    private static ChromosomeRemoteHome chromosomeHome;
    private static SpeciesRemoteHome speciesHome;
    private static UserRemoteHome userHome;
    
    // <editor-fold defaultstate="collapsed" desc="EJB infrastructure methods. Click the + sign on the left to edit the code.">
    // TODO Add code to acquire and use other enterprise resources (DataSource, JMS, enterprise bean, Web services)
    // TODO Add business methods or web service operations
    /**
     * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
     */
    public void setSessionContext(javax.ejb.SessionContext aContext) {
        context = aContext;
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() {
        
    }
    
    /**
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() {
        
    }
    // </editor-fold>
    
    /**
     * See section 7.10.3 of the EJB 2.0 specification
     * See section 7.11.3 of the EJB 2.1 specification
     */
    public void ejbCreate() {
        try {
            
            ProjectRemoteHome projectHome = (ProjectRemoteHome)locator.getHome(ServiceLocator.Services.PROJECT);
            grh = (GenotypeRemoteHome)locator.getHome(ServiceLocator.Services.GENOTYPE);
            surh = (SamplingUnitRemoteHome)locator.getHome(ServiceLocator.Services.SAMPLINGUNIT);
            irh = (IndividualRemoteHome)locator.getHome(ServiceLocator.Services.INDIVIDUAL);
            arh = (AlleleRemoteHome)locator.getHome(ServiceLocator.Services.ALLELE);
            mrh = (MarkerRemoteHome)locator.getHome(ServiceLocator.Services.MARKER);
            msrh = (MarkerSetRemoteHome)locator.getHome(ServiceLocator.Services.MARKERSET);
            umsrh = (UMarkerSetRemoteHome)locator.getHome(ServiceLocator.Services.UMARKERSET);
            umrh = (UMarkerRemoteHome)locator.getHome(ServiceLocator.Services.UMARKER);
            uarh = (UAlleleRemoteHome)locator.getHome(ServiceLocator.Services.UALLELE);
            
            chromosomeHome = (ChromosomeRemoteHome)locator.getHome(ServiceLocator.Services.CHROMOSOME);
            speciesHome = (SpeciesRemoteHome)locator.getHome(ServiceLocator.Services.SPECIES);
            userHome = (UserRemoteHome)locator.getHome(ServiceLocator.Services.USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns a graph from the individuals in the genotypes in pedigree
     * @param pedigree A collection of genotype dto's
     * @param depthFirst True if the graph should be built going depth first
     * @throws com.arexis.mugen.exceptions.ApplicationException If the graph could not be created
     * @return A graph of the pedigree
     */
    public Graph getGraphForPedigree(java.util.Collection pedigree, boolean depthFirst) throws ApplicationException {
        Graph g = null;
            
        try {
            Collection  inds = new ArrayList();
            IndividualRemote ind = null;
            GenotypeSpecialViewDTO dto = null;
            Iterator i = pedigree.iterator();
            while(i.hasNext()) {
                dto = (GenotypeSpecialViewDTO)i.next();
                ind = irh.findByPrimaryKey(new Integer(dto.getIid()));
                inds.add(ind);
            }

            PedigreeDeriver pd = new PedigreeDeriver(depthFirst);

            g = pd.graphFromCollection(inds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not generate graph data for pedigree export. "+e.getMessage());
        }
        return g;
    } 
    

    /**
     * Returns a collection of genotype dto's using a child view
     * @param father The father to start view from
     * @param mother The mother to start the view from
     * @param mid1 Marker restriction 1
     * @param mid2 Marker restriction 2
     * @param mid3 Marker restriction 3
     * @param depthFirst Wether or not to use depth first view
     * @throws com.arexis.mugen.exceptions.ApplicationException If the collection could not be generated
     * @return A collection of genotype dto's
     */
    public Collection getChildView(int father, int mother, int mid1, int mid2, int mid3, boolean depthFirst) throws ApplicationException {
        Collection  dto = new ArrayList();
        
        try {
            PedigreeDeriver pedigree = new PedigreeDeriver(depthFirst);
            IndividualRemote fatherInd = null;
            IndividualRemote motherInd = null;
            SamplingUnitRemote sur = null;
            
            if(father != 0)
                fatherInd = irh.findByPrimaryKey(new Integer(father));
            if(mother != 0)
                motherInd = irh.findByPrimaryKey(new Integer(mother));
            if(fatherInd != null)
                sur = fatherInd.getSamplingUnit();
            else if(motherInd != null)
                sur = motherInd.getSamplingUnit();
            else
                return dto;
            Collection individuals = sur.getIndividuals();
            
            GenotypeRemote geno = null;
            if(fatherInd != null || motherInd != null) {
                individuals = pedigree.childView(individuals, fatherInd, motherInd);

                Iterator i = individuals.iterator();
                GenotypeSpecialViewDTO gsvDTO = null;
                IndividualRemote ind = null;
                while(i.hasNext()) {
                    ind = (IndividualRemote)i.next();
                    gsvDTO = new GenotypeSpecialViewDTO(ind);
                   
                    MarkerRemote marker = null;

                    if(mid1 > 0) {
                        marker = mrh.findByPrimaryKey(new Integer(mid1));
                        geno = grh.findByPrimaryKey(new GenotypePk(mid1, ind.getIid()));
                        gsvDTO.setMarker1Allele((ArrayList)geno.getAlleles());
                        gsvDTO.setMname1(marker.getName());
                    }
                    if(mid2 > 0) {
                        marker = mrh.findByPrimaryKey(new Integer(mid2));
                        geno = grh.findByPrimaryKey(new GenotypePk(mid2, ind.getIid()));
                        gsvDTO.setMarker2Allele((ArrayList)geno.getAlleles());
                        gsvDTO.setMname2(marker.getName());
                    }
                    if(mid3 > 0) {
                        marker = mrh.findByPrimaryKey(new Integer(mid3));
                        geno = grh.findByPrimaryKey(new GenotypePk(mid3, ind.getIid()));
                        gsvDTO.setMarker3Allele((ArrayList)geno.getAlleles());
                        gsvDTO.setMname3(marker.getName());
                    }
                    
                    dto.add(gsvDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get child view.");
        }
        
        return dto;
    }    
    
    /**
     * Returns a collection of genotype dto's using a group as origin
     * @param gid The group id
     * @param mid1 Marker 1 restriction
     * @param mid2 Marker 2 restriction
     * @param mid3 Marker 3 restriction
     * @throws com.arexis.mugen.exceptions.ApplicationException If the collection could not be generated
     * @return A collection of genotype dto's
     */
    public Collection getGroupView(int gid, int mid1, int mid2, int mid3) throws ApplicationException {
        Collection  dto = new ArrayList();
        
        try {
            PedigreeDeriver pedigree = new PedigreeDeriver(false);
            
            Collection individuals = irh.findByGroup(gid);
            GenotypeRemote geno = null;
            Iterator i = individuals.iterator();
            GenotypeSpecialViewDTO gsvDTO = null;
            IndividualRemote ind = null;
            
            while(i.hasNext()) {
                ind = (IndividualRemote)i.next();
                gsvDTO = new GenotypeSpecialViewDTO(ind);

                MarkerRemote marker = null;

                if(mid1 > 0) {
                    marker = mrh.findByPrimaryKey(new Integer(mid1));
                    geno = grh.findByPrimaryKey(new GenotypePk(mid1, ind.getIid()));
                    gsvDTO.setMarker1Allele((ArrayList)geno.getAlleles());
                    gsvDTO.setMname1(marker.getName());
                }
                if(mid2 > 0) {
                    marker = mrh.findByPrimaryKey(new Integer(mid2));
                    geno = grh.findByPrimaryKey(new GenotypePk(mid2, ind.getIid()));
                    gsvDTO.setMarker2Allele((ArrayList)geno.getAlleles());
                    gsvDTO.setMname2(marker.getName());
                }
                if(mid3 > 0) {
                    marker = mrh.findByPrimaryKey(new Integer(mid3));
                    geno = grh.findByPrimaryKey(new GenotypePk(mid3, ind.getIid()));
                    gsvDTO.setMarker3Allele((ArrayList)geno.getAlleles());
                    gsvDTO.setMname3(marker.getName());
                }

                dto.add(gsvDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get group view.");
        }
        
        return dto;
    }    
    
    /**
     * Returns a collection of genotype dto's using a single individual (child) as origin
     * @param iid The child
     * @param mid1 Marker 1 restriction
     * @param mid2 Marker 2 restriction
     * @param mid3 Marker 3 restriction
     * @param depthFirst Wether or not to use depth first view
     * @throws com.arexis.mugen.exceptions.ApplicationException If the collection could not be created
     * @return A collection of genotype dto's
     */
    public Collection getParentalView(int iid, int mid1, int mid2, int mid3, boolean depthFirst) throws ApplicationException {
        Collection  dto = new ArrayList();
        
        try {
            PedigreeDeriver pedigree = new PedigreeDeriver(depthFirst);
            
            IndividualRemote ind = irh.findByPrimaryKey(new Integer(iid));
            SamplingUnitRemote sur = ind.getSamplingUnit();
            Collection individuals = sur.getIndividuals();
            
            GenotypeRemote geno = null;
            if(ind != null) {
                individuals = pedigree.parentalView(individuals, ind);
                Iterator i = individuals.iterator();
                GenotypeSpecialViewDTO gsvDTO = null;
                
                while(i.hasNext()) {
                    ind = (IndividualRemote)i.next();
                    gsvDTO = new GenotypeSpecialViewDTO(ind);
                   
                    MarkerRemote marker = null;

                    if(mid1 > 0) {
                        marker = mrh.findByPrimaryKey(new Integer(mid1));
                        geno = grh.findByPrimaryKey(new GenotypePk(mid1, ind.getIid()));
                        gsvDTO.setMarker1Allele((ArrayList)geno.getAlleles());
                        gsvDTO.setMname1(marker.getName());
                    }
                    if(mid2 > 0) {
                        marker = mrh.findByPrimaryKey(new Integer(mid2));
                        geno = grh.findByPrimaryKey(new GenotypePk(mid2, ind.getIid()));
                        gsvDTO.setMarker2Allele((ArrayList)geno.getAlleles());
                        gsvDTO.setMname2(marker.getName());
                    }
                    if(mid3 > 0) {
                        marker = mrh.findByPrimaryKey(new Integer(mid3));
                        geno = grh.findByPrimaryKey(new GenotypePk(mid3, ind.getIid()));
                        gsvDTO.setMarker3Allele((ArrayList)geno.getAlleles());
                        gsvDTO.setMname3(marker.getName());
                    }
                    
                    dto.add(gsvDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get parental view.");
        }
        
        return dto;
    }
    
    /**
     * Returns the number of genotypes in the project given the restrictions when using the parameters in pdo in query
     * @param pdo The paramdataobject with parameters from the data mining filter form
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the operation failed for some reason
     * @return The number of genotypes in the project given the restrictions
     */
    public int getNumberOfGenotypes(ParamDataObject pdo, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GENO_R", caller);
        try {
            return grh.getNumberOfGenotypes(pdo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not count genotypes.");
        }
    }
    
    /**
     * Returns a collection of genotypes. The size of the collection depends on the project settings, i.e. the number of rows to display on one page.
     * @return A collection of genotypes
     * @param pdo The paramdataobject with parameters used as data mining filter
     * @param caller The current caller object
     * @param pageManager The pagemanager object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the genotypes could not be retrieved
     */
    public Collection getGenotypes(MugenCaller caller, PageManager pageManager, ParamDataObject pdo) throws ApplicationException {
        validate("GENO_R", caller);
        Collection dtoList = new ArrayList();
        try {        
            Collection genotypes = grh.findByQuery(pdo, pageManager);
            
            Iterator itr = genotypes.iterator();
            GenotypeRemote gr = null;
            GenotypeDTO dto = null;
            
            
            while (itr.hasNext()) {
                gr = (GenotypeRemote)itr.next();

                dto = new GenotypeDTO(gr);

                // Add to array
                dtoList.add(dto);              
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get genotypes.");
        }
        return dtoList;
    }
    
    /**
     * Returns a specific genotype
     * @param mid The marker id
     * @param iid The individual id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the genotype could not be retrived
     * @return A genotype
     */
    public GenotypeDTO getGenotype(int mid, int iid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GENO_R", caller);
        Collection dtoList = new ArrayList();
        try {
            int index = 0;
            
            GenotypeRemote gr = grh.findByPrimaryKey(new GenotypePk(mid, iid));
            
            return new GenotypeDTO(gr);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not get genotype.");
        }
    }
    
    /**
     * Returns alleles for a specific marker
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the alleles could not be retrieved
     * @return The alleles for the specified marker
     */
    public Collection getAllelesForMarker(int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_R", caller);
        Collection dto = new ArrayList();
        try{
            AlleleRemote ar = null;
            
            Collection alleles = arh.findAllAllelesForMarker(mid);
            
            Iterator i = alleles.iterator();
            
            while(i.hasNext()){
                ar = (AlleleRemote)i.next();
                
                dto.add(new AlleleDTO(ar));
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve alleles for marker: "+mid);
        }
        return dto;
    }
    
    /**
     * Returns all unified alleles for the specified unified marker
     * @param umid The unified marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified alleles could not be retrieved
     * @return A collection of unified alleles
     */
    public Collection getUAllelesForUMarker(int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_R", caller);
        Collection dto = new ArrayList();
        try{
            UAlleleRemote ar = null;
            
            UMarkerRemote mr = null;
            
            Collection alleles = uarh.findAllUAllelesForUMarker(umid);
            
            SpeciesRemote sr = null;
            
            ChromosomeRemote cr = null;
            
            Iterator i = alleles.iterator();
            UserRemote ur = null;
            
            while(i.hasNext()){
                ar = (UAlleleRemote)i.next();
                dto.add(new UAlleleDTO(ar));
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve unified alleles for unified marker: "+umid);
        }
        return dto;
    }
    
    /**
     * Returns a specific allele
     * @param aid The allele id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the allele could not be retrieved
     * @return The allele
     */
    public AlleleDTO getAllele(int aid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_R", caller);
        Collection dto = new ArrayList();
        try{
            AlleleRemote ar = arh.findByPrimaryKey(new Integer(aid));
            
            return new AlleleDTO(ar);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve allele: "+aid);
        }
    }
    
    /**
     * Returns a specific unified allele
     * @param uaid The unified allele id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified allele could not be retrieved
     * @return The unified allele
     */
    public UAlleleDTO getUAllele(int uaid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_R", caller);
        try{
            UAlleleRemote ar = uarh.findByPrimaryKey(new Integer(uaid));
            return new UAlleleDTO(ar);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not retrieve unified allele uaid: "+uaid);
        }
    }
    
    /**
     * Updates a genotype
     * @param iid The individual id
     * @param mid The marker id
     * @param raw1 The raw 1 value
     * @param raw2 The raw 2 value
     * @param reference The reference
     * @param comm The comment
     * @param aid1 The id of the first allele
     * @param aid2 The id of the second allele
     * @param level The level
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the chromosome could not be updated
     */
    public void updateGenotype(int iid, int mid, java.lang.String raw1, java.lang.String raw2, java.lang.String reference, java.lang.String comm, int aid1, int aid2, int level, MugenCaller caller) throws ApplicationException {
        validate("GENO_W"+level, caller);
        validate("RAW 1", raw1, 20);
        validate("RAW 2", raw2, 20);
        validate("Reference", reference, 32);
        validate("Comment", comm, 256);
        try{
            GenotypePk pk = new GenotypePk(mid, iid);
            GenotypeRemote gr = grh.findByPrimaryKey(pk);
            gr.setCaller(caller);
            gr.addHistory();
            gr.setRaw1(raw1);
            gr.setRaw2(raw2);
            gr.setAid1(aid1);
            gr.setAid2(aid2);
            gr.setLevel(level);
            gr.setReference(reference);
            gr.setComm(comm);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update genotype");
        }
    }
    
    /**
     * Removes the specified genotype
     * @param mid The marker id
     * @param iid The individual id
     * @param caller The current caller object
     * @param level The genotype level (neccessary for privilege check)
     * @throws com.arexis.mugen.exceptions.ApplicationException If the genotype could not be removed
     */
    public void removeGenotype(int mid, int iid, MugenCaller caller, int level) throws ApplicationException {
        validate("GENO_W"+level, caller);
        
        try{
            GenotypePk pk = new GenotypePk(mid, iid);
            GenotypeRemote gr = grh.findByPrimaryKey(pk);
            gr.remove();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove genotype");
        }
    }
    
    /**
     * Updates the genotype levels
     * @param caller The current caller object
     * @param pdo The paramdataobject holding parameters used as data mining restrictions
     * @param level The level to update to
     * @throws com.arexis.mugen.exceptions.ApplicationException If the levels could not be updated
     */
    public void updateGenotypeLevels(MugenCaller caller, com.arexis.mugen.project.ParamDataObject pdo, String level) throws ApplicationException {
        validate("GENO_W"+level, caller);
        
        String sql3 = "";
        Collection arr = new ArrayList();
        boolean[] useTable = new boolean[5];
        for(int i=0;i<useTable.length;i++)
            useTable[i] = false;
        
        
        if(pdo != null && pdo.hasValues()) {
            ArrayList list = pdo.getKeys();
            String column = "";
            String data = "";
            String term = "";
            
            for(int i=0;i<list.size();i++) {
                if(sql3.length() > 0)
                    term = " AND";
                column = (String)list.get(i);
                
                data = pdo.getValue(column);
                if(data != null && data.length() > 0 && !data.equalsIgnoreCase("null")) {
                    data = data.replace('*', '%');
                    
                    // Check if we should use the chromosome table (only if not wildcard)
                    if(column.equalsIgnoreCase("chromosome") && !data.equals("%")) {
                        sql3 += term+" c.name LIKE '"+data+"' AND c.sid = su.sid AND su.suid = g.suid";
                        useTable[1] = true;
                    } else if(column.equalsIgnoreCase("marker")) {
                        sql3 += term+" m.name LIKE '"+data+"' AND m.suid = g.suid AND g.mid = m.mid";
                        useTable[0] = true;
                    } else if(column.equalsIgnoreCase("g.level_") && !data.equals("%")) {
                        data =level;
                        if(!data.equals("*"))
                            sql3 += term+" level_ = '"+data+"'";
                    } else if(column.equalsIgnoreCase("allele1") && !data.equals("%")) {
                        sql3 += term+" a.aid = g.aid1 AND a.name LIKE '"+data+"'";
                        useTable[2] = true;
                    } else if(column.equalsIgnoreCase("allele2") && !data.equals("%")) {
                        sql3 += term+" a.aid = g.aid2 AND a.name LIKE '"+data+"'";
                        useTable[2] = true;
                    } else if(column.equalsIgnoreCase("identity") && !data.equals("%")) {
                        sql3 += term+" i.identity LIKE '"+data+"' AND g.iid = i.iid";
                        useTable[3] = true;
                    } else if(column.equalsIgnoreCase("datefrom") && !data.equals("%")) {
                        sql3 += term+" g.date_ >= '"+data+"'";
                    } else if(column.equalsIgnoreCase("dateto") && !data.equals("%")) {
                        sql3 += term+" g.date_ <= '"+data+"'";
                    } else if(column.equalsIgnoreCase("usr") && !data.equals("%")) {
                        sql3 += term+" u.usr LIKE '"+data+"' AND u.id = g.id";
                        useTable[4] = true;
                    } else if(!data.equals("%") && !column.equalsIgnoreCase("ignore.level"))
                        sql3 += term+" "+column+" LIKE '"+data+"'";
                }
            }
            
            if(useTable[3])
                sql3 += " ORDER BY i.identity";
            
            makeConnection();
            
            Statement st = null;
            
            try {
                String sql1 = "SELECT g.iid, g.mid FROM genotypes g";
                String[] tables = {"markers m", "chromosomes c, sampling_units su", "alleles a", "individuals i", "users u"};
                String sql2 = " WHERE ";
                
                for(int i=0;i<useTable.length;i++) {
                    if(useTable[i]) {
                        sql1 += ","+tables[i];
                    }
                }
                st = conn.createStatement();
                
                ResultSet result = st.executeQuery(sql1+sql2+sql3);
                
                String sql = "";
                st = conn.createStatement();
                
                while (result.next()) {
                    sql = "UPDATE genotypes SET level_ = '";
                    sql += pdo.getValue("g.level_") + "' WHERE mid = "+result.getInt("mid")+" AND iid = "+result.getInt("iid");
                    st.execute(sql);
                }
            } catch (Exception se) {
                se.printStackTrace();
                throw new ApplicationException("Unable to update genotype levels."+se.getMessage());
            } finally {
                releaseConnection();
            }
        }
    }
    
    /**
     * Returns a collection of chromosomes for the current species
     * @param sid The species id
     ** @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the chromosomes could not be retrieved
     * @return A collection of chromosomes
     */
    public Collection getChromosomes(int sid, MugenCaller caller) throws ApplicationException {
        validate("GENO_R", caller);
        Collection dto = new ArrayList();
        try{
            ChromosomeRemote cr = null;
            
            //Collection chromosomes = chromosomeHome.findChromosomes(sid);
            Collection chromosomes = chromosomeHome.findBySpecies(sid, caller);
            
            Iterator i = chromosomes.iterator();
            
            while(i.hasNext()){
                cr = (ChromosomeRemote)i.next();
                dto.add(new ChromosomeDTO(cr));
            }
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create list for chromosomes...");
        }
    }
    
    /**
     * Returns all markers for a specific sampling unit
     * @return A collecition of markers
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the markers could not be retrived
     */
    public Collection getAllMarkers(MugenCaller caller) throws ApplicationException {
        validate("MRK_R", caller);
        Collection dto = new ArrayList();
        try{
            MarkerRemote mr = null;
            
            Collection markers = mrh.findAllMarkers(caller.getSuid());
            
            Iterator i = markers.iterator();
            
            while(i.hasNext()){
                mr = (MarkerRemote)i.next();
                dto.add(new MarkerDTO(mr));
            }
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create list for markers...");
        }
    }
    
    /**
     * Returns all markers for a specific sampling unit
     * @param cid The chromosome id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the markers could not be retrived
     * @return A collecition of markers
     */
    public Collection getAllMarkersForChromosome(MugenCaller caller, int cid) throws ApplicationException {
        validate("MRK_R", caller);
        Collection dto = new ArrayList();
        try{
            MarkerRemote mr = null;
            
            Collection markers = mrh.findByChromosome(cid, caller.getSuid());
            
            Iterator i = markers.iterator();
            
            while(i.hasNext()){
                mr = (MarkerRemote)i.next();
                dto.add(new MarkerDTO(mr));
            }
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create list for markers...");
        }
    }    
    
    /**
     * Creates a new genotype
     * @param iid The individual id
     * @param mid The marker id
     * @param comm The comment
     * @param raw1 The raw1 value
     * @param raw2 The raw2 value
     * @param ref The reference
     * @param aid1 The id of allele 1
     * @param aid2 The id of allele 2
     * @param level The level
     * @param suid The sampling unit id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the genotype could not be created
     */
    public void createGenotype(int iid, int mid, java.lang.String comm, java.lang.String raw1, java.lang.String raw2, java.lang.String ref, int aid1, int aid2, int level, int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GENO_W"+level, caller);
        validate("RAW 1", raw1, 20);
        validate("RAW 2", raw2, 20);
        validate("Reference", ref, 32);
        validate("Comment", comm, 256);
        try{
            IndividualRemote ir = irh.findByPrimaryKey(new Integer(iid));
            MarkerRemote mr = mrh.findByPrimaryKey(new Integer(mid));
            
            GenotypeRemote gr = grh.create(ir, mr, raw1, raw2, comm, ref, level, aid1, aid2, suid, caller);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create genotype");
        }
    }
    
    /**
     * Returns the number of markers
     * @param caller The current caller object
     * @param fdm The formdatamanager (new version of paramdataobject). This object holds parameter from the data mining filter
     * @throws com.arexis.mugen.exceptions.ApplicationException If the markers could not be counted
     * @return The number of markers
     */
    public int getNumberOfMarkers(com.arexis.mugen.MugenCaller caller, FormDataManager fdm) throws ApplicationException {
        validate("MRK_R", caller);
        try{
            return mrh.getNumberOfMarkers(fdm);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not count markers...");
        }
    }
    
    /**
     * Returns a specific number of markers depending on the project settings (number of rows to display).
     * @return A collection of markers
     * @param fdm The formdatamanager holding parameters for data mining filter
     * @param pageManager The pagemanager object handling the separation of pages
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the markers could not be retrieved
     */
    public Collection getMarkers(PageManager pageManager, com.arexis.mugen.MugenCaller caller, FormDataManager fdm) throws ApplicationException {
        validate("MRK_R", caller);
        Collection dto = new ArrayList();
        try{
            
            MarkerRemote mr = null;
            
            Collection markers = mrh.findByQuery(fdm, pageManager);
            
            Iterator i = markers.iterator();
            
            while(i.hasNext()){
                mr = (MarkerRemote)i.next();
                dto.add(new MarkerDTO(mr));
            }
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create list for markers...");
        }
    }
    
    /**
     * Returns the markers for the current sampling unit. The sampling unit id is automatically detected.
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the markers could not be retrieved
     * @return The markers for the current sampling unit
     */
    public Collection getMarkers(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_R", caller);
        Collection dto = new ArrayList();
        try{
            
            MarkerRemote mr = null;
            
            Collection markers = mrh.findAllMarkers(caller.getSuid());
            
            Iterator i = markers.iterator();
            
            while(i.hasNext()){
                mr = (MarkerRemote)i.next();
                dto.add(new MarkerDTO(mr));
            }
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not create list for markers...");
        }
    }
    
    /**
     * Returns a specific marker
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker could not be retrieved
     * @return The specified marker
     */
    public MarkerDTO getMarker(int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GENO_R", caller);
        try{
            MarkerRemote mr = mrh.findByPrimaryKey(new Integer(mid));
            
            return new MarkerDTO(mr);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get marker...");
        }
    }
    
    /**
     * Updates an allele
     * @param aid The id of the allele to update
     * @param comm The comment
     * @param name The name
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the allele could not be updated
     */
    public void updateAllele(int aid, String comm, String name, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        try{
            AlleleRemote ar = arh.findByPrimaryKey(new Integer(aid));
            ar.setCaller(caller);
            ar.addHistory();
            ar.setName(name);
            ar.setComm(comm);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update allele");
        }
    }
    
    /**
     * Removes the specified allele
     * @param aid The id of the allele to remove
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the allele could not be removed
     */
    public void removeAllele(int aid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_W", caller);
        
        try{
            AlleleRemote ar = arh.findByPrimaryKey(new Integer(aid));
            ar.remove();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove allele");
        }
    }
    
    /**
     * Creates a new allele
     * @param caller The current caller object
     * @param name The name of the allele
     * @param comm The comment for the allele
     * @param mid The marker id
     * @throws com.arexis.mugen.exceptions.ApplicationException If the allele could not be created
     */
    public void createAllele(com.arexis.mugen.MugenCaller caller, String name, String comm, int mid) throws ApplicationException {
        validate("MRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        int aid = 0;
        try {
            makeConnection();
            aid = getIIdGenerator().getNextId(conn, "alleles_seq");
            AlleleRemote ar = arh.create(aid, mid, name, comm, caller);
        } 
        catch (CreateException e) 
        {
            logger.error("Create of allele "+name+" failed", e);
            throw new com.arexis.mugen.exceptions.CreateException("Could not create a new allele.", e);
        }
        catch (RemoteException e)
        {
            logger.error("RemoteException AlleleRemote", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        finally{
            releaseConnection();
        }
    }
    
    /**
     * Updates an unified allele
     * @param uaid The id of the unified allele to update
     * @param comm The comment
     * @param name The name
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified allele could not be updated
     */
    public void updateUAllele(int uaid, String comm, String name, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        try{
            UAlleleRemote ar = uarh.findByPrimaryKey(new Integer(uaid));
            ar.setCaller(caller);
            ar.addHistory();
            ar.setName(name);
            ar.setComm(comm);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update unified allele");
        }
    }
    
    /**
     * Removes the specified unified allele
     * @param uaid The id of the unified allele to remove
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified allele could not be removed
     */
    public void removeUAllele(int uaid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_W", caller);
        
        try{
            UAlleleRemote ar = uarh.findByPrimaryKey(new Integer(uaid));
            ar.remove();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove unified allele");
        }
    }
    
    /**
     * Creates a new unified allele
     * @param caller The current caller object
     * @param name The name of the unified allele
     * @param comm The comment for the unified allele
     * @param umid The unified marker id
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified allele could not be created
     */
    public void createUAllele(com.arexis.mugen.MugenCaller caller, String name, String comm, int umid) throws ApplicationException {
        validate("UMRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        int uaid = 0;
        try {
            makeConnection();
            uaid = getIIdGenerator().getNextId(conn, "u_alleles_seq");
            
            UAlleleRemote ar = uarh.create(uaid, umid, name, comm, caller);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new unified allele.");
        } finally{
            releaseConnection();
        }
    }
    
    
    /**
     * Updates the specified marker
     * @param mid The marker id
     * @param name The name
     * @param comm The comment
     * @param p1 The first primer
     * @param p2 The second primer
     * @param position The position
     * @param alias The alias
     * @param cid The chromosome id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker could not be updated
     */
    public void updateMarker(int mid, java.lang.String name, java.lang.String comm, java.lang.String p1, java.lang.String p2, double position, java.lang.String alias, int cid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        validate("P1", p1, 40);
        validate("P2", p2, 40);
        validate("Alias", alias, 20);
        try{
            MarkerRemote mr = mrh.findByPrimaryKey(new Integer(mid));
            mr.setCaller(caller);
            mr.addHistory();
            mr.setName(name);
            mr.setComm(comm);
            mr.setAlias(alias);
            mr.setP1(p1);
            mr.setP2(p2);
            mr.setPosition(position);
            mr.setCid(cid);
        } 
        catch (RemoteException e)
        {
            logger.error("Remote exception");
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        catch (FinderException e)
        {
            throw new MarkerNotFoundException("Cannot find marker "+name);
        }
        /*
        catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update marker");
        }
         */
    }
    
    public boolean markerExists(String name, int suid, MugenCaller caller)
    {
        try
        {
            //logger.debug("markerExists, name="+name+", suid="+suid);
            MarkerRemote m = mrh.findByNameSamplingUnit(name, suid);
            if (m!=null)
                return true;
        } 
        catch (RemoteException e)
        {
            logger.error("Remote exception", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        catch (FinderException e)
        {
        }
        return false;
    }
    
    public int createOrUpdateMarkerImport(String name, String comm, String p1, String p2, String position, String alias, String chromosome, int suid, MugenCaller caller) throws ApplicationException    
    {
        logger.info("createOrUpdate called");
        int mid = 0;
        try
        {
            mid = updateMarkerImport(name, comm, p1, p2, position, alias, chromosome, suid, caller);
            logger.info("update done");
        }
        catch (ApplicationException e)
        {
            //iid = createNewIndividual(suid, caller, identity, alias, sex, father, mother, birthdate, comm);
            mid = createMarkerImport(name,comm,p1,p2, position, alias, chromosome, suid, caller);
            logger.info("create done");
        }
        return mid;
    }
    
    public int updateMarkerImport(String name, String comm, String p1, String p2, String position, String alias, String chromosome, int suid, MugenCaller caller) throws ApplicationException
    {
        SamplingUnitRemote su = null;
        ChromosomeRemote chr = null;
        MarkerRemote marker = null;
        try
        {
            double pos = new Double(position).doubleValue();
            su = surh.findByPrimaryKey(new Integer(suid));
            chr = chromosomeHome.findBySpeciesAndName(chromosome, su.getSpecies().getSid());
            marker = mrh.findByNameSamplingUnit(name, suid);
         
            updateMarker(marker.getMid(), name, comm, p1, p2, pos, alias, chr.getCid(), caller);
            return marker.getMid();
        } 
        catch (NumberFormatException ex)
        {
            logger.error("position value is not a double", ex);
            throw new IllegalValueException("Failed to handle position value. Not a number.",ex);
        } 
        catch (FinderException ex)
        {
            logger.error("Failed to find object", ex);
            if (su==null)
                throw new SamplingUnitNotFoundException("Could not find sampling unit suid="+suid, ex);
            else if (chr==null)
                throw new ChromosomeNotFoundException("Could not get chromosome", ex);
            else if (marker==null)
                throw new MarkerNotFoundException("Could not find marker", ex);
        }
        catch (RemoteException ex)
        {
            logger.error("Remote exception", ex);
            throw ExceptionLogUtil.createLoggableEJBException(ex);
        } 
        return -1;
    }
    
    public int createMarkerImport(String name, String comm, String p1, String p2, String position, String alias, String chromosome, int suid, MugenCaller caller) throws ApplicationException
    {
        int mid = 0;
        SamplingUnitRemote su = null;
        ChromosomeRemote chr = null;
        try
        {
            double pos = new Double(position).doubleValue();
            su = surh.findByPrimaryKey(new Integer(suid));
            chr = chromosomeHome.findBySpeciesAndName(chromosome, su.getSpecies().getSid());
            
            mid = createMarker(name, comm, p1, p2, pos, alias, chr.getCid(), suid, caller);
        } 
        catch (NumberFormatException ex)
        {
            logger.error("position value is not a double", ex);
            throw new IllegalValueException("Failed to handle position value. Not a number.",ex);
        } 
        catch (FinderException ex)
        {
            logger.error("Failed to find object", ex);
            if (su==null)
                throw new SamplingUnitNotFoundException("Could not find sampling unit suid="+suid, ex);
            else if (chr==null)
                throw new ChromosomeNotFoundException("Could not get chromosome", ex);
        }
        catch (RemoteException ex)
        {
            logger.error("Remote exception", ex);
            throw ExceptionLogUtil.createLoggableEJBException(ex);
        } 
        return mid;
    }
    
    /**
     * Creates a new marker
     * @param name The name
     * @param comm The comment
     * @param p1 The first primer
     * @param p2 The second primer
     * @param position The position
     * @param alias The alias
     * @param cid The chromosome id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker could not be created
     */
    public int createMarker(java.lang.String name, java.lang.String comm, java.lang.String p1, java.lang.String p2, double position, java.lang.String alias, int cid, int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        validate("P1", p1, 40);
        validate("P2", p2, 40);
        validate("Alias", alias, 20);
        int mid = 0;
        try {
            makeConnection();
            mid = getIIdGenerator().getNextId(conn, "markers_seq");
            
            MarkerRemote mr = mrh.create(mid, suid, cid, name, comm, alias, position, p1, p2, caller);
        } 
        catch (CreateException e) 
        {
            logger.error("Failed to create marker", e);
            throw new com.arexis.mugen.exceptions.CreateException("Could not create the new marker "+name,e);
        }
        catch (RemoteException e)
        {
            logger.error("RemoteException in createMarker", e);
            throw ExceptionLogUtil.createLoggableEJBException(e);
        }
        finally
        {
            releaseConnection();
        }
        return mid;
    }
    
    /**
     * Removes the specified marker
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker could not be removed
     */
    public void removeMarker(int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_W", caller);
        
        try{
            MarkerRemote mr = mrh.findByPrimaryKey(new Integer(mid));
            mr.remove();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove marker");
        }
    }
    
    /**
     * Updates the specified unified marker
     * @param umid The unified marker id
     * @param name The name
     * @param comm The comment
     * @param position The position
     * @param alias The alias
     * @param cid The chromosome id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker could not be updated
     */
    public void updateUMarker(int umid, java.lang.String name, java.lang.String comm, double position, java.lang.String alias, int cid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        validate("Alias", alias, 20);
        try{
            UMarkerRemote mr = umrh.findByPrimaryKey(new Integer(umid));
            mr.setCaller(caller);
            mr.addHistory();
            mr.setName(name);
            mr.setComm(comm);
            mr.setAlias(alias);
            mr.setPosition(position);
            mr.setCid(cid);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update unified marker");
        }
    }
    
    /**
     * Creates a new unified marker
     * @param sid The species id
     * @param name The name
     * @param comm The comment
     * @param position The position
     * @param alias The alias
     * @param cid The chromosome id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker could not be created
     */
    public void createUMarker(java.lang.String name, java.lang.String comm, double position, java.lang.String alias, int cid, com.arexis.mugen.MugenCaller caller, int sid) throws ApplicationException {
        validate("UMRK_W", caller);
        validate("UMRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        validate("Alias", alias, 20);
        int umid = 0;
        try {
            makeConnection();
            umid = getIIdGenerator().getNextId(conn, "u_markers_seq");
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));
            
            UMarkerRemote mr = umrh.create(umid, name, alias, comm, position, caller.getPid(), sid, cid, caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new unified marker.");
        } finally{
            releaseConnection();
        }
    }
    
    /**
     * Removes the specified unified marker
     * @param umid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker could not be removed
     */
    public void removeUMarker(int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_W", caller);
        
        try{
            UMarkerRemote mr = umrh.findByPrimaryKey(new Integer(umid));
            mr.remove();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove unified marker");
        }
    }
    
    /**
     * Returns the number of marker sets
     * @param caller The current caller object
     * @param fdm The formdatamanager used to generate the data mining filter
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker sets could not be retrieved
     * @return The number of marker sets
     */
    public int getNumberOfMarkerSets(com.arexis.mugen.MugenCaller caller, FormDataManager fdm) throws ApplicationException {
        validate("MRKS_R", caller);
        try{
            Collection markersets = msrh.findByQuery(fdm);
            return markersets.size();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not count marker sets...");
        }
    }
    
    
    /**
     * Returns a number of marker sets. The number of marker sets depends on the project settings (number of rows on page).
     * @return A number of marker sets
     * @param fdm The formdatamanager used to generate the data mining filter
     * @param pageManager The pagemanager object, handling the page separation in views
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker sets could not be retrieved
     */
    public Collection getMarkerSets(PageManager pageManager, com.arexis.mugen.MugenCaller caller, FormDataManager fdm) throws ApplicationException {
        validate("MRKS_R", caller);
        Collection dto = new ArrayList();
        try{
            int index = 0;
            
            MarkerSetRemote mr = null;
            
            Collection markersets = msrh.findByQuery(fdm);
            
            Iterator i = markersets.iterator();
            
            SamplingUnitRemote sur = null;
            
            UserRemote ur = null;
            
            SpeciesRemote sr = null;
            
            pageManager.setMax(markersets.size());
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            
            while(i.hasNext()){
                index++;
                
                if (index>=start && index<=stop) {
                    mr = (MarkerSetRemote)i.next();
                    dto.add(new MarkerSetDTO(mr));
                } else {
                    // Return if we have enough data
                    if(dto.size() == pageManager.getDelta())
                        return dto;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get marker sets...");
        }
    }
    
    /**
     * Returns all marker sets for a specific sampling unit
     * @param suid The sampling unit id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker sets could not be retrieved
     * @return A collection of marker sets
     */
    public Collection getMarkerSets(int suid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_R", caller);
        Collection dto = new ArrayList();
        try{
            MarkerSetRemote mr = null;
            
            Collection markersets = msrh.findMarkerSets(suid);
            
            Iterator i = markersets.iterator();
            
            SamplingUnitRemote sur = null;
            
            UserRemote ur = null;
            
            SpeciesRemote sr = null;
            
            while(i.hasNext()){
                mr = (MarkerSetRemote)i.next();
                dto.add(new MarkerSetDTO(mr));
            }
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get marker sets...");
        }
    }
    
    /**
     * Returns a specific marker set
     * @param msid The marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker set could nit be retrieved
     * @return A collection of marker sets
     */
    public MarkerSetDTO getMarkerSet(int msid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_R", caller);
        
        try{
            MarkerSetRemote mr = msrh.findByPrimaryKey(new Integer(msid));
            return new MarkerSetDTO(mr);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get marker set...");
        }
    }
    
    
    /**
     * Updates a marker set
     * @param msid The marker set id
     * @param name The name
     * @param comm The comment
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker set could not be updated
     */
    public void updateMarkerSet(int msid, java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_W", caller);
        validate("UMRK_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        
        try{
            MarkerSetRemote mr = msrh.findByPrimaryKey(new Integer(msid));
            mr.setCaller(caller);
            mr.addHistory();
            mr.setName(name);
            mr.setComm(comm);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update marker set");
        }
    }
    
    /**
     * Creates a new marker set
     * @param name The name for the marker set
     * @param comm The comment for the marker set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker set could not be created
     */
    public void createMarkerSet(java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        
        int msid = 0;
        try {
            makeConnection();
            msid = getIIdGenerator().getNextId(conn, "marker_sets_seq");
            
            MarkerSetRemote mr = msrh.create(msid, caller.getSuid(), name, comm, caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new marker set.");
        } finally{
            releaseConnection();
        }
    }
    
    /**
     * Removes the marker set
     * @param msid The marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker set could not be removed
     */
    public void removeMarkerSet(int msid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_W", caller);
        try{
            MarkerSetRemote mr = msrh.findByPrimaryKey(new Integer(msid));
            mr.remove();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove marker set");
        }
    }
    
    
    /**
     * Returns the positions for the specified marker set
     * @return The positions for the marker set
     * @param pageManager The pagemanager handling page display in the views
     * @param msid The marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the positions could not be retrieved
     */
    public Collection getMarkerSetPositions(int msid, com.arexis.mugen.MugenCaller caller, PageManager pageManager) throws ApplicationException {
        validate("MRKS_R", caller);
        makeConnection();
        Collection dto = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT MNAME, MID, CNAME, DEF_POSITION," +
                    " OVER_POSITION, POSITION FROM V_POSITIONS_2 WHERE MSID = ? " +
                    "ORDER BY TO_NUMBER_ELSE_NULL(CNAME), CNAME, POSITION, MNAME");
            ps.setInt(1, msid);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                dto.add(new PositionDTO(rs.getString("MNAME"), rs.getString("CNAME"),
                        rs.getInt("MID"), msid, rs.getDouble("POSITION"),
                        rs.getDouble("DEF_POSITION"), rs.getDouble("OVER_POSITION")));
            }
            
            pageManager.setMax(dto.size());
            
            return dto;
        } catch (SQLException se) {
            throw new ApplicationException("Failed to get information regarding positions\n"+se.getMessage());
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns the position for the specfied marker and marker set
     * @param msid The marker set id
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the position could not be retrieved
     * @return The position
     */
    public PositionDTO getMarkerSetPosition(int msid, int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_R", caller);
        makeConnection();
        PositionDTO dto = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT MNAME, MID, CNAME, DEF_POSITION," +
                    " OVER_POSITION, POSITION FROM V_POSITIONS_2 WHERE MSID = ? " +
                    " AND MID = ? ORDER BY TO_NUMBER_ELSE_NULL(CNAME), CNAME, POSITION, MNAME");
            ps.setInt(1, msid);
            ps.setInt(2, mid);
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
                dto = new PositionDTO(rs.getString("MNAME"), rs.getString("CNAME"),
                        rs.getInt("MID"), msid, rs.getDouble("POSITION"),
                        rs.getDouble("DEF_POSITION"), rs.getDouble("OVER_POSITION"));
            }
            
            
            return dto;
        } catch (SQLException se) {
            throw new ApplicationException("Failed to get information regarding position");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Updated the value of a position
     * @param position The new position
     * @param msid The marker set id
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the position could not be updated
     */
    public void updateMarkerSetPosition(int msid, int mid, double position, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_W", caller);
        makeConnection();
        try {
            PreparedStatement ps = null;
            ps = conn.prepareStatement("update positions set value = ? where msid = ? and mid = ?");
            ps.setDouble(1, position);
            ps.setInt(2, msid);
            ps.setInt(3, mid);
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to update marker set position for marker set:"+msid+", marker:"+mid);
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns the positions for the specified unified marker set
     * @return The positions for the unified marker set
     * @param pageManager The pagemanager handling page display in the views
     * @param umsid The unified marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the positions could not be retrieved
     */
    public Collection getUMarkerSetPositions(int umsid, com.arexis.mugen.MugenCaller caller, PageManager pageManager) throws ApplicationException {
        validate("UMRKS_R", caller);
        makeConnection();
        Collection dto = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT UMNAME, UMID, CNAME, DEF_POSITION," +
                    " OVER_POSITION, POSITION FROM V_U_POSITIONS_2 WHERE UMSID = ? " +
                    "ORDER BY TO_NUMBER_ELSE_NULL(CNAME), CNAME, POSITION, UMNAME");
            ps.setInt(1, umsid);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                dto.add(new UPositionDTO(rs.getString("UMNAME"), rs.getString("CNAME"), rs.getInt("UMID"), umsid, rs.getDouble("POSITION"), rs.getDouble("DEF_POSITION"), rs.getDouble("OVER_POSITION")));
            }
            
            pageManager.setMax(dto.size());
            
            return dto;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ApplicationException("Failed to get information regarding positions");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns the position for the specfied unified marker and unified marker set
     * @param umsid The unified marker set id
     * @param umid The unified marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the position could not be retrieved
     * @return The position
     */
    public UPositionDTO getUMarkerSetPosition(int umsid, int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_R", caller);
        makeConnection();
        UPositionDTO dto = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT UMNAME, UMID, CNAME, DEF_POSITION," +
                    " OVER_POSITION, POSITION FROM V_U_POSITIONS_2 WHERE UMSID = ? " +
                    " AND UMID = ? ORDER BY TO_NUMBER_ELSE_NULL(CNAME), CNAME, POSITION, UMNAME");
            ps.setInt(1, umsid);
            ps.setInt(2, umid);
            
            rs = ps.executeQuery();
            
            if(rs.next()) {
                dto = new UPositionDTO(rs.getString("UMNAME"), rs.getString("CNAME"), rs.getInt("UMID"), umsid, rs.getDouble("POSITION"), rs.getDouble("DEF_POSITION"), rs.getDouble("OVER_POSITION"));
            }
            
            return dto;
        } catch (SQLException se) {
            se.printStackTrace();
            throw new ApplicationException("Failed to get information regarding position");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Updated the value of a position
     * @param position The new position
     * @param umsid The unified marker set id
     * @param umid The unified marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the position could not be updated
     */
    public void updateUMarkerSetPosition(int umsid, int umid, double position, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_W", caller);
        makeConnection();
        try {
            PreparedStatement ps = null;
            ps = conn.prepareStatement("update u_positions set value = ? where umsid = ? and umid = ?");
            ps.setDouble(1, position);
            ps.setInt(2, umsid);
            ps.setInt(3, umid);
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to update unified marker set position for unified marker set:"+umsid+", marker:"+umid);
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Adds a marker to a marker set
     * @param msid The marker set id
     * @param mid The marker to add to the set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker could not be added to the set
     */
    public void addMarkerInSet(int msid, int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_W", caller);
        makeConnection();
        try {
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into positions (msid, mid, value, id, ts) values (?, ?, ?, ?, ?)");
            
            ps.setInt(1, msid);
            ps.setInt(2, mid);
            ps.setDouble(3, 0);
            ps.setInt(4, caller.getId());
            ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to add marker to set");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Removes a marker from a set
     * @param msid The marker set id
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the marker could not be removed from the set
     */
    public void removeMarkerInSet(int msid, int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_W", caller);
        makeConnection();
        try {
            PreparedStatement ps = null;
            ps = conn.prepareStatement("delete from positions where msid = ? and mid = ?");
            
            ps.setInt(1, msid);
            ps.setInt(2, mid);
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to remove marker from set");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Adds an unified marker to a unified marker set
     * @param umsid The unified marker set id
     * @param umid The unified marker to add to the set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker could not be added to the set
     */
    public void addUMarkerInSet(int umsid, int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_W", caller);
        makeConnection();
        try {
            PreparedStatement ps = null;
            ps = conn.prepareStatement("insert into u_positions (umsid, umid, value, id, ts) values (?, ?, ?, ?, ?)");
            
            ps.setInt(1, umsid);
            ps.setInt(2, umid);
            ps.setDouble(3, 0);
            ps.setInt(4, caller.getId());
            ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to add unified marker to set");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Removes an unified marker from a set
     * @param umsid The marker set id
     * @param umid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker could not be removed from the set
     */
    public void removeUMarkerInSet(int umsid, int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_W", caller);
        makeConnection();
        try {
            PreparedStatement ps = null;
            ps = conn.prepareStatement("delete from u_positions where umsid = ? and umid = ?");
            
            ps.setInt(1, umsid);
            ps.setInt(2, umid);
            
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Failed to remove unified marker from set");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Returns all unified markers for the specified chromosome
     * @return A collection of unified markers
     * @param formdata The formdatamanger used when generating data mining filter
     * @param pageManager The pagemanager object controlling page separation
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified markers could not be retrieved
     */
    public Collection getUMarkers(PageManager pageManager, com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("UMRK_R", caller);
        Collection dto = new ArrayList();
        try{
            UMarkerRemote mr = null;
            
            Collection umarkers = umrh.findByQuery(formdata);
            
            Iterator i = umarkers.iterator();
            
            UserRemote ur = null;
            
            SpeciesRemote sr = null;
            
            ChromosomeRemote cr = null;
            
            pageManager.setMax(umarkers.size());
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while(i.hasNext()){
                index++;
                
                if (index>=start && index<=stop) {
                    mr = (UMarkerRemote)i.next();
                    dto.add(new UMarkerDTO(mr));
                } else {
                    // Return if we have enough data
                    if(dto.size() == pageManager.getDelta())
                        return dto;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get unified markers...");
        }
    }
    
    /**
     * Returns all unified markers for the specified chromosome
     * @return A collection of unified markers
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified markers could not be retrieved
     */
    public Collection getUMarkers(com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_R", caller);
        Collection dto = new ArrayList();
        try{
            UMarkerRemote mr = null;
            
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));
            
            Collection umarkers = umrh.findUMarkers(caller.getPid());
            
            Iterator i = umarkers.iterator();
            
            UserRemote ur = null;
            
            SpeciesRemote sr = null;
            
            ChromosomeRemote cr = null;
            
            while(i.hasNext()){
                mr = (UMarkerRemote)i.next();
                dto.add(new UMarkerDTO(mr));
            }
            
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get unified markers...");
        }
    }
    
    
    /**
     * Returns the number of unified markers in the database
     * @param caller The current caller object
     * @param formdata The formdatamanger used when generating data mining filter
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified markers could not be counted
     * @return The number of unified markers
     */
    public int getNumberOfUMarkers(com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("UMRK_R", caller);
        
        try{
            Collection umarkers = umrh.findByQuery(formdata);
            return umarkers.size();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not count unified markers...");
        }
    }
    
    /**
     * Returns a specific unified marker
     * @param umid The unified marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker could not be retrieved
     * @return An unified marker
     */
    public UMarkerDTO getUMarker(int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_R", caller);
        UMarkerDTO dto = null;
        try{
            UMarkerRemote mr = umrh.findByPrimaryKey(new Integer(umid));
            dto = new UMarkerDTO(mr);
            
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get unified marker...");
        }
    }
    
    /**
     * Updates a unified marker set
     * @param umsid The unified marker set id
     * @param name The name
     * @param comm The comment
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker set could not be updated
     */
    public void updateUMarkerSet(int umsid, java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        try{
            UMarkerSetRemote mr = umsrh.findByPrimaryKey(new Integer(umsid));
            mr.setCaller(caller);
            mr.addHistory();
            mr.setName(name);
            mr.setComm(comm);
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not update unified marker set");
        }
    }
    
    /**
     * Creates a new unified marker set
     * @param sid The species id
     * @param name The name for the unified marker set
     * @param comm The comment for the unified marker set
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker set could not be created
     */
    public void createUMarkerSet(java.lang.String name, java.lang.String comm, com.arexis.mugen.MugenCaller caller, int sid) throws ApplicationException {
        validate("UMRKS_W", caller);
        validate("Name", name, 20);
        validate("Comment", comm, 256);
        int umsid = 0;
        try {
            makeConnection();
            umsid = getIIdGenerator().getNextId(conn, "u_marker_sets_seq");
            
            
            SamplingUnitRemote sur = surh.findByPrimaryKey(new Integer(caller.getSuid()));
            
            UMarkerSetRemote mr = umsrh.create(umsid, name, comm, caller.getPid(), sur.getSpecies().getSid(), caller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException("Could not create a new unified marker set.");
        } finally{
            releaseConnection();
        }
    }
    
    /**
     * Removes the unified marker set
     * @param umsid The unified marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker set could not be removed
     */
    public void removeUMarkerSet(int umsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_W", caller);
        
        try{
            UMarkerSetRemote mr = umsrh.findByPrimaryKey(new Integer(umsid));
            mr.remove();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not remove unified marker set");
        }
    }
    
    /**
     * Returns the mapping for the specified unified marker
     * @param umid The unified marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the mapping could not be retrived
     * @return The mapping for the unified marker
     */
    public Collection getUMarkerMapping(int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_R", caller);
        makeConnection();
        Collection dto = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement("select * from v_r_umid_mid_2 where umid = ?");
            ps.setInt(1, umid);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                dto.add(new UMarkerMappingDTO(rs.getString("suname"), rs.getString("mname"), rs.getInt("umid"), rs.getInt("mid")));
            }
            
            return dto;
        } catch (SQLException se) {
            throw new ApplicationException("Failed to get information regarding unified marker mapping");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Deletes an unified marker mapping
     * @param umid The unified marker id
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the mapping could not be deleted
     */
    public void deleteUMarkerMapping(int umid, int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_W", caller);
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("delete from r_umid_mid where umid = ? and mid = ?");
            ps.setInt(1, umid);
            ps.setInt(2, mid);
            
            ps.execute();
        } catch (SQLException se) {
            throw new ApplicationException("Failed to delete unified marker mapping");
        } finally {
            releaseConnection();
        }
    }
    
    /**
     * Creates a new unified marker mapping
     * @param umid The unified marker id
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the mapping could not be created
     */
    public void createUMarkerMapping(int umid, int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_W", caller);
        makeConnection();
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement("insert into r_umid_mid (umid, mid, ts) values (?, ?, ?)");
            ps.setInt(1, umid);
            ps.setInt(2, mid);
            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            
            ps.execute();
        } catch (SQLException se) {
            throw new ApplicationException("Failed to create unified marker mapping");
        } finally {
            releaseConnection();
        }
    }
    
    
    /**
     * Returns the number of unified marker sets
     * @param caller The current caller object
     * @param formdata The formdatamanager used when generating data mining filter
     * @throws com.arexis.mugen.exceptions.ApplicationException If the number of unified marker sets could not be counted
     * @return The number of unified marker sets
     */
    public int getNumberOfUMarkerSets(com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("UMRKS_R", caller);
        
        try{
            Collection umarkersets = umsrh.findByQuery(formdata);
            return umarkersets.size();
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not count unified marker sets...");
        }
    }
    
    /**
     * Returns a number of unified marker sets. The number of sets depends on the project settings (rows per page).
     * @return A collection of unified marker sets
     * @param formdata The formdatamanager used when generating the data mining filter
     * @param pageManager The pagemanager responsible for data separation into pages
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker sets could not be retrieved
     */
    public Collection getUMarkerSets(PageManager pageManager, com.arexis.mugen.MugenCaller caller, FormDataManager formdata) throws ApplicationException {
        validate("UMRKS_R", caller);
        Collection dto = new ArrayList();
        try{
            UMarkerSetRemote mr = null;
            
            Collection umarkersets = umsrh.findByQuery(formdata);
            
            Iterator i = umarkersets.iterator();
            
            UserRemote ur = null;
            
            SpeciesRemote sr = null;
            
            int start = pageManager.getStart();
            int stop = pageManager.getStop();
            int index = 0;
            
            while(i.hasNext()){
                index++;
                
                if (index>=start && index<=stop) {
                    mr = (UMarkerSetRemote)i.next();
                    sr = speciesHome.findByPrimaryKey(new Integer(mr.getSid()));
                    ur = userHome.findByPrimaryKey(new Integer(mr.getId()));
                    dto.add(new UMarkerSetDTO(mr, ur, sr));
                } else {
                    // Return if we have enough data
                    if(dto.size() == pageManager.getDelta())
                        return dto;
                    // Skip this object. This is outside the interval
                    i.next();
                }
            }
            
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get unified marker sets...");
        }
    }
    
    /**
     * Returns the unified marker sets.
     * @param caller The current caller object
     ** @param sid The species id
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker sets could not be retrieved
     * @return A collection of unified marker sets
     */
    public Collection getUMarkerSets(com.arexis.mugen.MugenCaller caller, int sid) throws ApplicationException {
        validate("UMRKS_R", caller);
        Collection dto = new ArrayList();
        try{
            UMarkerSetRemote mr = null;
            
            Collection umarkersets = umsrh.findUMarkerSets(sid, caller.getPid());
            
            Iterator i = umarkersets.iterator();
            
            UserRemote ur = null;
            
            SpeciesRemote sr = null;
            
            while(i.hasNext()){
                mr = (UMarkerSetRemote)i.next();
                sr = speciesHome.findByPrimaryKey(new Integer(mr.getSid()));
                ur = userHome.findByPrimaryKey(new Integer(mr.getId()));
                dto.add(new UMarkerSetDTO(mr, ur, sr));
            }
            
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get unified marker sets...");
        }
    }
    
    /**
     * Returns a specific unified marker set
     * @param umsid The unified marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the unified marker set could not be retrieved
     * @return The specified unified marker set, given that it was found
     */
    public UMarkerSetDTO getUMarkerSet(int umsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_R", caller);
        UMarkerSetDTO dto = null;
        try{
            UMarkerSetRemote mr = umsrh.findByPrimaryKey(new Integer(umsid));
            
            UserRemote ur = null;
            
            SpeciesRemote sr = null;
            
            sr = speciesHome.findByPrimaryKey(new Integer(mr.getSid()));
            ur = userHome.findByPrimaryKey(new Integer(mr.getId()));
            dto = new UMarkerSetDTO(mr, ur, sr);
            
            return dto;
        } catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("Could not get unified marker set...");
        }
    }
    
    /**
     * Returns the history for a genotype
     * @param iid The individual id
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the genotype
     */
    public Collection getGenotypeHistory(int iid, int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("GENO_R", caller);
        try {
            GenotypeRemote gr = grh.findByPrimaryKey(new GenotypePk(mid, iid));
            
            return gr.getHistory();
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find genotype history.");
        }
    }
    
    /**
     * Returns the history for an allele
     * @param aid The allele id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the allele
     */
    public Collection getAlleleHistory(int aid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_R", caller);
        try {
            AlleleRemote ar = arh.findByPrimaryKey(new Integer(aid));
            
            return ar.getHistory();
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find allele history.");
        }
    }
    
    /**
     * Returns the history for the marker
     * @param mid The marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the marker
     */
    public Collection getMarkerHistory(int mid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRK_R", caller);
        try {
            MarkerRemote mr = mrh.findByPrimaryKey(new Integer(mid));
            
            return mr.getHistory();
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find marker history.");
        }
    }
    
    /**
     * Returns the history for the marker set
     * @param msid The marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the marker set
     */
    public Collection getMarkerSetHistory(int msid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("MRKS_R", caller);
        try {
            MarkerSetRemote mr = msrh.findByPrimaryKey(new Integer(msid));
            
            return mr.getHistory();
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find marker set history.");
        }
    }
    
    /**
     * Returns the unified marker history
     * @param umid The unified marker id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the unified marker
     */
    public Collection getUMarkerHistory(int umid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_R", caller);
        try {
            UMarkerRemote mr = umrh.findByPrimaryKey(new Integer(umid));
            
            return mr.getHistory();
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find unified marker history.");
        }
    }
    
    /**
     * Returns the history for the unified marker set
     * @param umsid The unified marker set id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the unified marker set
     */
    public Collection getUMarkerSetHistory(int umsid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRKS_R", caller);
        try {
            UMarkerSetRemote mr = umsrh.findByPrimaryKey(new Integer(umsid));
            
            return mr.getHistory();
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find unified marker set history.");
        }
    }
    
    /**
     * Returns the history for the unified allele
     * @param uaid The unified allele id
     * @param caller The current caller object
     * @throws com.arexis.mugen.exceptions.ApplicationException If the history could not be retrieved
     * @return The history for the unified allele
     */
    public Collection getUAlleleHistory(int uaid, com.arexis.mugen.MugenCaller caller) throws ApplicationException {
        validate("UMRK_R", caller);
        try {
            UAlleleRemote mr = uarh.findByPrimaryKey(new Integer(uaid));
            
            return mr.getHistory();
        } catch (Exception se) {
            se.printStackTrace();
            throw new ApplicationException("Unable to find unified allele history.");
        }
    }
    
    
    // Used in GenotypeInheritanceCheckView    
    /**
     * Returns the marker with the specified id from the collection of markers
     * @param markers The collection of markers
     * @param id The id of the marker to retrieve
     * @return The marker with the specified id
     */
    public MarkerDTO getMarker(Collection markers, int id) {
        MarkerDTO dto = null;
        Iterator i = markers.iterator();
        if(markers.size() > 0) {   
            while(i.hasNext()) {
                dto = (MarkerDTO)i.next();            
                if(dto.getMid() == id) {
                    return dto;                
                }
            }
        }
        
        return null;
    }
    
    // Used in GenotypeInheritanceCheckView    
    /**
     * Returns the previously selected markers plus the selected markers from the available collection
     * @param available The available markers
     * @param previous The previously selected markers
     * @param selected The selected markers
     * @return The selected markers plus the previously selected markers
     */
    public Collection getChoosenMarkers(Collection available, Collection previous, String[] selected) {
        ArrayList retList = new ArrayList();
        Iterator i = previous.iterator();
        // Add the ones already selected
        while(i.hasNext()) {
            retList.add(i.next());
        }
        
        MarkerDTO dto = null;
        
        // Add the newly selected
        for(int j=0;j<selected.length;j++) {
            dto = getMarker(available, Integer.parseInt(selected[j]));
            if(dto != null && !retList.contains(dto))
                retList.add(dto);
        }  
        
        return retList;
    }
    
    // Used in GenotypeInheritanceCheckView
    /**
     * Removes the selected markers from the previously selected marker collection
     * @param available The available markers
     * @param previous The previously selected markers
     * @param selected The selected markers
     * @return The previously selected markers minus the markers to remove
     */
    public Collection removeChoosenMarkers(Collection available, Collection previous, String[] selected) {
        ArrayList retList = new ArrayList();
        Iterator i = previous.iterator();
        MarkerDTO dto = null;
        boolean doRemove = false;
        // Add the ones already selected
        while(i.hasNext()) {
            dto = (MarkerDTO)i.next();
            for(int j=0;j<selected.length;j++) {
                if(Integer.parseInt(selected[j]) == dto.getMid())
                    doRemove = true;
            }
            if(!doRemove)
                retList.add(dto);
            doRemove = false;
        }

        
        return retList;
    }  
    
    // Used in GenotypeInheritanceCheckView    
    /**
     * Removes the choosen groups from the selected groups
     * @param available The available groups
     * @param previous The previously selected groups
     * @param selected The selected groups to remove
     * @return The previously selected groups minus the groups to remove
     */
    public Collection removeChoosenGroups(Collection available, Collection previous, String[] selected) {
        ArrayList retList = new ArrayList();
        Iterator i = previous.iterator();
        GroupDTO dto = null;
        boolean doRemove = false;
        // Add the ones already selected
        while(i.hasNext()) {
            dto = (GroupDTO)i.next();
            for(int j=0;j<selected.length;j++) {
                if(Integer.parseInt(selected[j]) == dto.getGid())
                    doRemove = true;
            }
            if(!doRemove)
                retList.add(dto);
            doRemove = false;
        }

        
        return retList;
    }       
    
    // Used in GenotypeInheritanceCheckView    
    /**
     * Returns the group with the specified id from the collection of groups
     * @param groups The collection of groups
     * @param id The id of the group to retrieve
     * @return The group with the specified id
     */
    public GroupDTO getGroup(Collection groups, int id) {
        GroupDTO dto = null;
        Iterator i = groups.iterator();
        if(groups.size() > 0) {   
            while(i.hasNext()) {
                dto = (GroupDTO)i.next();            
                if(dto.getGid() == id) {
                    return dto;                
                }
            }
        }
        
        return null;
    }    
       
    // Used in GenotypeInheritanceCheckView    
    /**
     * Returns the previously selected groups plus the selected groups
     * @param available The available groups
     * @param previous The previously selected groups
     * @param selected The selected groups
     * @return The previously selected groups plus the selected groups
     */
    public Collection getChoosenGroups(Collection available, Collection previous, String[] selected) {
        ArrayList retList = new ArrayList();
        Iterator i = previous.iterator();
        // Add the ones already selected
        while(i.hasNext()) {
            retList.add(i.next());
        }
        
        GroupDTO dto = null;
        
        // Add the newly selected
        for(int j=0;j<selected.length;j++) {
            dto = getGroup(available, Integer.parseInt(selected[j]));
            if(dto != null && !retList.contains(dto))
                retList.add(dto);
        }  
        
        return retList;
    }      
}

