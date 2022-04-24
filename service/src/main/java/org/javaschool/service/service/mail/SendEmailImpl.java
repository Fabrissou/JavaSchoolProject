package org.javaschool.service.service.mail;

import org.javaschool.data.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SendEmailImpl implements SendEmail {
    @Autowired
    private MailSender mailSender;

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public void sendMail(String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(getAllEmails());
        msg.setFrom("fabrissou1@gmail.com");
        msg.setSubject(subject);
        msg.setText(text);

        mailSender.send(msg);
    }

    private String[] getAllEmails() {
        List<String> emails = new ArrayList<>();

        postsRepository.findAll().forEach(email -> {
            emails.add(email.getEmployeePost());
        });

        return emails.toArray(new String[(int) postsRepository.count()]);
    }
}
