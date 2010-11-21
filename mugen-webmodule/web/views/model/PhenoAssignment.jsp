<%
    com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");
    com.arexis.form.FormDataManager fdmEID = (com.arexis.form.FormDataManager)request.getAttribute("formdataEID");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>assign mp term</title>
    </head>
    <body class="content">
        <h1>assign mp term</h1>
        <p class="toolbar">&nbsp;</p>
        <form action="Controller" method="post">
                <table>
                    <tr>
                        <td>
                            <m:checkbox collection="mptermslevelone" emptyOption="true" onChange="this.form.submit()" name="levelOne" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelOne")%>"/>
                        </td>
                        
                            <% if(request.getAttribute("mptermsleveltwo")!=null && ((String)request.getAttribute("is_level_two_collection_empty")).compareTo("1")!=0){%>
                            <td>
                            <m:checkbox collection="mptermsleveltwo" emptyOption="true" onChange="this.form.submit()" name="levelTwo" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelTwo")%>"/>
                            </td>
                            <%}%>
                            
                            <% if(request.getAttribute("mptermslevelthree")!=null && ((String)request.getAttribute("is_level_three_collection_empty")).compareTo("1")!=0){%>
                            <td>
                            <m:checkbox collection="mptermslevelthree" emptyOption="true" onChange="this.form.submit()" name="levelThree" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelThree")%>"/>
                            </td>
                            <%}%>
                            
                            <% if(request.getAttribute("mptermslevelfour")!=null && ((String)request.getAttribute("is_level_four_collection_empty")).compareTo("1")!=0){%>
                            <%-- if(((String)request.getAttribute("is_level_three_empty")).compareTo("0")!=0){--%>
                            <td>
                            <m:checkbox collection="mptermslevelfour" emptyOption="true" onChange="this.form.submit()" name="levelFour" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelFour")%>"/>
                            </td>
                            <%}%>
                            
                            <% if(request.getAttribute("mptermslevelfive")!=null && ((String)request.getAttribute("is_level_five_collection_empty")).compareTo("1")!=0){%>
                            <td>
                            <m:checkbox collection="mptermslevelfive" emptyOption="true" onChange="this.form.submit()" name="levelFive" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelFive")%>"/>
                            </td>
                            <%}%>
                            
                            <% if(request.getAttribute("mptermslevelsix")!=null && ((String)request.getAttribute("is_level_six_collection_empty")).compareTo("1")!=0){%>
                            <td>
                            <m:checkbox collection="mptermslevelsix" emptyOption="true" onChange="this.form.submit()" name="levelSix" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelSix")%>"/>
                            </td>
                            <%}%>
                            
                            <% if(request.getAttribute("mptermslevelseven")!=null && ((String)request.getAttribute("is_level_seven_collection_empty")).compareTo("1")!=0){%>
                            <td>
                            <m:checkbox collection="mptermslevelseven" emptyOption="true" onChange="this.form.submit()" name="levelSeven" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelSeven")%>"/>
                            </td>
                            <%}%>
                            
                            <% if(request.getAttribute("mptermsleveleight")!=null && ((String)request.getAttribute("is_level_eight_collection_empty")).compareTo("1")!=0){%>
                            <td>
                            <m:checkbox collection="mptermsleveleight" emptyOption="true" onChange="this.form.submit()" name="levelEight" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelEight")%>"/>
                            </td>
                            <%}%>
                            
                            <% if(request.getAttribute("mptermslevelnine")!=null && ((String)request.getAttribute("is_level_nine_collection_empty")).compareTo("1")!=0){%>
                            <td>
                            <m:checkbox collection="mptermslevelnine" emptyOption="true" name="levelNine" idGetter="getPhenoId" textGetter="getPhenoName" selected="<%=fdm.getValue("levelNine")%>"/>
                            </td>
                            <%}%>
                    </tr>
                </table>
                <br>    
                <table class="block_data">
                    <tr>
                        <th class="data2" width="95%">Assigned MP Term(s)</th>
                        <th class="data2" width="5%">&nbsp;</th>
                    </tr>
                    <m:iterate-collection collection="phenoPaths"> 
                    <tr class="#?alt#">
                        <td>#:getPhenoPath#</td>
                        <td>
                            <a class="menu" href="Controller?workflow=PhenoUnAssignViaPhenoAssign&eid=<%=fdmEID.getValue("eid")%>&mp01=#:getPhenoId00#&mp02=#:getPhenoId01#&mp03=#:getPhenoId02#&mp04=#:getPhenoId03#&mp05=#:getPhenoId04#&mp06=#:getPhenoId05#&mp07=#:getPhenoId06#&mp08=#:getPhenoId07#&mp09=#:getPhenoId08#" onClick="return confirm('unassign mp term?')" title="unassign mp term">&nbsp;[unassign]</a>
                        </td>
                    </tr>
                    </m:iterate-collection>
                </table>
                <br>
                <p class="toolbar">
                    <input type="image" src="images/icons/assign.png" name="ass" value="Assign" title="assign mp term">
                    <a href="Controller?workflow=ViewModel&eid=<%=fdmEID.getValue("eid")%>" title="cancel"><img src="images/icons/nav_left_blue_24.png"></a>
                </p>
        </form> 
    </body>
</html>
 