package ai.cvbird.cvbirdsite.registration;

import ai.cvbird.cvbirdsite.model.TelegramUser;
import ai.cvbird.cvbirdsite.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnTelegramRegistrationCompleteEvent extends ApplicationEvent{
    private String email;
    private String password;

    public OnTelegramRegistrationCompleteEvent(String email, String password) {
        super(email);

        this.password = password;
        this.email = email;
    }
}
