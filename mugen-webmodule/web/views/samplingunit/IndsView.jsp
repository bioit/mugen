<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>JSP Page</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() {                
                define('bdateto', 'date', 'Birthdate (to)'); 
                define('bdatefrom', 'date', 'Birthdate (from)'); 
            }        
        </script>         
    </head>
    <body onLoad="init()">    
        <h1>Individuals</h1>        
        <form action="Controller" method="post">
        <input type="hidden" name="querytype" value="<%=(String)request.getAttribute("querytype")%>"/>
        <m:query type="simple">
            <table valign="top">
                <tr>
                    <td>Sampling unit <br>
                        <m:su-combobox onChange="this.form.submit()"/>                    
                    </td>               
                    <td>                       
                        Grouping <br><m:checkbox collection="groupingsdto" wildcardOption='true' idGetter="getGsid" textGetter="getName" name="gsid" selected='<%=fdm.getValue("gsid")%>' onChange="this.form.submit()"/>
                        <input id="button" type=submit name="set" value="Set">
                    </td>
                    <td>                       
                        Group <br><m:checkbox collection="groupdto" wildcardOption='true' idGetter="getGid" textGetter="getName" name="gid" selected='<%=fdm.getValue("gid")%>'/> 
                    </td>           
                </tr>
                <tr>    
                    <td colspan="5" align="right">                         
                        <a href="Controller?workflow=ViewIndividuals&querytype=advanced" onclick="this.form.submit()"><m:img name="nav_close" title="Advanced form"/></a>                                            
                        <input id="button" type="submit" name="collect.getindividualsaction.display" value="Display">                       
                        <input id="button" type="submit" name="reset" value="Reset">                        
                    </td>
                </tr>                     
            </table>  
        </m:query>
        <m:query type="advanced">        
            <table valign="top">
                <tr>
                    <td>Sampling unit <br>
                        <m:su-combobox onChange="this.form.submit()"/>                    
                    </td>  
                    <td>                       
                        Grouping <br><m:checkbox collection="groupingsdto" wildcardOption='true' idGetter="getGsid" textGetter="getName" name="gsid" selected='<%=fdm.getValue("gsid")%>' onChange="this.form.submit()"/>
                        <input id="button" type=submit name="set" value="Set">
                    </td>
                    <td>                       
                        Group <br><m:checkbox collection="groupdto" wildcardOption='true' idGetter="getGid" textGetter="getName" name="gid" selected='<%=fdm.getValue("gid")%>'/> 
                    </td>                       
                    <td>
                        Sex <br><m:sex value='<%=fdm.getValue("sex")%>' wildcard="true"/>
                    </td> 
                    <td>
                        Status <br><m:status value='<%=fdm.getValue("status")%>'/>
                    </td>                            
                </tr>                
                <tr>
                    <td>            
                        Identity <br><input type="text" name="identity" size="15" value='<%=fdm.getValue("identity")%>'/>
                    </td>            
                    <td>
                        Birthdate (from) <br><input type="text" name="bdatefrom" size="15" value='<%=fdm.getValue("bdatefrom")%>'/>
                    </td>                 
                    <td>
                        Birthdate (to) <br><input type="text" name="bdateto" size="15" value='<%=fdm.getValue("bdateto")%>'/>
                    </td> 
                    <td>
                        Father <br><input type="text" name="father" size="15" value='<%=fdm.getValue("father")%>'/>
                    </td>              
                    <td>
                        Mother <br><input type="text" name="mother" size="15" value='<%=fdm.getValue("mother")%>'/>
                    </td>    
                </tr>
                <tr>
                    <td>
                        Alias <br><input type="text" name="alias" size="15" value='<%=fdm.getValue("alias")%>'/>
                    </td>              
                    <td colspan=4 align="right"> 
                        <a href="Controller?workflow=ViewIndividuals&querytype=simple" onclick="this.form.submit()"><m:img name="nav_open" title="Simple form"/></a>                                            
                        <input id="button" type="submit" name="collect.getindividualsaction.display" value="Display">
                        <input id="button" type="submit" name="reset" value="Reset">
                        <a href="Controller?workflow=ExportIndividualsView" target="new"><m:img name="export" title="Export this view to csv file"/></a>
                    </td>
                </tr>                                
            </table>    
        </m:query>
        </form>       
        <hr id="ruler">
        <m:navigation-buttons workflow="ViewIndividuals" showText='true'/>        
        <table class="data">
        <tr>
            <th class="data">Identity</th>
            <th class="data">Alias</th>
            <th class="data">Sex</th>
            <th class="data">Birthdate</th>
            <th class="data">Father</th>
            <th class="data">Mother</th>
            <th class="data">User</th>
            <th class="data">Updated</th>
            <th class="data">Details</th>
        </tr>
        <m:iterate-collection collection="individuals">
        <tr class="#?alt#">
            <td>#:getIdentity#</td>
            <td>#:getAlias#</td>
            <td>#:getSex#</td>
            <td>#:getBirthDate#</td>
            <td><a href="Controller?workflow=ViewIndividual&iid=#:getFather.getIid#">#:getFather.getName#</a></td>
            <td><a href="Controller?workflow=ViewIndividual&iid=#:getMother.getIid#">#:getMother.getName#</a></td>
            <td>#:getUser#</td>
            <td>#:getUpdated#</td>
            <td><a href="Controller?workflow=ViewIndividual&iid=#:getIid#"><m:img name="view" title="View detailed information on this individual"/></a></td>
        </tr>
        
        </m:iterate-collection>
        </table> 
        
       <m:navigation-buttons workflow="ViewIndividuals"/> 
    </body>
</html>
