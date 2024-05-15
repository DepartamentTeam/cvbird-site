package ai.cvbird.cvbirdsite.dto;

import ai.cvbird.cvbirdsite.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelegramStatisticDTO {

    Long id;

    ZonedDateTime registrationDate;

    String telegramId;

    String telegramFirstName;

    Boolean telegramIsBot;

    String telegramUserName;

    String telegramLastName;

    String telegramLanguageCode;
}
