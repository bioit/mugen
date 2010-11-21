<%@
page import="java.io.*, com.lowagie.text.*, com.lowagie.text.pdf.*"
%><%
    //com.arexis.form.FormDataManager fdm = (com.arexis.form.FormDataManager)request.getAttribute("formdata");

    //com.arexis.mugen.model.modelmanager.ExpModelDTO dto = (com.arexis.mugen.model.modelmanager.ExpModelDTO)request.getAttribute("modeldto");
    
    //com.arexis.mugen.model.modelmanager.StrainDTO sdto = (com.arexis.mugen.model.modelmanager.StrainDTO)request.getAttribute("straindto");
    
    response.setContentType( "application/pdf" );
    
    // step 1: creation of a document-object
    Document document = new Document();

    // step 2:
    // we create a writer that listens to the document
    // and directs a PDF-stream to a temporary buffer

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PdfWriter.getInstance( document, buffer );

    // step 3: we open the document
    document.open();
    
    //create a new paragraph
    //Paragraph p1 = new Paragraph(dto.getLineName());
    //Paragraph p2 = new Paragraph(brake);
    //document.add(p1);
    //document.add(p2);

    // step 4: we add a paragraph to the document
    document.add(new Paragraph("good test"));

    // step 5: we close the document
    document.close();

    // step 6: we output the writer as bytes to the response output
    
    DataOutput output = new DataOutputStream( response.getOutputStream() );
    byte[] bytes = buffer.toByteArray();
    
    response.setContentLength(bytes.length);
    for( int i = 0; i < bytes.length; i++ ) { output.writeByte( bytes[i] ); }
%>

