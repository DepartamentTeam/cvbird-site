package ai.cvbird.cvbirdsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/* Simple JavaBean object that represent a VerificationToken
 * @since 13.01.2024
 * @author Konstantin Emelianov
 */
@Entity
@Table(name = "verification_token", indexes = {
        @Index(name = "idx__verification_token__id", columnList = "id"),
        @Index(name = "idx__verification_token__verification_token", columnList = "verification_token"),
        @Index(name = "idx__verification_token__expiry_date", columnList = "expiry_date")

})
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "verification_token", nullable = false)
    private String verificationToken;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificationToken(final String verificationToken, final User user) {
        super();

        this.verificationToken = verificationToken;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
