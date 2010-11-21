package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.species.gene.GeneRemote;
import com.arexis.mugen.project.user.UserRemote;
import java.io.Serializable;

public class GeneDTO implements Serializable {
    private String name, comm, ts, userName, userMail, userFullName, mgiid, genesymbol, geneexpress, idgene, idensembl;
    private int gaid, pid, userId, modelsnum, allelesnum;
    
    private String chromosomename;
    private int cid;
    
    private String speciesName;
    
    private String ensemblink, arrayexpresslink, mgilink, entrezlink, eurexpresslink;
    
    //industry links
    private String invitrogen = "";
    
    private String geneservice = "";
    
    //non-superscripted fields returned.
    public GeneDTO(GeneRemote gene) {
        try {
            UserRemote user = gene.getUser();
            userName = user.getUsr();
            userId = user.getId();
            userMail = user.getEmail();
            userFullName = user.getName();
            
            name = gene.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
            comm = gene.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;");
            
            ts = gene.getTs().toString();
            gaid = gene.getGaid();
            pid = gene.getProject().getPid();
            
            mgiid = gene.getMgiid();
            
            genesymbol = gene.getGenesymbol();
            if (genesymbol!=null || genesymbol.length()!=0)
                genesymbol = genesymbol.replaceAll("<","&lt;").replaceAll(">","&gt;");
            
            geneexpress = gene.getGeneexpress();
            if (geneexpress!=null || geneexpress.length()!=0)
                geneexpress = geneexpress.replaceAll("<","&lt;").replaceAll(">","&gt;");
            
            idgene = gene.getIdgene();
            idensembl = gene.getIdensembl();
            
            chromosomename = gene.getChromosome().getName();
            cid = gene.getChromosome().getCid();
            
            speciesName = gene.getChromosome().getSpecies().getName();
            
            ensemblink = getEnsemblink();
            arrayexpresslink = getArrayexpresslink();
            mgilink = getMgilink();
            entrezlink = getEntrezlink();
            
            modelsnum = gene.getModelscount();
            allelesnum = gene.getAllelescount();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //superscripted fields returned.
    //used through a model to get the affected gene etc.
    public GeneDTO(GeneRemote gene, int eid) {
        try {
            UserRemote user = gene.getUser();
            userName = user.getUsr();
            userId = user.getId();
            userMail = user.getEmail();
            userFullName = user.getName();
            
            name = gene.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
            name = name.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            comm = gene.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;");
            comm = comm.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            ts = gene.getTs().toString();
            gaid = gene.getGaid();
            pid = gene.getProject().getPid();
            
            chromosomename = gene.getChromosome().getName();
            cid = gene.getChromosome().getCid();
            
            speciesName = gene.getChromosome().getSpecies().getName();
            
            mgiid = gene.getMgiid();
            
            genesymbol = gene.getGenesymbol().replaceAll("<","&lt;").replaceAll(">","&gt;");
            genesymbol = genesymbol.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            geneexpress = gene.getGeneexpress().replaceAll("<","&lt;").replaceAll(">","&gt;");
            geneexpress = geneexpress.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            idgene = gene.getIdgene();
            idensembl = gene.getIdensembl();
            if (idensembl == ""){
                idensembl = "not provided";
            }
            
            ensemblink = getEnsemblink();
            arrayexpresslink = getArrayexpresslink();
            mgilink = getMgilink();
            entrezlink = getEntrezlink();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public GeneDTO(GeneRemote gene, String superscript) {
        try {
            UserRemote user = gene.getUser();
            userName = user.getUsr();
            userId = user.getId();
            userMail = user.getEmail();
            userFullName = user.getName();
            
            name = gene.getName().replaceAll("<","&lt;").replaceAll(">","&gt;");
            name = name.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            comm = gene.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;");
            comm = comm.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            ts = gene.getTs().toString();
            gaid = gene.getGaid();
            pid = gene.getProject().getPid();
            
            //chromosomename = gene.getChromosomeName(eid, gaid);
            chromosomename = gene.getChromosome().getName();
            cid = gene.getChromosome().getCid();
            
            speciesName = gene.getChromosome().getSpecies().getName();
            
            mgiid = gene.getMgiid();
            
            genesymbol = gene.getGenesymbol().replaceAll("<","&lt;").replaceAll(">","&gt;");
            genesymbol = genesymbol.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            geneexpress = gene.getGeneexpress().replaceAll("<","&lt;").replaceAll(">","&gt;");
            geneexpress = geneexpress.replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            
            idgene = gene.getIdgene();
            idensembl = gene.getIdensembl();
            if (idensembl == ""){
                idensembl = "not provided";
            }
            
            ensemblink = getEnsemblink();
            arrayexpresslink = getArrayexpresslink();
            eurexpresslink = getEurexpresslink();
            mgilink = getMgilink();
            entrezlink = getEntrezlink();
            
            invitrogen = conInvitrogen();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public int getModelsnum() {
        return modelsnum;
    }
    
    public int getAllelesnum() {
        return allelesnum;
    }
    public String getName() {
        if (name == null || name.length()== 0 || name.equalsIgnoreCase("null")){
            return "not provided yet";
        }
        return name;
    }

    public String getComm() {
        if (comm == null || comm.length()== 0 || comm.equalsIgnoreCase("null")){
            return "not provided yet";
        }
        return comm;
    }

    public String getTs() {
        return ts;
    }

    public String getUserName() {
        return userName;
    }
    
    public String getUserMail() {
        return userMail;
    }
    
    public String getUserFullName() {
        return userFullName;
    }

    public int getGaid() {
        return gaid;
    }

    public int getUserId() {
        return userId;
    }
    
    public int getPid() {
        return pid;
    }

    public String getMgiid(){
        if (mgiid == null || mgiid.length()== 0 || mgiid.equalsIgnoreCase("null")){
            return "not provided yet";
        }
        return mgiid;
    }
    
    public String getGenesymbol(){
        if (genesymbol == null || genesymbol.length()== 0 || genesymbol.equalsIgnoreCase("null")){
            return "not provided yet";
        }
        return genesymbol;
    }
    
    public String getGeneexpress(){
        if (geneexpress == null || geneexpress.length()== 0 || geneexpress.equalsIgnoreCase("null")){
            return "n/a";
        }
        return geneexpress;
    }
    
    public String getIdgene(){
        if (idgene == null || idgene.length()== 0 || idgene.equalsIgnoreCase("null")){
            return "n/a";
        }
        return idgene;
    }
    
    public String getIdensembl(){
        if (idensembl == null || idensembl.length()== 0 || idensembl.equalsIgnoreCase("null")){
            return "not provided yet";
        }
        return idensembl.trim();
    }
    
    public String getEnsemblink(){
        if (idensembl != null && idensembl.length()!= 0 && !idensembl.equalsIgnoreCase("null") && idensembl.compareTo("not provided yet")!=0 && idensembl.trim().startsWith("ENS")){
            if(idensembl.contains("EST")){
                if(idensembl.contains("MUS")){
                    ensemblink = "<a href=\"http://www.ensembl.org/Mus_musculus/geneview?gene="+idensembl.trim()+";db=otherfeatures\" target=\"_blank\">"+idensembl.trim()+"</a>";
                } else {
                    ensemblink = "<a href=\"http://www.ensembl.org/Homo_sapiens/geneview?gene="+idensembl.trim()+";db=otherfeatures\" target=\"_blank\">"+idensembl.trim()+"</a>";
                } 
            }else{
                if(idensembl.contains("MUS")){
                    ensemblink = "<a href=\"http://www.ensembl.org/Mus_musculus/geneview?gene="+idensembl.trim()+"\" target=\"_blank\">"+idensembl.trim()+"</a>";
                } else {
                    ensemblink = "<a href=\"http://www.ensembl.org/Homo_sapiens/geneview?gene="+idensembl.trim()+"\" target=\"_blank\">"+idensembl.trim()+"</a>";
                }
            }
        }else{
            ensemblink = "n/a";
        }
        return ensemblink;
    }
    
    public String getArrayexpresslink(){
        if (idensembl != null && idensembl.length()!= 0 && !idensembl.equalsIgnoreCase("null") && idensembl.compareTo("not provided yet")!=0 && idensembl.trim().startsWith("ENS")){
            if(idensembl.contains("EST")){
                arrayexpresslink = "n/a";
            }else{
                if(idensembl.contains("MUS")){
                    arrayexpresslink = "<a href=\"http://www.ebi.ac.uk/microarray-as/aew/DW?queryFor=gene&species=Mus+musculus&gene_query="+idensembl.trim()+"\"target=\"_blank\">"+"expression profiles"+"</a>";
                } else {
                    arrayexpresslink = "<a href=\"http://www.ebi.ac.uk/microarray-as/aew/DW?queryFor=gene&species=Homo+sapiens&gene_query="+idensembl.trim()+"\"target=\"_blank\">"+"expression profiles"+"</a>";
                } 
            }
        }else{
            arrayexpresslink = "n/a";
        }
        return arrayexpresslink;
    }
    
    public String getEurexpresslink(){
        /*if (idensembl != null && idensembl.length()!= 0 && !idensembl.equalsIgnoreCase("null") && idensembl.compareTo("not provided yet")!=0 && idensembl.trim().startsWith("ENS")){
            eurexpresslink = "<a href=\"http://www.eurexpress.org/ee/databases/tdbsearch?mode=search&page=&searchField=All&searchValue="+idensembl.trim()+"&Submit=Go\"target=\"_blank\">"+"embryonic expression pattern"+"</a>";*/
        if (genesymbol != null && genesymbol.length()!= 0 && !genesymbol.equalsIgnoreCase("null") && !genesymbol.equalsIgnoreCase("n/a")){
//            eurexpresslink = "<a href=\"http://www.eurexpress.org/ee/databases/tdbsearch?mode=search&page=&searchField=All&searchValue="+genesymbol.trim()+"&Submit=Go\"target=\"_blank\">"+"embryonic expression pattern"+"</a>";
            eurexpresslink = "<a href=\"http://www.eurexpress.org/ee/databases/tdbsearch?mode=search&page=&searchField=All&searchValue="+genesymbol.trim()+"&groupSelection=Group_All&standardAtlas=on&getStandardAtlas=yes&multistageAtlas=on&getMultistageAtlas=yes&findEverything=no&anatomySetNames=0&expressionStrengthOperator=g&expressionStrengthValue=&coverageOperator=g&coverageValue=\"target=\"_blank\">"+"embryonic expression pattern"+"</a>";
        }else{
            eurexpresslink = "n/a";
        }
        return eurexpresslink;
    }
    
    public String getMgilink(){
        if(mgiid!=null && mgiid.compareTo("not provided yet") != 0 && mgiid.length()!=0){
            mgilink = "<a href=\"http://www.informatics.jax.org/searches/accession_report.cgi?id=MGI:"+mgiid.trim()+"\"target=\"_blank\">"+"MGI:"+mgiid.trim()+"</a>";
        }else{
            mgilink = "n/a";
        }
        return mgilink;
    }
    
    public String getEntrezlink(){
        if(idgene!=null && idgene.compareTo("not provided yet") != 0 && idgene.length()!=0){
            entrezlink = "<a href=\"http://www.ncbi.nlm.nih.gov/entrez/query.fcgi?db=gene&cmd=Retrieve&dopt=full_report&list_uids="+idgene.trim()+"\" target=\"_blank\">"+idgene.trim()+"</a>";
        }else{
            entrezlink = "n/a";
        }
        return entrezlink;
    }
    
    public String conInvitrogen(){
        if(idgene!=null && idgene.compareTo("0") != 0 && idgene.compareTo("not provided yet") != 0 && idgene.compareTo("n/a") != 0 && idgene.length()!=0){
            invitrogen += "<td valign=\"top\"><table width=\"100%\">";
            invitrogen += "<tr class=\"alternatingTwo\"><td><b>Related Products</b></td></tr>";
            invitrogen += "<tr><td><img src=\"images/logos/invitrogen.png\" alt=\"invitrogen\" class=\"link\">&nbsp;<b>Invitrogen:&nbsp;</b><a href=\"http://igene.invitrogen.com/iGene/genecard.do?source=igene&geneId="+idgene.trim()+"\" target=\"_blank\">"+"go"+"</a></td></tr>";
            invitrogen += "<tr><td><img src=\"images/logos/geneservice.png\" alt=\"geneservice\" class=\"link\">&nbsp;<b>Geneservice:&nbsp;</b><a href=\"http://www.geneservice.co.uk/products/clonehunter/CloneHunter.jsp?category=accession&organism=Mus_musculus&Submit=Submit&searchField="+idgene.trim()+"\" target=\"_blank\">"+"go"+"</a></td></tr>";
            invitrogen += "</table></td>";
        }
        return invitrogen;
    }
    
    public String getInvitrogen(){
        return invitrogen;
    }
    
    public String getChromoName() {
        return chromosomename;
    }
    
    public int getCid() {
        return cid;
    }
    
    public String getSpeciesName() {
        return speciesName;
    }
}
