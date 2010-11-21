<% 
    /*
     * Write the content of a file to browser.
     */
    com.arexis.mugen.resource.file.FileRemote file =
            (com.arexis.mugen.resource.file.FileRemote)request.getAttribute("tmp.bean.file");
    
    byte[] data = file.getData();
    String mimeType = file.getMimeType();
    response.setContentType(mimeType);
    String fileName = file.getName();

    response.setHeader("Content-Disposition", "inline; filename=" + fileName);
    ServletOutputStream sos = response.getOutputStream();
    sos.write(data);

%>