/*
 * MultipartHelper.java
 *
 * Created on September 19, 2005, 9:45 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.resource.resourcemanager;

import com.arexis.mugen.MugenCaller;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.naming.directory.BasicAttributes;
import org.apache.log4j.Logger;


/**
 *
 * @author heto
 */
public class MultipartHelper {
    
    private static Logger logger = Logger.getLogger(MultipartHelper.class);
    
    private MultipartParser mp;
    
    private BasicAttributes bas;
    
    private ResourceManagerRemote resourceManager;
    
    private ArrayList fileIds;
    
    private MugenCaller caller;
    
    /**
     * Creates a new instance of MultipartHelper 
     */
    public MultipartHelper(MultipartParser mp, ResourceManagerRemote resourceManager, MugenCaller caller) {
        this.mp = mp;
        this.resourceManager = resourceManager;
        fileIds = new ArrayList();
        bas = new BasicAttributes();
        this.caller = caller;
    }
    
    
//    public String getParam(Part p)
//    {
//        try
//        {
//            if (p.isParam())
//            {
//                ParamPart pp = (ParamPart)p;
//
//                return pp.getStringValue();
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }
    
    /**
     * Parse parameters and store files in this request.
     * 
     * Parameters can be collected with getAttributes.
     * FileIds can be collected with getFileIds
     *
     */
    public void parse()
    {
       logger.debug("parsing file");
       try
       {
        
            Part p = null;
            while ((p = mp.readNextPart()) !=null)
            {
                if (p.isParam())
                {
                    ParamPart pp = (ParamPart)p;
                    String id = pp.getName();
                    String val = pp.getStringValue();

                    bas.put(id, val);
                    //logger.debug("param "+id+"="+val);
                }
                else if (p.isFile())
                {
                    logger.debug("isFile!");
                    FilePart fp1 = (FilePart)p;
                    InputStream is = fp1.getInputStream();

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int c;
                    while ((c = is.read())!= -1)
                    {
                        out.write(c);
                    }
                    System.out.flush();
                    out.flush();
                    out.close();

                    byte[] data = out.toByteArray();

                    logger.debug("Data file size="+data.length);
                    logger.debug("Content type="+fp1.getContentType());


                    int fileid = resourceManager.saveFile(fp1.getFileName(), fp1.getContentType(), data, caller);
                    fileIds.add(new Integer(fileid));

                    // GC data.
                    data = null;
                }
                else
                    logger.warn("Not param and not file");
            }
       }
       catch (Exception e)
       {
           logger.error("Exception then parsing files and attributes", e);
       }
    }
    
    /**
     * Get the file ids for the files stored in database for this operation.
     */
    public ArrayList getFileIds()
    {
        return fileIds;
    }
    
    /**
     * Get all the attributes for the operation
     */
    public BasicAttributes getAttributes()
    {
        return bas;
    }
    
    
    
//    public void saveFile(Part p, FileManagerRemote fileManager) throws ApplicationException
//    {
//        try {
//            if (p.isFile())
//            {
//                FilePart fp1 = (FilePart)p;
//                InputStream is = fp1.getInputStream();
//                
//                ByteArrayOutputStream out = new ByteArrayOutputStream();
//                int c;
//                while ((c = is.read())!= -1)
//                {
//                    out.write(c);
//                }
//                System.out.flush();
//                out.flush();
//                out.close();
//                
//                byte[] data = out.toByteArray();
//                
//                System.out.println("Data file size="+data.length);
//                //System.out.println(new String(data));
//                //System.out.println(data);
//                
//                
//                
//                System.out.println("Content type="+fp1.getContentType());
//                
//                
//                fileManager.saveFile(fp1.getFileName(), fp1.getContentType(), data);
//                
//                // GC data.
//                data = null;
//            }
//        } catch (ApplicationException e) {
//            throw e;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApplicationException("Failed to sage file");
//        }
//    }
}
