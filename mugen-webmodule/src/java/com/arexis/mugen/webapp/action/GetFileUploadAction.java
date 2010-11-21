/*
 * GetFileUploadAction.java
 *
 * Created on September 15, 2005, 10:22 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.arexis.mugen.webapp.action;

import com.arexis.mugen.exceptions.ApplicationException;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;



/**
 *
 * @author heto
 */
public class GetFileUploadAction extends MugenAction {
    
    /** Creates a new instance of GetFileUploadAction */
    public GetFileUploadAction() {
    }
    
    public String getName() {
        return "GetFileUploadAction";
    }
    
    public boolean performAction(HttpServletRequest request, ServletContext context) throws ApplicationException {
        try {
            MultipartParser mp = new MultipartParser(request, 100000000);
            Part p = mp.readNextPart();
            
            if (p.isFile())
            {
                FilePart fp1 = (FilePart)p;
                InputStream is = fp1.getInputStream();
                
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int c;
                while ((c = is.read())!= -1)
                {
                    out.write(c);
                    //System.out.write(c);
                }
                System.out.flush();
                out.flush();
                out.close();
                
                byte[] data = out.toByteArray();
                
                System.out.println("Data file size="+data.length);
                //System.out.println(new String(data));
                //System.out.println(data);
                
                
                System.out.println("Content type="+fp1.getContentType());
                
                
                resourceManager.saveFile(fp1.getFileName(), fp1.getContentType(), data, null);
                
                // GC data.
                data = null;
            }
            
            
            return true;
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
