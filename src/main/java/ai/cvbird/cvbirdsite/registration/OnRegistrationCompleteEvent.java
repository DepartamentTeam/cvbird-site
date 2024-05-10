package ai.cvbird.cvbirdsite.registration;

import ai.cvbird.cvbirdsite.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private Locale locale;
    private String appURL;


    public OnRegistrationCompleteEvent( User user, Locale locale, String appURL) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.appURL =appURL;
    }

}
