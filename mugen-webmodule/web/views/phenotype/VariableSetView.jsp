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
<jsp:useBean id="variablesetdto" scope="request" type="com.arexis.mugen.phenotype.phenotypemanager.VariableSetDTO" />
<%@ page import="java.util.Collection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>View Variable Set</title>
    </head>
    <body>
        <h1>View Variable Set</h1>
        <form action="Controller" method="post">
            <table class="block">
                <tr class="block">
                    <th class="block" colspan="3">
                        <m:hide privilege="VAR_W">
                            <a href="Controller?workflow=EditVariableSet&vsid=<jsp:getProperty name="variablesetdto" property="vsid"/>"><m:img name="edit"/></a>
                            <a href="Controller?workflow=RemoveVariableSet&vsid=<jsp:getProperty name="variablesetdto" property="vsid"/>" title="Delete this variable set" onClick="return confirm('Remove this variable set?')"><m:img name="delete"/></a>
                        </m:hide>                        
                        &nbsp;
                        <a href="Controller?workflow=ViewVariableSet&vsid=<jsp:getProperty name="variablesetdto" property="vsid"/>&variableset.history=true" title="Expand/Collapse history information"><m:img name="history"/></a>
                    </th>
                </tr>
                <tr>
                    <td>    
                        <b>Name</b><br>
                        <jsp:getProperty name="variablesetdto" property="name"/>
                    </td>
                    <td>    
                        <b>Comment</b><br>
                        <jsp:getProperty name="variablesetdto" property="comm"/>
                    </td>
                    <td>
                        <b>Species</b><br> 
                        <jsp:getProperty name="variablesetdto" property="species"/>
                    </td>
                </tr>
                <tr>
                    <td colspan=3>
                        <m:hide-block name="variableset.history">
                            <table class="data">  
                                <tr>
                                    <th colspan="4" class="data" align="center">Current data</th>
                                </tr>
                                <tr>                  
                                    <th class="data">Value</th>
                                    <th class="data">Comment</th>
                                    <th class="data">User</th>
                                    <th class="data">Last updated</th>
                                </tr>
                                <tr>
                                    <td><jsp:getProperty name="variablesetdto" property="name"/></td>
                                    <td><jsp:getProperty name="variablesetdto" property="comm"/></td>
                                    <td><jsp:getProperty name="variablesetdto" property="user"/></td>
                                    <td><jsp:getProperty name="variablesetdto" property="updated"/></td>                     
                                </tr>
                                <tr>
                                    <th colspan="4" class="data" align="center">History</th>
                                </tr>                    
                                <m:iterate-collection collection="<%=(Collection)request.getAttribute("history")%>">
                                    <tr class="#?alt#">
                                        <td>#:getName#</td>
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
            
            <br>
            
                <table class="block">
                    <tr class="block">
                        <th class="block">
                            <a href="Controller?workflow=ViewVariableSet&vsid=<jsp:getProperty name="variablesetdto" property="vsid"/>&variableset.members=true">Variables</a>
                            &nbsp;
                            <a href="Controller?workflow=ViewVariableSetMembership&vsid=<jsp:getProperty name="variablesetdto" property="vsid"/>"><m:img name="members"/></a>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <m:hide-block name="variableset.members">
                                <table>
                                    <m:iterate-collection collection="members">
                                    <tr>
                                        <td>
                                            #:getName#
                                        </td>
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
