package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.model.researchapplication.ResearchApplicationRemote;
import com.arexis.mugen.model.strain.strain.StrainRemote;
import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.project.user.UserRemote;
import com.arexis.mugen.resource.file.FileRemote;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ExpModelDTO implements Serializable, Comparable {
    private String accNr, lineName, contactName, background, comm, contactMail;
    private int eid, contactId, userId, researchAppId, genotyping, handling, phenotypes, suid;
    private String user, participant, mutations, groupName;
    private String ts;
    private String researchAppText;
    private String researchAppType;
    private String availability;
    private StrainDTO strain;
    private int level;
    private int desired_level;
    private String designation;
    private String strainType;
    private Collection availabilityDTOs;
    
    //string to host the institutions url
    private String insturl;
    
    //strings for allele info
    private String Allelemgiid, Allelesymbol, Allelename, Allelemutations, Allelemutationabbrs;
    
    //strings for gene info
    private String geneChromosome, geneMgiid, geneSymbol, geneName;
    
    //strings for genetic background information
    private String backcrossingStrain, backcrossesNumber;
    
    //to check if something is a transgene
    private int DistParam=0;
    
    /**
     * Creates a new instance of ExpModelDTO
     * @param model The model
     */
    public ExpModelDTO(ExpModelRemote model) {
        try {
            this.accNr = model.getIdentity();
            this.lineName = model.getAlias().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.lineName = this.lineName.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");

            UserRemote contact = model.getContact();
            contactName="";
            contactId=0;
            groupName = "";
            if (contact!=null)
            {
                this.contactName = contact.getName();
                this.contactId = contact.getId();
                this.groupName = contact.getGroupName();
                this.contactMail = contact.getEmail();
                this.insturl = contact.getGroupLink().getUrl();
            }

            this.user = model.getUser().getUsr();
            //this.participant = model.getUser().getGroupName();
            this.participant = model.getContact().getGroupName();
            this.userId = model.getUser().getId();
            this.ts = model.getTs().toString();
            this.eid = model.getEid();
            
            this.researchAppText = model.getResearchApplicationText().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.researchAppText = this.researchAppText.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.background = model.getGeneticBackground().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.background = this.background.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.availability = model.getAvailability().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.availability = this.availability.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.comm = model.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.comm = this.comm.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.phenotypes = model.getNumberOfPhenotypes();
            this.suid = model.getSamplingUnit().getSuid();
            
            FileRemote file = model.getGenotypingFile();
            if(file != null) 
                genotyping = file.getFileId();
            else
                genotyping = 0;
            
            file = model.getHandlingFile();
            if(file != null) 
                handling = file.getFileId();
            else
                handling = 0;
            
            ResearchApplicationRemote app = model.getResearchApplication();
            if(app != null) {
                this.researchAppType = app.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
                this.researchAppType = this.researchAppType.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                
                this.researchAppId = app.getRaid();
            }
            
            strain = new StrainDTO(model.getStrain());
            
            designation = strain.getDesignation();
            //strainType = strain.getStrainTypeNames();
            
            this.level = model.getLevel();
            
            this.desired_level = model.getDesiredLevel();
            
            this.mutations = model.getMutationTypesForModel();
            
            this.DistParam = model.getMutationDistinctionParameter();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Creates a new instance of ExpModelDTO for non-superscripted edit view
     * @param model The model
     */
    public ExpModelDTO(ExpModelRemote model, String superscript) {
        try {
            this.accNr = model.getIdentity();
            this.lineName = model.getAlias().replaceAll("<","&lt;").replaceAll(">","&gt;");
            //this.lineName = this.lineName.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");

            UserRemote contact = model.getContact();
            contactName="";
            contactId=0;
            groupName = "";
            if (contact!=null)
            {
                this.contactName = contact.getName();
                this.contactId = contact.getId();
                this.groupName = contact.getGroupName();
                this.contactMail = contact.getEmail();
                this.insturl = contact.getGroupLink().getUrl();
            }

            this.user = model.getUser().getUsr();
            //this.participant = model.getUser().getGroupName();
            this.participant = model.getContact().getGroupName();
            this.userId = model.getUser().getId();
            this.ts = model.getTs().toString();
            this.eid = model.getEid();
            
            this.researchAppText = model.getResearchApplicationText().replaceAll("<","&lt;").replaceAll(">","&gt;");
            //this.researchAppText = this.researchAppText.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.background = model.getGeneticBackground().replaceAll("<","&lt;").replaceAll(">","&gt;");
            //this.background = this.background.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.availability = model.getAvailability().replaceAll("<","&lt;").replaceAll(">","&gt;");
            //this.availability = this.availability.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.comm = model.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;");
            //this.comm = this.comm.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.phenotypes = model.getNumberOfPhenotypes();
            this.suid = model.getSamplingUnit().getSuid();
            
            FileRemote file = model.getGenotypingFile();
            if(file != null) 
                genotyping = file.getFileId();
            else
                genotyping = 0;
            
            file = model.getHandlingFile();
            if(file != null) 
                handling = file.getFileId();
            else
                handling = 0;
            
            ResearchApplicationRemote app = model.getResearchApplication();
            if(app != null) {
                this.researchAppType = app.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
                //this.researchAppType = this.researchAppType.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                
                this.researchAppId = app.getRaid();
            }
            
            strain = new StrainDTO(model.getStrain());
            
            designation = strain.getDesignation();
            //strainType = strain.getStrainTypeNames();
            
            this.level = model.getLevel();
            
            this.desired_level = model.getDesiredLevel();
            
            this.mutations = model.getMutationTypesForModel();
            
            this.DistParam = model.getMutationDistinctionParameter();
            
            //availabilityDTOs = model.getAvailabilityForModel(this.eid);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //--------------constructor for IMSR generation-----------------------------
    /**
     * Creates a new instance of ExpModelDTO for non-superscripted IMSR extraction view
     * @param model The model
     * @param oneTwoThree just an integer...
     */
    public ExpModelDTO(ExpModelRemote model, int oneTwoThree) {
        try {
            this.accNr = model.getIdentity();
            this.lineName = model.getAlias().replaceAll("<","&lt;").replaceAll(">","&gt;");

            this.eid = model.getEid();
            
            
            strain = new StrainDTO(model.getStrain(),"supercript");
            
            this.designation = strain.getDesignation().replaceAll("<","&lt;").replaceAll(">","&gt;");
            
            this.level = model.getLevel();
            
            this.desired_level = model.getDesiredLevel();
            
            this.mutations = model.getMutationTypesForModel();
            
            availabilityDTOs = model.getAvailabilityForModel(this.eid);
           
            //----------patenta-------------------
            Collection strainAll = model.getStrainAlleleInfo();
            Iterator tmpIter = strainAll.iterator();
            
            //based on the assumption that we only have one strain allele
            //it might be that in the near future a model pops up with more strain alleles attached to it
            //in that case the following while loop will need a slight modification
            while(tmpIter.hasNext()) {                
                StrainAlleleDTO strAllTmp = (StrainAlleleDTO)tmpIter.next();
                Allelemgiid = strAllTmp.getMgiId();
                //if allele mgi is n/a it should appear as blank otherwise...
                if (Allelemgiid.compareTo("")!=0 && Allelemgiid.compareTo("0")!=0 && !Allelemgiid.toUpperCase().contains("MGI")){
                    Allelemgiid = "MGI:"+ Allelemgiid;
                }else{
                    Allelemgiid = "";
                }
                Allelesymbol = strAllTmp.getSymbol().replaceAll("<","&lt;").replaceAll(">","&gt;");
                //if allele symbol is n/a it should appear blank otherwise...
                if(Allelesymbol.compareToIgnoreCase("N/A")==0){
                    Allelesymbol = "";
                }
                Allelename = strAllTmp.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
                //if allele name is n/a it should appear blank otherwise...
                if(Allelename.compareToIgnoreCase("N/A")==0){
                    Allelename = "";
                }
                Allelemutations = strAllTmp.getMutations();
                Allelemutationabbrs = strAllTmp.getMutationabbrs();
            }
            
            Collection genesAff = model.getGeneAffected();
            Iterator tmpGeneIter = genesAff.iterator();
            
            //just like with strain allele we assume that one mutant is connected to one gene
            //if we come across a strain that has more than one genes the while loop will have to be modified
            while(tmpGeneIter.hasNext()){
                GeneRemote geneTmp = (GeneRemote)tmpGeneIter.next();
                geneChromosome = geneTmp.getChromosome().getAbbr();
                geneMgiid = geneTmp.getMgiid();
                if (geneMgiid.compareTo("")!=0 && geneMgiid.compareTo("0")!=0 && !geneMgiid.toUpperCase().contains("MGI") && geneMgiid.compareToIgnoreCase("not provided yet")!=0){
                    geneMgiid = "MGI:"+ geneMgiid;
                }else{
                    geneMgiid = "";
                }
                geneSymbol = geneTmp.getGenesymbol().replaceAll("<","&lt;").replaceAll(">","&gt;");
                geneName = geneTmp.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //--------------constructor for generation of List of Backcrosses-----------
    //--------------all is superscripted+genetic background info is present-----
    public ExpModelDTO(ExpModelRemote model, int bcInt, String bcString) {
        try {
            this.accNr = model.getIdentity();
            this.lineName = model.getAlias().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.lineName = this.lineName.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");

            UserRemote contact = model.getContact();
            contactName="";
            contactId=0;
            groupName = "";
            if (contact!=null)
            {
                this.contactName = contact.getName();
                this.contactId = contact.getId();
                this.groupName = contact.getGroupName();
                this.contactMail = contact.getEmail();
                this.insturl = contact.getGroupLink().getUrl();
            }

            this.user = model.getUser().getUsr();
            //this.participant = model.getUser().getGroupName();
            this.participant = model.getContact().getGroupName();
            this.userId = model.getUser().getId();
            this.ts = model.getTs().toString();
            this.eid = model.getEid();
            
            this.researchAppText = model.getResearchApplicationText().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.researchAppText = this.researchAppText.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.background = model.getGeneticBackground().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.background = this.background.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.availability = model.getAvailability().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.availability = this.availability.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.comm = model.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;");
            this.comm = this.comm.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            this.phenotypes = model.getNumberOfPhenotypes();
            this.suid = model.getSamplingUnit().getSuid();
            
            FileRemote file = model.getGenotypingFile();
            if(file != null) 
                genotyping = file.getFileId();
            else
                genotyping = 0;
            
            file = model.getHandlingFile();
            if(file != null) 
                handling = file.getFileId();
            else
                handling = 0;
            
            ResearchApplicationRemote app = model.getResearchApplication();
            if(app != null) {
                this.researchAppType = app.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
                this.researchAppType = this.researchAppType.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
                
                this.researchAppId = app.getRaid();
            }
            
            strain = new StrainDTO(model.getStrain());
            
            designation = strain.getDesignation();
            //strainType = strain.getStrainTypeNames();
            
            this.level = model.getLevel();
            
            this.desired_level = model.getDesiredLevel();
            
            this.mutations = model.getMutationTypesForModel();
            
            this.DistParam = model.getMutationDistinctionParameter();
            
            //----------patenta-------------------
            Collection genbackAll = model.getGeneticBackgroundInfo();
            Iterator tmpIter = genbackAll.iterator();
            
            //based on the assumption that we only have one genetic back tuple
            //it might be that in the near future a model pops up with more genetic back tuples attached to it
            //in that case the following while loop will need a slight modification
            while(tmpIter.hasNext()) {                
                GeneticBackgroundDTO genbackTmp = (GeneticBackgroundDTO)tmpIter.next();
                backcrossingStrain = genbackTmp.getBackcrossing_strain_name();
                backcrossesNumber = genbackTmp.getBackcrosses();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the number of phenotypes for the model
     * @return The number of phenotypes
     */
    public int getPhenotypes() {
        return phenotypes;
    }
    
    /**
     * Returns the comment for the model
     * @return The comment for the model
     */
    public String getComm() {
        if(comm == null || comm.length() == 0 || comm.equalsIgnoreCase("null"))
            return "";
        return comm;
    }
    
    /**
     * Returns the string 'Handling' which is shown as linkname given that a handling file exists
     * @return The string 'Handling'
     */
    public String getHandlingName() {
        if(handling == 0)
            return "";
        else
            return "Handling";
    }
    
    /**
     * Returns the string 'Genotyping' which is shown as linkname given that a genotyping file exists
     * @return The string 'Genotyping'
     */
    public String getGenotypingName() {
        if(genotyping == 0)            
            return "";
        else
            return "Genotyping";
    }
    
    /**
     * Returns the fileid of the genotyping file
     * @return The genotyping file id
     */
    public int getGenotypingId() {
        return genotyping;
    }
    
    /**
     * Returns the id of the handling instructions file
     * @return The id of the handling instructions file
     */
    public int getHandlingId() {
        return handling;
    }

    /**
     * Returns the accession number for the model
     * @return The accession number for the model
     */
    public String getAccNr() {
        return accNr;
    }

    /**
     * Returns the name of the lin
     * @return The name of the line
     */
    public String getLineName() {
        if(lineName == null || lineName.length() == 0)
            return "No line name specified";         
        return lineName;
    }

    /**
     * Returns the name of the person contact regarding the model
     * @return The name of the contact
     */
    public String getContactName() {
        return contactName;
    }
    
    /**
     * Returns the e-mail of the person contact regarding the model
     * @return The name of the contact
     */
    public String getContactMail() {
        return contactMail;
    }

    /**
     * Returns the id of the model
     * @return The id of the model
     */
    public int getEid() {
        return eid;
    }
    
    /**
     * Returns the goup_name of the person contact regarding the model
     * @return The goup_name of the contact
     */
    /*public String getGroupName() {
        return groupName;
    }*/
    
    /**
     * Returns the id of the user that made the last changes on the model
     * @return The name of the user that made the last changes on the model
     */
    public int getUserId() {
        return userId;
    }    

    /**
     * Returns the id of the person to contact regarding the model
     * @return The id of the contact
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Returns the username of the user that made the last changes on the model
     * @return The username of the user that made the last changes on the model
     */
    public String getUser() {
        return user;
    }
    
    /**
     * Returns the research group of the user that made the last changes on the model
     * @return The research group of the user that made the last changes on the model
     */
    public String getParticipant() {
        return participant;
    }

    /**
     * Returns the date for when the model was last modified
     * @return The date for when the model was last changed
     */
    public String getTs() {
        return ts;
    }    

    /**
     * Returns the research application text
     * @return The research application text
     */
    public String getResearchAppText() {
        if(researchAppText == null || researchAppText.length() == 0)
            return "No research application specified";
        return researchAppText;
    }

    /**
     * Returns the background for the model
     * @return The background for the model
     */
    public String getBackground() {
        return background;
    }

    /**
     * Returns the availability of the model
     * @return The availability
     */
    public String getAvailability() {
        if(availability == null || availability.length() == 0)
            return "No availability specified";        
        return availability;
    }

    /**
     * Returns the id of the research application for the model
     * @return The research application id
     */
    public int getResearchAppId() {
        return researchAppId;
    }

    /**
     * Returns the research application type
     * @return The research application type
     */
    public String getResearchAppType() {
        if(researchAppType == null || researchAppType.length() == 0)
            return "No application type specified";        
        return researchAppType;
    }
    
    public int compareTo(Object anotherObj)
    {
        if(!(anotherObj instanceof ExpModelDTO))
            throw new ClassCastException("Object is of wrong class. ExpModelDTO object expected but not found.");
        return getLineName().compareTo(((ExpModelDTO)anotherObj).getLineName());
    }
    
    public boolean equals(Object obj)
    {
        if (((ExpModelDTO)obj).eid == this.eid)
            return true;
        else 
            return false;
    }
    
    public int getSuid()
    {
        return suid;
    }
    
    public StrainDTO getStrain()
    {
        return strain;
    }

    public String getLevel()
    {
        if (level == 0)
            return "Public";
        else if (level == 1)
            return "Mugen";
        else if (level == 2)
            return "Admin";
        else 
            return "Error";
    }
    
    public String getDesiredLevel()
    {
        if (desired_level == 0)
            return "Public";
        else if (desired_level == 1)
            return "Mugen";
        else if (desired_level == 2)
            return "Admin";
        else 
            return "Error";
    }
    
    public String getGroupName(){
        return groupName;
    }
    
    public String getMutations(){
        return mutations;
    }
    
    public String getDesignation(){
        return designation;
    }
    
    public String getMutationTypes(){
        int j=0;
        String mutationTypes = "";
        AvailabilityDTO avTMP;
        Iterator i = availabilityDTOs.iterator();
        while (i.hasNext())
            {
                avTMP = (AvailabilityDTO)i.next();
                if (j == 0)
                    mutationTypes += avTMP.getTypeabbr();
                if (j!=0 && !mutationTypes.contains(avTMP.getTypeabbr()) && avTMP.getTypeabbr().compareTo("ND")!=0){
                    mutationTypes += ", ";
                    mutationTypes += avTMP.getTypeabbr();
                }
                
                j++;
            }
        return mutationTypes;
    }
    
    public String getMutationStates(){
        int j=0;
        String mutationStates = "";
        AvailabilityDTO avTMP;
        Iterator i = availabilityDTOs.iterator();
        while (i.hasNext())
            {
                avTMP = (AvailabilityDTO)i.next();
                if (j == 0)
                    mutationStates += avTMP.getStateabbr();
                if (j!=0 && !mutationStates.contains(avTMP.getStateabbr()) && avTMP.getStateabbr().compareTo("ND")!=0){
                    mutationStates += ", ";
                    mutationStates += avTMP.getStateabbr();
                }
                
                j++;
            }
        return mutationStates;
    }
    
    //--------strain allele data related functions-----
    
    public String getAllMgiid(){
        return Allelemgiid;
    }
    
    public String getAllSymbol(){
        return Allelesymbol;
    }
    
    public String getAllName(){
        return Allelename;
    }
    
    public String getAllMutations(){
        return Allelemutations;
    }
    
    public String getAllMutationabbrs(){
        return Allelemutationabbrs;
    }
    
    //-----------gene data related functions---------
    //geneChromosome, geneMgiid, geneSymbol, geneName;
    public String getGeneChromosome(){
        return geneChromosome;
    }
    
    public String getGeneMgiid(){
        return geneMgiid;
    }
    
    public String getGeneSymbol(){
        return geneSymbol;
    }
    
    public String getGeneName(){
        return geneName;
    }
    
    public int getDistParam(){
        return DistParam;
    }
    
    public String getBackcrossingStrain(){
        return backcrossingStrain;
    }
    
    public String getBackcrossesNumber(){
        return backcrossesNumber;
    }
    
    public String getInsturl(){
        return insturl;
    }
    
}
