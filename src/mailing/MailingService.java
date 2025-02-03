/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailing;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aitziber
 */
public class MailingService {
    private final String HOST = "localhost";
    private final String PORT = "25";
    private final String SENDER_EMAIL = "farmapp@support.com";
    private final String SENDER_PASSWORD = "";

    private Session session;

    public MailingService() {
        Properties props = new Properties();
        props.setProperty("mail.smtps.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.starttls.enable", "false");
        props.setProperty("mail.smtps.auth", "false");

        session = Session.getInstance(props, null);
    }

    public boolean sendEmail(String recipient) {
        Transport transport = null;
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            msg.setSubject("FarmApp - Password change");
            msg.setText("New password:", "utf-8", "html");
            msg.setSentDate(new Date());

            transport = session.getTransport("smtp");
            transport.connect(HOST, SENDER_EMAIL, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());

            return true;
        } catch (MessagingException e) {
            Logger.getLogger(MailingService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                if (transport != null) transport.close();
            } catch (MessagingException e) {
                Logger.getLogger(MailingService.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
