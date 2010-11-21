/*
 * RepositoriesDTO.java
 *
 * Created on 11 Ιούλιος 2006, 10:38 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.mugen.model.modelmanager;

import com.arexis.mugen.model.repositories.RepositoriesRemote;
import java.io.Serializable;

/**
 *
 * @author zouberakis
 */
public class RepositoriesDTO implements Serializable {
    private int rid, pid;
    private String reponame, repourl;
    
    
    /** Creates a new instance of RepositoriesDTO */
public RepositoriesDTO(RepositoriesRemote repository) {
        try{
            rid = repository.getRid();
            reponame = repository.getReponame();
            repourl = repository.getRepourl();
            pid = repository.getPid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getRid(){
        return rid;
    }
    
    public java.lang.String getReponame(){
        return reponame;
    }
    
    public java.lang.String getRepourl(){
        return repourl;
    }
    
    public int getPid(){
        return pid;
    }
}
