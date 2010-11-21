<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="ind" scope="request" class="com.arexis.mugen.samplingunit.samplingunitmanager.IndividualDTO" />
<%@ page import="java.util.Collection" %>
<%
    com.arexis.mugen.MugenCaller caller = (com.arexis.mugen.MugenCaller)request.getSession().getAttribute("caller");
    String suidName = caller.getSuidName();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Individual</title>
    </head>
    <body>
        
        
        <m:window title="Individual information" name="su.ind" workflow="ViewIndividual" state="expanded">
            <menu>
                <m:hide privilege="IND_W"> 
                    <a href="Controller?workflow=EditIndividual&iid=<jsp:getProperty name="ind" property="iid"/>"><m:img name="edit" title="Edit this individual"/></a>
                    <a href="Controller?workflow=RemoveIndividual&iid=<jsp:getProperty name="ind" property="iid"/>" onClick="return confirm('Remove sampling unit?')"><m:img name="delete" title="Remove this individual"/></a>
                </m:hide>
            </menu>
            <body>
                <table class="block">
                    
                    <tr class="block">
                        <td class="block">
                            <b>Identity</b><br> <jsp:getProperty name="ind" property="identity"/>
                        </td>
                        <td class="block">
                            <b>Alias</b><br> <jsp:getProperty name="ind" property="alias"/>
                        </td> 
                        
                        <td class="block">
                            <b>Status</b><br><jsp:getProperty name="ind" property="status"/>
                        </td>                         
                    </tr>
                    
                    <tr class="block">
                        <td class="block">
                            <b>Birth date</b><br> <jsp:getProperty name="ind" property="birthDate"/>
                        </td>
                        <td class="block">
                            <b>Sex</b><br> <jsp:getProperty name="ind" property="sex"/>
                        </td>                        
                    </tr>
                    
                    <tr class="block">
                        <td class="block">
                            <b>Father</b><br> <jsp:getProperty name="ind" property="fatherName"/>
                        </td>
                        <td class="block">
                            <b>Mother</b><br> <jsp:getProperty name="ind" property="motherName"/>
                        </td> 
                        
                    </tr>
                    
                    <tr class="block">
                        <td class="block" colspan="3">
                            <b>Comment</b><br><jsp:getProperty name="ind" property="comm"/>
                        </td>            
                    </tr>      
                    
                    <tr class="block">
                        <td class="block">
                            <b>User</b><br> <jsp:getProperty name="ind" property="user"/>
                        </td>          
                        <td class="block">
                            <b>Last updated</b><br><jsp:getProperty name="ind" property="updated"/>
                        </td>        
                    </tr>
                </table>
            </body>
            
        </m:window>
        
        
        
    
        <br>
        
        <m:window title="History" name="su.ind.history" workflow="ViewIndividual">
            <menu>
                
            </menu>
            <body>
                <table class="block_data">                     
                    <tr>
                        <th colspan="10" class="data" align="center">Current data</th>
                    </tr>
                    <tr>
                        <th class="data">Identity</th>
                        <th class="data">Alias</th>
                        <th class="data">Father</th>
                        <th class="data">Mother</th>
                        <th class="data">Sex</th>
                        <th class="data">Birthdate</th>
                        <th class="data">Status</th>
                        <th class="data">Comment</th>
                        <th class="data">User</th>                        
                        <th class="data">Last updated</th>
                    </tr>
                    <tr>
                        <td><jsp:getProperty name="ind" property="identity"/></td>
                        <td><jsp:getProperty name="ind" property="alias"/></td>
                        <td><jsp:getProperty name="ind" property="fatherName"/></td>
                        <td><jsp:getProperty name="ind" property="motherName"/></td>
                        <td><jsp:getProperty name="ind" property="sex"/></td>
                        <td><jsp:getProperty name="ind" property="birthDate"/></td>
                        <td><jsp:getProperty name="ind" property="status"/></td>
                        <td><jsp:getProperty name="ind" property="comm"/></td>                        
                        <td><jsp:getProperty name="ind" property="user"/></td>
                        <td><jsp:getProperty name="ind" property="updated"/></td>                        
                    </tr>
                    <tr>
                        <th colspan="10" class="data" align="center">History</th>
                    </tr>                    
                    <m:iterate-collection collection="history">
                        <tr class="#?alt#">
                            <td>#:getIdentity#</td>
                            <td>#:getAlias#</td>
                            <td>#:getFatherName#</td>
                            <td>#:getMotherName#</td>
                            <td>#:getSex#</td>
                            <td>#:getBirthDate#</td>
                            <td>#:getStatus#</td>
                            <td>#:getComm#</td>                            
                            <td>#:getUser#</td>
                            <td>#:getUpdated#</td>                            
                        </tr>
                    </m:iterate-collection>                  
                </table>
            </body>
        </m:window>
     
        <br>
        <form action="Controller">
            <m:back/>
        </form>   
    </body>
</html>
