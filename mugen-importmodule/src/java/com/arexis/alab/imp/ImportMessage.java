package com.arexis.alab.imp;

import com.arexis.mugen.MugenCaller;
import java.io.Serializable;
import javax.naming.directory.BasicAttributes;




// Add business logic below. (Right-click in editor and choose
// "EJB Methods > Add Business Method" or "Web Service > Add Operation")

public class ImportMessage implements Serializable
{
    public MugenCaller caller;
    //public int fileid;
    public int jobid;
    public BasicAttributes attrs;
    
    public static final int IMPORT = 0;
    public static final int CHECK = 1;
    
    // IMPORT or CHECK
    public int mode;
    
    public ImportMessage()
    {
        
    }
}