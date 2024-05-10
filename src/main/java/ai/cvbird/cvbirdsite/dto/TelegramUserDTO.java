package ai.cvbird.cvbirdsite.dto;

import ai.cvbird.cvbirdsite.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUserDTO {

    Long id;

    String email;

    ZonedDateTime registrationDate;

    String telegramId;

    String telegramFirstName;

    Boolean telegramIsBot;

    String telegramUserName;

    String telegramLastName;

    String telegramLanguageCode;

    User cvbirdUserId;
}
