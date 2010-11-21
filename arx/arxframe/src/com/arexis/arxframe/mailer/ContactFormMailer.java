package com.arexis.arxframe.mailer;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

public final class ContactFormMailer {
    
    private static Properties fMailServerConfig = new Properties();
    
    public ContactFormMailer() {
    //sendEmail("zouberakis@fleming.com", "mmmdb@mugen-noe.org", "test greeting", "hello mate");
   }

    public void sendEmail(String from, String to, String subject, String message, String who) {
        Session session = Session.getDefaultInstance( fMailServerConfig, null );
        MimeMessage mess = new MimeMessage(session);
        try {
            mess.setFrom(new InternetAddress(from));
            mess.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mess.setSubject(subject);
            mess.setText(message+"\n\n"+who);
            //mess.setText(who);
            Transport.send(mess);
        } catch (MessagingException ex) {
            System.err.println("Cannot send email. " + ex);
        }
    }

    public static void refreshConfig() {
        fMailServerConfig.clear();
        fetchConfig();
    }

    static {
        fetchConfig();
    }

    private static void fetchConfig() {
      fMailServerConfig = System.getProperties();
      fMailServerConfig.put("mail.smtp.host", "mail.mugen-noe.org");
    }
}
