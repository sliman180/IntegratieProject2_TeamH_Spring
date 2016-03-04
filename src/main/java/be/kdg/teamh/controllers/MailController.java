package be.kdg.teamh.controllers;

import be.kdg.teamh.services.contracts.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {

    public final static Logger logger = Logger.getLogger(MailController.class);

    @Autowired
    private MailService service;

    @RequestMapping(value = "/send")
    public String send(){
        try {
            service.send("georgy.bagramyan@student.kdg.be ","Sprin Boot Mail TEST","Moussa dit is een test om te zien of ik email kan verzenden vanuit java applicatie.");
            return "Mail send!";
        } catch (MessagingException e) {
            logger.error("***********ERROR SENDINGMAIL: "+e);
            return "Error when sending mail!";
        }
    }
}
