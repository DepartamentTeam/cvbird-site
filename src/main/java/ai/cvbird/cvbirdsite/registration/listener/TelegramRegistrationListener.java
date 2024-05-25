package ai.cvbird.cvbirdsite.registration.listener;

import ai.cvbird.cvbirdsite.model.TelegramUser;
import ai.cvbird.cvbirdsite.model.User;
import ai.cvbird.cvbirdsite.registration.OnTelegramRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class TelegramRegistrationListener implements ApplicationListener<OnTelegramRegistrationCompleteEvent> {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.subject.password-telegram-registration}")
    private String subject;

    @Value("${server.host}")
    private String serverHost;

    @Value("${mail.sender}")
    private String sender;

    @Override
    public void onApplicationEvent(OnTelegramRegistrationCompleteEvent event) {
        String recipientAddress = event.getEmail();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setFrom(sender);
        email.setSubject(subject);
        email.setText("Hello. Your password : " + event.getPassword());
        mailSender.send(email);

        //TODO delete it
        System.out.println(recipientAddress + " : " + subject + " : " +  event.getPassword() );
    }

}
