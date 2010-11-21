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
<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");         
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Create phenotype</title>
        <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('value', 'string', 'Value', 1, 20);                
                define('date', 'date', 'Date'); 
                define('reference', 'string', 'Reference', null, 32); 
                define('comm', 'string', 'Comment', null, 256); 
            }        
        </script> 
    </head>
    <body onLoad="init()">
        <h1>Create Phenotype</h1>
        <form action="Controller" method="post">
            <p>
                <table>     
                    <tr>
                        <td>Sampling unit <br>
                            <m:su-combobox onChange="this.form.submit()"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Variable <br>
                            <m:checkbox collection="variablesdto" name="vid" idGetter="getVid" textGetter="getName" selected='<%=fdm.getValue("variable")%>'/>
                        </td>
                    </tr>
                    <tr>                                      
                        <td>
                            Identity <br>
                            <m:checkbox collection="individualsdto" name="iid" idGetter="getEid" textGetter="getIdentity" selected='<%=fdm.getValue("identity")%>' />
                        </td>
                    </tr>  
                    <tr>
                        <td>Value</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="value" size="35" value='<%=fdm.getValue("value")%>'/></td>
                    </tr>
                    <tr>
                        <td>Date</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="date" size="35" value='<%=fdm.getValue("date")%>'/></td>
                    </tr>    
                    <tr>
                        <td>Reference</td>
                    </tr>               
                    <tr>
                        <td><input type="text" name="reference" size="35" value='<%=fdm.getValue("reference")%>'/></td>
                    </tr>                
                    <tr>
                        <td>Comment</td>
                    </tr>
                    <tr>
                        <td><textarea type="text" cols="35" rows="6" name="comm"><%=fdm.getValue("comm")%></textarea></td>
                    </tr>                    
                </table>
            </p>
            <p>
                <m:save saveName="new" cancelName="back" onClick="validate();return returnVal;"/>
            </p>
        </form> 
    </body>
</html>
