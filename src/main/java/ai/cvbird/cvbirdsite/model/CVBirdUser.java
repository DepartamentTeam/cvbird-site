package ai.cvbird.cvbirdsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Entity
@Table(name = "cvbird_user", indexes = {
        @Index(name = "idx__cvbird_user__email", columnList = "email"),
        @Index(name = "idx__cvbird_user__cvbird_user_id", columnList = "cvbird_user_id"),
        @Index(name = "idx__cvbird_user__telegram_id", columnList = "telegram_id")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CVBirdUser {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cvbird_user_id")
    Long cvBirdUserId;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "registration_date")
    ZonedDateTime registrationDate;

    @Column(name = "telegram_id", unique = true)
    String telegramId;

    @Column(name = "telegram_first_name")
    String telegramFirstName;

    @Column(name = "telegram_is_bot")
    Boolean telegramIsBot;

    @Column(name = "telegram_user_name")
    String telegramUserName;

    @Column(name = "telegram_last_name")
    String telegramLastName;

    @Column(name = "telegram_language_code")
    String telegramLanguageCode;

    public CVBirdUser() {
        super();
        this.enabled=false;
    }
}
