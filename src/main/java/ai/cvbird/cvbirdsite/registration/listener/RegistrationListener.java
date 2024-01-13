package ai.cvbird.cvbirdsite.registration.listener;

import ai.cvbird.cvbirdsite.model.User;
import ai.cvbird.cvbirdsite.registration.OnRegistrationCompleteEvent;
import ai.cvbird.cvbirdsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.subject.verification-token}")
    private String subject;

    @Value("${server.host}")
    private String serverHost;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);

        String recipientAddress = user.getEmail();
        String confirmationUrl = event.getAppURL() + "/regitrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + serverHost + confirmationUrl);
        mailSender.send(email);

        //TODO delete it
        System.out.println(recipientAddress + " : " + subject + " : " + message + "\r\n" + serverHost + confirmationUrl);
    }
}
