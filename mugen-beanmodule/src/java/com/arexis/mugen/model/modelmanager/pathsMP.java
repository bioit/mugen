
package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyRemote;
import com.arexis.mugen.phenotype.ontology.PhenotypeOntologyPathRemote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class pathsMP implements Serializable {
    
    private int leaf = 0;
    private Collection roots = new ArrayList();
    
    private int pathid;
    private String mpids = "";
    private String mpnames = "";
    private String mpnameshort = "";
    private String mpnameshortlink = "";
    private String mptreenode = "";
    private String pld = "";
    private String pldfull = "";
    
    private int eid;
    private String oldmpath;
    private String oldmpathtrans;
    Collection altpaths = new ArrayList();
    Collection altpathstrans = new ArrayList();
    private String altpathstr = "";
    private int altpathl;
    
    private int mpid;
    private String mpname;
    
    public pathsMP(int leaf, Collection roots) {
        this.leaf = leaf;
        this.roots = roots;
    }
    
    public pathsMP(String mpids, String mpnames) {
        this.mpids = mpids;
        this.mpnames = mpnames;
    }
    
    public pathsMP(PhenotypeOntologyPathRemote php){
        try {
            this.pathid = php.getPpid();
            this.mpids = php.getPath();
            this.mpnames = php.getPathtrans();
            this.mpnameshort = mpnames.substring(mpnames.indexOf(">")+1);
            this.mpnameshortlink = constructMpnameshortlink(mpnameshort);
            this.mptreenode = php.getPathtreenode();
            this.pld = php.getPathtreenodePLD();
            this.pldfull = php.getPathtreenodeFullPLD();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String constructMpnameshortlink(String mpnameshort){
        String tmp1 = mpnameshort.substring(0, mpnameshort.lastIndexOf(">")+1);
        String tmp2 = mpnameshort.substring(mpnameshort.lastIndexOf(">")+1);
        String tmp3 = mpids.substring(mpids.lastIndexOf(">")+1);
        mpnameshortlink = tmp1 + "<a class=\"menu\" href=\"Controller?workflow=ViewPhenotypeOntology&potid=" + tmp3 + "\">" + tmp2 + "</a>";
        return mpnameshortlink;
    }
    
    public pathsMP(PhenotypeOntologyRemote mp){
        try {
            this.mpid = mp.getId();
            this.mpname = mp.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public pathsMP(int eid, String oldmpath, String oldmpathtrans){
        this.eid = eid;
        this.oldmpath = oldmpath;
        this.oldmpathtrans = oldmpathtrans;
    }
    
    public int getMpid(){
        return mpid;
    }
    
    public String getMpname(){
        return mpname;
    }
    
    public String getMpnameshort(){
        return mpnameshort;
    }
    
    public String getMpnameshortlink(){
        return mpnameshortlink;
    }
    
    public int getEid(){
        return eid;
    }
    
    public String getOldmpath(){
        return oldmpath;
    }
    
    public String getOldmpathtrans(){
        return oldmpathtrans;
    }
    
    public void addAlternativePath(String altpath){
        altpaths.add(altpath);
    }
    
    public void addAlternativePathTrans(String altpathtrans){
        altpathstrans.add(altpathtrans);
    }
    
    public Collection getAltpaths(){
        return altpaths;
    }
    
    public String getAltpathstr(){
        return altpathstr;
    }
    
    public int getAltpathl(){
        return altpaths.size();
    }
    
    public void constructAltpathstr(){
        Iterator i = altpaths.iterator();
        Iterator j = altpathstrans.iterator();
        while(i.hasNext()){
            altpathstr += "<br><a href=\"Controller?workflow=replaceMP&eid="+eid+"&mpold="+oldmpath+"&mpnew="+(String) i.next()+"\" title=\"replace mp term\" onClick=\"return confirm('replace mp term?')\">&nbsp;+&nbsp;"+(String) j.next()+"</a><br>";
        }
    }
    
    public int getPathid(){
        return pathid;
    }
    
    public String getMpnames(){
        return mpnames;
    }
    
    public String getMpids(){
        return mpids;
    }
    
    public String getMptreenode(){
        return mptreenode;
    }
    
    public String getPld(){
        return pld;
    }
    
    public String getPldfull(){
        return pldfull;
    }
    
    public int getLeaf() {
        return leaf;
    }
    
    public Collection getRoots(){
        return roots;
    }
    
}
