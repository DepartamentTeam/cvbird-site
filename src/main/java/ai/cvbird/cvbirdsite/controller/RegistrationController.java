package ai.cvbird.cvbirdsite.controller;

import ai.cvbird.cvbirdsite.model.User;
import ai.cvbird.cvbirdsite.model.VerificationToken;
import ai.cvbird.cvbirdsite.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

@Controller
public class RegistrationController {
    //TODO
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

    public RegistrationController() {
        super();
    }

    @GetMapping("/registration_confirm")
    public ResponseEntity<String> confirmRegistration
            (HttpServletRequest request, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            String invalidMessage = "Invalid token";
            //String message = messages.getMessage("auth.message.invalidToken", null, locale);
            return ResponseEntity.ok(invalidMessage);
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            //String messageValue = messages.getMessage("auth.message.expired", null, locale);
            String expiredMessage = "The token has expired";
            return ResponseEntity.ok(expiredMessage);
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);

        //Redirect
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/signin"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
