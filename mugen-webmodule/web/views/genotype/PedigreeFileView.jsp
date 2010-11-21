<% 
    /*
     * Write the content of a file to browser.
     */
    String data = (String)request.getAttribute("pedigreeTree");
    String type = (String)request.getAttribute("type");

    com.arexis.util.graph.Graph g = (com.arexis.util.graph.Graph)request.getSession().getAttribute("tmp.getspecialviewaction.pedigreegraph");
    com.arexis.util.graph.GraphExporter exp = new com.arexis.util.graph.GraphExporter();
    String format = (String)request.getParameter("format");
    String s = "";
    if(g != null) {
        if(format.equals("gml"))
            s = exp.exportGML(g);
        else if(format.equals("xgml"))
            s = exp.exportXGML(g);    
        else if(format.equals("gxl"))
            s = exp.exportGXL(g);
        else if(format.equals("graphml"))
            s = exp.exportGraphML(g);    
        else
            s = "Error! Unknown option selected";
    }
    
    response.setContentType("application/"+format);
    response.setHeader("Content-Disposition", "inline; filename=pedigree-graph."+format);    
    
    out.println(s);

%>