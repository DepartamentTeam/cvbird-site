package ai.cvbird.cvbirdsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "telegram_user", indexes = {
        @Index(name = "idx__telegram_user__email", columnList = "email"),
        @Index(name = "idx__telegram_user__id", columnList = "id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramUser {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email", updatable = false)
    String email;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cvbird_user_id", referencedColumnName = "id")
    User cvbirdUser;
}
