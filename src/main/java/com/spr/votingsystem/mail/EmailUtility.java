package com.spr.votingsystem.mail;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class EmailUtility {
    public static void sendEmail(String host, int port, final String username,
                                 final String password, String toAddr, String subj, String msg) {
        Email email = EmailBuilder.startingBlank()
          .from("From", "sprvotingsystem@disroot.org")
          .to("To", toAddr)
          .withSubject(subj)
          .withPlainText(msg)
          .buildEmail();

        Mailer mailer = MailerBuilder
          .withSMTPServer(host, port, username, password)
          .withTransportStrategy(TransportStrategy.SMTP_TLS)
          .buildMailer();

        mailer.sendMail(email);
    }

    public static String emailConstructor(String type, String content) {
        switch (type) {
            case "pass":
                return "Welcome to the SPR voting system! Please use this temporary password to access your account: " + content;
            case "vote":
                return "Your vote was successfully registered in our system. Please safely store this token for any future inquiries: " + content;
            default:
                return "This is a generic message";
        }
       //TODO email content generation per basis.
    }
}
