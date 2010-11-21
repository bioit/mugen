package com.arexis.mugen.model.modelmanager;

import java.io.Serializable;

public class pathnodeMP implements Serializable {
    
    private int node = 0;
    
    public pathnodeMP(int node) {
        this.node = node;
    }
    
    public int getNode() {
        return node;
    }
}
