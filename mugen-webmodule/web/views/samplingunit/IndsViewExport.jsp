<%
    response.setContentType("text/plain");
    response.setHeader("Content-Disposition", "inline; filename=individuals.txt");
    
    String export = (String)request.getAttribute("export");  
    response.getWriter().print(export);
%>