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
<jsp:useBean id="variabledto" scope="request" type="com.arexis.mugen.phenotype.phenotypemanager.VariableDTO" />
<%@ page import="java.util.Collection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Variable</title>
    </head>
    <body>
        <h1>View Variable</h1>
        
        
        
        <form action="Controller" method="post">
        
            <table class="block">
                <tr class="block">
                    <th class="block" colspan="3">
                        <m:hide privilege="VAR_W">
                            <a href="Controller?workflow=EditVariable&vid=<jsp:getProperty name="variabledto" property="vid"/>"><m:img name="edit"/></a>
                            <a href="Controller?workflow=RemoveVariable&vid=<jsp:getProperty name="variabledto" property="vid"/>" title="Delete this model" onClick="return confirm('Remove this variable?')"><m:img name="delete"/></a>
                        </m:hide>                        
                        &nbsp;
                        <a href="Controller?workflow=ViewVariable&vid=<jsp:getProperty name="variabledto" property="vid"/>&variable.history=true" title="Expand/Collapse history information"><m:img name="history"/></a>
                    </th>
                </tr>
                <tr>
                    <td>
                        <b>Name</b><br>
                        <jsp:getProperty name="variabledto" property="name"/>
                    </td>
                    <td>
                        <b>Unit</b><br>
                        <jsp:getProperty name="variabledto" property="unit"/>
                    </td>
                    <td>
                        <b>Type</b><br>
                        <jsp:getProperty name="variabledto" property="type"/>
                    </td>
                </tr>
                <tr>
                    <td colspan=2>
                        <b>Comment</b><br>
                        <jsp:getProperty name="variabledto" property="comm"/>
                    </td>
                    <td>
                        <b>Species</b><br>
                        <jsp:getProperty name="variabledto" property="speciesname"/>
                    </td>
                </tr>
                <tr>
                    <td colspan=3>
                        <m:hide-block name="variable.history">
                        <table class="data">  
                            <tr>
                                <th colspan="6" class="data" align="center">Current data</th>
                            </tr>
                            <tr>                  
                                <th class="data">Name</th>
                                <th class="data">Type</th>
                                <th class="data">Unit</th>
                                <th class="data">Comment</th>                        
                                <th class="data">User</th>
                                <th class="data">Last updated</th>
                            </tr>
                            <tr>
                                <td><jsp:getProperty name="variabledto" property="name"/></td>
                                <td><jsp:getProperty name="variabledto" property="type"/></td>
                                <td><jsp:getProperty name="variabledto" property="unit"/></td>
                                <td><jsp:getProperty name="variabledto" property="comm"/></td>
                                <td><jsp:getProperty name="variabledto" property="user"/></td>
                                <td><jsp:getProperty name="variabledto" property="updated"/></td>                     
                            </tr>
                            <tr>
                                <th colspan="6" class="data" align="center">History</th>
                            </tr>                
                            <m:iterate-collection collection="history">
                                <tr class="#?alt#">
                                    <td>#:getName#</td>
                                    <td>#:getType#</td>
                                    <td>#:getUnit#</td>
                                    <td>#:getComm#</td>
                                    <td>#:getUser#</td>
                                    <td>#:getUpdated#</td>
                                </tr>                                            
                            </m:iterate-collection>                     
                        </table>
                        </m:hide-block>
                    </td>
                </tr>
            </table>
            <p>     
                <m:back/>
            </p>
        </form> 
    </body>
</html>
