package com.arexis.mugen.search;

import com.arexis.mugen.model.expmodel.ExpModelRemote;
import com.arexis.mugen.project.project.ProjectRemote;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class ModelSearchResult extends SearchResult implements Serializable {
    
    public ModelSearchResult(ExpModelRemote model, String workflow) {
        try {
            this.workflow = workflow;
            this.name = model.getAlias().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            this.comment = model.getComm().replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("&lt;","<sup>").replaceAll("&gt;","</sup>");
            type = "Mutant";
            
            Collection projects = model.getSamplingUnit().getProjects();
            Iterator i = projects.iterator();
            int j = 0;
            project = "";
            
            while (i.hasNext()) {
                if (j!=0)
                    project += ", ";
                project += ((ProjectRemote)i.next()).getName();
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
