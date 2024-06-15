package ai.cvbird.cvbirdsite.dto;

import ai.cvbird.cvbirdsite.validator.UniqueEmail;
import ai.cvbird.cvbirdsite.validator.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CVBirdUserResponse {

    Long cvBirdUserId;

    Boolean enabled;

    String email;

    ZonedDateTime registrationDate;

    String telegramId;

    String telegramFirstName;

    Boolean telegramIsBot;

    String telegramUserName;

    String telegramLastName;

    String telegramLanguageCode;
}
