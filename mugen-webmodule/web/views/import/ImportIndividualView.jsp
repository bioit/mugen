<%--
    Mugen JSP View.
    
--%>

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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html">
        <%@ taglib uri="/WEB-INF/lib/ArxTags.jar" prefix="m" %>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <title>JSP Page</title>
    </head>
    <body>

    <h1>Import Individuals and groupings</h1>
    <h2>Properties</h2>
    <form action="Controller" method="post" enctype="multipart/form-data">
        File:<br>
        <input type="file" name="file"><br>
        
        Sampling unit:<br>
        <m:su-combobox name="suid"/>
        <br>
        
        
        Import mode:<br>
        <select name="mode">
            <option value="create">Create</option>
            <option value="update">Update</option>
            <option value="create_or_update">Create Or Update</option>
        </select>
        <br>
        
        Comment:<br>
        <textarea name="comment"></textarea>
        <br>
        
        Test import only:</br>
        <select name="test">
            <option value="true">Yes</option>
            <option selected value="false">No</option>
        </select>
        <br>
        
        <input type="submit">
        
    </form>
    </body>
</html>
