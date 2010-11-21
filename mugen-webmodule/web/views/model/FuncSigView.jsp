<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="funcsig" scope="request" type="com.arexis.mugen.model.modelmanager.FunctionalSignificanceDTO" />   
<%
    com.arexis.mugen.model.modelmanager.FunctionalSignificanceDTO dto = (com.arexis.mugen.model.modelmanager.FunctionalSignificanceDTO)request.getAttribute("funcsig");
    int fileId = dto.getFileId();

    String addFile = "";
    String removeFile = "Remove";
    
    if(fileId == 0) {
        addFile = "Add";
        removeFile = "";   
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <link rel="stylesheet" type="text/css" href="test.css" />
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <title>Edit group</title>   
    </head>
    <body>

    <h1>Functional Significance</h1>
    <table class="info">
        <td>
            This is the detailed view of a Functional Significance entry. You can attach one file to each Functional Significance entry, 
            press <b>Add</b> to add a file or <b>Remove</b> to remove a file (given that one is already attached). The notepad will take you
            to the edit page for this entry and the trashbin will remove the entry.
        </td>
    </table>
    <form action="Controller" method="post">
        <p>
            <a href="Controller?workflow=ViewModels" title="Go to the listing over all models">Models</a>&nbsp>>
            <a href="Controller?workflow=ViewModel" title="Return to the current model">Model</a>&nbsp>>
            Functional Significance   
        </p>
        <table class="block">
            <th class="block" colspan="2">
                <m:hide privilege="MODEL_W" suid="<%=funcsig.getSuid()%>">
                    <a href="Controller?workflow=EditFuncSig&fsid=<jsp:getProperty name="funcsig" property="fsid"/>"><m:img name="edit" title="Edit this functional significance"/></a>
                    <a href="Controller?workflow=RemoveFuncSig&fsid=<jsp:getProperty name="funcsig" property="fsid"/>" onClick="return confirm('Remove functional signifiance?')"><m:img name="delete" title="Remove this functional signifiance"/></a>
                </m:hide>
                &nbsp;
            </th>
            <tr class="block">
                <td class="block">
                    <b>Functional significance name</b><br> <jsp:getProperty name="funcsig" property="name"/>
                </td>
            </tr>
            <tr class="block">
                <td class="block">
                    <b>Functional significance type</b><br> <jsp:getProperty name="funcsig" property="typeName"/>
                </td>
            </tr>           
            <tr class="block">
                <td class="block">
                    <b>Comment</b><br> <jsp:getProperty name="funcsig" property="comm"/>
                </td>            
            </tr>  
            <tr>
                <td class="block">
                    <b>Attached file</b>
                    <m:hide privilege="MODEL_W" suid="<%=funcsig.getSuid()%>">
                    [
                        <a href="Controller?workflow=AddFuncSigFile&fsid=<jsp:getProperty name="funcsig" property="fsid"/>" title="Attach a file to this functional significance"><%=addFile%></a>
                        <a href="Controller?workflow=RemoveFuncSigFile&fsid=<jsp:getProperty name="funcsig" property="fsid"/>" title="Remove the attached file"><%=removeFile%></a>
                    ]
                    </m:hide>
                    <br>
                    <a href="Controller?workflow=ViewFile&fileid=<jsp:getProperty name="funcsig" property="fileId"/>" title="View the attached file"><jsp:getProperty name="funcsig" property="fileName"/></a>
                </td> 
            </tr>
            <tr class="block">
                <td class="block">                
                    <b>User</b><br> <a href="Controller?workflow=ViewUser&id=<jsp:getProperty name="funcsig" property="userId"/>" title="View the details for this user"><jsp:getProperty name="funcsig" property="user"/></a> 
                </td>            
            </tr> 
            <tr class="block">
                <td class="block">
                    <b>Last updated</b><br> <jsp:getProperty name="funcsig" property="ts"/>
                </td>            
            </tr>               
        </table>
    </form>
    </body>
</html>
