/*
 * ImportException.java
 *
 * Created on March 13, 2006, 9:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.arexis.alab.imp;

/**
 *
 * @author heto
 */
public class ImportException extends Exception {
    
    private int row;
    
    /** Creates a new instance of ImportException */
    public ImportException(String msg, int row) {
        super(msg);
        this.row = row;
    }
    
    public int getRow()
    {
        return row;
    }
    
}
