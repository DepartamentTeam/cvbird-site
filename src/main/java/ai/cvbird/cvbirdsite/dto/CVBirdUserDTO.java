package ai.cvbird.cvbirdsite.dto;

import ai.cvbird.cvbirdsite.validator.UniqueEmail;
import ai.cvbird.cvbirdsite.validator.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CVBirdUserDTO {

    Long cvBirdUserId;

    @UniqueEmail
    @ValidEmail(message="Please provide a valid email address")
    private String email;

    private String password;

    private String confirmPassword;

    ZonedDateTime registrationDate;

    String telegramId;

    String telegramFirstName;

    Boolean telegramIsBot;

    String telegramUserName;

    String telegramLastName;

    String telegramLanguageCode;
}
