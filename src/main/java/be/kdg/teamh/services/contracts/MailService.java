package be.kdg.teamh.services.contracts;

import javax.mail.MessagingException;

public interface MailService {
    void send(String to, String subject, String body) throws MessagingException;
}
