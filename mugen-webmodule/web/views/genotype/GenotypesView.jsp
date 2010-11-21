
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.Collection" %>
<%
    com.arexis.mugen.project.ParamDataObject pdo = (com.arexis.mugen.project.ParamDataObject)request.getAttribute("paramdataobject");
          
    String update = "<a href=\"Controller?workflow=UpdateGenotypeLevels\">Update levels</a>";
    Collection genotypes = (Collection)request.getAttribute("genotypesdto");
    if(genotypes.size() == 0)
        update = "Update levels";
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Genotypes</title>
    </head>
    <body>         
        <h1>Genotypes</h1>    

        <form action="Controller" method="post"> 
        <input type="hidden" name="querytype" value="<%=(String)request.getAttribute("querytype")%>"/>        
        <m:query type="simple"> 
            <table>
                <tr>
                    <td>Sampling unit <br>
                        <m:su-combobox name="g.suid" onChange="this.form.submit()"/>    
                        <input id="button" type=submit name="set" value="Set">
                    </td>
                    <td>                       
                        Grouping <br><m:checkbox collection="groupingsdto" wildcardOption='true' idGetter="getGsid" textGetter="getName" name="gsid" selected='<%=pdo.getValue("gsid")%>' onChange="this.form.submit()"/>
                        <input id="button" type=submit name="set" value="Set">
                    </td>
                    <td>                       
                        Group <br><m:checkbox collection="groupdto" wildcardOption='true' idGetter="getGid" textGetter="getName" name="gid" selected='<%=pdo.getValue("gid")%>'/> 
                    </td>  
                </tr>
                <tr>
                    <td colspan=3 align="right" valign="bottom"> 
                        <a href="Controller?workflow=ViewGenotypes&querytype=advanced"><m:img name="nav_close" title="Advanced form"/></a>
                        <input id="button" type="submit" name="collect.getgenotypesaction.display" value="Display">
                        <input id="button" type="submit" name="reset" value="Reset">
                    </td>                    
                </tr>    
                <tr>
                    <td colspan="3" align="right">
                        <%=update%>
                    </td>
                </tr>                
            </table>
        </m:query>
        <m:query type="advanced">             
            <table>
                <tr>
                    <td>Sampling unit <br>
                        <m:su-combobox name="g.suid" onChange="this.form.submit()"/>    
                        <input id="button" type=submit name="set" value="Set">
                    </td>
                    <td>                       
                        Grouping <br><m:checkbox collection="groupingsdto" wildcardOption='true' idGetter="getGsid" textGetter="getName" name="gsid" selected='<%=pdo.getValue("gsid")%>' onChange="this.form.submit()"/>
                        <input id="button" type=submit name="set" value="Set">
                    </td>
                    <td>                       
                        Group <br><m:checkbox collection="groupdto" wildcardOption='true' idGetter="getGid" textGetter="getName" name="gid" selected='<%=pdo.getValue("gid")%>'/> 
                    </td>                     
                    <td>Chromosome <br>
                        <m:checkbox collection="chromosomes" selected='<%=pdo.getValue("chromosome")%>' name="chromosome" idGetter="getName" textGetter="getName"/>                    
                    </td>             
                    <td>Level <br>
                        <m:checkbox collection="levels" idGetter="getLevel" textGetter="getLevel" name="g.level_" selected="<%=pdo.getValue("g.level_")%>"/>
                    </td>                
                </tr>
                <tr>           
                    <td>
                        Reference <br><input type="text" name="g.reference" size="15" value='<%=pdo.getValue("g.reference")%>'/>
                    </td>             
                    <td>
                        Marker <br><input type="text" name="marker" size="15" value='<%=pdo.getValue("marker")%>'/>
                    </td>                                 
                    <td>
                        Allele name 1 <br><input type="text" name="allele1" size="15" value='<%=pdo.getValue("allele1")%>'/>
                    </td> 
                    <td>
                        Allele name 2 <br><input type="text" name="allele2" size="15" value='<%=pdo.getValue("allele2")%>'/>
                    </td>                          
                    <td>            
                        Identity <br><input type="text" name="identity" size="15" value='<%=pdo.getValue("identity")%>'/>
                    </td>                   
                </tr>
                <tr>  
                    <td>
                        Date from <br><input type="text" name="datefrom" size="15" value='<%=pdo.getValue("datefrom")%>'/>
                    </td>    
                    <td>
                        Date to <br><input type="text" name="dateto" size="15" value='<%=pdo.getValue("dateto")%>'/>
                    </td>            
                    <td>
                        User <br><input type="text" name="usr" size="15" value='<%=pdo.getValue("usr")%>'/>
                    </td>              
                    <td colspan=2 align="right" valign="bottom">  
                        <a href="Controller?workflow=ViewGenotypes&querytype=simple"><m:img name="nav_open" title="Simple form"/></a>
                        <input id="button" type="submit" name="collect.getgenotypesaction.display" value="Display">
                        <input id="button" type="submit" name="reset" value="Reset">
                    </td>
                </tr>     
                <tr>
                    <td colspan="5" align="right">
                        <%=update%>
                    </td>
                </tr>
            </table> 
        </m:query>      
        </form>  
        <hr id="ruler">
        <m:navigation-buttons workflow="ViewGenotypes" showText='true'/>
        <table class="data">
        <tr>
            <th class="data">Marker</th>
            <th class="data">Identity</th>
            <th class="data">Allele 1</th>
            <th class="data">Allele 2</th>
            <th class="data">L</th>
            <th class="data">Reference</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
            <m:hide privilege="GENO_W">
                <th class="data">Edit</th>
            </m:hide>
        </tr>
        <m:iterate-collection collection="genotypesdto">
	 
        <tr class="#?alt#">
            <td><a href="Controller?workflow=ViewMarker&mid=#:getMid#">#:getMarkerName#</a></td>
            <td><a href="Controller?workflow=ViewIndividual&iid=#:getIid#">#:getIdentity#</a></td>
            <td>#:getAllele1#</td>
            <td>#:getAllele2#</td>
            <td>#:getLevel#</td>
            <td>#:getReference#</td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewGenotype&mid=#:getMid#&iid=#:getIid#"><m:img name="view"/></a></td>
            <m:hide privilege="GENO_W">
                <td><a href="Controller?workflow=EditGenotype&mid=#:getMid#&iid=#:getIid#"><m:img name="edit"/></a></td>
            </m:hide>
        </tr>
        
        </m:iterate-collection>
        </table> 
       <m:navigation-buttons workflow="ViewGenotypes"/>
    </body>
</html>
