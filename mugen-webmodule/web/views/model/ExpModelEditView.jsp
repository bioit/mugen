<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="modeldto" scope="request" type="com.arexis.mugen.model.modelmanager.ExpModelDTO" />
<%
    com.arexis.mugen.model.modelmanager.ExpModelDTO model = (com.arexis.mugen.model.modelmanager.ExpModelDTO)request.getAttribute("modeldto");
    String userId = ""+model.getContactId();
    String raid = ""+model.getResearchAppId();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>edit mugen mouse</title>
       <script language="JavaScript" src="javascripts/validate.js"></script>
        <script language="JavaScript">
            function init() { 
                define('alias', 'string', 'Line name', 1, 255);                
                define('comm', 'string', 'Authors comments', null, 255);                
                define('researchAppsText', 'string', 'Research Application Comments', null, 255); 
            }        
        </script>
    </head>
    <body onLoad="init()" class="content">
        <h1>edit mugen mouse</h1>
        <p class="toolbar">
        <a href="Controller?workflow=EditModel&model.help=true"><m:img name="info_24" title="help & info"/></a>
        </p>
        <form action="Controller" method="post">
                <%--info table--%>
                <m:hide-block name="model.help">
                <table>    
                    <tr>
                        <td>
                            <table class="info">
                                <td>
                                    <b>Help & Info on this page:</b><br><br>
                                    <b>Line name:</b> On this field the strain's common line name as it was given or last edited appears. You can freely edit it. All special characters, including numbers are allowed.<br>
                                    <b>Research application type:</b> The research application type as it was firstly chosen appears. If you believe that the research application type of this strain has changed or was wrongly entered in the first place you can choose a different research application type.<br>
                                    <b>Research applications comments:</b> If you are not satisfied with the existing research application comments you can edit them.<br>
                                    <b>Contact:</b> Here you should choose the person who is responsible for this strain if different from the person who was considered responsible for it at first.<br>
                                    <b>Authors comments:</b> Should you need to add, remove or edit some of the author's comments please do it on this field.<br>
                                    <b>Saving your data: </b> Once you're done with editing data you can click on the save button to store the new strain data in the MUGEN database. If you are having second thoughts you can always click on the cancel button to return to the previously visited web-page without storing data in the database.<br>
                                </td>
                            </table>
                        </td>
                    </tr>     
                </table>     
                </m:hide-block>
                <table>
                    <tr>                                  
                        <td><b>Line name</b></td>                            
                    </tr>
                    <tr>
                        <td>
                            <input type="text" name="alias" size="35" maxlength="255" value='<jsp:getProperty name="modeldto" property="lineName"/>'/>
                            <input type="hidden" name="geneticBackground" value="n.a." />
                            <input type="hidden" name="availability" value="n.a."/>
                        </td>
                    </tr>
                    <tr>                                  
                        <td><b>Research Application Type</b></td>
                    </tr>                    
                    <tr>
                        <td>
                            <m:checkbox collection="researchApps"  idGetter="getRaid" textGetter="getName" name="raid" selected='<%=modeldto.getResearchAppId()%>'/>
                        </td>
                    </tr>
                    <tr>                                  
                        <td><b>Research Applications Comments</b></td>
                    </tr>                    
                    <tr>
                        <td>
                            <textarea type="text" cols="35" rows="6" maxlength="255" name="researchAppsText"><jsp:getProperty name="modeldto" property="researchAppText"/></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Contact</b></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="users" idGetter="getId" textGetter="getName" name="contactId" selected='<%=modeldto.getContactId()%>'/>
                        </td>
                    </tr>                                         
                    <tr>
                        <td><b>Authors comments</b></td>
                    </tr>                    
                    <tr>
                        <td>
                            <textarea type="text" cols="35" rows="6" name="comm"><jsp:getProperty name="modeldto" property="comm"/></textarea>                            
                        </td>
                    </tr>
                    <m:hide privilege="MODEL_ADM" suid="<%=modeldto.getSuid()%>">
                        <tr>
                            <td><b>Actual Dissemination Level</b></td>
                        </tr>
                        <tr>
                            <td>
                                <m:checkbox collection="levels" idGetter="toString" textGetter="toString" name="level" selected='<%=modeldto.getLevel()%>'/>
                            </td>
                        </tr>
                    </m:hide>
                    <tr>
                        <td><b>Desired Dissemination Level</b></td>
                    </tr>
                    <tr>
                        <td>
                            <m:checkbox collection="levels" idGetter="toString" textGetter="toString" name="desired_level" selected='<%=modeldto.getDesiredLevel()%>'/>
                        </td>
                    </tr>
                    
                    <m:hide privilege="MODEL_MUGEN" suid="<%=modeldto.getSuid()%>">
                        <input type="hidden" name="level" value='<%=modeldto.getLevel()%>' />
                    </m:hide>
                </table>
                <br>
            <p class="toolbar">
                <input type="image" src="images/icons/save.png" name="save" value="Save" onClick="validate();return returnVal;" title="submit changes">
                <input type="image" src="images/icons/cancel.png" name="back" value="Cancel" title="cancel">
            </p>
        </form> 
    </body>
</html>

