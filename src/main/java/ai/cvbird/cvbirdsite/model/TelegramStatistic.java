package ai.cvbird.cvbirdsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Entity
@Table(name = "telegram_statistic_data", indexes = {
        @Index(name = "idx__telegram_statistic_data__telegram_id", columnList = "telegram_id"),
        @Index(name = "idx__telegram_statistic_data__id", columnList = "id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramStatistic {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "registration_date", updatable = false)
    ZonedDateTime registrationDate;

    @Column(name = "telegram_id", updatable = false)
    String telegramId;

    @Column(name = "telegram_first_name", updatable = false)
    String telegramFirstName;

    @Column(name = "telegram_is_bot", updatable = false)
    Boolean telegramIsBot;

    @Column(name = "telegram_user_name", updatable = false)
    String telegramUserName;

    @Column(name = "telegram_last_name", updatable = false)
    String telegramLastName;

    @Column(name = "telegram_language_code", updatable = false)
    String telegramLanguageCode;
}
